import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DebounceService {
  /** 
   * Returns a function, that, as long as it continues 
   * to be invoked, will not be triggered. The function 
   * will be called after it stops being called for N milliseconds.
   * */
  debounce(func: (input:string) => void, wait = 50): Function {
    let h: any;
    return (input:string) => {
      clearTimeout(h);
      h = setTimeout(() => func(input), wait);
    }
  }
}
