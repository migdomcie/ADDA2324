package tests.BT;


import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import datos.DatosProductosNavidad;
import ejercicios.ProductosNavidadEdge;
import ejercicios.ProductosNavidadHeuristic;
import ejercicios.ProductosNavidadVertex;
import ejercicios.SolucionProductosNavidad;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TestBTEj2_2 {

	public static void main(String[] args) {

		DatosProductosNavidad.iniDatos("ficheros/ejercicios/Ejercicio2DatosEntrada2.txt");

		ProductosNavidadVertex vInicial = ProductosNavidadVertex.initial();
		Predicate<ProductosNavidadVertex> goal = ProductosNavidadVertex.goal(); 
		
		EGraph<ProductosNavidadVertex, ProductosNavidadEdge> graph = //(AlumnosVertex v_inicial, Predicate<AlumnosVertex> es_terminal) { 
			EGraph.virtual(vInicial, goal, PathType.Sum, Type.Min)
					.goalHasSolution(ProductosNavidadVertex.goalHasSolution())
					//.greedyEdge(AlumnosVertex::greedyEdge)
					.heuristic(ProductosNavidadHeuristic::heuristic0)
					.build();

		GreedyOnGraph<ProductosNavidadVertex, ProductosNavidadEdge> alg_voraz = GreedyOnGraph.of(graph);		
		GraphPath<ProductosNavidadVertex, ProductosNavidadEdge> path = alg_voraz.path();
//		path = alg_voraz.isSolution(path)? path: null;

//		path = null;
		
		//BT<ProductosNavidadVertex,ProductosNavidadVertex,SolucionProductosNavidad>alg_bt = path==null? BT.of(graph):
			//BT.of(graph, null, path.getWeight(), path, true);
		BT<ProductosNavidadVertex,ProductosNavidadEdge,SolucionProductosNavidad>alg_bt = BT.of(graph, null, path.getWeight(), path, true);
		
		var res = alg_bt.search().orElse(null);
		var outGraph = alg_bt.outGraph();
		if(outGraph!=null) {
			Predicate<ProductosNavidadVertex> vs = v -> res.getVertexList().contains(v);
			Predicate<ProductosNavidadEdge> es = e -> res.getEdgeList().contains(e);
			GraphColors.toDot(outGraph, "ficheros_generados/ProductosNavidadBTGraph2.gv", 
					v -> v.toString(),
					e -> e.action().toString(), 
					v -> GraphColors.colorIf(Color.red, vs.test(v)),
					e -> GraphColors.colorIf(Color.red, es.test(e)));

		}	
//		System.out.println(outGraph.vertexSet());
		if(res!=null)
			System.out.println("Solucion BT: " + SolucionProductosNavidad.of(res.getEdgeList().stream().map(v-> v.action()).toList()) + "\n");
		else 
			System.out.println("BT no obtuvo solucion\n");
		
	}
}
