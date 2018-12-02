import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { NgcCookieConsentModule, NgcCookieConsentConfig } from 'ngx-cookieconsent';

import { AppComponent } from './app.component';
import { CookieComponent } from './cookie/cookie.component';


const cookieconfig: NgcCookieConsentConfig= {
  "cookie": {"domain": window.location.hostname},
  "palette": {
    "popup": {
      "background": "#F47C79",
      "text": "#50514F",
      "link": "#F3FFBD"
    },
    "button": {
      "background": "#50514F",
      "text": "#F3FFBD"
    }
  },
  "theme": "classic",
  "position": "bottom-right",
  "content": {
    "link": "more info",
    "href": "ng/cookiepolicy"
  }
}

@NgModule({
  declarations: [
    AppComponent,
    CookieComponent
  ],
  imports: [
    BrowserModule,
    NgcCookieConsentModule.forRoot(cookieconfig)
  ],
  providers: [
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
