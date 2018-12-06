import { Component, OnInit, OnDestroy, ViewChild, ElementRef, Renderer2 } from '@angular/core';
import { NgcCookieConsentService, NgcNoCookieLawEvent, NgcStatusChangeEvent, NgcInitializeEvent } from 'ngx-cookieconsent';
import { Subscription, timer } from 'rxjs';
import { ClipService } from './service/clip.service';
import { RestService } from './service/rest.service';
import { Key } from './model/key';
import { GrowliService } from './growl/growli.service';
import { AlertType } from './model/alert';
import { LoadiComponent } from './load/loadi/loadi.component';
import { Loadi } from './model/loadi';
import { JsonViewComponent } from './json-view/json-view.component';
import { VERSION } from 'src/environments/version';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  err: string;
  disableBtnClass:string;
  urlClass: string;
  txtClass:string;
  urlInputVal: string;
  taInputVal: string='{}';
  inputJson: JSON;
  expanded: boolean;
  copied: boolean = false;
  hideUrl:boolean=true;
  slugs: Key[];
  version = VERSION;
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
    private growliService: GrowliService) { }
  ngOnInit(): void {
    this.expanded=true;
    this.update(this.taInputVal);
    this.rest.cacheCount().subscribe((key:Key[])=>{
      this.slugs = key;
    });
  }
  beautifyJson(json: string) {
    this.update(json);
    if (this.inputJson) {
      let loadi:Loadi = this.showLoadi('formatting', 25000);
      const content = JSON.stringify(this.inputJson, undefined, 2);
      this.copied = this.clip.copyFromContent(content);
      this.renderer2.removeClass(this.btnBeautifyJson.nativeElement, 'btn-primary');
      this.renderer2.addClass(this.btnBeautifyJson.nativeElement, 'btn-success');
      this.taInputVal = content;
      setTimeout(() => {
        this.renderer2.removeClass(this.btnBeautifyJson.nativeElement, 'btn-success');
        this.renderer2.addClass(this.btnBeautifyJson.nativeElement, 'btn-primary');
      }, 1000);
      this.hideLoadi(loadi);
    }
  }
  uglifyJson(json: string) {
    this.update(json);
    if (this.inputJson) {
      let loadi:Loadi = this.showLoadi('compressing', 25000);
      const content = JSON.stringify(this.inputJson);
      this.copied = this.clip.copyFromContent(content);
      this.renderer2.removeClass(this.btnUglyJson.nativeElement, 'btn-primary');
      this.renderer2.addClass(this.btnUglyJson.nativeElement, 'btn-success');
      this.taInputVal = content;
      setTimeout(() => {
        this.renderer2.removeClass(this.btnUglyJson.nativeElement, 'btn-success');
        this.renderer2.addClass(this.btnUglyJson.nativeElement, 'btn-primary');
      }, 1000);
      this.hideLoadi(loadi);
    }
  }
  expCollapse() {
    if (this.expanded) {
      this.expanded = false;
    } else {
      this.expanded = true;
    }
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
      return;
    }
    let loadi:Loadi = this.showLoadi('fetching', 25000);
    try {
      this.err = undefined;
      this.inputJson = JSON.parse(input);
      this.disableBtnClass='';
      this.txtClass='';
      this.hideLoadi(loadi);
    } catch (e) {
      this.err = e.message;
      this.inputJson = JSON;
      this.disableBtnClass='disabled';
      this.txtClass='border-danger';
      this.hideLoadi(loadi);
    }
  }
  createJson(json: string){
    this.renderer2.addClass(this.btnSaveJson.nativeElement, 'btn-warning');
    this.renderer2.addClass(this.btnSaveJson.nativeElement, 'disabled');
    setTimeout(() => {
      this.resetBtnSaveJson();
    }, 2000);
    this.update(json);
    this.rest.create(json).subscribe((key:Key)=>{
      this.growliService.addAlert("Saved @" + key.slug, AlertType.SUCCESS);
      this.slugs.push(key);
      this.resetBtnSaveJson();
    });
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
  showPopper(){
    alert('popper');
  }
}