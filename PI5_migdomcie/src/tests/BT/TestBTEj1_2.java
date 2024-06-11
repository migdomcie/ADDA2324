package tests.BT;


import java.util.function.Predicate;
import java.util.stream.Collectors;


import datos.DatosVerduras;

import ejercicios.VerdurasVertex;
import ejercicios.SolucionVerduras;
import ejercicios.VerdurasEdge;
import ejercicios.VerdurasHeuristic;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TestBTEj1_2 {

	public static void main(String[] args) {

		DatosVerduras.iniDatos("ficheros/ejercicios/Ejercicio1DatosEntrada2.txt");

		VerdurasVertex vInicial = VerdurasVertex.initial();
		Predicate<VerdurasVertex> goal = VerdurasVertex.goal(); 
		
		EGraph<VerdurasVertex, VerdurasEdge> graph = //(AlumnosVertex v_inicial, Predicate<AlumnosVertex> es_terminal) { 
			EGraph.virtual(vInicial, goal, PathType.Sum, Type.Max)
					.goalHasSolution(VerdurasVertex.goalHasSolution())
					//.greedyEdge(AlumnosVertex::greedyEdge)
					.heuristic(VerdurasHeuristic::heuristic1)
					.build();
				
		//GreedyOnGraph<VerdurasVertex, VerdurasEdge> alg_voraz = GreedyOnGraph.of(graph);		
		//GraphPath<VerdurasVertex, VerdurasEdge> path = alg_voraz.path();
//		path = alg_voraz.isSolution(path)? path: null;

//		path = null;
		
		//BT<ProductosNavidadVertex,ProductosNavidadVertex,SolucionProductosNavidad>alg_bt = path==null? BT.of(graph):
			//BT.of(graph, null, path.getWeight(), path, true);
	//	BT<VerdurasVertex,VerdurasEdge,SolucionVerduras>alg_bt = BT.of(graph, null, path.getWeight(), path, true);
		BT<VerdurasVertex,VerdurasEdge,SolucionVerduras>alg_bt = BT.of(graph, null, null, null, true);
		
		var res = alg_bt.search().orElse(null);
		var outGraph = alg_bt.outGraph();
		if(outGraph!=null) {
			Predicate<VerdurasVertex> vs = v -> res.getVertexList().contains(v);
			Predicate<VerdurasEdge> es = e -> res.getEdgeList().contains(e);
			GraphColors.toDot(outGraph, "ficheros_generados/VerdurasBTGraph2.gv", 
					v -> v.toString(),
					e -> e.action().toString(), 
					v -> GraphColors.colorIf(Color.red, vs.test(v)),
					e -> GraphColors.colorIf(Color.red, es.test(e)));

		}	
		if(res!=null) {
//			System.out.println(res.getEdgeList().stream().map(e-> e.action()).collect(Collectors.toList()));
			System.out.println("Solucion BT: \n" + SolucionVerduras.of(res.getEdgeList().stream().map(e-> e.action()).collect(Collectors.toList())) + "\n");
		
		}
		else 
			System.out.println("BT no obtuvo solucion\n");
		
	}
}
