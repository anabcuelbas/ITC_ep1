import java.io.*;

public class LeitorAna {
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
            
            AutomatoAna automato = new AutomatoAna(cabecalho[0], cabecalho[1], cabecalho[2], cabecalho[3], estadosAceitacao);

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