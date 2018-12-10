import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map, catchError } from 'rxjs/operators';
import { Key } from '../model/key';
import { CheckSlugRsp } from '../model/checkSlug.rsp';

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
  cacheCount(bucket:string): Observable<Key[]>{
    return this.http.get<Key[]>('api/'+bucket+'/keys',this.httpOptions).pipe(catchError(this.handleError('cacheCount')));
  }
  checkSlug(bucket:string, slug:string):Observable<CheckSlugRsp>{
    return this.http.get<CheckSlugRsp>('api/exists/'+bucket+'/'+slug, this.httpOptions).pipe(catchError(this.handleError('checkSlug',slug)));
  }
  create (bucket:string, json: string, slug?:string): Observable<Key> {
    return this.http.post<Key>('api/'+bucket+'/'+(slug && slug.length>0?slug:''),json,this.httpOptions).pipe(catchError(this.handleError('create',json)));
  }
  fetchJson(url:string):Observable<any>{
    return this.http.get<any>(url,this.httpOptions).pipe(catchError(this.handleError('fetchJson',url)))
  }
  handleError(arg0: string, json?: string): any {
    console.log('error@: ' + arg0 + ':' + json);
  }
}
