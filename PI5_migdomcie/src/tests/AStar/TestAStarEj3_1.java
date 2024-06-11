package tests.AStar;

import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;

import datos.DatosProductosDistribucion;
import datos.DatosProductosNavidad;
import datos.DatosVerduras;
import ejercicios.ProductosDistribucionEdge;
import ejercicios.ProductosDistribucionHeuristic;
import ejercicios.ProductosDistribucionVertex;
import ejercicios.ProductosNavidadEdge;
import ejercicios.ProductosNavidadVertex;
import ejercicios.SolucionProductosDistribucion;
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

public class TestAStarEj3_1 {

	public static void main(String[] args) {
		// Set up
		Locale.setDefault(Locale.of("en", "US"));

		String id_fichero = "Ejercicio3DatosEntrada1.txt";
		DatosProductosDistribucion.iniDatos("ficheros/ejercicios/"+id_fichero);
		System.out.println("\n\tResultados para el test " + id_fichero + "\n");

		// V�rtices clave

		ProductosDistribucionVertex start = ProductosDistribucionVertex.initial();
		Predicate<ProductosDistribucionVertex> goal = ProductosDistribucionVertex.goal();

		// Grafo

		System.out.println("#### Algoritmo A* ####");

		// Algoritmo A*
		EGraph<ProductosDistribucionVertex, ProductosDistribucionEdge> graph =
					EGraph.virtual(start,goal,PathType.Sum, Type.Min)
					.edgeWeight(x -> x.weight())
					//.greedyEdge(ProductosDistribucionVertex::greedyEdge)
					.goalHasSolution(ProductosDistribucionVertex.goalHasSolution())
					.heuristic(ProductosDistribucionHeuristic::heuristic1)
					.build();
					
		AStar<ProductosDistribucionVertex, ProductosDistribucionEdge,?> aStar = AStar.ofGreedy(graph);
			
		GraphPath<ProductosDistribucionVertex, ProductosDistribucionEdge> gp = aStar.search().get();
			
		List<Integer> gp_as = gp.getEdgeList().stream().map(x -> x.action())
					.collect(Collectors.toList());
	
		SolucionProductosDistribucion s_as = SolucionProductosDistribucion.of(gp_as);

		System.out.println(s_as);
//		System.out.println(gp_as);

		GraphColors.toDot(aStar.outGraph(), "ficheros_generados/ProductosDistribucionAStarGraph1.gv", 
					v -> v.toString(),
					e -> e.action().toString(), 
					v -> GraphColors.colorIf(Color.red, ProductosDistribucionVertex.goal().test(v)),
					e -> GraphColors.colorIf(Color.red, gp.getEdgeList().contains(e)));
	}
	

}
