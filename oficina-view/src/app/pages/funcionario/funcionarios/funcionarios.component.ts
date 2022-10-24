import { FuncionariosService } from './../../../services/funcionarios.service';
import { Funcionario } from './../../../interfaces/funcionario';
import { Component, OnInit } from '@angular/core';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { ConfirmModalComponent } from 'src/app/components/confirm-modal/confirm-modal.component';

@Component({
  selector: 'app-funcionarios',
  templateUrl: './funcionarios.component.html',
  styleUrls: ['./funcionarios.component.css']
})
export class FuncionariosComponent implements OnInit {

  funcionarios: Funcionario[] = [];

  constructor(
    private funcionarioService: FuncionariosService,
    private bsModalService: BsModalService,
    private modalRef: BsModalRef
  ) { }

  ngOnInit(): void {
    this.funcionarioService.listar().subscribe((funcionarios: Funcionario[])=>{
      this.funcionarios = funcionarios;
    })
  }

  openModalComponent(
    idFuncionario?: number,
  ) {
    const initialStateDeletar = {
      idFuncionario
    };


    this.modalRef = this.bsModalService.show(ConfirmModalComponent, {
      initialState: initialStateDeletar,
      class: 'my-modal',
    });

  }

}
