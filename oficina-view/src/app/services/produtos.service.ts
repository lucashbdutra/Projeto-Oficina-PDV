import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Produto } from '../interfaces/produto';
import { LoginService } from './login.service';

@Injectable({
  providedIn: 'root'
})
export class ProdutosService {

  endpoint = 'produtos';
  api = environment.api;

  constructor(
    private http: HttpClient,
    private loginService: LoginService
  ) { }

  listar(){
    return this.http.get<Produto[]>(`${this.api}/${this.endpoint}/`, this.loginService.getOptions());
  }

  deletar(id: number){
    return this.http.delete(`${this.api}/${this.endpoint}/${id}`, this.loginService.getOptions());
  }

  salvar(produto: Partial<Produto>){
    return this.http.post<Produto>(`${this.api}/${this.endpoint}/create`, produto, this.loginService.getOptions());
  }

  editar(id: number, produto: Partial<Produto>){
    return this.http.put(`${this.api}/${this.endpoint}/${id}`, produto, this.loginService.getOptions());
  }

  buscaPorId(id: number){
    return this.http.get<Produto>(`${this.api}/${this.endpoint}/${id}`, this.loginService.getOptions());
  }
}
