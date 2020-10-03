//Dupla: Giovana Armani - 11207686 e Ana Beatriz Cuelbas - 11207881

import java.io.*;

public class afn {
    public int qntEstados;
    public int qntSimbolos;
    public int qntTransicoes;
    public int estadoInicial;
    public int[] estadosAceitacao;
    public int[][] matrizTransicoes;

    public afn(String qntEstados, String qntSimbolos, String qntTransicoes, String estadoInicial, int[] estadosAceitacao) {
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
                                    aceito = AvaliaRecursivo(cadeia, this.matrizTransicoes[2][j], posicaoProximoSimbolo, estadoAtual);
                                    if(aceito) return true;
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
                        aceito = AvaliaRecursivo(cadeia, this.matrizTransicoes[2][j], posicaoProximoSimbolo + 1, -1);
                    } else if(this.matrizTransicoes[1][j] == 0) {
                        aceito = AvaliaRecursivo(cadeia, this.matrizTransicoes[2][j], posicaoProximoSimbolo, estadoAtual);
                    }
    
                    if(aceito) return true;
                }
            }
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        FileReader ler = new FileReader(new File(args[0])); 
        BufferedReader leitor = new BufferedReader(ler);

        FileWriter saida = new FileWriter(new File(args[1]));
        PrintWriter escritor = new PrintWriter(saida);
     
        int qntAutomatos = Integer.parseInt(leitor.readLine());
        for(int i = 0; i < qntAutomatos; i++) {

            String informacoes = leitor.readLine();
            String[] cabecalho = informacoes.split(" ");
            
            String aceitacao = leitor.readLine();
            String[] stringAceitacao = aceitacao.split(" ");
            int estadosAceitacao[] = new int[stringAceitacao.length];
            
            for(int j = 0; j < stringAceitacao.length; j++){
                estadosAceitacao[j] = Integer.parseInt(stringAceitacao[j]);
            }
            
            afn automato = new afn(cabecalho[0], cabecalho[1], cabecalho[2], cabecalho[3], estadosAceitacao);

            int qntTransicoes = Integer.parseInt(cabecalho[2]);
            for(int j = 0; j < qntTransicoes; j++) {
                String transicao = leitor.readLine();
                String[] partesTransicao = transicao.split(" ");

                automato.ConstroiMatriz(j, Integer.parseInt(partesTransicao[0]), Integer.parseInt(partesTransicao[1]), Integer.parseInt(partesTransicao[2]));
            }
            
            int qntCadeias = Integer.parseInt(leitor.readLine());
            for(int j = 0; j < qntCadeias; j++) {
                String cadeia = leitor.readLine();
                String[] vetorCadeia = cadeia.split(" ");
                int[] vetorCadeiaInt = new int[vetorCadeia.length];
                
                for(int k = 0; k < vetorCadeia.length; k++) {
                    vetorCadeiaInt[k] = Integer.parseInt(vetorCadeia[k]);
                }

                Boolean resultado = automato.AvaliaCadeia(vetorCadeiaInt);
                if(resultado) {
                    escritor.print("1");
                } else {
                    escritor.print("0");
                }
                escritor.print(" ");
            }
            escritor.println();
        }
        saida.close();
    }
}
