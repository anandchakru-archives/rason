export class Alert {
    type: string;
    message: string;
    dismissable: boolean;
}

export enum AlertType {
    SUCCESS,
    INFO,
    WARNING,
    DANGER
}