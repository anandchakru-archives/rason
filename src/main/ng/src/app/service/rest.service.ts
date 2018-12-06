import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map, catchError } from 'rxjs/operators';
import { Key } from '../model/key';

@Injectable({
  providedIn: 'root'
})
export class RestService {
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json',
      'Authorization': 'my-auth-token'
    })
  };
  constructor(private http: HttpClient) { }
  cacheCount(): Observable<Key[]>{
    return this.http.get<Key[]>('api/keys',this.httpOptions).pipe(catchError(this.handleError('cacheCount')));
  }
  create (json: string): Observable<Key> {
      return this.http.post<Key>('api/',json,this.httpOptions).pipe(catchError(this.handleError('create',json)));
  }
  fetchJson(url:string):Observable<any>{
    return this.http.get<any>(url,this.httpOptions).pipe(catchError(this.handleError('fetchJson',url)))
  }
  handleError(arg0: string, json?: string): any {
    console.log('error@: ' + arg0 + ':' + json);
  }
}
