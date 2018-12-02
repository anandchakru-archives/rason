import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { NgcCookieConsentService, NgcNoCookieLawEvent, NgcStatusChangeEvent, NgcInitializeEvent } from 'ngx-cookieconsent';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'ng';    
}
