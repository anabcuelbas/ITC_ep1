import java.util.Arrays;

public class Autonomo {
    public int qntEstados;
    public int qntSimbolos;
    public int qntTransicoes;
    public int[] estadosAceitacao;
    public int[][] matrizTransicoes;

    public Autonomo(String qntEstados, String qntSimbolos, String qntTransicoes, int[] estadosAceitacao) {
        this.qntEstados = Integer.parseInt(qntEstados);
        this.qntSimbolos = Integer.parseInt(qntSimbolos);
        this.qntTransicoes = Integer.parseInt(qntTransicoes);
        this.estadosAceitacao = estadosAceitacao;

        matrizTransicoes = new int [this.qntEstados][this.qntEstados];
    }

    public void ConstroiMatriz(int linha, int transicao, int coluna) {

        this.matrizTransicoes[linha][coluna] = transicao;
    }

    public boolean AvaliaCadeia(int[] cadeia, int posicaoCadeia, int estadoAtual) {
        int simbolo = cadeia[posicaoCadeia];
        if(posicaoCadeia == (cadeia.length - 1)) { //verifica se está na ultima posição, se continuar é porque não está
            for(int i = 0; i < estadosAceitacao.length; i++) {
                if(estadoAtual == estadosAceitacao[i]) return true;
            }
        }

        for(int i = 0; i < this.qntEstados; i++) {
            if(this.matrizTransicoes[estadoAtual][i] == simbolo) {
                return this.AvaliaCadeia(cadeia, posicaoCadeia++, i);
            }
        }
        return false;
    }
}
