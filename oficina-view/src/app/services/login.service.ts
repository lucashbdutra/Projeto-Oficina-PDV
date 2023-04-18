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

  endpoint = 'auth';
  api = environment.api;

  public authorizationData = '';
  public httpOptions = {
    headers: new HttpHeaders()
  };

  setData(login: Partial<Login>){

    if(!login.funcionario){
      this.localStorage.set('admin', 'true');
    }
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

  cadastroAdmin(login: Partial<Login>){
    return this.http.post<Login>(`${this.api}/${this.endpoint}/registerAdmin/`, login);
  }

  cadastroFuncionario(idFuncionario: Number, login: Partial<Login>){
    return this.http.post<Login>(`${this.api}/${this.endpoint}/registerFuncionario/${idFuncionario}`, login);
  }

  isFirstLogin(){
    return this.http.get<boolean>(`${this.api}/${this.endpoint}`);
  }

  logOut(){
    this.localStorage.clear();
  }

  isAuthenticated(){
    let isTokenPresent = this.localStorage.get('token');
    return isTokenPresent ? true: false;
  }

}
