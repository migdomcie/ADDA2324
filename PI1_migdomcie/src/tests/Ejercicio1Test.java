package tests;

import java.util.List;
import java.util.function.Function;

import ejemplos.Ejemplo1;
import ejercicios.Ejercicio1;
import ejercicios.Ejercicio2;
import us.lsi.common.IntPair;
import us.lsi.geometria.Punto2D;
import us.lsi.streams.Stream2;

public class Ejercicio1Test {

	public static void main(String[] args) {
		String file="ficheros/ejercicios/PI1Ej1DatosEntrada.txt";
		Function<String, IntPair> func= str->{
			String[] linea= str.split(",");
			IntPair pareja=
					IntPair.of(Integer.valueOf(linea[0].trim()),
							Integer.valueOf(linea[1].trim()));
			return pareja;
		};
		List<IntPair> ls= Stream2.file(file).map(func).toList();
		
		System.out.println("Datos Entrada:\n" + ls);
		for(IntPair p: ls) {
			System.out.println("1) iterativo:\n" + Ejercicio1.solucionIt(p.first(), p.second()));
			System.out.println("1) recursivo final:\n" + Ejercicio1.solucionRecFinal(p.first(), p.second()));
		}
	}
}
