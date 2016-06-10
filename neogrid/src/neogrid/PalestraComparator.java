package neogrid;

import java.util.Comparator;

public class PalestraComparator implements Comparator<Palestra>{

	@Override
	public int compare(Palestra palestra1, Palestra palestra2) {
 
		int tempoPalestra1 = Integer.parseInt(palestra1.getTempo());
		int tempoPalestra2 = Integer.parseInt(palestra2.getTempo());
		
		if(tempoPalestra1 < tempoPalestra2) {
			return -1;
		}
		else if(tempoPalestra1 > tempoPalestra2){
			return 1;
		}
		else{
			return 0;
		}
	}
}
