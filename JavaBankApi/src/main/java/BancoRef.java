import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe abstrata que representa um banco genérico.
 */
public abstract class BancoRef {
    public static final double TAXA_TRANFERENCIA_ENTRE_BANCOS = 22.0;

    private final String bancoNome;
    private final int bancoNumero;
    private final List<Conta> contas;

    /**
     * Método construtor da classe BancoRef.
     *
     * @param bancoNumero Número de identificação do banco.
     * @param bancoNome   Nome do banco.
     */
    public BancoRef(int bancoNumero, String bancoNome) {
        this.bancoNumero = bancoNumero;
        this.bancoNome = bancoNome;
        this.contas = new ArrayList<>();
    }

    /**
     * Método para criar a nova conta no sistema do banco.
     *
     * @param conta Conta bancária a ser salva no registro do banco.
     */
    public void criarConta(Conta conta) {
        contas.add(conta);
    }


    /**
     * Busca uma conta no sistema do banco pelo número de identificação da mesma.
     *
     * @param contaNumero Número de identificação da conta do cliente.
     * @return Retorna a conta procurada.
     * @throws UnsupportedOperationException Lançada se o cliente não for encontrado.
     */
    public Conta getContaPorNumero(int contaNumero) {

        if (contas.isEmpty()) {
            throw new UnsupportedOperationException(
                    "[AVISO]\nOperação não realizada!\nMotivo: Lista de contas vazia.");
        }

        List<Conta> contaList = contas.stream()
                .filter(conta -> conta.getContaNumero() == contaNumero)
                .collect(Collectors.toList());

        if (contaList.isEmpty()) {
            throw new UnsupportedOperationException(
                    "[AVISO]\nOperação não realizada!\nMotivo: Conta \"" + contaNumero + "\" não encontrada.");
        }

        return contaList.get(0);
    }

    /**
     * Realiza um saque na conta do cliente.
     * Este método é para uso interno.
     *
     * @param conta Conta do cliente.
     * @param valor Valor a ser sacado da conta.
     * @throws UnsupportedOperationException Lança exceção se a operação não puder ser realizada.
     */
    private void sacar(Conta conta, double valor)
            throws UnsupportedOperationException {

        if (valor <= 0.0) {
            throw new UnsupportedOperationException(
                    "[AVISO]\nOperação não realizada!\nMotivo: Valor inválido.");
        }

        if (!conta.temSaldo(valor)) {
            throw new UnsupportedOperationException(
                    "[AVISO]\nOperação não realizada!\nMotivo: Saldo insuficiente.");
        }

        conta.setSaldo(conta.getSaldo() - valor);
    }

    /**
     * Realiza um saque na conta do cliente.
     *
     * @param contaOrig Conta do cliente.
     * @param valor     Valor a ser sacado da conta.
     * @throws UnsupportedOperationException Lança exceção se a operação não puder ser realizada.
     */
    public void fazerSaque(Conta contaOrig, double valor)
            throws UnsupportedOperationException {

        double saldoAnterior = contaOrig.getSaldo();
        sacar(contaOrig, valor);

        String operacao = "\n# Saque" +
                "\n  +--> Banco origem ........ " + contaOrig.getBancoNumero() + " - " + contaOrig.getBancoNome() +
                "\n  +--> Saldo anterior ...... " + Utils.toMoney(saldoAnterior) +
                "\n  +--> Valor depositado .... " + Utils.toMoney(valor) +
                "\n  +--> Saldo disponível .... " + Utils.toMoney(contaOrig.getSaldo());
        contaOrig.salvarOperacao(operacao);
    }

    /**
     * Realiza um depósito na conta do cliente ou outra conta no mesmo banco.
     *
     * @param contaOrig Conta do cliente depositante.
     * @param contaDest Conta do cliente beneficiário.
     * @param valor     Valor a ser depositado da conta.
     * @throws UnsupportedOperationException Lança exceção se a operação não puder ser realizada.
     */
    public void fazerDeposito(Conta contaOrig, Conta contaDest, double valor)
            throws UnsupportedOperationException {

        double saldoAnterior = contaDest.getSaldo();
        contaDest.setSaldo(contaDest.getSaldo() + valor);

        String origem = (contaOrig.getContaNumero() == contaDest.getContaNumero()) ? "O próprio titular" : contaOrig.getTitular();

        String operacao = "\n# Depósito" +
                "\n  +--> Banco origem ........ " + contaOrig.getBancoNumero() + " - " + contaOrig.getBancoNome() +
                "\n  +--> Titular origem ...... " + contaOrig.getContaNumero() + " - " +origem +
                "\n  +--> Saldo anterior ...... " + Utils.toMoney(saldoAnterior) +
                "\n  +--> Valor depositado .... " + Utils.toMoney(valor) +
                "\n  +--> Saldo disponível .... " + Utils.toMoney(contaDest.getSaldo());
        contaDest.salvarOperacao(operacao);
    }

    /**
     * Realiza um depósito interbancário na conta do cliente.
     *
     * @param bancoNumeroOrig Número do banco do depositante.
     * @param bancoNomeOrig   Nome do banco do depositante.
     * @param contaNumeroOrig Número da conta do depositante.
     * @param titularOrig     Nome do titular depositante.
     * @param contaDest       Conta do cliente beneficiário.
     * @param valor           Valor a ser depositado da conta.
     */
    public void fazerDepositoInterbancario(
            int bancoNumeroOrig,
            String bancoNomeOrig,
            int contaNumeroOrig,
            String titularOrig,
            Conta contaDest,
            double valor) {

        double saldoAnterior = contaDest.getSaldo();
        contaDest.setSaldo(contaDest.getSaldo() + valor);

        String operacao = "\n# Depósito interbancário" +
                "\n  +--> Banco origem ........ " + bancoNumeroOrig + " - " + bancoNomeOrig +
                "\n  +--> Titular origem ...... " + contaNumeroOrig + " - " + titularOrig +
                "\n  +--> Saldo anterior ...... " + Utils.toMoney(saldoAnterior) +
                "\n  +--> Valor depositado .... " + Utils.toMoney(valor) +
                "\n  +--> Saldo disponível .... " + Utils.toMoney(contaDest.getSaldo());
        contaDest.salvarOperacao(operacao);
    }

    /**
     * Realiza uma transferência interna de uma conta para outra no mesmo banco.
     *
     * @param contaOrig Conta do cliente depositante.
     * @param contaDest Conta do cliente beneficiário.
     * @param valor     Valor a ser depositado da conta.
     * @throws UnsupportedOperationException Lança exceção se a operação não puder ser realizada.
     */
    public void fazerTransferenciaInterna(Conta contaOrig, Conta contaDest, double valor)
            throws UnsupportedOperationException {
        double saldoAnteriorContaOrig = contaOrig.getSaldo();

        if (!existeConta(contaDest)) {
            throw new UnsupportedOperationException(
                    "[AVISO]\nOperação não realizada!\nMotivo: Conta destino não existe.");
        }
        if (contaOrig.getContaNumero() == contaDest.getContaNumero()) {
            throw new UnsupportedOperationException(
                    "[AVISO]\nOperação não realizada!\nMotivo: Conta origem igual a conta destino.");
        }

        sacar(contaOrig, valor);
        fazerDeposito(contaOrig, contaDest, valor);

        String operacaoContaOrig = "\n# Transferência interna" +
                "\n  +--> Banco origem ......... " + contaOrig.getBancoNumero() + " - " + contaOrig.getBancoNome() +
                "\n  +--> Titular origem ....... " + contaOrig.getContaNumero() + " - " + contaOrig.getTitular() +
                "\n  +--> Titular destino ...... " + contaDest.getContaNumero() + " - " + contaDest.getTitular() +
                "\n  +--> Saldo anterior ....... " + Utils.toMoney(saldoAnteriorContaOrig) +
                "\n  +--> Valor transferido .... " + Utils.toMoney(valor) +
                "\n  +--> Saldo disponível ..... " + Utils.toMoney(contaOrig.getSaldo());
        contaOrig.salvarOperacao(operacaoContaOrig);
    }

    /**
     * Realiza uma transferência externa entre contas de bancos diferentes.
     *
     * @param contaOrig       Conta do cliente depositante.
     * @param bancoNumeroDest Número do banco do beneficiário.
     * @param bancoNomeDest   Nome do banco do beneficiário.
     * @param contaNumeroDest Número da conta do beneficiário.
     * @param titularDest     Nome do beneficiário
     * @param valor           Valor a ser depositado na conta do beneficiário.
     * @throws UnsupportedOperationException Lança exceção se a operação não for realizada.
     */
    public void fazerTransferenciaExterna(
            Conta contaOrig,
            int bancoNumeroDest,
            String bancoNomeDest,
            int contaNumeroDest,
            String titularDest,
            double valor)
            throws UnsupportedOperationException {

        double saldoAnteriorContaOrig = contaOrig.getSaldo();
        sacar(contaOrig, valor + TAXA_TRANFERENCIA_ENTRE_BANCOS);

        String operacaoContaOrig = "\n# Transferência externa" +
                "\n  +--> Banco origem ......... " + contaOrig.getBancoNumero() + " - " + contaOrig.getBancoNome() +
                "\n  +--> Titular origem ....... " + contaOrig.getContaNumero() + " - " + contaOrig.getTitular() +
                "\n  +--> Banco destino ........ " + bancoNumeroDest + " - " + bancoNomeDest +
                "\n  +--> Titular destino ...... " + contaNumeroDest + " - " + titularDest +
                "\n  +--> Saldo anterior ....... " + Utils.toMoney(saldoAnteriorContaOrig) +
                "\n  +--> Valor transferido .... " + Utils.toMoney(valor) +
                "\n  +--> Taxa bancária ........ " + Utils.toMoney(TAXA_TRANFERENCIA_ENTRE_BANCOS) +
                "\n  +--> Saldo disponível ..... " + Utils.toMoney(contaOrig.getSaldo());
        contaOrig.salvarOperacao(operacaoContaOrig);
    }

    /**
     * Verifica se a conta existe no sistema do banco.
     *
     * @param conta Conta a ser verificada.
     * @return Retorna true se a conta existe, false caso contrário.
     */
    public boolean existeConta(Conta conta) {
        return contas.contains(conta);
    }

    /**
     * Pega nome do banco.
     *
     * @return Retorna o nome do banco.
     */
    public String getBancoNome() {
        return bancoNome;
    }

    /**
     * Pega número de identificação do banco.
     *
     * @return Retorna o número de identificação do banco.
     */
    public int getBancoNumero() {
        return bancoNumero;
    }

    @Override
    public String toString() {
        return "[" + bancoNumero + "] " + bancoNome;
    }
}
