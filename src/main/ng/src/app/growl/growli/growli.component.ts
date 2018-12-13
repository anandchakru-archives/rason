import { Component, OnInit, Input } from '@angular/core';
import { Alert } from '../../model/ui/alert';
import { GrowliService } from '../growli.service';

@Component({
  selector: 'app-growli',
  templateUrl: './growli.component.html',
  styleUrls: ['./growli.component.scss']
})
export class GrowliComponent implements OnInit {
  @Input() alertCount: number;
  @Input() autoClose: number;

  public alerts: Alert[];

  constructor(private growlService: GrowliService) {

  }

  ngOnInit() {
      this.growlService.configure(this.alertCount, this.autoClose);
      this.growlService.alerts.subscribe((alerts: Alert[]) => {
          this.alerts = alerts;
      });
  }

  public closeAlert(alert: Alert): void {
      if (alert.dismissable) {
          this.growlService.removeAlert(alert);
      }
  }

}
