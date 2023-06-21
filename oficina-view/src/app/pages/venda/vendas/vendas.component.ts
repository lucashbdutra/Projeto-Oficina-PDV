import { LocalStorageService } from 'src/app/services/local-storage.service';
import { ProdutosService } from './../../../services/produtos.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Venda } from '../../../interfaces/venda';
import { ClientesService } from 'src/app/services/clientes.service';
import { NonNullableFormBuilder, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { Produto } from 'src/app/interfaces/produto';
import { Cliente } from 'src/app/interfaces/cliente';
import { VendasService } from 'src/app/services/vendas.service';
import { ALERT_MESSAGE } from 'src/app/enums/alert-message';

@Component({
  selector: 'app-vendas',
  templateUrl: './vendas.component.html',
  styleUrls: ['./vendas.component.css']
})
export class VendasComponent implements OnInit {

  venda = this.formBuilder.group({
    quantidade: [0, Validators.required]
  })

  pagamento = this.formBuilder.group({
    metodo: ['', Validators.required],
    valorPago: [0, Validators.required],
    desconto: [0, Validators.required]
  })

  produtos: Produto[] = [];
  backup: Produto[] = [];
  idCliente = 0;

  produto?: Produto;
  cliente?: Cliente;
  vendas?: Venda;

  metodo: string = '';
  desconto: number = 0;
  subtotal: number = 0;
  valorPago: number = 0;
  troco: number = 0;
  total: number = 0;

  produtosList: Produto[] = [];

  constructor(
    private formBuilder: NonNullableFormBuilder,
    private clienteService: ClientesService,
    private vendasService: VendasService,
    private produtosService: ProdutosService,
    private localStorage: LocalStorageService,
    private router: Router,
    private route: ActivatedRoute,
    private toaster: ToastrService
  ) { }

  ngOnInit(): void {
    this.idCliente = Number(this.route.snapshot.paramMap.get('id'));
    this.buscarCliente();
    this.buscarProdutos();
  }

  condition(): boolean{
    if(Number(this.venda.value.quantidade) <= Number(this.produto?.quantidade)
    && Number(this.venda.value.quantidade) > 0 || Boolean(this.produto?.isService) === true){
      return false;
    }
    return true;
  }

  buscarCliente(){
    this.clienteService.buscaPorId(this.idCliente).subscribe((cliente: Cliente) => {
      this.cliente = cliente;
    })
  }

  buscarProdutos(){
    this.produtosService.listar().subscribe((produtos: Produto[]) => {
      this.produtos = produtos;
      this.backup = produtos;
    })
  }

  handleProducts(produto: Produto){
    this.produto = produto;

  }

  setList(tipo: string){

    let produto = this.produto as Produto;
    let produtoNew = Object.assign({}, produto) as Produto;
    produtoNew.quantidade = Number(this.venda.value.quantidade);

    if(!this.produtosList.includes(produto)){
      this.produtosList.push(produtoNew);
      this.venda.reset();
      if(produtoNew.isService){
        this.subtotal += produtoNew.valorFinal;
      } else{
        this.subtotal += produtoNew.valorCusto * produtoNew.quantidade;
      }

    }

  }


  calculo(){

    const pagamento = this.pagamento.value
    this.metodo = String(pagamento.metodo);
    const desconto = Number(pagamento.desconto)
    const valorPago = Number(pagamento.valorPago)
    this.valorPago = valorPago;

    let valor = 0;
    for(let produto of this.produtosList){
      if(produto.isService){
        valor += produto.valorFinal;
      } else{
        valor += produto.valorCusto * produto.quantidade;
      }
    }
    this.subtotal = valor;

    this.desconto = (valor * desconto) / 100;



    if(this.metodo === 'dinheiro'){
      this.total = valor - this.desconto;
      this.troco = (this.total - this.valorPago) * -1;
    } else{
      this.total = 0;
    }

    console.log(this.pagamento.value)
  }

  onSubmit(){
    let username = String(this.localStorage.get('username'));
    const cliente = this.cliente as Cliente;
    let venda: Partial<Venda> = {};
    venda.cliente = cliente;
    venda.produtos = this.produtosList;
    this.vendasService.realizarVenda(venda, username).subscribe((venda: Venda) => {
      if(venda){
        this.vendas = venda;
        this.toaster.success('Venda realizada com sucesso!', '', {
          timeOut: 2000,
        });
        this.router.navigate(['/home']);
      }
    }, (error) => {
      this.toaster.error(ALERT_MESSAGE.ERROR_SELL, '', {
        timeOut: 2000,
      });
    });

  }

  search(event: Event){
    const target = event.target as HTMLInputElement
    const value = target.value
    this.produtos = this.backup.filter((produto) => {
      return produto.nome.toLowerCase().includes(value);
    })
  }

}
