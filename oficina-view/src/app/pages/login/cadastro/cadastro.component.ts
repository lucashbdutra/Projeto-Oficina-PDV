import { ToastrService } from 'ngx-toastr';
import { NonNullableFormBuilder, Validators } from '@angular/forms';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Login } from 'src/app/interfaces/login';
import { LoginService } from 'src/app/services/login.service';
import { ALERT_MESSAGE } from 'src/app/enums/alert-message';
import { ActivatedRoute, Router } from '@angular/router';
import { Funcionario } from 'src/app/interfaces/funcionario';
import { FuncionariosService } from 'src/app/services/funcionarios.service';

@Component({
  selector: 'app-cadastro',
  templateUrl: './cadastro.component.html',
  styleUrls: ['./cadastro.component.css']
})
export class CadastroComponent implements OnInit {

  @Output() login = new EventEmitter<Event>();

  idFuncionario: number = 0;

  constructor(
    private formBuilder: NonNullableFormBuilder,
    private loginService: LoginService,
    private funcionarioService: FuncionariosService,
    private toaster: ToastrService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    let idFuncionario = Number(this.route.snapshot.paramMap.get('idFuncionario'));
    if(idFuncionario != 0){
      this.idFuncionario = idFuncionario;
    }
  }

  form = this.formBuilder.group({
    username: ['', Validators.required],
    password: ['', Validators.required],
    confirmarSenha: ['', Validators.required]
  })

  onCadastro(){

    const senha = this.form.value.password;
    const confirmar = this.form.value.confirmarSenha;

    if(senha == confirmar){
      const login: Login = {
        username: String(this.form.value.username),
        password: String(senha)
      };

      if(this.form.valid){

        if(this.idFuncionario != 0){
          this.loginService.cadastroFuncionario(this.idFuncionario, login).subscribe((login: Login) => {
            if(login){
              this.form.reset();
              this.toaster.success('Cadastro realizado com sucesso!', '', {
                timeOut: 2000,
              });
              this.router.navigate(['/funcionarios']);
            }
          }, (error) => {
            this.toaster.error(ALERT_MESSAGE.ERROR_SIGNIN, '', {
              timeOut: 2000,
            });
          });

        } else{
          this.loginService.cadastroAdmin(login).subscribe((login: Login) => {
            if(login){
              this.form.reset();
              this.toaster.success('Cadastro realizado com sucesso!', '', {
                timeOut: 2000,
              });
              this.login.emit();
            }
          }, (error) => {
            this.toaster.error(ALERT_MESSAGE.ERROR_SIGNIN, '', {
              timeOut: 2000,
            });
          });
        }
        } else{

          this.toaster.warning('Preencha todos os campos', '', {
            timeOut: 2000,
          });
        }
    }else{
      this.toaster.warning('As senhas devem ser iguais', '', {
        timeOut: 2000,
      });
    }
  }


  onLogin(){
    this.login.emit();
  }



}
