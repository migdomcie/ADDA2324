package ejercicios;

import java.util.HashMap;
import java.util.Map;

import us.lsi.common.IntPair;

public class Ejercicio4 {
	public static String gRecSM(Integer a, Integer b) {
		String ac= "";
		if(a<= 4) {
			ac=a.toString() + "." + b.toString();
		}else if(b<=4) {
			ac= b.toString() + "-" + a.toString();
		}else {
			ac= gRecSM(a/2, b-2) + "," 
				+ gRecSM(a-2, b/2) + ","
				+ gRecSM(a-1, b-1);
		}
		return ac;
	}
	
	public static String gRecCM(Integer a, Integer b) {
		Map<IntPair, String> m= new HashMap<>();
		return gRecCM(a, b, m);
		
	}
	
	private static String gRecCM(Integer a, Integer b, Map<IntPair, String> m) {
		String res= "";
		if(m.containsKey(IntPair.of(a,b))) {
			res= m.get(IntPair.of(a, b));
		}else {
			if(a<= 4) {
				res=a.toString() + "." + b.toString();
			}else if(b<=4) {
				res= b.toString() + "-" + a.toString();
			}else {
				res= gRecCM(a/2, b-2, m) + "," 
					+ gRecCM(a-2, b/2, m) + ","
					+ gRecCM(a-1, b-1, m);
			}
			m.put(IntPair.of(a, b), res);
		}
		return res;
	}
	
	public static String gIt(Integer a, Integer b) {
		String res="";
		Map<IntPair, String> m= new HashMap<>();
		for(int i=0; i<=a; i++) {
			for(int j=0; j<=b; j++) {
				if(i<= 4) {
					res= String.valueOf(i) + "." + String.valueOf(j);
				}else if(j<=4) {
					res= String.valueOf(j) + "-" + String.valueOf(i);
				}else {
					res= m.get(IntPair.of(i/2, j-2)) + "," 
						+ m.get(IntPair.of(i-2, j/2)) + ","
						+ m.get(IntPair.of(i-1, j-1));
				}
				m.put(IntPair.of(i, j), res);
			}	
		}
		return res; //m.get(IntPair.of(a, b));
	}
}

