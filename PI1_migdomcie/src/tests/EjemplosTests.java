package tests;

import java.util.List;
import java.util.function.Function;

import ejemplos.Ejemplo1;
import us.lsi.geometria.Punto2D;
import us.lsi.streams.Stream2;

public class EjemplosTests {

	public static void main(String[] args) {
		String file= "ficheros/ejemplos/Ejemplo1DatosEntrada.txt";
		Function<String, Punto2D> parsePunto= s-> {
			String[] v= s.split(",");
			return Punto2D.of(Double.valueOf(v[0]), Double.valueOf(v[1]));
		};
		List<Punto2D> ls= Stream2.file(file).map(parsePunto).toList();
		System.out.println("Datos Entrada:\n" + ls);
		System.out.println("1) funcional:\n" + Ejemplo1.ejemplo1(ls));
		System.out.println("2) iterativo:\n" + Ejemplo1.ejemplo1It(ls));
		System.out.println("3) recursivo:\n" + Ejemplo1.ejemplo1RecFinal(ls));

	}

}
