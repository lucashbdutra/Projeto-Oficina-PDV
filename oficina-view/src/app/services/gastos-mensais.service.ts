import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Gastos } from '../interfaces/gastos';
import { LoginService } from './login.service';

@Injectable({
  providedIn: 'root'
})
export class GastosMensaisService {

  endpoint = 'gastos';
  api = environment.api;

  constructor(
    private http: HttpClient,
    private loginService: LoginService
  ) { }

  calcularGastos(gastos: Partial<Gastos>){
    return this.http.post<Gastos>(`${this.api}/${this.endpoint}/gerarGastos`, gastos, this.loginService.getOptions())
  }

  gerarFechamento(gastos: Partial<Gastos>){
    return this.http.post<Gastos>(`${this.api}/${this.endpoint}/fechamento`, gastos, this.loginService.getOptions())
  }

  listar(){
    return this.http.get<Gastos[]>(`${this.api}/${this.endpoint}/`, this.loginService.getOptions());
  }

}
