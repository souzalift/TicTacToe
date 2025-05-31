package TicTacToe;
import java.util.Random;
import java.util.Scanner;

public class JogoDaVelha {
    static Scanner scanner = new Scanner(System.in);
    static String[][] tabuleiro;
    static int tamanho = 3; // padrão 3x3
    static String jogador1, jogador2;
    static String simbolo1 = "X", simbolo2 = "O";
    static int vitorias1 = 0, vitorias2 = 0, empates = 0;
    static boolean contraMaquina = false;

    public static void main(String[] args) {
        System.out.println("==== JOGO DA VELHA INTERATIVO ====");
        iniciarJogo();
    }

    static void iniciarJogo() {
        configurarJogadores();
        selecionarTamanhoTabuleiro();

        String jogarNovamente;
        do {
            inicializarTabuleiro();
            jogarPartida();
            exibirPlacar();

            // VALIDAÇÃO DA RESPOSTA
            while (true) {
                System.out.print("Deseja jogar novamente? (s/n): ");
                jogarNovamente = scanner.nextLine().trim().toLowerCase();
                if (jogarNovamente.equals("s") || jogarNovamente.equals("n")) {
                    break;
                } else {
                    System.out.println("Entrada inválida. Digite apenas 's' para sim ou 'n' para não.");
                }
            }

        } while (jogarNovamente.equals("s"));


        System.out.println("Obrigado por jogar!");
    }

    static void configurarJogadores() {
        System.out.print("Digite o nome do Jogador 1: ");
        jogador1 = scanner.nextLine();
        System.out.print("Digite o símbolo do Jogador 1 (ex: A, X): ");
        simbolo1 = scanner.nextLine();

        System.out.print("Deseja jogar contra a máquina? (s/n): ");
        String resposta = scanner.nextLine();
        if (resposta.equalsIgnoreCase("s")) {
            jogador2 = "Máquina";
            simbolo2 = simbolo1.equals("O") ? "X" : "O";
            contraMaquina = true;
        } else {
            System.out.print("Digite o nome do Jogador 2: ");
            jogador2 = scanner.nextLine();
            System.out.print("Digite o símbolo do Jogador 2: ");
            simbolo2 = scanner.nextLine();
        }
    }

    static void selecionarTamanhoTabuleiro() {
        System.out.print("Selecione o tamanho do tabuleiro (3, 5, 7): ");
        tamanho = Integer.parseInt(scanner.nextLine());
        if (tamanho != 3 && tamanho != 5 && tamanho != 7) {
            System.out.println("Tamanho inválido. Usando padrão 3x3.");
            tamanho = 3;
        }
    }

    static void inicializarTabuleiro() {
        tabuleiro = new String[tamanho][tamanho];
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                tabuleiro[i][j] = " ";
            }
        }
    }

    static void exibirTabuleiro() {
        System.out.println("\nTabuleiro:");
        System.out.println(" ");
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                System.out.print(" " + tabuleiro[i][j] + " ");
                if (j < tamanho - 1) System.out.print("|");
            }
            System.out.println();
            if (i < tamanho - 1) {
                for (int j = 0; j < tamanho; j++) {
                    System.out.print("---");
                    if (j < tamanho - 1) System.out.print("+");
                }
                System.out.println();
            }
        }
    }

    static void jogarPartida() {
        boolean jogador1Vez = true;
        int jogadas = 0;
        while (true) {
            exibirTabuleiro();
            if (jogador1Vez || !contraMaquina) {
            	    System.out.println(" ");
                if (jogador1Vez)
                    System.out.println(jogador1 + " (" + simbolo1 + "), sua vez.");
                else
                    System.out.println(jogador2 + " (" + simbolo2 + "), sua vez.");
            }

            int[] posicao;
            if (!jogador1Vez && contraMaquina) {
                posicao = jogadaDaMaquina();
            } else {
                posicao = pedirPosicao();
            }

            String simboloAtual = jogador1Vez ? simbolo1 : simbolo2;
            if (tabuleiro[posicao[0]][posicao[1]].equals(" ")) {
                tabuleiro[posicao[0]][posicao[1]] = simboloAtual;
                jogadas++;

                if (verificarVitoria(simboloAtual)) {
                    exibirTabuleiro();
                    if (jogador1Vez) {
                        System.out.println(jogador1 + " venceu!");
                        vitorias1++;
                    } else {
                        System.out.println(jogador2 + " venceu!");
                        vitorias2++;
                    }
                    break;
                }

                if (jogadas == tamanho * tamanho) {
                    exibirTabuleiro();
                    System.out.println("Empate!");
                    empates++;
                    break;
                }

                jogador1Vez = !jogador1Vez;
            } else {
                System.out.println("Posição já ocupada! Tente novamente.");
            }
        }
    }

    static int[] pedirPosicao() {
        int linha, coluna;
        while (true) {
            try {
                System.out.print("Digite a linha (0 a " + (tamanho - 1) + "): ");
                linha = Integer.parseInt(scanner.nextLine());
                System.out.print("Digite a coluna (0 a " + (tamanho - 1) + "): ");
                coluna = Integer.parseInt(scanner.nextLine());

                if (linha >= 0 && linha < tamanho && coluna >= 0 && coluna < tamanho)
                    return new int[]{linha, coluna};
                else
                    System.out.println("Posição inválida.");
            } catch (Exception e) {
                System.out.println("Entrada inválida, use apenas números.");
            }
        }
    }

    static int[] jogadaDaMaquina() {
        Random rand = new Random();
        int linha, coluna;
        do {
            linha = rand.nextInt(tamanho);
            coluna = rand.nextInt(tamanho);
        } while (!tabuleiro[linha][coluna].equals(" "));
        System.out.println("Máquina jogou em: (" + linha + ", " + coluna + ")");
        return new int[]{linha, coluna};
    }

    static boolean verificarVitoria(String simbolo) {
        // Verifica linhas e colunas
        for (int i = 0; i < tamanho; i++) {
            boolean linha = true, coluna = true;
            for (int j = 0; j < tamanho; j++) {
                if (!tabuleiro[i][j].equals(simbolo)) linha = false;
                if (!tabuleiro[j][i].equals(simbolo)) coluna = false;
            }
            if (linha || coluna) return true;
        }

        // Verifica diagonais
        boolean diagPrincipal = true, diagSecundaria = true;
        for (int i = 0; i < tamanho; i++) {
            if (!tabuleiro[i][i].equals(simbolo)) diagPrincipal = false;
            if (!tabuleiro[i][tamanho - 1 - i].equals(simbolo)) diagSecundaria = false;
        }
        return diagPrincipal || diagSecundaria;
    }

    static void exibirPlacar() {
        System.out.println("\n--- PLACAR ---");
        System.out.println(jogador1 + ": " + vitorias1 + " vitória(s)");
        System.out.println(jogador2 + ": " + vitorias2 + " vitória(s)");
        System.out.println("Empates: " + empates);
    }
}
