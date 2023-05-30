import { Funcionario } from "./funcionario";
import { Venda } from "./venda";

export interface VendasFuncionario {
  funcionario: Funcionario,
    vendas: Venda[],
    valorTotal: number;
}
