/**
 * Trabalho LPA - UCSAL 2025.1
 * Prof. Fabio Sales
 * Tema: Jogo da Velha
 *
 * Alunos:
 * Agnaldo Nascimento
 * Alisson Brito
 * Gustavo Chaves
 * Igor Machado
 * Marcelo Junior
 * Raian Teles
 */
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

        // Validação do símbolo do Jogador 1
        while (true) {
            System.out.print("Digite o símbolo do Jogador 1 (apenas 1 caractere, ex: X, O, A): ");
            simbolo1 = scanner.nextLine().trim();
            if (simbolo1.length() == 1) {
                break;
            } else {
                System.out.println("Símbolo inválido. Digite apenas um único caractere.");
            }
        }

        String resposta;
        while (true) {
            System.out.print("Deseja jogar contra a máquina? (s/n): ");
            resposta = scanner.nextLine().trim().toLowerCase();

            if (resposta.equals("s") || resposta.equals("n")) {
                break;
            } else {
                System.out.println("Entrada inválida. Digite apenas 's' para sim ou 'n' para não.");
            }
        }

        if (resposta.equals("s")) {
            jogador2 = "Máquina";
            simbolo2 = simbolo1.equalsIgnoreCase("O") ? "X" : "O";
            contraMaquina = true;
        } else {
            System.out.print("Digite o nome do Jogador 2: ");
            jogador2 = scanner.nextLine();

            // Validação do símbolo do Jogador 2
            while (true) {
                System.out.print("Digite o símbolo do Jogador 2 (apenas 1 caractere): ");
                simbolo2 = scanner.nextLine().trim();
                if (simbolo2.length() != 1) {
                    System.out.println("Símbolo inválido. Digite apenas um único caractere.");
                } else if (simbolo2.equalsIgnoreCase(simbolo1)) {
                    System.out.println("Símbolo já utilizado pelo Jogador 1. Escolha um símbolo diferente.");
                } else {
                    break;
                }
            }
        }
    }

    // Método para selecionar o tamanho do tabuleiro
    static void selecionarTamanhoTabuleiro() {
        while (true) {
            System.out.print("Selecione o tamanho do tabuleiro (3, 5 ou 7): ");
            String entrada = scanner.nextLine().trim();

            // Verifica se a entrada contém apenas dígitos
            if (entrada.matches("\\d+")) {
                int valor = Integer.parseInt(entrada);
                if (valor == 3 || valor == 5 || valor == 7) {
                    tamanho = valor;
                    break;
                } else {
                    System.out.println("Valor inválido. Escolha apenas entre 3, 5 ou 7.");
                }
            } else {
                System.out.println("Entrada inválida. Digite um número inteiro válido.");
            }
        }
    }

    // Método para inicializar o tabuleiro
    static void inicializarTabuleiro() {
        tabuleiro = new String[tamanho][tamanho];
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                tabuleiro[i][j] = " ";
            }
        }
    }

    // Método para exibir o tabuleiro
    static void exibirTabuleiro() {
        System.out.println("\nTabuleiro:\n");

        // Cabeçalho com índices das colunas (começando do 1)
        System.out.print("    ");
        for (int j = 1; j <= tamanho; j++) {
            System.out.printf(" %2d ", j);
        }
        System.out.println();

        // Linha separadora
        System.out.print("    ");
        for (int j = 0; j < tamanho; j++) {
            System.out.print("----");
        }
        System.out.println();

        // Corpo do tabuleiro com índices das linhas (começando do 1)
        for (int i = 0; i < tamanho; i++) {
            System.out.printf("%2d |", i + 1);
            for (int j = 0; j < tamanho; j++) {
                System.out.printf(" %s |", tabuleiro[i][j]);
            }
            System.out.println();

            // Linha separadora
            System.out.print("    ");
            for (int j = 0; j < tamanho; j++) {
                System.out.print("----");
            }
            System.out.println();
        }
    }

    // Método principal para jogar a partida
    static void jogarPartida() {
        boolean jogador1Vez = true;
        int jogadas = 0;
        while (true) {
            exibirTabuleiro();
            if (jogador1Vez || !contraMaquina) {
                System.out.println(" ");
                if (jogador1Vez) {
                    System.out.println(jogador1 + " (" + simbolo1 + "), sua vez.");
                } else {
                    System.out.println(jogador2 + " (" + simbolo2 + "), sua vez.");
                }
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

    // Método para pedir a posição ao usuário
    static int[] pedirPosicao() {
        int linha, coluna;

        while (true) {
            System.out.print("Digite a linha (1 a " + tamanho + "): ");
            String entradaLinha = scanner.nextLine().trim();

            System.out.print("Digite a coluna (1 a " + tamanho + "): ");
            String entradaColuna = scanner.nextLine().trim();

            // Verifica se ambas as entradas são numéricas
            if (entradaLinha.matches("\\d+") && entradaColuna.matches("\\d+")) {
                linha = Integer.parseInt(entradaLinha) - 1;
                coluna = Integer.parseInt(entradaColuna) - 1;

                if (linha >= 0 && linha < tamanho && coluna >= 0 && coluna < tamanho) {
                    return new int[]{linha, coluna};
                } else {
                    System.out.println("Posição inválida. Digite números entre 1 e " + tamanho + ".");
                }
            } else {
                System.out.println("Entrada inválida. Use apenas números inteiros.");
            }
        }
    }

    // Método para simular a jogada da máquina
    static int[] jogadaDaMaquina() {
        Random aleatorio = new Random();
        int linha, coluna;
        do {
            linha = aleatorio.nextInt(tamanho);
            coluna = aleatorio.nextInt(tamanho);
        } while (!tabuleiro[linha][coluna].equals(" "));
        System.out.println("Máquina jogou em: (" + (linha + 1) + " , " + (coluna + 1) + ")");
        return new int[]{linha, coluna};
    }

    static boolean verificarVitoria(String simbolo) {
        // Verifica linhas e colunas
        for (int i = 0; i < tamanho; i++) {
            boolean linha = true, coluna = true;
            for (int j = 0; j < tamanho; j++) {
                if (!tabuleiro[i][j].equals(simbolo)) {
                    linha = false;
                }
                if (!tabuleiro[j][i].equals(simbolo)) {
                    coluna = false;
                }
            }
            if (linha || coluna) {
                return true;
            }
        }

        // Verifica diagonais
        boolean diagPrincipal = true, diagSecundaria = true;
        for (int i = 0; i < tamanho; i++) {
            if (!tabuleiro[i][i].equals(simbolo)) {
                diagPrincipal = false;
            }
            if (!tabuleiro[i][tamanho - 1 - i].equals(simbolo)) {
                diagSecundaria = false;
            }
        }
        return diagPrincipal || diagSecundaria;
    }

    // Método para exibir o placar
    static void exibirPlacar() {
        System.out.println("\n--- PLACAR ---");
        System.out.println(jogador1 + ": " + vitorias1 + " vitória(s)");
        System.out.println(jogador2 + ": " + vitorias2 + " vitória(s)");
        System.out.println("Empates: " + empates);
    }
}
