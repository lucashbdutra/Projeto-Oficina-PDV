import { Funcionario } from "./funcionario";

export interface Login {
  username: string,
  password: string,
  token?: string;
  funcionario?: Funcionario;
}
