package tests;

import java.util.List;
import java.util.function.Function;

import ejemplos.Ejemplo2;
import us.lsi.common.IntPair;
import us.lsi.streams.Stream2;

public class Ejemplo2Test {

	public static void main(String[] args) {
		String file= "ficheros/ejemplos/Ejemplo2DatosEntrada.txt";
		Function<String, IntPair> parsePareja= s-> {
			String[] v= s.split(",");
			return IntPair.of(Integer.valueOf(v[0]), Integer.valueOf(v[0]));
		};
		List<IntPair> ls= Stream2.file(file).map(parsePareja).toList();
		System.out.println("Datos Entrada:\n" + ls);
		for(IntPair pareja: ls) {
			System.out.println("1) recursivo no final:\n" + Ejemplo2.solucionRecNoFinal(pareja.first(), pareja.second()));
			System.out.println("2) recursivo final:\n" + Ejemplo2.solucionRecFinal(pareja.first(), pareja.second()));
			System.out.println("3) iterativo:\n" + Ejemplo2.solucionIt(pareja.first(), pareja.second()));
			System.out.println("4) funcional:\n" + Ejemplo2.solucionFunc(pareja.first(), pareja.second()) +"\n");
		}
	}

}
