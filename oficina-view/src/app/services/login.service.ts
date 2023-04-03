import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Login } from '../interfaces/login';
import { LocalStorageService } from './local-storage.service';

@Injectable({
  providedIn: 'root'
})
export class LoginService {


  constructor(
    private http: HttpClient,
    private localStorage: LocalStorageService
  ) { }

  endpoint = 'login';
  api = environment.api;

  public authorizationData = '';
  public httpOptions = {
    headers: new HttpHeaders()
  };

  setData(login: Partial<Login>){
    this.localStorage.set('username', String(login.username));
    this.localStorage.set('token', String(login.token));

  }

  getOptions(){
    this.authorizationData = 'Bearer ' + this.localStorage.get('token');
    return this.httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        'Authorization': this.authorizationData
      })
    };
  }

  authenticate(login: Partial<Login>){
    return this.http.post<Login>(`${this.api}/${this.endpoint}/authenticate/`, login);
  }

  cadastro(login: Partial<Login>){
    return this.http.post<Login>(`${this.api}/${this.endpoint}/register/`, login);
  }

  logOut(){
    this.localStorage.clear();
  }

  isAuthenticated(){
    let isTokenPresent = this.localStorage.get('token');
    return isTokenPresent ? true: false;
  }

}
