public class AutomatoAna {
    public int qntEstados;
    public int qntSimbolos;
    public int qntTransicoes;
    public int estadoInicial;
    public int[] estadosAceitacao;
    public int[][] matrizTransicoes;

    public AutomatoAna(String qntEstados, String qntSimbolos, String qntTransicoes, String estadoInicial, int[] estadosAceitacao) {
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

    public void ConstroiMatriz(int coluna, int estadoInicial, int simboloTransicao, int estadoFinal) {
    
        this.matrizTransicoes[0][coluna] = estadoInicial;
        this.matrizTransicoes[1][coluna] = simboloTransicao;
        this.matrizTransicoes[2][coluna] = estadoFinal;
    }

    public boolean AvaliaCadeia(int[] cadeia) {
        boolean aceito = false;

        aceito = AvaliaRecursivo(cadeia, this.estadoInicial, 0, -1);

        return aceito;
    }

    private boolean AvaliaRecursivo(int[] cadeia, int estadoAtual, int posicaoProximoSimbolo, int vemDeCadeiaVazia) {
        int simbolo = -1;
        boolean aceito = false;

        if(posicaoProximoSimbolo < cadeia.length) {
            simbolo = cadeia[posicaoProximoSimbolo];
        }

        if(cadeia[0] == 0) {
            for(int i = 0; i < this.estadosAceitacao.length; i++) {
                if(estadoAtual == this.estadosAceitacao[i]) return true;
            }
        }

        if(posicaoProximoSimbolo >= cadeia.length) {
            for(int i = 0; i < this.estadosAceitacao.length; i++) {
                if(estadoAtual == this.estadosAceitacao[i]) {
                    return true;
                } else {
                    for(int j = 0; j < this.qntTransicoes; j++) {
                        if(this.matrizTransicoes[0][j] == estadoAtual) {
                            if(this.matrizTransicoes[1][j] == 0) {
                                if(this.matrizTransicoes[2][j] == this.estadosAceitacao[i]) {
                                    if(this.matrizTransicoes[2][j] != vemDeCadeiaVazia) {
                                        aceito = AvaliaRecursivo(cadeia, this.matrizTransicoes[2][j], posicaoProximoSimbolo, estadoAtual);
                                        if(aceito) return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return false;
        } else {
            for(int j = 0; j < this.qntTransicoes; j++) {
                if(this.matrizTransicoes[0][j] == estadoAtual) {
                    if(this.matrizTransicoes[1][j] == simbolo) {
                        if(this.matrizTransicoes[2][j] != vemDeCadeiaVazia) {
                            aceito = AvaliaRecursivo(cadeia, this.matrizTransicoes[2][j], posicaoProximoSimbolo + 1, -1);
                        }
                    } else if(this.matrizTransicoes[1][j] == 0) {
                        if(this.matrizTransicoes[2][j] != vemDeCadeiaVazia) {
                            aceito = AvaliaRecursivo(cadeia, this.matrizTransicoes[2][j], posicaoProximoSimbolo, estadoAtual);
                        }
                    }
    
                    if(aceito) return true;
                }
            }
            return false;
        }
    }
}
