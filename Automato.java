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
        for(int i = 0; i < this.qntEstados; i++) {
            for(int j = 0; j < this.qntEstados; j++) {
                matrizTransicoes[i][j] = -1;
            }
        }
    }

    public void ConstroiMatriz(int linha, int transicao, int coluna) {

        this.matrizTransicoes[linha][coluna] = transicao;
    }

    public boolean AvaliaCadeia(int[] cadeia) {
        boolean aceito = false;
        aceito = AvaliaRecursivo(cadeia, 0, 0, -1);

        return aceito;
    }

    private boolean AvaliaRecursivo(int[] cadeia, int posicaoCadeia, int estadoAtual, int vemDeCadeiaVazia) {
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
                    if(i != vemDeCadeiaVazia) {
                        aceito = this.AvaliaRecursivo(cadeia, posicaoCadeia + 1, i, -1);
                        if(aceito) {
                            return true;
                        }
                    }
                } else if(this.matrizTransicoes[estadoAtual][i] == 0) {
                    if(i != vemDeCadeiaVazia) {
                        aceito = this.AvaliaRecursivo(cadeia, posicaoCadeia + 1, i, estadoAtual);
                        if(aceito) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    }
}
