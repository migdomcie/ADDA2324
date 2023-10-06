package ejercicios;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Ejercicio2 {
	
	public static List<Integer> solucionRec (Integer a, Integer b){
		List<Integer> ac= new ArrayList<>();
		if(a<2 || b < 2) {
			ac.add(a*b);
		}else if(a>b) {
			ac= solucionRec(a%b, b-1);
			ac.add(a);
		}else {
			ac= solucionRec(a-2, b/2);
			ac.add(b);
		}
		
		return ac;
	}
	
	public static List<Integer> solucionRecFinal(Integer a, Integer b){
		List<Integer> ac= new ArrayList<>();
		
		return solucionRecFinal(a,b,ac);
	}
	
	public static List<Integer> solucionRecFinal(Integer a, Integer b, List<Integer> ac){
		if(a<2 || b < 2) {
			ac.add(a*b);
		}else if(a>b) {
			ac.add(a);
			ac= solucionRecFinal(a%b, b-1, ac);

		}else {
			ac.add(b);
			ac= solucionRecFinal(a-2, b/2, ac);	
		}
		
		return ac;
	}
}
