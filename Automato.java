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
        int simbolo = cadeia[posicaoCadeia];
        boolean aceito = false;

        if(posicaoCadeia == cadeia.length) {
            int [] ultimosEstados = new int [this.qntTransicoes];

            for(int i = 0; i < this.qntTransicoes; i++) {
                if(this.matrizTransicoes[0][i] == estadoAtual) {
                    if(this.matrizTransicoes[1][i] == simbolo){
                        ultimosEstados[i] = this.matrizTransicoes[2][i];
                    }
                }
            }
            for(int i = 0; i < this.qntTransicoes; i++) {
                if(estadoAtual == ultimosEstados[i]) {
                    return true;
                }
            }
            return false;
        } else {
            for(int j = 0; j < this.qntTransicoes; j++) {
                if(this.matrizTransicoes[0][j] == estadoAtual) {
                    if(this.matrizTransicoes[1][j] == simbolo) {
                        if(this.matrizTransicoes[0][j] != vemDeCadeiaVazia) {
                            aceito = this.AvaliaRecursivo(cadeia, posicaoCadeia + 1, this.matrizTransicoes[2][j], -1);
                            if(aceito) {
                                return true;
                            }
                        }
                    } else if(this.matrizTransicoes[1][j] == 0) {
                        if(this.matrizTransicoes[0][j] != vemDeCadeiaVazia) {
                            aceito = this.AvaliaRecursivo(cadeia, posicaoCadeia, this.matrizTransicoes[2][j], estadoAtual);
                            if(aceito) {
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }
    }
}
