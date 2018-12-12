export class Alert {
    type: string;
    message: string;
    dismissable: boolean;
    small:boolean;
}

export enum AlertType {
    SUCCESS,
    INFO,
    WARNING,
    DANGER
}