import { Funcionario } from './../../../interfaces/funcionario';
import { FuncionariosService } from './../../../services/funcionarios.service';
import { Component, OnInit } from '@angular/core';
import { NonNullableFormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Validacoes } from 'src/app/components/utils/validacoes';

@Component({
  selector: 'app-cadastro-funcionario',
  templateUrl: './cadastro-funcionario.component.html',
  styleUrls: ['./cadastro-funcionario.component.css']
})
export class CadastroFuncionarioComponent implements OnInit {

  idFuncionario = 0;

  funcionario = this.formBuilder.group({
    nome:['',
    Validators.compose([
      Validators.required,
      Validators.minLength(3),
      Validators.maxLength(100)
    ])
    ],
    cpf:['',
    Validators.compose([
      Validators.required,
      Validacoes.ValidaCpf
    ])
    ],
    contato:['', Validators.required],
    email:['', Validators.email],
    endereco:['', Validators.required],
    salario:[0, Validators.required]
  })

  constructor(
    private formBuilder: NonNullableFormBuilder,
    private funcionarioService: FuncionariosService,
    private toaster: ToastrService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.idFuncionario = Number(this.route.snapshot.paramMap.get('id'));
    if(this.idFuncionario!=0){
      this.funcionarioService.buscaPorId(this.idFuncionario).subscribe((funcionario: Funcionario) => {
        this.funcionario.setValue({
          nome: funcionario.nome,
          cpf: funcionario.cpf,
          contato: funcionario.contato,
          email: funcionario.email,
          endereco: funcionario.endereco,
          salario: funcionario.salario
        })
      })
    }

  }

  cadastrar(){
    const funcionario = this.funcionario.value as Funcionario;
    const id = this.idFuncionario;

    if(id != 0){
      funcionario.id = id;
      this.funcionarioService.editar(id, funcionario).subscribe(() => {
        this.toaster.success('Edição realizada com sucesso!', '', {
          timeOut: 3000,
        });
        this.router.navigate(['/funcionarios']);
      } , (erro) => {
        this.toaster.error('Houve um problema com sua solicitação!', '', {
          timeOut: 2000,
        });
      });
    }
    this.funcionarioService.salvar(funcionario).subscribe((funcionario: Funcionario) => {
      this.toaster.success('Cadastro realizado com sucesso!', '', {
        timeOut: 3000,
      });
      console.log(funcionario);
      this.router.navigate([`/login/cadastro/${funcionario.id}`]);
    }, (erro) => {
      this.toaster.error('Houve um problema com sua solicitação!', '', {
        timeOut: 2000,
      });
    });

  }
}
