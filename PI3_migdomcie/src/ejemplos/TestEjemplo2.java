package ejemplos;


import org.jgrapht.graph.SimpleWeightedGraph;

import datos.Carretera;
import datos.Ciudad;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;


public class TestEjemplo2 {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testsEjemplo2("Andalucia", "Sevilla", "Almeria");
		
	}
	
	public static void testsEjemplo2(String file, String origen, String destino) {
		
		SimpleWeightedGraph<Ciudad,Carretera> g =  
				GraphsReader.newGraph("ficheros/ejemplos/" + file + ".txt",
						Ciudad::ofFormat, 
						Carretera::ofFormat,
						Graphs2::simpleWeightedGraph,
						Carretera::km);

		System.out.println("Archivo de entrada " + file + ".txt \n" + "Datos de entrada: " + g);

		
		System.out.println("Apartado A):");
		Ejemplo2.apartadoA(g, file, origen, destino);
		
		System.out.println("Apartado B):");
		Ejemplo2.apartadoB(g, file);
		
	}

}
