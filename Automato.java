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

    public void ConstroiMatriz(int coluna, int estadoInicial, int simboloTransicao, int estadoFinal) {
    
        this.matrizTransicoes[0][coluna] = estadoInicial;
        this.matrizTransicoes[1][coluna] = simboloTransicao;
        this.matrizTransicoes[2][coluna] = estadoFinal;
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
            //System.out.println("Entrou no else do simbolo");
            simbolo = cadeia[posicaoCadeia - 1];
        }

        //System.out.print(estadoAtual + " leu " + simbolo + " -> ");
        //System.out.println("Estado Atual: " + estadoAtual);
        //System.out.println("Simbolo Lido: " + simbolo);

        if(posicaoCadeia == cadeia.length) {
            //System.out.println("Entrou na confirmacao de ultima posicao");
            for(int i = 0; i < estadosAceitacao.length; i++) {
                if(estadoAtual == estadosAceitacao[i]) {
                    /* System.out.println("Cadeia foi aceita");
                    System.out.println(); */
                    return true;
                }
            }
            //System.out.println();
            return false;
        } else {
            for(int j = 0; j < this.qntTransicoes; j++) {
                if(this.matrizTransicoes[0][j] == estadoAtual) {
                    if(this.matrizTransicoes[1][j] == simbolo) {
                        if(this.matrizTransicoes[0][j] != vemDeCadeiaVazia) {
                            //System.out.println("Chamou recursao normal");
                            aceito = this.AvaliaRecursivo(cadeia, posicaoCadeia + 1, this.matrizTransicoes[2][j], -1);
                            if(aceito) {
                                return true;
                            }
                        }
                    } else if(this.matrizTransicoes[1][j] == 0) {
                        //System.out.println("Entrou aqui!!!");
                        if(this.matrizTransicoes[0][j] != vemDeCadeiaVazia) {
                            //System.out.println("Chamou recursao vazia");
                            aceito = this.AvaliaRecursivo(cadeia, posicaoCadeia, this.matrizTransicoes[2][j], estadoAtual);
                            if(aceito) {
                                return true;
                            }
                        }
                    }
                }
            }
            //System.out.println();
            return false;
        }
    }
}
