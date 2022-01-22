import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Classe que simula um caixa eletrônico 24hs. É pelo caixa eletrônico
 * que o usuário interage com os serviços dos bancos normalmente.
 */
public class CaixaEletronico24Hs {

    private final Scanner input;
    private final BancoCentral bancoCentral;

    /**
     * Construtor da classe CaixaEletronico24Hs.
     */
    public CaixaEletronico24Hs() {
        this.bancoCentral = new BancoCentral();
        this.input = new Scanner(System.in);
        this.input.useLocale(Locale.US);
        configuracao();
    }

    /**
     * Tela inicial do caixa eletrônico. Mostra uma lista de bancos
     * que o usuário pode acessar.
     */
    public void mostrarTelaInicial() {
        List<String> bancoList = this.bancoCentral.getListaNomeDeBancos();
        int opcao = 0;

        do {
            Utils.banner("Caixa eletrônico 24 Hs");
            System.out.println("[Lista de bancos]\n");
            bancoList.forEach(System.out::println);
            System.out.println("[-1] Sair");
            System.out.print("|--> ");

            // Ler entrada da opção.
            try {
                opcao = Integer.parseInt(input.next());
                if (opcao == -1) {
                    continue;
                }
            } catch (NumberFormatException e) {
                continue;
            }

            try {
                mostrarTelaBanco(this.bancoCentral.getBancoPorNumero(opcao));
            } catch (UnsupportedOperationException e) {
                System.err.println(e.getMessage());
            }

        } while (opcao != -1);

        input.close();

        // Mostra a tela final do caixa eletrônico.
        mostrarTelaFinal();
    }

    /**
     * Tela de um banco específico.
     * Pede o número da conta e a senha do usuário.
     *
     * @param banco Banco onde o cliente tem uma conta.
     */
    private void mostrarTelaBanco(Banco banco) {
        Conta conta;
        int contaNumero;
        int senha;

        Utils.banner(banco.getBancoNome());
        System.out.println("[Acesso a conta]\n");

        try {
            System.out.print("Número da conta: ");
            contaNumero = Integer.parseInt(input.next());
            System.out.print("Senha (4 dígitos): ");
            senha = Integer.parseInt(input.next());

            conta = banco.getContaPorNumero(contaNumero);
            if (conta.verificaSenha(senha)) {
                mostrarTelaConta(banco, conta);
            } else {
                System.err.println("\n[AVISO]\nOperação não realizada!\nMotivo: Senha incorreta.\n");
            }
        } catch (NumberFormatException e) {
            System.err.println("\n[AVISO]\nOperação não realizada!\nMotivo: Entrada incorreta.\n");
        } catch (UnsupportedOperationException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Tela de acesso à conta que o cliente tem em um banco.
     *
     * @param banco Banco onde o cliente tem uma conta.
     * @param conta Conta bancário do cliente.
     */
    private void mostrarTelaConta(Banco banco, Conta conta) {
        int opcao = 0;

        do {
            Utils.banner(banco.getBancoNome() + " - " + conta.getContaTipoText());
            System.out.println("[Olá, " + conta.getPrimeiroNome() + "]\n");
            System.out.println("[0] Consultar Saldo");
            System.out.println("[1] Consultar Extrato");
            System.out.println("[2] Saque");
            System.out.println("[3] Depósito");
            System.out.println("[4] Transferência interna");
            System.out.println("[5] Transferência externa");
            System.out.println("[-1] Sair da conta");
            System.out.print("|--> ");

            // Ler entrada da opção.
            try {
                opcao = Integer.parseInt(input.next());
            } catch (NumberFormatException ignored) {
            }

            switch (opcao) {
                case 0 -> conta.imprimirSaldo();
                case 1 -> conta.imprimirExtrato();
                case 2 -> mostrarTelaSaque(banco, conta);
                case 3 -> mostrarTelaDeposito(banco, conta);
                case 4 -> mostrarTelaTranferenciaInterna(banco, conta);
                case 5 -> mostrarTelaTranferenciaExterna(banco, conta);
            }
        } while (opcao != -1);
    }


    /**
     * Tela de acesso à operação de saque da conta do cliente.
     *
     * @param banco Banco onde o cliente tem uma conta.
     * @param conta Conta bancário do cliente.
     */
    private void mostrarTelaSaque(Banco banco, Conta conta) {
        double valor;
        int senha;

        Utils.banner(banco.getBancoNome() + " - " + conta.getContaTipoText());
        System.out.println("[Olá, " + conta.getPrimeiroNome() + "]");
        System.out.println("[Operação de saque]\n");

        try {
            System.out.print("Valor do saque: ");
            valor = Double.parseDouble(input.next());
            System.out.print("Senha (4 dígitos): ");
            senha = Integer.parseInt(input.next());

            if (conta.verificaSenha(senha)) {
                banco.fazerSaque(conta, valor);
                System.out.println("\nSaque de " + Utils.toMoney(valor) + " realizado.\n");
            } else {
                System.err.println("\n[AVISO]\nOperação não realizada!\nMotivo: Senha incorreta.\n");
            }
        } catch (NumberFormatException e) {
            System.err.println("\n[AVISO]\nOperação não realizada!\nMotivo: Entrada incorreta.\n");
        } catch (UnsupportedOperationException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Tela de acesso à operação de depósito da conta do cliente.
     *
     * @param banco Banco onde o cliente tem uma conta.
     * @param conta Conta bancário do cliente.
     */
    private void mostrarTelaDeposito(Banco banco, Conta conta) {
        double valor;
        int senha;

        Utils.banner(banco.getBancoNome() + " - " + conta.getContaTipoText());
        System.out.println("[Olá, " + conta.getPrimeiroNome() + "]");
        System.out.println("[Operação de depósito]\n");

        try {
            System.out.print("Valor do deposito: ");
            valor = Double.parseDouble(input.next());
            System.out.print("Senha (4 dígitos): ");
            senha = Integer.parseInt(input.next());

            if (conta.verificaSenha(senha)) {
                banco.fazerDeposito(conta, conta, valor);
                System.out.println("\nDepósito de " + Utils.toMoney(valor) + " realizado.\n");
            } else {
                System.err.println("\n[AVISO]\nOperação não realizada!\nMotivo: Senha incorreta.\n");
            }
        } catch (NumberFormatException e) {
            System.err.println("\n[AVISO]\nOperação não realizada!\nMotivo: Entrada incorreta.\n");
        } catch (UnsupportedOperationException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Tela de acesso à operação de transferência interna da conta do cliente.
     * Realiza a transferência entre contas no mesmo banco.
     *
     * @param banco Banco onde o cliente tem uma conta.
     * @param conta Conta bancário do cliente.
     */
    private void mostrarTelaTranferenciaInterna(Banco banco, Conta conta) {
        double valor;
        int contaNumero;
        int senha;

        Utils.banner(banco.getBancoNome() + " - " + conta.getContaTipoText());
        System.out.println("[Olá, " + conta.getPrimeiroNome() + "]");
        System.out.println("[Operação de transferência]\n");

        try {
            System.out.print("Número da conta destino: ");
            contaNumero = Integer.parseInt(input.next());
            System.out.print("Valor da transferência: ");
            valor = Double.parseDouble(input.next());
            System.out.print("Senha (4 dígitos): ");
            senha = Integer.parseInt(input.next());

            Conta contaDest = banco.getContaPorNumero(contaNumero);
            if (conta.verificaSenha(senha)) {
                banco.fazerTransferenciaInterna(conta, contaDest, valor);
                System.out.println("\nTranferência de " + Utils.toMoney(valor) + " realizada.\n");
            } else {
                System.err.println("\n[AVISO]\nOperação não realizada!\nMotivo: Senha incorreta.\n");
            }
        } catch (NumberFormatException e) {
            System.err.println("\n[AVISO]\nOperação não realizada!\nMotivo: Entrada incorreta.\n");
        } catch (UnsupportedOperationException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Tela de acesso à operação de transferência externa da conta do cliente.
     * Realiza a transferência entre contas de bancos diferentes.
     *
     * @param bancoOrig Banco onde o cliente tem uma conta.
     * @param contaOrig Conta bancário do cliente.
     */
    private void mostrarTelaTranferenciaExterna(Banco bancoOrig, Conta contaOrig) {
        double valor;
        int bancoNumeroDest;
        int contaNumeroDest;
        int senha;

        Utils.banner(bancoOrig.getBancoNome() + " - " + contaOrig.getContaTipoText());
        System.out.println("[Olá, " + contaOrig.getPrimeiroNome() + "]");
        System.out.println("[Operação de transferência - externa]");
        System.out.println("[TAXA: " + Utils.toMoney(Banco.TAXA_TRANFERENCIA_ENTRE_BANCOS) + "]\n");

        try {
            System.out.print("Número do banco destino: ");
            bancoNumeroDest = Integer.parseInt(input.next());
            System.out.print("Número da conta destino: ");
            contaNumeroDest = Integer.parseInt(input.next());
            System.out.print("Valor da transferência: ");
            valor = Double.parseDouble(input.next());
            System.out.print("Senha (4 dígitos): ");
            senha = Integer.parseInt(input.next());

            if (contaOrig.verificaSenha(senha)) {
                this.bancoCentral.fazerTransferenciaEntreBancos(bancoOrig, contaOrig, valor, bancoNumeroDest, contaNumeroDest);
                System.out.println("\nTransferência externa de " + Utils.toMoney(valor) + " realizada.\n");
            } else {
                System.err.println("\n[AVISO]\nOperação não realizada!\nMotivo: Senha incorreta.\n");
            }
        } catch (NumberFormatException e) {
            System.err.println("\n[AVISO]\nOperação não realizada!\nMotivo: Entrada incorreta.\n");
        } catch (UnsupportedOperationException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Mostra a tela final do caixa eletrônico 24hs.
     * Tela mostrada no encerramento do programa.
     */
    private void mostrarTelaFinal() {
        Utils.banner("Caixa eletrônico 24 Hs");
        System.out.println("[Tela final]\n");
        System.out.println("Obrigado por usar nossos serviços.\n");
        System.out.println("Volte sempre!");
        System.out.println("[FIM]");
    }

    /**
     * Configura alguns objetos para ser possível testar o programa.
     */
    private void configuracao() {
        // Código do Banco = BB
        // Código do Tipo da conta = T
        // Código da Conta corrente = BB1CC
        // Código da Conta poupança = BB2CP

        // Banco Tetra
        Banco bt = new Banco(11, "Banco Tetra");
        this.bancoCentral.criarBanco(bt);
        int bNumero = bt.getBancoNumero();
        String bNome = bt.getBancoNome();
        bt.criarConta(new ContaCorrente(bNumero, 11101, "Cláudio André Mergen Taffarel", bNome, 5000.0, 1234));
        bt.criarConta(new ContaCorrente(bNumero, 11102, "Jorge de Amorim Campos", bNome, 1200.0, 1234));
        bt.criarConta(new ContaCorrente(bNumero, 11103, "Ricardo Roberto Barreto da Rocha", bNome, 12000, 1234));
        bt.criarConta(new ContaCorrente(bNumero, 11104, "Ronaldo Luís Nazário de Lima", bNome, 6500.0, 1234));
        bt.criarConta(new ContaCorrente(bNumero, 11105, "Mauro da Silva Gomes", bNome, 7565.0, 1234));
        bt.criarConta(new ContaPoupanca(bNumero, 11201, "Carlos Caetano Bledorn Verri", bNome, 2500.0, 1234));
        bt.criarConta(new ContaPoupanca(bNumero, 11202, "Leonardo Nascimento de Araújo", bNome, 3600.0, 1234));
        bt.criarConta(new ContaPoupanca(bNumero, 11203, "Raí Souza Vieira de Oliveira", bNome, 2400.0, 1234));
        bt.criarConta(new ContaPoupanca(bNumero, 11204, "Romário de Souza Faria", bNome, 5400.0, 1234));

        // Banco Intelectual
        Banco bi = new Banco(12, "Banco Intelectual");
        this.bancoCentral.criarBanco(bi);
        bNumero = bi.getBancoNumero();
        bNome = bi.getBancoNome();
        bi.criarConta(new ContaCorrente(bNumero, 12101, "Marie Sklodowska Curie", bNome, 4000.0, 1234));
        bi.criarConta(new ContaCorrente(bNumero, 12102, "Pierre Curie", bNome, 3200.0, 1234));
        bi.criarConta(new ContaCorrente(bNumero, 12103, "Antoine Henri Becquerel", bNome, 1700, 1234));
        bi.criarConta(new ContaCorrente(bNumero, 12104, "Joseph John Thomson", bNome, 4500.0, 1234));
        bi.criarConta(new ContaPoupanca(bNumero, 12201, "Guglielmo Marconi", bNome, 5500.0, 1234));
        bi.criarConta(new ContaPoupanca(bNumero, 12202, "Nikola Tesla", bNome, 2500.0, 1234));
        bi.criarConta(new ContaPoupanca(bNumero, 12203, "Albert Einstein", bNome, 5500.0, 1234));

        // Banco Humor
        Banco bh = new Banco(13, "Banco Humor");
        this.bancoCentral.criarBanco(bh);
        bNumero = bh.getBancoNumero();
        bNome = bh.getBancoNome();
        bh.criarConta(new ContaCorrente(bNumero, 13101, "José Thomaz da Cunha Vasconcellos Neto", bNome, 7500.0, 1234));
        bh.criarConta(new ContaCorrente(bNumero, 13102, "Francisco Anysio de Oliveira Paula Filho", bNome, 4500.0, 1234));
        bh.criarConta(new ContaCorrente(bNumero, 13103, "José Ronald Golias", bNome, 2200, 1234));
        bh.criarConta(new ContaPoupanca(bNumero, 13201, "Lírio Mário Da Costa", bNome, 2800.0, 1234));
        bh.criarConta(new ContaPoupanca(bNumero, 13202, "Ary Christoni de Toledo Piza", bNome, 1700.0, 1234));

    }
}