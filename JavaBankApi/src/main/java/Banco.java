/**
 * Classe concreta de um banco.
 */
public class Banco extends BancoRef {

    /**
     * Método construtor da classe Banco.
     *
     * @param bancoNumero Número de identificação do banco.
     * @param bancoNome   Nome do banco.
     */
    public Banco(int bancoNumero, String bancoNome) {
        super(bancoNumero, bancoNome);
    }
}
