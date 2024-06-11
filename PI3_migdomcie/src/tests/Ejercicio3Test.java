package tests;


import java.util.List;

import org.jgrapht.Graph;

import datos.RelacionTarea;
import datos.Tarea;
import ejercicios.Ejercicio3;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;


public class Ejercicio3Test {
	
	static String file= "ejercicio3_1.txt";
	
	public static void main(String[] args) {
		testsEjercicio3();
	}
	
	public static void testsEjercicio3() {
		List<Tarea> listaTareasTestB= List.of(Tarea.of("Tarea5"), Tarea.of("Tarea8"), Tarea.of("Tarea8"));
		
		for(int i=1; i<=3; i++) {
				file= String.format("ejercicio3_%d", i);
				Graph<Tarea, RelacionTarea> g = GraphsReader
						.newGraph("ficheros/ejercicios/" + file + ".txt", //fichero de datos
								Tarea::ofFormat, //factoria para construir los vertices
								RelacionTarea::ofFormat, //factoria para crear las aristas
								Graphs2::simpleDirectedGraph); //creador del grafo;
						
			//Para mostrar el grafo original
			GraphColors.toDot(g,"resultados/ejercicios/ejercicio3/" + file + ".gv",
					v-> v.toString(), //que etiqueta mostrar en vertices y aristas
					e-> e.toString(),
					v->GraphColors.color(Color.black), //color o estilo de vertices y aristas
					e->GraphColors.color(Color.black));
			
			System.out.println("Archivo " + file + ".txt \n");
			
			// a) Lista de precedencia de vertices (tareas)
			System.out.println("Apartado a):");
			
			Ejercicio3.apartadoA(file, g);
			
			// b) Vertices precedentes a vertice dado (tareas precedentes a dada)
			System.out.println("\nApartado b):");
						
			Ejercicio3.apartadoB(file, g, listaTareasTestB.get(i-1), "apartadoB");
			
			// c) Tareas  mas dependientes
			System.out.println("\nApartado c):");

			Ejercicio3.apartadoC(file, g, "apartadoC");

			System.out.println("------------------------------\n");
		}
	}
}
