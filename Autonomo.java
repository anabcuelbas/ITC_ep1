
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

    public void AvaliaCadeia(int[] cadeia) {
        System.out.println("nois");
    }
}
