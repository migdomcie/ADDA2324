package ejercicios.manualBT;

import datos.DatosPersonas;

public class TestPersonasBTManual_3 {

	public static void main(String[] args) {
		
		String id_fichero= "Ejercicio4DatosEntrada3.txt";
		DatosPersonas.iniDatos("ficheros/ejercicios/"+ id_fichero);
		System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");
		DatosPersonas.toConsole();

		PersonasBT.search();

		System.out.println(PersonasBT.getSolucion() + "\n");			
			
		
	}

}
