package ejercicios.manualPD;

import datos.DatosVerduras;

public class TestVerdurasPDRManual_2 {

	public static void main(String[] args) {
		
		String id_fichero= "Ejercicio1DatosEntrada2.txt";
		DatosVerduras.iniDatos("ficheros/ejercicios/"+id_fichero);
		System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");
		System.out.println("Solucion obtenida: " + VerdurasPDR.search());
		
	}
	
}