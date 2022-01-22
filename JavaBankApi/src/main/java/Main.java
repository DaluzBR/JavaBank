/**
 * Classe principal deste programa que simula um banco digital.
 * É a classe que possui métodos por onde a execução do
 * programa começa.
 */
public class Main {

    public static void main(String[] args) {
        // Cria um objeto caixa eletrônico 24Hs.
        CaixaEletronico24Hs cx = new CaixaEletronico24Hs();
        // Mostra a tela inicial do caixa eletrônico 24Hs.
        cx.mostrarTelaInicial();
    }
}
