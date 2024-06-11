package tests;


import java.util.List;

import org.jgrapht.Graph;

import datos.Atraccion;
import datos.RelacionVecindad;
import ejercicios.Ejercicio2;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.common.Pair;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;


public class Ejercicio2Test {
	
	static String file= "ejercicio2_1";
	public static void main(String[] args) {
		testsEjercicio2();
	}
	
	public static void testsEjercicio2() {
		List<Pair<Atraccion, Atraccion>> listaParesAtraccionesTestA=
				List.of(Pair.of(Atraccion.of("Coches de choque", 10, 9.5, 7), Atraccion.of("Raton Vacilon", 25, 7.5, 7)),
				Pair.of(Atraccion.of("Coches de choque", 10, 9.5, 7), Atraccion.of("Patitos", 10, 5.5, 3)),
				Pair.of(Atraccion.of("Casa del Terror", 20, 7.0, 10), Atraccion.of("Pim pam pum", 15, 6.0, 4)));
		List<Integer> listaHorasDisponiblesTestC= List.of(5, 2, 3);	
		
		for(int i=1; i<=3; i++) {
			file= String.format("ejercicio2_%d", i);
			Graph<Atraccion, RelacionVecindad> gd = GraphsReader
					.newGraph("ficheros/ejercicios/" + file + ".txt", //fichero de datos
							Atraccion::ofFormat, //factoria para construir los vertices
							RelacionVecindad::ofFormat, //factoria para crear las aristas
							Graphs2::simpleWeightedGraph, //creador del grafo
							RelacionVecindad::distancia); //funcion para  determinar peso de la arista

			Graph<Atraccion, RelacionVecindad> gt = GraphsReader
					.newGraph("ficheros/ejercicios/" + file + ".txt", //fichero de datos
							Atraccion::ofFormat, //factoria para construir los vertices
							RelacionVecindad::ofFormat, //factoria para crear las aristas
							Graphs2::simpleWeightedGraph, //creador del grafo
							RelacionVecindad::tiempo); //funcion para  determinar peso de la arista

					
		//Para mostrar el grafo original
		GraphColors.toDot(gd,"resultados/ejercicios/ejercicio2/" + file + ".gv",
				v-> v.nombreAtraccion(), //que etiqueta mostrar en vertices y aristas
				e-> e.toString(),
				v->GraphColors.color(Color.black), //color o estilo de vertices y aristas
				e->GraphColors.color(Color.black));
		
		
		System.out.println("Archivo " + file + ".txt \n");
	
		// a) Camino de menor distancia para ir de una a otra atraccion (Dijkstra)
		System.out.println("Apartado a):");
		
		Ejercicio2.apartadoA(file, gd, listaParesAtraccionesTestA.get(i-1).first(), listaParesAtraccionesTestA.get(i-1).second(), "apartadoA");
		
		// b) Camino cerrado de menor tiempo medio que pase por todas las atracciones
		//exactamente una vez y vuelva al origen (HeldKarpTSP)
		System.out.println("\nApartado b):");

		Ejercicio2.apartadoB(file, gt, "apartadoB");

		// c) Camino de atracciones mas populares en funcion del tiempo, empezando en una dada
		System.out.println("\nApartado c):");
		
		Ejercicio2.apartadoC(file, gt, listaHorasDisponiblesTestC.get(i-1), "apartadoC");

		System.out.println("------------------------------\n");
		
		}
	}
}
