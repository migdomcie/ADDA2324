package ejercicios.manualBT;

import datos.DatosProductosDistribucion;

public class TestProductosDistribucionBTManual_1 {

	public static void main(String[] args) {
		
		String id_fichero= "Ejercicio3DatosEntrada1.txt";
		DatosProductosDistribucion.iniDatos("ficheros/ejercicios/"+ id_fichero);
		System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");
		DatosProductosDistribucion.toConsole();

		ProductosDistribucionBT.search();

		System.out.println(ProductosDistribucionBT.getSolucion() + "\n");			
			
		
	}

}