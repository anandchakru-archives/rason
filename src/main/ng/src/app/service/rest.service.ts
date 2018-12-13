import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { CheckSlugRsp } from '../model/be/checkslug.rsp';
import { BucketSlugs } from '../model/be/bucketslugs';
import { BucketSlug } from '../model/be/bucketslug';

@Injectable({
  providedIn: 'root'
})
export class RestService {
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json',
      'Accept':  'application/json'
    })
  };
  constructor(private http: HttpClient) { }
  keys(bucket:string): Observable<BucketSlugs>{
    return this.http.get<BucketSlugs>('api/'+bucket+'/keys',this.httpOptions);
  }
  exists(bucket:string, slug:string):Observable<CheckSlugRsp>{
    return this.http.get<CheckSlugRsp>('api/exists/'+bucket+'/'+slug, this.httpOptions);
  }
  create (bucket:string, json: string, slug?:string): Observable<BucketSlug> {
    return this.http.post<BucketSlug>('api/'+bucket+'/'+(slug && slug.length>0?slug:''),json,this.httpOptions);
  }
  update (bucket:string, json: string, slug:string): Observable<BucketSlug> {
    return this.http.put<BucketSlug>('api/'+bucket+'/'+slug,json,this.httpOptions);
  }
  fetchJson(url:string):Observable<any>{
    return this.http.get<any>(url,this.httpOptions);
  }
}
