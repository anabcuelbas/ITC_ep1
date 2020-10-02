import java.io.*;

public class Leitor {
    public static void main(String[] args) throws IOException {
        FileReader ler = new FileReader(new File(args[0])); 
        BufferedReader leitor = new BufferedReader(ler);
     
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
            
            Autonomo autonomo = new Autonomo(cabecalho[0], cabecalho[1], cabecalho[2], estadosAceitacao);

            int qntTransicoes = Integer.parseInt(cabecalho[2]);
            for(int j = 0; j < qntTransicoes; j++) {
                String transicao = leitor.readLine();
                String[] partesTransicao = transicao.split(" ");

                autonomo.ConstroiMatriz(Integer.parseInt(partesTransicao[0]), Integer.parseInt(partesTransicao[1]), Integer.parseInt(partesTransicao[2]));
            }
            
            int qntCadeias = Integer.parseInt(leitor.readLine());
            for(int j = 0; j < qntCadeias; j++) {
                String cadeia = leitor.readLine();
                String[] vetorCadeia = cadeia.split(" ");
                int[] vetorCadeiaInt = new int[vetorCadeia.length];
                
                for(int k = 0; k < vetorCadeia.length; k++) {
                    vetorCadeiaInt[k] = Integer.parseInt(vetorCadeia[k]);
                }

                Boolean resultado = autonomo.AvaliaCadeia(vetorCadeiaInt);
                System.out.println(resultado);
            }
        }
    }
}