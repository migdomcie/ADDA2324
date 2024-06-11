package tests;

import java.util.List;
import java.util.function.Function;

import ejercicios.Ejercicio4;
import us.lsi.common.IntPair;
import us.lsi.streams.Stream2;

public class Ejercicio4Test {

	public static void main(String[] args) {
		String file= "ficheros/ejercicios/PI1Ej4DatosEntrada.txt";
		Function<String, IntPair> func= str->{
			String[] linea= str.split(",");
			IntPair pareja=
					IntPair.of(Integer.valueOf(linea[0].trim()),
							Integer.valueOf(linea[1].trim()));
			return pareja;
		};
		List<IntPair> ls=  Stream2.file(file).map(func).toList();
		
		System.out.println("Datos Entrada:\n" + ls);
		for(IntPair p: ls) {
			System.out.println("4) recursivo sin memoria:\n" + Ejercicio4.gRecSM(p.first(), p.second()));
			System.out.println("4) recursivo con memoria:\n" + Ejercicio4.gRecCM(p.first(), p.second()));
			System.out.println("4) iterativo:\n" + Ejercicio4.gIt(p.first(), p.second()) + "\n");
		}
	}

}
