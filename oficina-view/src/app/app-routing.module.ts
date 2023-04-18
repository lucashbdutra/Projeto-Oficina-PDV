import { EstoqueComponent } from './pages/produto/estoque/estoque.component';
import { RelatorioComponent } from './pages/financas/relatorio/relatorio.component';
import { CadastroProdutoComponent } from './pages/produto/cadastro-produto/cadastro-produto.component';
import { ProdutosComponent } from './pages/produto/produtos/produtos.component';
import { CadastroFuncionarioComponent } from './pages/funcionario/cadastro-funcionario/cadastro-funcionario.component';
import { FuncionariosComponent } from './pages/funcionario/funcionarios/funcionarios.component';
import { CadastroClienteComponent } from './pages/cliente/cadastro-cliente/cadastro-cliente.component';
import { ClientesComponent } from './pages/cliente/clientes/clientes.component';
import { HomeComponent } from './components/home/home.component';
import { GuardService } from './services/guard.service';
import { LoginComponent } from './pages/login/login/login.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CadastroComponent } from './pages/login/cadastro/cadastro.component';
import { VendasComponent } from './pages/venda/vendas/vendas.component';
import { SelectClientComponent } from './pages/venda/select-client/select-client.component';
import { GastosMensaisComponent } from './pages/financas/gastos-mensais/gastos-mensais.component';
import { ServicosComponent } from './pages/produto/servicos/servicos.component';

const routes: Routes = [
  {
    path: 'login', component: LoginComponent
  },
  {
    path: 'login/cadastro', component: CadastroComponent
  },

  {
    path: 'login/cadastro/:idFuncionario', component: CadastroComponent
  },

  {
    path: '', pathMatch: 'full', redirectTo: 'login'
  },


  {
    path: 'home', canActivate:[GuardService], component: HomeComponent
  },


  {
    path: 'financas', canActivate:[GuardService], component: GastosMensaisComponent
  },
  {
    path: 'financas/relatorio', canActivate:[GuardService], component: RelatorioComponent
  },


  {
    path: 'venda', canActivate:[GuardService], component: SelectClientComponent
  },
  {
    path: 'venda/finalizar/:id', canActivate:[GuardService], component: VendasComponent
  },


  {
    path: 'clientes', canActivate:[GuardService], component: ClientesComponent
  },
  {
    path: 'clientes/cadastro', canActivate:[GuardService], component: CadastroClienteComponent
  },
  {
    path: 'clientes/editar/:id', canActivate:[GuardService], component: CadastroClienteComponent
  },
  {
    path: 'clientes/cadastro/venda', canActivate:[GuardService], component: CadastroClienteComponent
  },


  {
    path: 'funcionarios', canActivate:[GuardService], component: FuncionariosComponent
  },
  {
    path: 'funcionarios/cadastro', canActivate:[GuardService], component: CadastroFuncionarioComponent
  },
  {
    path: 'funcionarios/editar/:id', canActivate:[GuardService], component: CadastroFuncionarioComponent
  },


  {
    path: 'produtos', canActivate:[GuardService], component: ProdutosComponent
  },
  {
    path: 'servicos', canActivate:[GuardService], component: ServicosComponent
  },
  {
    path: 'estoque', canActivate:[GuardService], component: EstoqueComponent
  },
  {
    path: 'produtos/cadastro', canActivate:[GuardService], component: CadastroProdutoComponent
  },
  {
    path: 'produtos/editar/:id', canActivate:[GuardService], component: CadastroProdutoComponent
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
