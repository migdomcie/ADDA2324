package tests;

import java.util.List;
import java.util.function.Function;

import ejemplos.Ejemplo1;
import ejercicios.Ejercicio2;
import us.lsi.common.IntPair;
import us.lsi.streams.Stream2;

public class Ejercicio2Test {

	public static void main(String[] args) {
		String file="ficheros/ejercicios/PI1Ej2DatosEntrada.txt";
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
			System.out.println("2) recursivo:\n" + Ejercicio2.solucionRec(p.first(), p.second()));
			System.out.println("2) recursivo final:\n" + Ejercicio2.solucionRecFinal(p.first(), p.second()));
			//System.out.println("2) iterativo:\n" + Ejercicio2.solucionIt(p.first(), p.second()));
			//System.out.println("2) funcional:\n" + Ejercicio2.solucionFuncional(p.first(), p.second()));

		}

	}
	

}
