package ejercicios.manualBT;

import datos.DatosPersonas;
import us.lsi.common.List2;

public class TestPersonasBTManual_1 {

	public static void main(String[] args) {
		
		String id_fichero= "Ejercicio4DatosEntrada1.txt";
		DatosPersonas.iniDatos("ficheros/ejercicios/"+ id_fichero);
		System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");
		DatosPersonas.toConsole();

		PersonasBT.search();

		System.out.println(PersonasBT.getSolucion() + "\n");
//		System.out.println(List2.intersection(List2.of("A"), List2.of("B")).size());
			
		
	}

}
