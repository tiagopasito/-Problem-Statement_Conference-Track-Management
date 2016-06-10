package neogrid;

import java.time.LocalTime;
import java.util.regex.Pattern;

public class Palestra {
	
	private String nome;
	private String tempo;
	private LocalTime horarioInicio;
	
	public String getNome() {
		return this.nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getTempo() {
		return this.tempo;
	}
	
	public void setTempo(String tempo) {
		this.tempo = tempo;
	}	

	public LocalTime getHorarioInicio() {
		return this.horarioInicio;		
	}

	public void setHorarioInicio(int hora, int min) {	
		this.horarioInicio = LocalTime.of(hora, 0).plusMinutes(min);
	}
	
	/**
	 * Cria um objeto para cada linha
	 * Separa o nome do tempo da palestra
	 * @param linha
	 * @param conferencia
	 */
	Palestra(String linha, Conferencia conferencia){
		
		String[] str = linha.split(Pattern.quote(" "));
		
		int ultimaPosicao = str.length - 1;
		
		if(str[ultimaPosicao].equals("lightning")){			
			this.setTempo("5");
		}	
		else if(str[ultimaPosicao].equals("Event")){			
			this.setTempo("0");
		}			
		else{
			String minutosString = str[ultimaPosicao].substring(0, 2);
			this.setTempo(minutosString);
		}
		String nomePalestra = linha;
				
		this.setNome(nomePalestra);
		
		conferencia.setPalestras(this);
	}
}
