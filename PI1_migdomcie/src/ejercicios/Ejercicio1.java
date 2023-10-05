package ejercicios;

import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Ejercicio1 {
	
	public static String ejercicio1(Integer varA, Integer varB) {
		UnaryOperator<EnteroCadena> nx = elem -> {
			return EnteroCadena.of(elem.a() + 3, elem.a() % 2 == 0 ?
					elem.a() + "*" : elem.a() + "!");
		};
		return Stream.iterate(EnteroCadena.of(varA, "A"),
				elem -> elem.a() < varB, nx)
				.filter(elem -> elem.a() % 10 != 0)
				.map(elem -> elem.s()).collect(Collectors.joining("-"));
	}
	
	//Proporcione una solución iterativa y otra recursiva final equivalentes.
	
	public static String solucionIt(Integer varA, Integer varB) {
		EnteroCadena e= EnteroCadena.of(varA, "A");
		Integer a= e.a();
		String s= e.s();
		String ac= s;
		while(e.a() < varB) {
			if(a%10 != 0 && ! ac.equals(s)) {
				ac= ac + "-" + s;
			}
			a= e.a() + 3;
			s= e.a() % 2 == 0 ?
					e.a() + "*" :
					e.a() + "!";
			e= EnteroCadena.of(a, s);
		}
		return ac;
	}
	
	public static String solucionRecFinal(Integer varA, Integer varB) {
		EnteroCadena e= EnteroCadena.of(varA, "A");
		return solucionRecFinal(varA, varB, e,  e.s());
		
	}
	
	private static String solucionRecFinal(Integer varA, Integer varB,
			EnteroCadena e, String ac) {
		if(e.a() < varB) {
			if(e.a()%10 != 0 &&  !ac.equals(e.s())) {
				ac= ac + "-" + e.s();
			}
			ac= solucionRecFinal(varA, varB,
					EnteroCadena.of(e.a()+3, e.a() % 2 == 0 ?
							e.a() + "*" :
							e.a() + "!"),
							ac);
			
		}
		return ac;
	}
}
