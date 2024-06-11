package ejercicios;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
			ac.add(0, a*b);
		}else if(a>b) {
			ac.add(0, a);
			ac= solucionRecFinal(a%b, b-1, ac);

		}else {
			ac.add(0, b);
			ac= solucionRecFinal(a-2, b/2, ac);	
		}
		return ac;
	}
	
	public static List<Integer> solucionIt(Integer a, Integer b){
		List<Integer> ac= new ArrayList<>();
		while(!(a<2 || b < 2)) {
			if(a>b) {
				ac.add(0, a);
				a= a%b;
				b= b-1;

			}else {
				ac.add(0, b);
				a= a-2;
				b= b/2;	
			} 
			
		}
		ac.add(0, a*b);
		return ac;
	}
	
	public static List<Integer> solucionFunc(Integer a, Integer b){
		Tupla t= Stream.iterate(Tupla.first(a, b),
				e-> e.next())
				.filter(e-> e.a()<2 || e.b() < 2)
				.findFirst()
				.get();
		
		t.ac().add(0, t.a()*t.b());
		return t.ac();
	}
	
	public static record Tupla (Integer a, Integer b, List<Integer> ac) {
		public static Tupla of(Integer a, Integer b, List<Integer> ac) {
			return new Tupla(a, b, ac);
		}
		
		public static Tupla first(Integer a, Integer b) {
			return of(a,b, new ArrayList<>());
		}
		
		public Tupla next() {
			Tupla t= null;
			if(a>b) {
				ac.add(0, a);
				t= of(a%b, b-1, ac);
			}else {
				ac.add(0, b);
				t= of(a-2, b/2, ac);	
			}
			return t;
		}
	}
}