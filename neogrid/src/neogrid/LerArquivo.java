package neogrid;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LerArquivo {

	LerArquivo(String nomeArq, Conferencia conferencia) {		
		
		try {
			FileReader arq = new FileReader(nomeArq);
			BufferedReader lerArq = new BufferedReader(arq);
			
			String linha = lerArq.readLine();			
		
			while (linha != null) {																					
				Palestra palestra = new Palestra(linha, conferencia);
				
				linha = lerArq.readLine();				
			}
			arq.close();
		} 
		catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
	}
}