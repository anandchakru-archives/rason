import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { NgcCookieConsentModule, NgcCookieConsentConfig } from 'ngx-cookieconsent';

import { AppComponent } from './app.component';
import { CookieComponent } from './cookie/cookie.component';
import { JsonViewComponent } from './json-view/json-view.component';
import { HttpClientModule } from '@angular/common/http';
import { GrowlModule } from './growl/growl.module';
import { LoadModule } from './load/load.module';
import { WINDOW_PROVIDERS } from './service/window.service';

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
    CookieComponent,
    JsonViewComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    NgcCookieConsentModule.forRoot(cookieconfig),
    GrowlModule,
    LoadModule
  ],
  providers: [
    WINDOW_PROVIDERS
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
