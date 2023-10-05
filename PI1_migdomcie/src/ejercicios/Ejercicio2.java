package ejercicios;

import java.util.ArrayList;
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
	
	private static List<Integer> solucionRecFinal(Integer a, Integer b, List<Integer> ac){
		
		if(a<2 || b < 2) {
			ac.add(a*b);
		}else if(a>b) {
			ac= solucionRecFinal(a%b, b-1, List.of());
			ac.add(a);

		}else {
			ac= solucionRecFinal(a-2, b/2, List.of());
			ac.add(b);
		}
		
		return ac;
	}
}
