import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe abstrata que representa uma conta bancária genérica.
 */
public abstract class Conta {

    public static final int CONTA_CORRENTE = 1;
    public static final int CONTA_POUPANCA = 2;

    private final int bancoNumero;
    private final int contaNumero;
    private final String titular;
    private final String bancoNome;
    private final int contaTipo;
    private double saldo;
    private final int senha;
    private final List<String> operacoes;

    /**
     * Construtor da classe Conta.
     *
     * @param bancoNumero Código que identifica o banco.
     * @param contaNumero Código que identifica a conta do cliente.
     * @param titular     Nome do cliente titular da conta.
     * @param bancoNome   Nome do banco onde o cliente possui uma conta.
     * @param contaTipo   Tipo da conta.
     * @param saldo       Saldo disponível na conta.
     * @param senha       Senha de acesso a conta.
     */
    public Conta(
            int bancoNumero,
            int contaNumero,
            String titular,
            String bancoNome,
            int contaTipo,
            double saldo,
            int senha) {
        this.contaNumero = contaNumero;
        this.bancoNumero = bancoNumero;
        this.titular = titular;
        this.bancoNome = bancoNome;
        this.contaTipo = contaTipo;
        this.senha = senha;
        this.saldo = saldo;
        this.operacoes = new ArrayList<>();
    }

    /**
     * Verifica se existe saldo na conta para realizar a operação.
     *
     * @param valor Valor monetário na conta.
     * @return Retorna true se existe saldo na conta, false caso contrário.
     */
    public boolean temSaldo(double valor) {
        return this.saldo >= valor;
    }

    /**
     * Retorna o nome completo do cliente.
     *
     * @return Retorna o nome completo do cliente.
     */
    public String getTitular() {
        return this.titular;
    }

    /**
     * Retorna o primeiro nome do cliente.
     *
     * @return Retorna o primeiro nome do cliente.
     */
    public String getPrimeiroNome() {
        return this.titular.split(" ")[0];
    }

    /**
     * Retorna o código de identificação do banco onde o cliente tem uma conta.
     *
     * @return Retorna o número do banco.
     */
    public int getBancoNumero() {
        return this.bancoNumero;
    }

    /**
     * Retorna o nome do banco onde o cliente tem uma conta.
     *
     * @return Retorna o nome do banco.
     */
    public String getBancoNome() {
        return this.bancoNome;
    }

    /**
     * Retorna o código de identificação da conta do cliente.
     *
     * @return Retorna o número da conta.
     */
    public int getContaNumero() {
        return this.contaNumero;
    }

    /**
     * Define o valor do saldo que o usuário tem na conta.
     *
     * @param saldo Valor monetário que o usuário possui na conta.
     */
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    /**
     * Retorna o valor do saldo que o usuário tem na conta.
     *
     * @return Retorna o saldo do usuário.
     */
    public double getSaldo() {
        return this.saldo;
    }

    /**
     * Verifica se a senha de acesso é a correta.
     *
     * @param senha Senha que o cliente tenta acessar a conta.
     * @return Retorna true se a senha coincide, false caso contrário.
     */
    public boolean verificaSenha(int senha) {
        return this.senha == senha;
    }

    /**
     * Código do tipo da conta.
     *
     * @return Número que representa o código de um tipo de conta.
     */
    public int getContaTipo() {
        return this.contaTipo;
    }

    /**
     * Exibe o tipo da conta de forma textual.
     *
     * @return Texto informando o tipo da conta.
     */
    abstract public String getContaTipoText();

    /**
     * Salva um registro das operações bancárias realizadas na conta.
     */
    public void salvarOperacao(String operacao) {
        this.operacoes.add(operacao);
    }

    /**
     * Imprime o saldo bancário da conta do cliente.
     */
    public void imprimirSaldo() {
        Utils.banner(getBancoNome() + " - Saldo bancário");
        System.out.println(" Banco .............. " + getBancoNumero() + " - " + getBancoNome());
        System.out.println(" Titular ............ " + getTitular());
        System.out.println(" Nº Conta ........... " + getContaNumero());
        System.out.println(" Tipo Conta ......... " + getContaTipoText());
        System.out.println(" Saldo disponível ... " + Utils.toMoney(getSaldo()));
    }

    /**
     * Imprime o extrato bancário da conta do cliente.
     */
    public void imprimirExtrato() {
        Utils.banner(getBancoNome() + " - Extrato bancário");
        System.out.println(" Banco .............. " + getBancoNumero() + " - " + getBancoNome());
        System.out.println(" Titular ............ " + getTitular());
        System.out.println(" Nº Conta ........... " + getContaNumero());
        System.out.println(" Tipo Conta ......... " + getContaTipoText());
        System.out.println(" Saldo disponível ... " + Utils.toMoney(getSaldo()));

        System.out.println("\n------------- [Extrato das operações] -------------");

        if (this.operacoes.isEmpty()) {
            System.out.println("Nenhuma operação realizada neste período.\n");
        } else {
            this.operacoes.forEach(System.out::println);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conta conta = (Conta) o;
        return this.bancoNumero == conta.bancoNumero &&
                this.contaNumero == conta.contaNumero &&
                this.contaTipo == conta.contaTipo &&
                this.titular.equals(conta.titular);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.titular, this.bancoNumero, this.contaNumero, this.contaTipo);
    }
}
