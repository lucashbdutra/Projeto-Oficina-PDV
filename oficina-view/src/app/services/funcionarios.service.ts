import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Funcionario } from '../interfaces/funcionario';
import { LoginService } from './login.service';

@Injectable({
  providedIn: 'root'
})
export class FuncionariosService {

  endpoint = 'funcionarios';
  api = environment.api;

  constructor(
    private http: HttpClient,
    private loginService: LoginService
  ) { }

  listar(){
    return this.http.get<Funcionario[]>(`${this.api}/${this.endpoint}/`, this.loginService.getOptions());
  }

  deletar(id: number){
    return this.http.delete(`${this.api}/${this.endpoint}/${id}`, this.loginService.getOptions());
  }

  salvar(funcionario: Partial<Funcionario>){
    return this.http.post<Funcionario>(`${this.api}/${this.endpoint}/`, funcionario, this.loginService.getOptions());
  }

  editar(id: number, funcionario: Partial<Funcionario>){
    return this.http.put(`${this.api}/${this.endpoint}/${id}`, funcionario, this.loginService.getOptions());
  }

  buscaPorId(id: number){
    return this.http.get<Funcionario>(`${this.api}/${this.endpoint}/${id}`, this.loginService.getOptions());
  }
}
