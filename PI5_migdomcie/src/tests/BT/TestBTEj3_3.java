package tests.BT;


import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import datos.DatosProductosDistribucion;
import ejercicios.ProductosDistribucionEdge;
import ejercicios.ProductosDistribucionHeuristic;
import ejercicios.ProductosDistribucionVertex;
import ejercicios.SolucionProductosDistribucion;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TestBTEj3_3 {

	public static void main(String[] args) {

		DatosProductosDistribucion.iniDatos("ficheros/ejercicios/Ejercicio3DatosEntrada3.txt");

		ProductosDistribucionVertex vInicial = ProductosDistribucionVertex.initial();
		Predicate<ProductosDistribucionVertex> goal = ProductosDistribucionVertex.goal(); 
		
//		System.out.println(vInicial);
//		System.out.println(vInicial.neighbor(5));
		EGraph<ProductosDistribucionVertex, ProductosDistribucionEdge> graph = //(AlumnosVertex v_inicial, Predicate<AlumnosVertex> es_terminal) { 
			EGraph.virtual(vInicial, goal, PathType.Sum, Type.Min)
					.goalHasSolution(ProductosDistribucionVertex.goalHasSolution())
					//.greedyEdge(AlumnosVertex::greedyEdge)
					.heuristic(ProductosDistribucionHeuristic::heuristic1)
					.build();

//		GreedyOnGraph<ProductosDistribucionVertex, ProductosDistribucionEdge> alg_voraz = GreedyOnGraph.of(graph);		
//		GraphPath<ProductosDistribucionVertex, ProductosDistribucionEdge> path = alg_voraz.path();
//		path = alg_voraz.isSolution(path)? path: null;

//		path = null;
		
		//BT<ProductosNavidadVertex,ProductosNavidadVertex,SolucionProductosNavidad>alg_bt = path==null? BT.of(graph):
			//BT.of(graph, null, path.getWeight(), path, true);
		BT<ProductosDistribucionVertex,ProductosDistribucionEdge,SolucionProductosDistribucion>alg_bt = BT.of(graph, null, null, null, true);
		
		var res = alg_bt.search().orElse(null);
		var outGraph = alg_bt.outGraph();
		if(outGraph!=null) {
			Predicate<ProductosDistribucionVertex> vs = v -> res.getVertexList().contains(v);
			Predicate<ProductosDistribucionEdge> es = e -> res.getEdgeList().contains(e);
			GraphColors.toDot(outGraph, "ficheros_generados/ProductosDistribucionBTGraph3.gv", 
					v -> v.toString(),
					e -> e.action().toString(), 
					v -> GraphColors.colorIf(Color.red, vs.test(v)),
					e -> GraphColors.colorIf(Color.red, es.test(e)));

		}	
//		System.out.println(outGraph.vertexSet());
		if(res!=null)
			System.out.println("Solucion BT: " + SolucionProductosDistribucion.of(res.getEdgeList().stream().map(p-> p.action()).toList()) + "\n");
		else 
			System.out.println("BT no obtuvo solucion\n");
		
	}
}
