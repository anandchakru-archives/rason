import { Injectable } from '@angular/core';
import { Alert, AlertType } from '../model/alert';
import { Subject,Observable, timer } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GrowliService {

  public alerts: Subject<Alert[]> = new Subject<Alert[]>();

    public alertHolder: Alert[] = new Array<Alert>();

    alertCount = 50;
    autoClose = 10000;

    constructor() {
    }

    public configure(alertCount: number, autoClose: number): void {
        if (typeof alertCount !== "undefined" && alertCount !== null) {
            if (!isNaN(alertCount) && alertCount > 0) {
                this.alertCount = alertCount;
            } else {
                console.error("parameter alertCount must be a valid number > 0, to leave default, do not provide this parameter");
            }
        }
        if (typeof autoClose !== "undefined" && autoClose !== null) {
            if (!isNaN(autoClose) && autoClose > 0) {
                this.autoClose = autoClose;
            } else {
                console.error("parameter autoClose must be a valid number > 0, to leave default, do not provide this parameter");
            }
        }
    }

    public addAlert(message: string, type: AlertType, autoClose?: number, dismissable?: boolean, small?: boolean): void {
        if (this.alertHolder.length >= this.alertCount) {
            // remove the oldest alert
            this._removeAlertById(0, this.alertHolder, this.alerts);
        }
        if (typeof dismissable === "undefined" || dismissable === null) {
            dismissable = true;
        }
        let cssType = this._convertTypeToCssClass(type);
        let alert = {message: message, type: cssType, dismissable: dismissable, small: small};
        this.alertHolder.push(alert);
        this.alerts.next(this.alertHolder);
        if (autoClose && autoClose > -1) {
            this._scheduleAlertHide(autoClose, alert);
        } else if (this.autoClose > -1) {
            this._scheduleAlertHide(this.autoClose, alert);
        }
    }

    public removeAlert(alert: Alert): void {
        this._removeAlert(alert, this.alertHolder, this.alerts);
    }

    private _removeAlert(alert: Alert, alertHolder: Alert[], alerts: Subject<Alert[]>): void {
        let index: number = alertHolder.indexOf(alert);
        this._removeAlertById(index, alertHolder, alerts);
    }

    private _scheduleAlertHide(timeout: number, alert: Alert) {
        let displayTimeout = timer(timeout);
        displayTimeout.subscribe(() => {
            this._removeAlert(alert, this.alertHolder, this.alerts);
        });
    }

    private _convertTypeToCssClass(type: AlertType): string {
        if (type === AlertType.SUCCESS) {
            return "success";
        } else if (type === AlertType.INFO) {
            return "info";
        } else if (type === AlertType.WARNING) {
            return "warning";
        } else if (type === AlertType.DANGER) {
            return "danger";
        }
    }

    private _removeAlertById(id: number, alertHolder: Alert[], alerts: Subject<Alert[]>): void {
        alertHolder.splice(id, 1);
        alerts.next(alertHolder);
    }
}
