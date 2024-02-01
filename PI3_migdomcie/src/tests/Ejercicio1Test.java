package tests;

import java.util.function.Predicate;

import org.jgrapht.Graph;

import datos.RelacionUsuario;
import datos.Usuario;
import ejercicios.Ejercicio1;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;


public class Ejercicio1Test {

	static String file= "ejercicio1_1";
	public static void main(String[] args) {
		testsEjercicio1();
	}
	
	public static void testsEjercicio1() {
		for(int i=1; i<=3; i++) {
			file= String.format("ejercicio1_%d", i);
			Graph<Usuario, RelacionUsuario> g = GraphsReader
					.newGraph("ficheros/ejercicios/" + file + ".txt", //fichero de datos
							Usuario::ofFormat, //factoria para construir los vertices
							RelacionUsuario::ofFormat, //factoria para crear las aristas
							Graphs2::simpleDirectedWeightedGraph, //creador del grafo
							RelacionUsuario::indInter); //funcion para  determinar peso de la arista

					
		//Para mostrar el grafo original
		GraphColors.toDot(g,"resultados/ejercicios/ejercicio1/" + file + ".gv",
				v-> v.nombreUsuario(), //que etiqueta mostrar en vertices y aristas
				e-> e.toString(),
				v->GraphColors.color(Color.black), //color o estilo de vertices y aristas
				e->GraphColors.color(Color.black));
		
		
		System.out.println("Archivo " + file + ".txt \n");
	
		// a) Predicado: los usuarios que siguen a más de 3 usuarios, y cuyo índice medio de
		//interacción con las publicaciones de los usuarios a los que siguen sea
		//mayor de 2.5.
		System.out.println("Apartado a):");
		Predicate<Usuario> pv1 = c -> g.outgoingEdgesOf(c).size() > 3 && Ejercicio1.promedio(g.edgesOf(c)) > 2.5;
		Predicate<RelacionUsuario> pa1 = ca -> g.containsEdge(ca);
		
		Ejercicio1.apartadoA(file, g,pv1,pa1,"apartadoA");
		
		// b) Componentes Conexas
		System.out.println("\nApartado b):");
		
		Ejercicio1.apartadoB(file, g, "apartadoB");
		
		// c) Vertex Cover
		System.out.println("\nApartado c):");
		
		Graph<Usuario, RelacionUsuario> gsimple = GraphsReader
				.newGraph("ficheros/ejercicios/" + file + ".txt", //fichero de datos
						Usuario::ofFormat, //factoria para construir los vertices
						RelacionUsuario::ofFormat, //factoria para crear las aristas
						Graphs2::simpleGraph); //creador del grafo
		
		Ejercicio1.apartadoC(file, g, gsimple, "apartadoC");
		
		// d) Lista de 2 usuarios que más interacción media presentan entre
		//sus seguidores, considerando sólo aquellos usuarios que: tienen al menos 5
		//seguidores, presentan más de 3 aficiones, y cuyo índice de actividad es mayor
		//que 4
		System.out.println("\nApartado d):");
		
		Ejercicio1.apartadoD(file, g, "apartadoD");

		System.out.println("------------------------------\n");
		
		}
	}		
}


