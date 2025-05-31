package TicTacToe;

import java.util.Random;
import java.util.Scanner;

public class JogoDaVelha {

  static char[][] tabuleiro = new char[3][3];
  static char jogadorAtual = 'X';
  static Scanner scanner = new Scanner(System.in);
  static Random random = new Random();
  static boolean contraMaquina = false;

  public static void main(String[] args) {
    boolean jogarNovamente;

    do {
      escolherModoDeJogo();
      reiniciarTabuleiro();
      jogar();
      jogarNovamente = perguntarReinicio();
    } while (jogarNovamente);

    System.out.println("Obrigado por jogar!");
  }

  // Permite ao jogador escolher se quer jogar contra a máquina ou outro jogador
  public static void escolherModoDeJogo() {
    System.out.println("Deseja jogar contra a máquina? (s/n):");
    char resposta = scanner.next().toLowerCase().charAt(0);
    contraMaquina = (resposta == 's');
  }

  // Reinicia o tabuleiro
  public static void reiniciarTabuleiro() {
    for (int linha = 0; linha < 3; linha++) {
      for (int coluna = 0; coluna < 3; coluna++) {
        tabuleiro[linha][coluna] = ' ';
      }
    }
    jogadorAtual = 'X';
  }

  // Exibe o tabuleiro atual
  public static void exibirTabuleiro() {
    System.out.println("  0   1   2");
    for (int linha = 0; linha < 3; linha++) {
      System.out.print(linha + " ");
      for (int coluna = 0; coluna < 3; coluna++) {
        System.out.print(tabuleiro[linha][coluna]);
        if (coluna < 2)
          System.out.print(" | ");
      }
      System.out.println();
      if (linha < 2)
        System.out.println("  ---------");
    }
  }

  // Loop principal da partida
  public static void jogar() {
    boolean jogoAtivo = true;

    while (jogoAtivo) {
      exibirTabuleiro();

      if (contraMaquina && jogadorAtual == 'O') {
        System.out.println("Vez da máquina...");
        jogadaMaquina();
      } else {
        System.out.println("Jogador " + jogadorAtual + ", informe linha e coluna (ex: 1 2):");
        int linha = scanner.nextInt();
        int coluna = scanner.nextInt();

        if (!jogadaValida(linha, coluna)) {
          System.out.println("Jogada inválida, tente novamente.");
          continue;
        }

        tabuleiro[linha][coluna] = jogadorAtual;
      }

      if (verificarVencedor()) {
        exibirTabuleiro();
        System.out.println("Vitória do jogador " + jogadorAtual + "!");
        jogoAtivo = false;
      } else if (verificarEmpate()) {
        exibirTabuleiro();
        System.out.println("Empate!");
        jogoAtivo = false;
      } else {
        alternarJogador();
      }
    }
  }

  // Alterna entre os jogadores X e O
  public static void alternarJogador() {
    jogadorAtual = (jogadorAtual == 'X') ? 'O' : 'X';
  }

  // Verifica se há um vencedor
  public static boolean verificarVencedor() {
    for (int i = 0; i < 3; i++) {
      if (tabuleiro[i][0] == jogadorAtual && tabuleiro[i][1] == jogadorAtual && tabuleiro[i][2] == jogadorAtual)
        return true;
      if (tabuleiro[0][i] == jogadorAtual && tabuleiro[1][i] == jogadorAtual && tabuleiro[2][i] == jogadorAtual)
        return true;
    }
    if (tabuleiro[0][0] == jogadorAtual && tabuleiro[1][1] == jogadorAtual && tabuleiro[2][2] == jogadorAtual)
      return true;
    if (tabuleiro[0][2] == jogadorAtual && tabuleiro[1][1] == jogadorAtual && tabuleiro[2][0] == jogadorAtual)
      return true;

    return false;
  }

  // Verifica se o jogo terminou em empate
  public static boolean verificarEmpate() {
    for (int linha = 0; linha < 3; linha++) {
      for (int coluna = 0; coluna < 3; coluna++) {
        if (tabuleiro[linha][coluna] == ' ')
          return false;
      }
    }
    return true;
  }

  // Executa jogada aleatória válida da máquina
  public static void jogadaMaquina() {
    int linha, coluna;

    do {
      linha = random.nextInt(3);
      coluna = random.nextInt(3);
    } while (!jogadaValida(linha, coluna));

    tabuleiro[linha][coluna] = jogadorAtual;
    System.out.println("Máquina jogou na posição: " + linha + " " + coluna);
  }

  // Verifica se a jogada é válida
  public static boolean jogadaValida(int linha, int coluna) {
    return linha >= 0 && linha < 3 && coluna >= 0 && coluna < 3 && tabuleiro[linha][coluna] == ' ';
  }

  // Pergunta ao jogador se deseja jogar novamente
  public static boolean perguntarReinicio() {
    System.out.println("Deseja jogar novamente? (s/n):");
    char resposta = scanner.next().toLowerCase().charAt(0);
    return resposta == 's';
  }
}
