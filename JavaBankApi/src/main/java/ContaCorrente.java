/**
 * Classe concreta de uma conta bancária.
 * Esta conta representa uma ContaCorrente.
 */
public class ContaCorrente extends Conta {
    /**
     * Construtor da classe ContaCorrente.
     *
     * @param bancoNumero Código que identifica o banco.
     * @param contaNumero Código que identifica a conta do cliente.
     * @param titular     Nome do cliente titular da conta.
     * @param bancoNome   Nome do banco onde o cliente possui uma conta.
     * @param saldo       Saldo disponível na conta.
     * @param senha       Senha de acesso a conta.
     */
    public ContaCorrente(
            int bancoNumero,
            int contaNumero,
            String titular,
            String bancoNome,
            double saldo,
            int senha) {
        super(bancoNumero, contaNumero, titular, bancoNome, CONTA_CORRENTE, saldo, senha);
    }

    @Override
    public String getContaTipoText() {
        return "Conta corrente";
    }

    public void taxaMensal() {
        // TODO
    }
}
