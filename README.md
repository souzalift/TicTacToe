# 🎮 Jogo da Velha Interativo em Java

Um jogo da velha (tic-tac-toe) em Java com suporte para **1 ou 2 jogadores**, jogabilidade interativa no console, **tabuleiros personalizados (3x3, 5x5 ou 7x7)**, placar acumulado, e jogadas validadas.

## 📦 Funcionalidades

- ✅ Suporte a 1 jogador (contra máquina) ou 2 jogadores humanos
- ✅ Seleção do tamanho do tabuleiro: 3x3, 5x5 ou 7x7
- ✅ Interface textual limpa com **coordenadas visíveis (iniciando em 1)**
- ✅ Prevenção de jogadas inválidas (posição ocupada ou fora dos limites)
- ✅ Validação de símbolos (apenas 1 caractere por jogador, e sem repetição)
- ✅ Máquina com jogada automática aleatória
- ✅ Placar de vitórias acumuladas entre partidas
- ✅ Repetição de partidas sem reiniciar o programa

## 🛠️ Como executar

1. Certifique-se de ter o **Java instalado** (JDK 8+):

```bash
java -version
```

2. Compile o arquivo::

```bash
javac JogoDaVelha.java
```

3. Execute:

```bash
java JogoDaVelha
```

## 💡 Exemplo de uso

```bash
==== JOGO DA VELHA INTERATIVO ====

Digite o nome do Jogador 1: Fábio Sales
Digite o símbolo do Jogador 1 (apenas 1 caractere, ex: X, O, A): X
Deseja jogar contra a máquina? (s/n): s
Selecione o tamanho do tabuleiro (3, 5, 7): 3

=========== TABULEIRO ===========

     1   2   3
    ────────────
 1 │   │   │   │
    ────────────
 2 │   │   │   │
    ────────────
 3 │   │   │   │
    ────────────

Fábio Sales (X), sua vez.
Digite a linha (1 a 3):

```

## ✍️ Autores

Igor Machado — @souzalift

Implementação textual feita com foco didático, validações robustas e organização escalável.

## 📄 Licença

Este projeto é de uso livre para fins educacionais e pode ser adaptado com créditos ao autor.
