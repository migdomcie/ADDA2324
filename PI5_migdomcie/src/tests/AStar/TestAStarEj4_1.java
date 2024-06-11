package tests.AStar;

import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;

import datos.DatosPersonas;
import datos.DatosProductosNavidad;
import datos.DatosVerduras;
import ejercicios.PersonasEdge;
import ejercicios.PersonasHeuristic;
import ejercicios.PersonasVertex;
import ejercicios.ProductosDistribucionEdge;
import ejercicios.ProductosDistribucionHeuristic;
import ejercicios.ProductosDistribucionVertex;
import ejercicios.ProductosNavidadEdge;
import ejercicios.ProductosNavidadVertex;
import ejercicios.SolucionPersonas;
import ejercicios.SolucionProductosDistribucion;
import ejercicios.SolucionVerduras;
import ejercicios.VerdurasEdge;
import ejercicios.VerdurasHeuristic;
import ejercicios.VerdurasVertex;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.common.Pair;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TestAStarEj4_1 {

	public static void main(String[] args) {
		// Set up
		Locale.setDefault(Locale.of("en", "US"));

		String id_fichero = "Ejercicio4DatosEntrada1.txt";
		DatosPersonas.iniDatos("ficheros/ejercicios/"+id_fichero);
		System.out.println("\n\tResultados para el test " + id_fichero + "\n");

		// V�rtices clave

		PersonasVertex start = PersonasVertex.initial();
		Predicate<PersonasVertex> goal = PersonasVertex.goal();

		// Grafo

		System.out.println("#### Algoritmo A* ####");

		// Algoritmo A*
		EGraph<PersonasVertex, PersonasEdge> graph =
					EGraph.virtual(start,goal,PathType.Sum, Type.Max)
					.edgeWeight(x -> x.weight())
					//.greedyEdge(PersonasVertex::greedyEdge)
					.goalHasSolution(PersonasVertex.goalHasSolution())
					.heuristic(PersonasHeuristic::heuristic0)
					.build();
					
		AStar<PersonasVertex, PersonasEdge,?> aStar = AStar.ofGreedy(graph);
			
		GraphPath<PersonasVertex, PersonasEdge> gp = aStar.search().get();
			
		List<Integer> gp_as = gp.getEdgeList().stream().map(x -> x.action())
					.collect(Collectors.toList());
		List<Pair<Integer, Integer>> gp_ps= gp.getVertexList().get(gp.getVertexList().size()-1).listaParejas();

		SolucionPersonas s_as = SolucionPersonas.ofPairs(gp_ps);

		System.out.println(s_as);
//		System.out.println(gp_as);

		GraphColors.toDot(aStar.outGraph(), "ficheros_generados/PersonasAStarGraph1.gv", 
					v -> v.toString(),
					e -> e.action().toString(), 
					v -> GraphColors.colorIf(Color.red, PersonasVertex.goal().test(v)),
					e -> GraphColors.colorIf(Color.red, gp.getEdgeList().contains(e)));
	}
	

}
