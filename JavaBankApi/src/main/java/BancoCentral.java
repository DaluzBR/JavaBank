import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe para representar o Banco Central (Banco dos bancos).
 * Está classe contém a lista de bancos existente, bem como
 * ajuda nas operações entre bancos diferentes.
 */
public class BancoCentral {
    private final List<Banco> bancos;

    /**
     * Método construtor da classe BancoCentral.
     */
    public BancoCentral() {
        this.bancos = new ArrayList<>();
    }

    /**
     * Método para adicionar um novo banco no registro do Banco Central.
     *
     * @param banco Banco a ser salva no registro do Banco Central.
     */
    public void criarBanco(Banco banco) {
        bancos.add(banco);
    }

    /**
     * Busta um banco por seu número.
     *
     * @param numero Número de identificação do banco.
     * @return Retorna um objeto Banco.
     * @throws UnsupportedOperationException Lança exceção se o banco não for encontrado.
     */
    public Banco getBancoPorNumero(int numero) throws UnsupportedOperationException {

        if (bancos.isEmpty()) {
            throw new UnsupportedOperationException(
                    "[AVISO]\nOperação não realizada!\nMotivo: Lista de contas vazia.");
        }

        List<Banco> bancoList = bancos.stream()
                .filter(banco -> banco.getBancoNumero() == numero)
                .collect(Collectors.toList());

        if (bancoList.isEmpty()) {
            throw new UnsupportedOperationException(
                    "[AVISO]\nOperação não realizada!\nMotivo: Banco " + numero + " não encontrado.");
        }

        return bancoList.get(0);
    }

    /**
     * Lista os nomes dos bancos que é possível acessar por este caixa eletrônico 24hs.
     *
     * @return Retorna uma lista com o nome dos bancos.
     * @throws UnsupportedOperationException Lança exceção se nenhum banco for encontrado.
     */
    public List<String> getListaNomeDeBancos() throws UnsupportedOperationException {
        if (bancos.isEmpty()) {
            throw new UnsupportedOperationException(
                    "[AVISO]\nOperação não realizada!\nMotivo: Lista de bancos vazias.");
        }

        return bancos.stream().map(Banco::toString).collect(Collectors.toList());
    }

    /**
     * Verifica se a conta existe no sistema do banco.
     *
     * @param banco Conta a ser verificada.
     * @return Retorna true se a conta existe, false caso contrário.
     */
    public boolean existebanco(Banco banco) {
        return bancos.contains(banco);
    }

    /**
     * Realiza transferência bancária entre bancos.
     *
     * @param bancoOrig       Banco origem que faz a transferência.
     * @param contaOrig       Conta origem que quer fazer a transferência.
     * @param valor           Valor a ser transferido.
     * @param numeroBancoDest Número do banco destino da transferência.
     * @param numeroContaDest Número da conta destino da transferência.
     * @throws UnsupportedOperationException Lança a exceção se a transferência não for possível.
     */
    public void fazerTransferenciaEntreBancos(
            Banco bancoOrig, Conta contaOrig, double valor,
            int numeroBancoDest, int numeroContaDest
    ) throws UnsupportedOperationException {
        if (!existebanco(bancoOrig)) {
            throw new UnsupportedOperationException(
                    "[AVISO]\nOperação não realizada!\nMotivo: Banco origem não existe.");
        }
        if (!bancoOrig.existeConta(contaOrig)) {
            throw new UnsupportedOperationException(
                    "[AVISO]\nOperação não realizada!\nMotivo: Conta origem não existe.");
        }
        Banco bancoDest = getBancoPorNumero(numeroBancoDest);

        if (bancoOrig.getBancoNumero() == bancoDest.getBancoNumero()) {
            throw new UnsupportedOperationException(
                    "[AVISO]\nOperação não realizada!\nMotivo: Banco origem igual ao banco destino.");
        }

        Conta contaDest = bancoDest.getContaPorNumero(numeroContaDest);

        bancoOrig.fazerTransferenciaExterna(
                contaOrig,
                bancoDest.getBancoNumero(),
                bancoDest.getBancoNome(),
                contaDest.getContaNumero(),
                contaDest.getTitular(),
                valor);

        bancoDest.fazerDepositoInterbancario(
                bancoOrig.getBancoNumero(),
                bancoOrig.getBancoNome(),
                contaOrig.getContaNumero(),
                contaOrig.getTitular(),
                contaDest,
                valor);
    }
}
