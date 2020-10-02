public class Automato {
    public int qntEstados;
    public int qntSimbolos;
    public int qntTransicoes;
    public int[] estadosAceitacao;
    public int[][] matrizTransicoes;

    public Automato(String qntEstados, String qntSimbolos, String qntTransicoes, int[] estadosAceitacao) {
        this.qntEstados = Integer.parseInt(qntEstados);
        this.qntSimbolos = Integer.parseInt(qntSimbolos);
        this.qntTransicoes = Integer.parseInt(qntTransicoes);
        this.estadosAceitacao = estadosAceitacao;

        matrizTransicoes = new int [this.qntEstados][this.qntEstados];
    }

    public void ConstroiMatriz(int linha, int transicao, int coluna) {

        this.matrizTransicoes[linha][coluna] = transicao;
    }

    public boolean AvaliaCadeia(int[] cadeia) {
        boolean aceito = false;
        aceito = AvaliaRecursivo(cadeia, 0, 0);

        return aceito;
    }

    private boolean AvaliaRecursivo(int[] cadeia, int posicaoCadeia, int estadoAtual) {
        //Problema 1: o estado atual tá sempre atrasado, no fim ele para no estado anterior do que deveria (resolvi feiamente)
        //Problema 2: A matriz no java é inicializada com 0, e o símbolo vazio também é representado com 0
        //Problema 3: Tratar o caso da transição vazia - não pode avançar a posicaoCadeia
        //Problema 4: Evitar loop quando o símbolo é vazio
        int simbolo;
        boolean aceito = false;

        if(posicaoCadeia < cadeia.length) {
            simbolo = cadeia[posicaoCadeia];
        } else {
            simbolo = cadeia[posicaoCadeia - 1];
        }

        if(posicaoCadeia == cadeia.length) {
            for(int i = 0; i < estadosAceitacao.length; i++) {
                if(estadoAtual == estadosAceitacao[i]) {
                    return true;
                }
            }
            return false;
        } else {
            for(int i = 0; i < this.qntEstados; i++) {
                if(this.matrizTransicoes[estadoAtual][i] == simbolo) {
                    aceito = this.AvaliaRecursivo(cadeia, posicaoCadeia + 1, i);
                    if(aceito) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
