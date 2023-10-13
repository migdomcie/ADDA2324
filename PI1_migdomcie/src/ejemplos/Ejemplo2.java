package ejemplos;

import java.util.stream.Stream;

public class Ejemplo2 {
	
	public static String solucionRecNoFinal(Integer a, Integer b) {
		String ac= "";
		if(a<5 || b<5) {
			ac= String.format("(%d)", a*b);
		}else {
			ac= String.valueOf(a+b) + solucionRecNoFinal(a/2, b-2);
		}
		return ac;
	}
	
	public static String solucionRecFinal(Integer a, Integer b) {
		String ac= "";
		return solucionRecFinal(a, b, ac);
		
	}
	
	private static String solucionRecFinal(Integer a, Integer b, String ac) {
		if(a<5 || b<5) {
			ac= ac + String.format("(%d)", a*b);
		}else {
			ac= solucionRecFinal(a/2, b-2, ac + String.valueOf(a+b));
		}
		return ac;
	}
	
	public static String solucionIt(Integer a, Integer b) {
		String ac="";
		while(!(a<5 || b<5)) {
			ac= ac + String.valueOf(a+b);
			a= a/2;
			b= b-2;
		}
		
		ac= ac + String.format("(%d)", a*b);
		
		return ac;
	}
	
	private static record Tupla (Integer a, Integer b, String ac) {
		public static Tupla of(Integer a, Integer b, String ac) {
			return new Tupla(a, b, ac);
		}
		
		public static Tupla first(Integer a, Integer b){
			return of(a, b, "");
		}
		
		public Tupla next() {
			return of(a/2, b-2, ac + String.valueOf(a+b));
		}
	}
	
	public static String solucionFunc(Integer a, Integer b){
		Tupla t= Stream.iterate(Tupla.first(a, b), e-> e.next())
				.filter(e -> e.a<5 || e.b<5) //filtra con la condicion del caso base
				.findFirst().get(); //obtiene el primer elemento de la secuencia: caso base
		
		return t.ac() + "(" + String.valueOf(t.a()*t.b()) + ")";
	}
}
