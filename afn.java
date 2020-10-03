//Dupla: Giovana Armani - 11207686 e Ana Beatriz Cuelbas - 11207881

import java.io.*;

/**
	Esta classe representa os autômatos. O método princial
	instancia um objeto deste tipo para cada autômato a ser testado.
*/

public class afn {
    public int qntEstados;
    public int qntSimbolos;
    public int qntTransicoes;
    public int estadoInicial;
    public int[] estadosAceitacao;
    public int[][] matrizTransicoes;

    /**
        Construtor da classe afn. São registrados recuperados do arquivo de testes e a matriz de 
        transições é inicializada com -1 para que não houvesse conflito com a string vazia representada por 0.
	*/

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

    /**
		Método chamado para inserir cada transição passada no arquivo de teste na matriz de transições.
		
        @param coluna indica em qual coluna da matriz os valores deverão ser inseridos de acordo com a linha do arquivo lida.
        @param estadoInicial índice do estado corrente que será salvo na primeira linha.
        @param simboloTransicao índice do símbolo que será salvo na segunda linha.
        @param estadoFinal índice do estado novo que será salvo na terceira linha.
	*/

    public void ConstroiMatriz(int coluna, int estadoInicial, int simboloTransicao, int estadoFinal) {
    
        this.matrizTransicoes[0][coluna] = estadoInicial;
        this.matrizTransicoes[1][coluna] = simboloTransicao;
        this.matrizTransicoes[2][coluna] = estadoFinal;
    }

    /**
        Método chamado para iniciar a avaliação de uma cadeia, buscando o estado inicial do autômato e 
        chamando o método recursivo que fará a avaliação.
		
        @param cadeia cadeia a ser avaliada.
        @return um valor booleano que indica se a cadeia é aceita pelo autômato (true) ou não (false).
	*/

    public boolean AvaliaCadeia(int[] cadeia) {
        boolean aceito = false;

        aceito = AvaliaRecursivo(cadeia, this.estadoInicial, 0);

        return aceito;
    }

    /**
        Método chamado para avaliar uma cadeia. É recuperado o símbolo da cadeia de acordo com a posição passada
        e avaliado se é o último símbolo. Caso seja, é verificado se existe alguma transição que com esse símbolo ou
        uma cadeia vazia alcance um estado de aceitação a partir do estado atual. Caso exista, é retornado true e caso
        contrário, false. Caso não esteja no último símbolo da cadeia, são identificadas as transições com o símbolo 
        atual ou cadeia vazia e a função é chamada recursivamente para cada um dos possíveis caminhos que o autômato pode 
        seguir.
		
        @param cadeia cadeia a ser avaliada.
        @param estadoAtual estado atual no autômato.
        @param posicaoProximoSimbolo posição na cadeia do símbolo que deverá ser lido.
        @return um valor booleano que indica se a cadeia é aceita pelo autômato (true) ou não (false).
	*/

    private boolean AvaliaRecursivo(int[] cadeia, int estadoAtual, int posicaoProximoSimbolo) {
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
                                    aceito = AvaliaRecursivo(cadeia, this.matrizTransicoes[2][j], posicaoProximoSimbolo);
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
                        aceito = AvaliaRecursivo(cadeia, this.matrizTransicoes[2][j], posicaoProximoSimbolo + 1);
                    } else if(this.matrizTransicoes[1][j] == 0) {
                        aceito = AvaliaRecursivo(cadeia, this.matrizTransicoes[2][j], posicaoProximoSimbolo);
                    }
    
                    if(aceito) return true;
                }
            }
            return false;
        }
    }

    /**
        O método principal. Este método le o arquivo de entrada por linha e chama o construtor 
        da classe afn para criar os autômatos com as caracteristicas especificadas pelo arquivo de texto.
        É montada também a matriz de transições desse autômato e por ultimo, cada cadeia de teste é 
        enviada para a função de avaliação.
        Por último, os resultados dessas avaliações são devolvidos no arquivo de saída.

		@param args argumentos passados ao programa através da linha de comando. Ao executar o 
        o programa é necessário passar como parâmetro os nomes dos arquivos com os testes e o arquivo
        para devolução dos resultados.
	*/

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
