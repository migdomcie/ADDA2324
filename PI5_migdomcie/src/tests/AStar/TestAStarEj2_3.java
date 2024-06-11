package tests.AStar;

import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;

import datos.DatosProductosNavidad;
import datos.DatosVerduras;
import ejercicios.ProductosNavidadEdge;
import ejercicios.ProductosNavidadHeuristic;
import ejercicios.ProductosNavidadVertex;
import ejercicios.SolucionProductosNavidad;
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

public class TestAStarEj2_3 {

	public static void main(String[] args) {
		// Set up
		Locale.setDefault(Locale.of("en", "US"));

		String id_fichero = "Ejercicio2DatosEntrada3.txt";
		DatosProductosNavidad.iniDatos("ficheros/ejercicios/"+id_fichero);
		System.out.println("\n>\tResultados para el test " + id_fichero + "\n");

		// V�rtices clave

		ProductosNavidadVertex start = ProductosNavidadVertex.initial();
		Predicate<ProductosNavidadVertex> goal = ProductosNavidadVertex.goal();

		// Grafo

		System.out.println("#### Algoritmo A* ####");

		// Algoritmo A*
		EGraph<ProductosNavidadVertex, ProductosNavidadEdge> graph =
					EGraph.virtual(start,goal,PathType.Sum, Type.Min)
					.edgeWeight(x -> x.weight())
					//.greedyEdge(ProductosNavidadVertex::greedyEdge)
					.goalHasSolution(ProductosNavidadVertex.goalHasSolution())
					.heuristic(ProductosNavidadHeuristic::heuristic0)
					.build();
					
		AStar<ProductosNavidadVertex, ProductosNavidadEdge,?> aStar = AStar.ofGreedy(graph);
			
		GraphPath<ProductosNavidadVertex, ProductosNavidadEdge> gp = aStar.search().get();
			
		List<Integer> gp_as = gp.getEdgeList().stream().map(x -> x.action())
					.collect(Collectors.toList());
	
		SolucionProductosNavidad s_as = SolucionProductosNavidad.of(gp_as);

		System.out.println(s_as);
//		System.out.println(gp_as);

		GraphColors.toDot(aStar.outGraph(), "ficheros_generados/ProductosNavidadAStarGraph3.gv", 
					v -> v.toString(),
					e -> e.action().toString(), 
					v -> GraphColors.colorIf(Color.red, ProductosNavidadVertex.goal().test(v)),
					e -> GraphColors.colorIf(Color.red, gp.getEdgeList().contains(e)));
	}
	

}
