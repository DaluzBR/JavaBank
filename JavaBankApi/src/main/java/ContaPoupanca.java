
/**
 * Classe concreta de uma conta bancária.
 * Esta conta representa uma conta poupança.
 */
public class ContaPoupanca extends Conta {
    /**
     * Construtor da classe ContaPoupança.
     *
     * @param bancoNumero Código que identifica o banco.
     * @param contaNumero Código que identifica a conta do cliente.
     * @param titular     Nome do cliente titular da conta.
     * @param bancoNome   Nome do banco onde o cliente possui uma conta.
     * @param saldo       Saldo disponível na conta.
     * @param senha       Senha de acesso a conta.
     */
    public ContaPoupanca(
            int bancoNumero,
            int contaNumero,
            String titular,
            String bancoNome,
            double saldo,
            int senha) {
        super(bancoNumero, contaNumero, titular, bancoNome, CONTA_POUPANCA, saldo, senha);
    }

    @Override
    public String getContaTipoText() {
        return "Conta poupança";
    }

    public void rendimento() {
        // TODO
    }
}