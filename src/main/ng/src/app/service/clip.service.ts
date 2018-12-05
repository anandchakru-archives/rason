import { Inject, Injectable, Optional, Renderer, SkipSelf, InjectionToken } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ClipService {
    private tempTextArea: HTMLTextAreaElement | undefined;
    constructor() { }
    public get isSupported(): boolean {
        return !!document.queryCommandSupported && !!document.queryCommandSupported('copy');
    }

    /**
     * copyFromInputElement
     */
    public copyFromInputElement(targetElm: HTMLInputElement | HTMLTextAreaElement): boolean {
        try {
            targetElm.select();
            let copied = document.execCommand('copy');
            targetElm && targetElm.blur();
            window.getSelection().removeAllRanges();
            return copied;
        } catch (error) {
            return false;
        }
    }

    /**
     * Creates a fake textarea element, sets its value from `text` property,
     * and makes a selection on it.
     */
    public copyFromContent(content: string) {
        if (!this.tempTextArea) {
            this.tempTextArea = this.createTempTextArea(document, window);
            document.body.appendChild(this.tempTextArea);
        }
        this.tempTextArea.value = content;
        return this.copyFromInputElement(this.tempTextArea);
    }

    // remove temporary textarea if any
    public destroy() {
        if (this.tempTextArea) {
            document.body.removeChild(this.tempTextArea);
            this.tempTextArea = undefined;
        }
    }

    // create a fake textarea for copy command
    private createTempTextArea(doc: Document, window: Window): HTMLTextAreaElement {
        const isRTL = doc.documentElement.getAttribute('dir') === 'rtl';
        let ta: HTMLTextAreaElement;
        ta = doc.createElement('textarea');
        // Prevent zooming on iOS
        ta.style.fontSize = '12pt';
        // Reset box model
        ta.style.border = '0';
        ta.style.padding = '0';
        ta.style.margin = '0';
        // Move element out of screen horizontally
        ta.style.position = 'absolute';
        ta.style[isRTL ? 'right' : 'left'] = '-9999px';
        // Move element to the same position vertically
        const yPosition = window.pageYOffset || doc.documentElement.scrollTop;
        ta.style.top = yPosition + 'px';
        ta.setAttribute('readonly', '');
        return ta;
    }
}