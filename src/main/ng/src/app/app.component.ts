import { Component, OnInit, OnDestroy, ViewChild, ElementRef, Renderer2 } from '@angular/core';
import { NgcCookieConsentService, NgcNoCookieLawEvent, NgcStatusChangeEvent, NgcInitializeEvent } from 'ngx-cookieconsent';
import { Subscription } from 'rxjs';
import { ClipService } from './service/clip.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  err: string;
  btnClass:string;
  txtClass:string;
  input: string='{}';
  inputJson: JSON;
  expanded: boolean;
  copied: boolean = false;
  @ViewChild('copyPrettyJson') copyPrettyJson: ElementRef;
  @ViewChild('copyUglyJson') copyUglyJson: ElementRef;
  constructor(private clipboardService: ClipService, private renderer2: Renderer2) { }
  ngOnInit(): void {
    this.expanded=true;
    this.update(this.input);
  }
  formatAndCopyPrettyJson(json: string) {
    this.update(json);
    if (this.inputJson) {
      const content = JSON.stringify(this.inputJson, undefined, 2);
      this.copied = this.clipboardService.copyFromContent(content);
      this.renderer2.removeClass(this.copyPrettyJson.nativeElement, 'btn-primary');
      this.renderer2.addClass(this.copyPrettyJson.nativeElement, 'btn-success');
      this.input = content;
      setTimeout(() => {
        this.renderer2.removeClass(this.copyPrettyJson.nativeElement, 'btn-success');
        this.renderer2.addClass(this.copyPrettyJson.nativeElement, 'btn-primary');
      }, 1000);
    }
  }
  formatAndCopyUglyJson(json: string) {
    this.update(json);
    if (this.inputJson) {
      const content = JSON.stringify(this.inputJson);
      this.copied = this.clipboardService.copyFromContent(content);
      this.renderer2.removeClass(this.copyUglyJson.nativeElement, 'btn-primary');
      this.renderer2.addClass(this.copyUglyJson.nativeElement, 'btn-success');
      this.input = content;
      setTimeout(() => {
        this.renderer2.removeClass(this.copyUglyJson.nativeElement, 'btn-success');
        this.renderer2.addClass(this.copyUglyJson.nativeElement, 'btn-primary');
      }, 1000);
    }
  }
  expCollapse() {
    if (this.expanded) {
      this.expanded = false;
    } else {
      this.expanded = true;
    }
  }
  formatJson(json: string) {
    this.update(json);
  }
  private update(input: string) {
    if (!input || !input.length) {
      this.inputJson = JSON;
      this.btnClass='disabled';
      this.txtClass='border-danger';
      return;
    }
    try {
      this.err = undefined;
      this.inputJson = JSON.parse(input);
      this.btnClass='';
      this.txtClass='';
    } catch (e) {
      this.err = e.message;
      this.inputJson = undefined;
      this.btnClass='disabled';
      this.txtClass='border-danger';
    }
  }
}
