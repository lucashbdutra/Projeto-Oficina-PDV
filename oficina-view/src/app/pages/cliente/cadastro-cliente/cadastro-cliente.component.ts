import { Component, OnInit } from '@angular/core';
import { NonNullableFormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Cliente } from 'src/app/interfaces/cliente';
import { ClientesService } from 'src/app/services/clientes.service';

@Component({
  selector: 'app-cadastro-cliente',
  templateUrl: './cadastro-cliente.component.html',
  styleUrls: ['./cadastro-cliente.component.css']
})
export class CadastroClienteComponent implements OnInit {

  idCliente = 0;

  cliente = this.formBuilder.group({
    nome:['', Validators.required],
    cpf:['', Validators.required],
    contato:['', Validators.required],
    email:['', Validators.email],
    endereco:['', Validators.required]
  })

  constructor(
    private formBuilder: NonNullableFormBuilder,
    private clienteService: ClientesService,
    private toaster: ToastrService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.idCliente = Number(this.route.snapshot.paramMap.get('id'));

    if(this.idCliente!=0){
      this.clienteService.buscaPorId(this.idCliente).subscribe((cliente: Cliente) => {
        this.cliente.setValue({
          nome: cliente.nome,
          cpf: cliente.cpf,
          contato: cliente.contato,
          email: cliente.email,
          endereco: cliente.endereco
        })
      })
    }
  }

  cadastrar(){
    const cliente = this.cliente.value as Cliente;
    const id = this.idCliente;

    if(id != 0){
      cliente.id = id;
      this.clienteService.editar(id, cliente).subscribe(() => {
        this.toaster.success('Edição realizada com sucesso!', '', {
          timeOut: 3000,
        });
        this.router.navigate(['/clientes']);
      } , (erro) => {
        this.toaster.error('Houve um problema com sua solicitação!', '', {
          timeOut: 2000,
        });
      });
    }
    this.clienteService.salvar(cliente).subscribe(() => {
      this.toaster.success('Cadastro realizado com sucesso!', '', {
        timeOut: 3000,
      });
      this.router.navigate(['/clientes']);
    }, (erro) => {
      this.toaster.error('Houve um problema com sua solicitação!', '', {
        timeOut: 2000,
      });
    });

  }


}
