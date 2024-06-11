package tests.BT;


import java.util.List;
import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import datos.DatosPersonas;
import ejercicios.PersonasEdge;
import ejercicios.PersonasHeuristic;
import ejercicios.PersonasVertex;
import ejercicios.SolucionPersonas;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.common.Pair;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TestBTEj4_1 {

	public static void main(String[] args) {

		DatosPersonas.iniDatos("ficheros/ejercicios/Ejercicio4DatosEntrada1.txt");

		PersonasVertex vInicial = PersonasVertex.initial();
		Predicate<PersonasVertex> goal = PersonasVertex.goal(); 

//		System.out.println(vInicial);
//		vInicial.actions();
//		System.out.println(vInicial.neighbor(2));
//		vInicial.neighbor(2).actions();	
//		System.out.println(vInicial.neighbor(2).neighbor(-1));
//		vInicial.neighbor(2).neighbor(-1).actions();
//		System.out.println(vInicial.neighbor(2).neighbor(-1).neighbor(3));
//		vInicial.neighbor(2).neighbor(-1).neighbor(3).actions();
//		System.out.println(vInicial.neighbor(2).neighbor(-1).neighbor(3).neighbor(-1));
//		vInicial.neighbor(2).neighbor(-1).neighbor(3).neighbor(-1).actions();

		
		EGraph<PersonasVertex, PersonasEdge> graph = //(AlumnosVertex v_inicial, Predicate<AlumnosVertex> es_terminal) { 
			EGraph.virtual(vInicial, goal, PathType.Sum, Type.Max)
					.goalHasSolution(PersonasVertex.goalHasSolution())
					//.greedyEdge(AlumnosVertex::greedyEdge)
					.heuristic(PersonasHeuristic::heuristic0)
					.build();

		GreedyOnGraph<PersonasVertex, PersonasEdge> alg_voraz = GreedyOnGraph.of(graph);		
		GraphPath<PersonasVertex, PersonasEdge> path = alg_voraz.path();
//		path = alg_voraz.isSolution(path)? path: null;

//		path = null;
		
		//BT<ProductosNavidadVertex,ProductosNavidadVertex,SolucionProductosNavidad>alg_bt = path==null? BT.of(graph):
			//BT.of(graph, null, path.getWeight(), path, true);
		BT<PersonasVertex,PersonasEdge,SolucionPersonas>alg_bt = BT.of(graph, null, path.getWeight(), path, true);
		
		var res = alg_bt.search().orElse(null);
		var outGraph = alg_bt.outGraph();
		if(outGraph!=null) {
			Predicate<PersonasVertex> vs = v -> res.getVertexList().contains(v);
			Predicate<PersonasEdge> es = e -> res.getEdgeList().contains(e);
			GraphColors.toDot(outGraph, "ficheros_generados/PersonasBTGraph1.gv", 
					v -> v.toString(),
					e -> e.action().toString(), 
					v -> GraphColors.colorIf(Color.red, vs.test(v)),
					e -> GraphColors.colorIf(Color.red, es.test(e)));

		}	
//		System.out.println(res.getVertexList().get(res.getVertexList().size()-1).listaParejas());
		List<Pair<Integer, Integer>> gp_ps= res.getVertexList().get(res.getVertexList().size()-1).listaParejas();
		
		if(res!=null)
			System.out.println("Solucion BT: \n" + SolucionPersonas.ofPairs(gp_ps) + "\n");
		else 
			System.out.println("BT no obtuvo solucion\n");
		
	}
}
