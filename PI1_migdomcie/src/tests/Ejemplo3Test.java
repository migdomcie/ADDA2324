package tests;

import java.util.List;

import ejemplos.Ejemplo3;
import us.lsi.common.Pair;
import us.lsi.streams.Stream2;

public class Ejemplo3Test {

	public static void main(String[] args) {
		String file = "ficheros/ejemplos/Ejemplo3DatosEntrada.txt";
		List<Pair<Integer,Integer>> ls = Stream2.file(file)
				.map(s -> Pair.parse(s,",",s1->Integer.parseInt(s1),s2->Integer.parseInt(s2)))
				.toList();
		// Explicar diferencia entre Stream2.file(_) y Files2.streamFromFile(_)

		ls.forEach(p -> {
			System.out.println("1) Solucion R. Sin Mem.: " + Ejemplo3.solucionRecursivaSinMemoria(p.first(), p.second()));
			System.out.println("2) Solucion R. Con Mem.: " + Ejemplo3.solucionRecursivaConMemoria(p.first(), p.second()));
			System.out.println("3) Solucion Iterativa:   " + Ejemplo3.solucionIterativa(p.first(), p.second()));
			System.out.println("________________________________");
		});
		System.out.println(".............................................................................................................................");
	}

}
