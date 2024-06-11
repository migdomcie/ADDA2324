package ejercicios.manualPD;

import datos.DatosProductosNavidad;

public class TestProductosNavidadPDRManual_2 {

	public static void main(String[] args) {
		
		String id_fichero= "Ejercicio2DatosEntrada2.txt";
		DatosProductosNavidad.iniDatos("ficheros/ejercicios/"+id_fichero);
		System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");
		System.out.println("Solucion obtenida: " + ProductosNavidadPDR.search());
		
	}
	
}
