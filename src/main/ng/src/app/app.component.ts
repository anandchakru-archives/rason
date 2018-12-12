import { Component, OnInit, OnDestroy, ViewChild, ElementRef, Renderer2 } from '@angular/core';
import { Subscription, timer, Subject } from 'rxjs';
import { ClipService } from './service/clip.service';
import { RestService } from './service/rest.service';
import { Key } from './model/key';
import { GrowliService } from './growl/growli.service';
import { AlertType } from './model/alert';
import { LoadiComponent } from './load/loadi/loadi.component';
import { Loadi } from './model/loadi';
import { JsonViewComponent } from './json-view/json-view.component';
import { VERSION } from 'src/environments/version';
import { CheckSlugRsp } from './model/checkSlug.rsp';
import { DataPair } from './model/data.pair';
import { DebounceService } from './service/debounce.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, OnDestroy {
  bucket:string='rnd';
  err: string;
  disableBtnClass:string;
  urlClass: string;
  txtClass:string;
  searchNotificationsVal:string;
  urlInputVal: string;
  taInputVal: string='{}';
  inputJson: JSON;
  expanded: boolean;
  copied: boolean = false;
  hideUrl:boolean=true;
  processingJson: boolean = false;
  slugInputModalClass:string='';
  slugInputClass:string='';
  slugInputModalStyle:any={"display": "none"};
  slugs: Key[];
  version = VERSION;
  slugSub:Subject<DataPair>  = new Subject();
  slugSubSubs:Subscription;
  debouncedUpdate: Function;
  processingJsonStyle: any={'width':'0%'}
  processingJsonTimers:any[]=new Array();
  @ViewChild('btnBeautifyJson') btnBeautifyJson: ElementRef;
  @ViewChild('btnUglyJson') btnUglyJson: ElementRef;
  @ViewChild('btnSaveJson') btnSaveJson: ElementRef;
  @ViewChild('btnExpandCollapse') btnExpandCollapse: ElementRef;
  @ViewChild('btnUrl') btnUrl: ElementRef;
  @ViewChild('btnNofication') btnNofication: ElementRef;
  @ViewChild('loadiComponent') loadiComponent: LoadiComponent;
  @ViewChild('appJsonView') appJsonView: JsonViewComponent;

  constructor(private clip: ClipService, 
    private renderer2: Renderer2, 
    private rest: RestService,
    private growliService: GrowliService,
    public debounceService: DebounceService) {
      this.debouncedUpdate = this.debounceService.debounce((input:string) => {
        this.update(input);
      }, 1000);
    }
  ngOnInit(): void {
    this.expanded=true;
    this.update(this.taInputVal);
    this.rest.cacheCount(this.bucket).subscribe((key:Key[])=>{
      this.slugs = key;
    });
    this.slugSubSubs = this.slugSub.subscribe((dp:DataPair)=>{
      this.createJson(dp.val, undefined, dp.key.slug);
    });
  }

  ngOnDestroy(): void {
    this.slugSubSubs.unsubscribe();
  }
  beautifyJson(json: string, event) {
    this.update(json);
    if (this.inputJson) {
      let loadi:Loadi = this.showLoadi('formatting', 25000);
      const content = JSON.stringify(this.inputJson, undefined, 2);
      this.copied = (event && event.shiftKey)?this.clip.copyFromContent(content):false;
      this.renderer2.removeClass(this.btnBeautifyJson.nativeElement, 'btn-primary');
      this.renderer2.addClass(this.btnBeautifyJson.nativeElement, 'btn-success');
      this.taInputVal = content;
      setTimeout(() => {
        this.renderer2.removeClass(this.btnBeautifyJson.nativeElement, 'btn-success');
        this.renderer2.addClass(this.btnBeautifyJson.nativeElement, 'btn-primary');
        if(this.copied){
          this.growliService.addAlert("Copied formatted to clipboard.", AlertType.SUCCESS);
        }
      }, 1000);
      this.hideLoadi(loadi);
    }
  }
  uglifyJson(json: string, event) {
    this.update(json);
    if (this.inputJson) {
      let loadi:Loadi = this.showLoadi('compressing', 25000);
      const content = JSON.stringify(this.inputJson);
      this.copied = (event && event.shiftKey)?this.clip.copyFromContent(content):false;
      this.renderer2.removeClass(this.btnUglyJson.nativeElement, 'btn-primary');
      this.renderer2.addClass(this.btnUglyJson.nativeElement, 'btn-success');
      this.taInputVal = content;
      setTimeout(() => {
        this.renderer2.removeClass(this.btnUglyJson.nativeElement, 'btn-success');
        this.renderer2.addClass(this.btnUglyJson.nativeElement, 'btn-primary');
        if(this.copied){
          this.growliService.addAlert("Copied compressed to clipboard.", AlertType.SUCCESS);
        }
      }, 1000);
      this.hideLoadi(loadi);
    }
  }
  expCollapse() {
    this.btnExpandCollapse.nativeElement.disabled=true;
    if (this.expanded) {
      this.expanded = false;
    } else {
      this.expanded = true;
    }
    setTimeout(() => {
      this.btnExpandCollapse.nativeElement.disabled=false;
    }, 0);
  }
  private updateUrl(url:string){
    if(!url || url.length==0 || url===this.urlInputVal){
      return;
    }
    this.urlInputVal = url;
    this.taInputVal = '{}';
    this.update(this.taInputVal);
    this.urlClass='';
    let loadi:Loadi = this.showLoadi('fetching', 25000);
    this.rest.fetchJson(url).subscribe((rsp:any)=>{
      let anyy = JSON.stringify(rsp);
      if(anyy && anyy.length){
        this.taInputVal = anyy;
        this.update(anyy);
        this.urlClass = '';
      }else{
        this.urlClass = 'border-danger';  
      }
      this.hideLoadi(loadi);
    },(error) => {
      this.urlClass = 'border-danger';
      this.hideLoadi(loadi);
    });
  }
  private update(input: string) {
    if (!input || !input.length) {
      this.inputJson = JSON;
      this.disableBtnClass='disabled';
      this.txtClass='border-danger';
    }else{
      try {
        this.err = undefined;
        this.inputJson = JSON.parse(input);
        this.disableBtnClass='';
        this.txtClass='';
      } catch (e) {
        this.err = e.message;
        this.inputJson = JSON;
        this.disableBtnClass='disabled';
        this.txtClass='border-danger';
      }
    }
    setTimeout(()=>{this.resetProgress();}, 200);
  }
  showProcessing(){
    this.resetProgress();
    this.processingJsonStyle = {'width':'0%'};
    let width = 0;
    this.processingJsonTimers.push(setInterval(()=>{
      this.processingJsonStyle = {'width': (width+=5) + '%'};
      if(width>100){
        width=0;
      }
    },200));

    this.processingJson = true;
    setTimeout(()=>{this.resetProgress();}, 25000);
  }
  private resetProgress() {
    this.processingJsonStyle = { 'width': '100%' };
    this.processingJson = false;
    this.processingJsonTimers.forEach((timer) => {
      clearInterval(timer);
    });
  }

  checkSlug(slug:string){
    if(slug && slug.length>4){
      this.rest.checkSlug(this.bucket,slug).subscribe((rsp:CheckSlugRsp)=>{
        if(rsp.exists){
          this.slugInputClass='border-danger';
        }else{
          this.slugInputClass='border-success';
        }
      });
    }
  }
  saveJson(slug:string, json:string){
    this.slugInputClass='';
    const key:Key = new Key();
    key.slug = slug;
    const dp:DataPair = new DataPair();
    dp.key=key;
    dp.val = json;
    this.slugSub.next(dp);
  }
  createJson(json: string, event, slug?:string){
    if(event && event.shiftKey){
      this.slugInputModalClass='show';
      this.slugInputModalStyle={"display": "block"};
      return;
    }
    if(!json || json.length<8){
      this.growliService.addAlert("Expecting a minimum of 8 characters long for JSON.", AlertType.WARNING);
      this.resetBtnSaveJson();
      this.hideSlugInputModal();
      return;
    }
    this.renderer2.addClass(this.btnSaveJson.nativeElement, 'btn-warning');
    this.renderer2.addClass(this.btnSaveJson.nativeElement, 'disabled');
    setTimeout(() => {
      this.resetBtnSaveJson();
    }, 2000);
    this.update(json);
    this.rest.create(this.bucket, json, slug).subscribe((key:Key)=>{
      this.growliService.addAlert("Saved @" + key.slug, AlertType.SUCCESS);
      this.slugs.push(key);
      this.resetBtnSaveJson();
      this.hideSlugInputModal();
    });
  }
  hideSlugInputModal(){
    this.slugInputModalClass='';
    this.slugInputModalStyle={"display": "none"};
  }
  resetBtnSaveJson(){
    this.renderer2.removeClass(this.btnSaveJson.nativeElement, 'btn-warning');
    this.renderer2.removeClass(this.btnSaveJson.nativeElement, 'disabled');
    this.renderer2.addClass(this.btnSaveJson.nativeElement, 'btn-info');
  }
  toggleHideUrl(){
    this.hideUrl=!this.hideUrl;
    if(!this.hideUrl && (!this.urlInputVal || !this.urlInputVal.length) && (!this.taInputVal || this.taInputVal.length==0 || this.taInputVal==='{}')){
      this.updateUrl("https://api.github.com/repos/anandchakru/rason");
    }
  }
  showLoadi(msg?:string, timeout?:number, timeoutMsg?: string): Loadi{
    if(!msg || !msg.length)msg = "working";
    if(!timeout)timeout=10000;
    if(!timeoutMsg || !timeoutMsg.length)timeoutMsg = "tired of waiting, giving up on " + msg;

    const loadi = new Loadi();
    loadi.msg= msg;
    loadi.timeout= timeout;
    loadi.timeoutMsg= timeoutMsg;
    loadi.id = this.loadiComponent.loadis.push(loadi)-1;
    timer(timeout).subscribe(()=>{
      if(this.hideLoadi(loadi)>=0){
        this.growliService.addAlert(loadi.timeoutMsg, AlertType.DANGER);
      }
    });
    return loadi;
  }
  hideLoadi(loadi:Loadi):number{
    const loadiIndex = this.loadiComponent.loadis.indexOf(loadi);
    this.loadiComponent.loadis.splice(loadiIndex, 1);
    return loadiIndex;
  }
  loadUrl(url:string){
    this.updateUrl(url);
    return false;
  }
  showVersion(){
    this.growliService.addAlert(JSON.stringify(this.version), AlertType.INFO);
  }
}