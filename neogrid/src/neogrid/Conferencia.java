/* Cria lista
 * Da o nome do arquivo
 */

package neogrid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

public class Conferencia {

	private List<Palestra> palestras = new ArrayList<Palestra>();

	private List<Palestra> track1 = new ArrayList<Palestra>();
	
	private List<Palestra> track2 = new ArrayList<Palestra>();
	
	private static final int MORNING_TIME = 180;
	
	private static final int AFTERNOON_MIN_TIME = 180;
	
	private static final int AFTERNOON_MAX_TIME = 240;
	
	public List<Palestra> getPalestras() {
		return palestras;
	}

	public void setPalestras(Palestra palestra) {
		this.palestras.add(palestra);
	}
	
	public List<Palestra> getTrack1() {
		return this.track1;
	}

	public void setTrack1(Palestra palestra) {
		this.track1.add(palestra);
	}

	public List<Palestra> getTrack2() {
		return this.track2;
	}

	public void setTrack2(Palestra palestra) {
		this.track2.add(palestra);
	}

	/**
	 * Seta o horario das palestras
	 */
	private void setaHorarioPalestra(List<Palestra> track) {

		Iterator<Palestra> iterator = track.iterator();
		
		int minutos = 0;
		int horas = 9;
		while(iterator.hasNext()){
		
			Palestra palestraAtual = iterator.next();

			palestraAtual.setHorarioInicio(horas, minutos);
			
			minutos += Integer.parseInt(palestraAtual.getTempo());
		}
	}

	
	/**
	 * Remove paletras colocadas na track da lista de palestras
	 */
	private void removePalestrasUsadas(List<Palestra> track) {

		for(Palestra trackPalestra : track){
			
			for(int i = 0; i < this.getPalestras().size(); i++) {
			
				if(this.getPalestras().get(i).equals(trackPalestra)) {
				
					this.getPalestras().remove(i);
				}		
			}
		}
	}

	private void mostraPalestras() {
		Iterator<Palestra> iterator = this.getPalestras().iterator();
		
		while(iterator.hasNext()){
			System.out.print(iterator.next().getNome());
		}
	}
	
	private void mostraTrack(List<Palestra> track, String titulo){
		Iterator<Palestra> iterator = track.iterator();
		
		System.out.println(titulo);
		while(iterator.hasNext()){
			
			Palestra palestraAtual = iterator.next();
			
			System.out.print(palestraAtual.getHorarioInicio() + " ");
			System.out.println(palestraAtual.getNome());
		}
		System.out.println();
	}

	private void ordenaPalestras(){
		List<Palestra> palestras = this.getPalestras();
		
		Collections.sort(palestras, Collections.reverseOrder(new PalestraComparator()));
	}
	
	private void fittingProposalsMorning(List<Palestra> track){
		ListIterator<Palestra> iteratorPalestras = this.getPalestras().listIterator();		
		
		while(iteratorPalestras.hasNext()){	
			
			Palestra palestraAtual = iteratorPalestras.next();	
			
			int tempoPalestraAtual = Integer.parseInt(palestraAtual.getTempo());
			int trackManha = 0;

			//soma tempos
			int i;
			for(i = 0; i < track.size(); i++){					
				trackManha += Integer.parseInt(track.get(i).getTempo());
			}
			
			if((trackManha + tempoPalestraAtual) == MORNING_TIME) {
				
				track.add(palestraAtual);						
				
				//add almoco
				Palestra almoco = new Palestra("Lunch", this);	
				track.add(almoco);
				int index = track.size();
				track.get(index-1).setTempo("60");
				
				int indexLunch = this.palestras.size();			
				this.palestras.remove(indexLunch - 1);
				break;
			}
			else if((trackManha + tempoPalestraAtual) < MORNING_TIME){ 
				
				track.add(palestraAtual);
			}
			else if((trackManha + tempoPalestraAtual) > MORNING_TIME){					

				//remove palestra atual da track								
				track.remove(i-1);																			
			}		
		}		
	}
	
	private void fittingProposalsAfternoon(List<Palestra> track){
		
		ListIterator<Palestra> iteratorPalestras = this.getPalestras().listIterator();		
		
		//acha posicao do almoco
		int posicaoInicial;
		for(posicaoInicial = 0; posicaoInicial < track.size(); posicaoInicial++){				
	
			track.get(posicaoInicial).getNome().equals("Lunch");
		}
	
		while(iteratorPalestras.hasNext()){	

			Palestra palestraAtual = iteratorPalestras.next();	
			
			int tempoPalestraAtual = Integer.parseInt(palestraAtual.getTempo());
			int trackTarde = 0;
			
			//soma tempos
			for(int i = posicaoInicial; i < track.size(); i++){									
		
				trackTarde += Integer.parseInt(track.get(i).getTempo());
			}

			if(((trackTarde + tempoPalestraAtual) < AFTERNOON_MIN_TIME)) {
				
				track.add(palestraAtual);
			}
			else if( ((trackTarde + tempoPalestraAtual) > AFTERNOON_MIN_TIME) && ((trackTarde + tempoPalestraAtual) < AFTERNOON_MAX_TIME) ) {
			
				track.add(palestraAtual);				
			}
		}
	}
	
	public static void main(String[] args) {

		System.out.printf("Informe o diretorio e nome do arquivo:\n");

		Scanner ler = new Scanner(System.in);

		String nome = ler.nextLine(); 									

		Conferencia conferencia = new Conferencia();

		new LerArquivo(nome, conferencia); 								
																		
		conferencia.ordenaPalestras();									
						
		conferencia.fittingProposalsMorning(conferencia.track1);			
		conferencia.removePalestrasUsadas(conferencia.getTrack1());
		
		conferencia.fittingProposalsMorning(conferencia.track2);
		conferencia.removePalestrasUsadas(conferencia.getTrack2());

		conferencia.fittingProposalsAfternoon(conferencia.track1);
		conferencia.removePalestrasUsadas(conferencia.getTrack1());
				
		conferencia.fittingProposalsAfternoon(conferencia.track2);
		
		conferencia.removePalestrasUsadas(conferencia.getTrack2());

		//add Networking
		Palestra networking1 = new Palestra("Networking Event", conferencia);	
		conferencia.track1.add(networking1);
		
		Palestra networking2 = new Palestra("Networking Event", conferencia);	
		conferencia.track2.add(networking2);
		
		conferencia.setaHorarioPalestra(conferencia.track1);
		conferencia.setaHorarioPalestra(conferencia.track2);
		
		conferencia.mostraTrack(conferencia.track1, "Track 1:");
		conferencia.mostraTrack(conferencia.track2, "Track 2:");
	}
}