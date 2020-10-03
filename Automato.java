import java.util.LinkedList;

public class Automato {
    public int qntEstados;
    public int qntSimbolos;
    public int qntTransicoes;
    public int estadoInicial;
    public int[] estadosAceitacao;
    public int[][] matrizTransicoes;
    //public List<Integer> ultimosEstados = new LinkedList<Integer>();

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

        if(posicaoCadeia == (cadeia.length - 1)) {
            LinkedList ultimosEstados = new LinkedList<Integer>();
            
            for(int i = 0; i < this.qntTransicoes; i++) {
                if(this.matrizTransicoes[0][i] == estadoAtual) {
                    if(this.matrizTransicoes[1][i] == simbolo){
                        ultimosEstados = estadosPorCadeiaVazia(estadoAtual, estadoAtual, this.matrizTransicoes[2][i], this.matrizTransicoes);
                    }
                }
            }
            for(int i = 0; i < ultimosEstados.size(); i++){
                for(int j = 0; j < this.estadosAceitacao.length; j++) {
                    if(ultimosEstados.get(i).equals(estadosAceitacao[j])) {
                        return true;
                    }
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

    private LinkedList estadosPorCadeiaVazia(int estadoOriginal, int estadoSaida, int estadoEntrada, int[][] matrizDoAutomato) {
        LinkedList ultimosEstados = new LinkedList<Integer>();
        LinkedList proximoDoProximo = new LinkedList<Integer>();
        ultimosEstados.add(estadoSaida);
        for(int i = 0; i < this.qntTransicoes; i++) {
            if(matrizDoAutomato[0][i] == estadoEntrada && matrizDoAutomato[1][i] == 0) {
                if(matrizDoAutomato[2][i] != estadoOriginal) {
                    proximoDoProximo = estadosPorCadeiaVazia(estadoOriginal, estadoEntrada, matrizDoAutomato[2][i], matrizDoAutomato);
                    ultimosEstados.add(proximoDoProximo);
                    return ultimosEstados;
                }
            }
        }
        return ultimosEstados;
    }
}
