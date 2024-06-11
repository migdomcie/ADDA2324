package tests.AStar;

import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;

import datos.DatosVerduras;
import ejercicios.SolucionVerduras;
import ejercicios.VerdurasEdge;
import ejercicios.VerdurasHeuristic;
import ejercicios.VerdurasVertex;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TestAStarEj1_3 {

	public static void main(String[] args) {
		// Set up
		Locale.setDefault(Locale.of("en", "US"));

		String id_fichero = "Ejercicio1DatosEntrada3.txt";
		DatosVerduras.iniDatos("ficheros/ejercicios/"+id_fichero);
		System.out.println("\n>\tResultados para el test " + id_fichero + "\n");

		// V�rtices clave

		VerdurasVertex start = VerdurasVertex.initial();
		Predicate<VerdurasVertex> goal = VerdurasVertex.goal();

		// Grafo

		System.out.println("#### Algoritmo A* ####");

		// Algoritmo A*
		EGraph<VerdurasVertex, VerdurasEdge> graph =
					EGraph.virtual(start,goal,PathType.Sum, Type.Max)
					.edgeWeight(x -> x.weight())
					//.greedyEdge(VerdurasVertex::greedyEdge)
					.goalHasSolution(VerdurasVertex.goalHasSolution())
					.heuristic(VerdurasHeuristic::heuristic1)
					.build();
					
		AStar<VerdurasVertex, VerdurasEdge,?> aStar = AStar.ofGreedy(graph);
			
		GraphPath<VerdurasVertex, VerdurasEdge> gp = aStar.search().get();
			
		List<Integer> gp_as = gp.getEdgeList().stream().map(x -> x.action())
					.collect(Collectors.toList());
	
		SolucionVerduras s_as = SolucionVerduras.of(gp_as);

		System.out.println(s_as);
//		System.out.println(gp_as);

		GraphColors.toDot(aStar.outGraph(), "ficheros_generados/VerdurasAStarGraph3.gv", 
					v -> v.toString(),
					e -> e.action().toString(), 
					v -> GraphColors.colorIf(Color.red, VerdurasVertex.goal().test(v)),
					e -> GraphColors.colorIf(Color.red, gp.getEdgeList().contains(e)));
	}
	

}
