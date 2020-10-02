public class Automato {
    public int qntEstados;
    public int qntSimbolos;
    public int qntTransicoes;
    public int estadoInicial;
    public int[] estadosAceitacao;
    public int[][] matrizTransicoes;

    public Automato(String qntEstados, String qntSimbolos, String qntTransicoes, String estadoInicial, int[] estadosAceitacao) {
        this.qntEstados = Integer.parseInt(qntEstados);
        this.qntSimbolos = Integer.parseInt(qntSimbolos);
        this.qntTransicoes = Integer.parseInt(qntTransicoes);
        this.estadoInicial = Integer.parseInt(estadoInicial);
        this.estadosAceitacao = estadosAceitacao;

        matrizTransicoes = new int [3][this.qntTransicoes];
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < this.qntTransicoes; j++) {
                matrizTransicoes[i][j] = -1;
            }
        }
    }

    public void ConstroiMatriz(int coluna, int estadoSaida, int simboloTransicao, int estadoEntrada) {
    
        this.matrizTransicoes[0][coluna] = estadoSaida;
        this.matrizTransicoes[1][coluna] = simboloTransicao;
        this.matrizTransicoes[2][coluna] = estadoEntrada;
    }

    public boolean AvaliaCadeia(int[] cadeia) {
        boolean aceito = false;
        aceito = AvaliaRecursivo(cadeia, 0, this.estadoInicial, -1);

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
                if(this.matrizTransicoes[0][i] == estadoAtual) {
                    for(int j = 0; j < this.qntEstados; j++) {
                        if(this.matrizTransicoes[1][j] == simbolo) {
                            if(i != vemDeCadeiaVazia) {
                                aceito = this.AvaliaRecursivo(cadeia, posicaoCadeia + 1, i, -1);
                                if(aceito) {
                                    return true;
                                }
                            }
                        } else if(this.matrizTransicoes[1][j] == 0) {
                            if(i != vemDeCadeiaVazia) {
                                aceito = this.AvaliaRecursivo(cadeia, posicaoCadeia, i, estadoAtual);
                                if(aceito) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
            return false;
        }
    }
}
