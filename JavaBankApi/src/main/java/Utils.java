import java.util.Locale;

/**
 * Classe utilitária.
 * Possui métodos úteis usados com frequência.
 */
public class Utils {

    /**
     * Formata um valor decimal em formato monetário.
     *
     * @param valor Valor a ser formatado.
     * @return Retorna um texto que exibe o valor em formato monetário.
     */
    public static String toMoney(double valor) {
        return String.format(Locale.US, "R$ %.2f", valor);
    }

    /**
     * Exibe um banner.
     * Usado para tornar as saídas no console mais organizadas.
     * O tamanho do banner é proporcional ao número de caracteres
     * do título.
     *
     * @param title Título a ser exibido no banner.
     */
    public static void banner(String title) {
        String text = "[ " + title + " ]";
        String stars = "#".repeat(text.length() + 22);
        System.out.println("\n" + stars);
        System.out.println("########## " + text + " ##########");
        System.out.println(stars);
    }

}
