import { ProdutosService } from './../../../services/produtos.service';
import { Component, OnInit } from '@angular/core';
import { NonNullableFormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Produto } from 'src/app/interfaces/produto';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-cadastro-produto',
  templateUrl: './cadastro-produto.component.html',
  styleUrls: ['./cadastro-produto.component.css']
})
export class CadastroProdutoComponent implements OnInit {

  idProduto = 0;

  produto = this.formBuilder.group({
    nome:['', Validators.required],
    quantidade:[0, Validators.required],
    valorCusto:[0, Validators.required],
    valorFinal:[0, Validators.required]
  })

  constructor(
    private formBuilder: NonNullableFormBuilder,
    private produtoService: ProdutosService,
    private toaster: ToastrService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.idProduto = Number(this.route.snapshot.paramMap.get('id'));
    if(this.idProduto!=0){
      this.produtoService.buscaPorId(this.idProduto).subscribe((produto: Produto) => {
        this.produto.setValue({
          nome: produto.nome,
          quantidade: produto.quantidade,
          valorCusto: produto.valorCusto,
          valorFinal: produto.valorFinal
        })
      })
    }
  }

  cadastrar(){
    const produto = this.produto.value as Produto;
    const id = this.idProduto;

    if(id != 0){
      produto.id = id;
      this.produtoService.editar(id, produto).subscribe(() => {
        this.toaster.success('Edição realizada com sucesso!', '', {
          timeOut: 3000,
        });
        this.router.navigate(['/produtos']);
      } , (erro) => {
        this.toaster.error('Houve um problema com sua solicitação!', '', {
          timeOut: 2000,
        });
      });
    }
    this.produtoService.salvar(produto).subscribe(() => {
      this.toaster.success('Cadastro realizado com sucesso!', '', {
        timeOut: 3000,
      });
      this.router.navigate(['/produtos']);
    }, (erro) => {
      this.toaster.error('Houve um problema com sua solicitação!', '', {
        timeOut: 2000,
      });
    });

  }

}
