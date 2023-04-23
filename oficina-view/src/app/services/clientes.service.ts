import { LoginService } from './login.service';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Cliente } from '../interfaces/cliente';

@Injectable({
  providedIn: 'root'
})
export class ClientesService {

  endpoint = 'clientes';
  api = environment.api;

  constructor(
    private http: HttpClient,
    private loginService: LoginService
  ) { }

  listar(){
    return this.http.get<Cliente[]>(`${this.api}/${this.endpoint}/`, this.loginService.getOptions());
  }

  deletar(id: number){
    return this.http.delete(`${this.api}/${this.endpoint}/${id}`, this.loginService.getOptions());
  }

  salvar(cliente: Partial<Cliente>){
    return this.http.post<Cliente>(`${this.api}/${this.endpoint}/`, cliente, this.loginService.getOptions());
  }

  editar(id: number, cliente: Partial<Cliente>){
    return this.http.put(`${this.api}/${this.endpoint}/${id}`, cliente, this.loginService.getOptions());
  }

  buscaPorId(id: number){
    return this.http.get<Cliente>(`${this.api}/${this.endpoint}/${id}`, this.loginService.getOptions());
  }

}
