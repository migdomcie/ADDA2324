package tests.PD;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;

import datos.DatosPersonas;
import datos.DatosProductosDistribucion;
import datos.DatosProductosNavidad;
import datos.DatosVerduras;
import ejercicios.PersonasEdge;
import ejercicios.PersonasHeuristic;
import ejercicios.PersonasVertex;
import ejercicios.ProductosDistribucionEdge;
import ejercicios.ProductosDistribucionHeuristic;
import ejercicios.ProductosDistribucionVertex;
import ejercicios.ProductosNavidadEdge;
import ejercicios.ProductosNavidadHeuristic;
import ejercicios.ProductosNavidadVertex;
import ejercicios.SolucionPersonas;
import ejercicios.SolucionProductosDistribucion;
import ejercicios.SolucionProductosNavidad;
import ejercicios.SolucionVerduras;
import ejercicios.VerdurasEdge;
import ejercicios.VerdurasHeuristic;
import ejercicios.VerdurasVertex;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.common.Pair;
import us.lsi.graphs.alg.PDR;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;
import us.lsi.graphs.alg.GreedyOnGraph;


public class TestPDEj4_3 {

		public static void main(String[] args) {

			// Set up
			Locale.setDefault(Locale.of("en", "US"));

				String id_fichero = "Ejercicio4DatosEntrada3.txt";
				DatosPersonas.iniDatos("ficheros/ejercicios/"+id_fichero);
				System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");
//				DatosSubconjuntos.toConsole();
				// V�rtices clave

				PersonasVertex start = PersonasVertex.initial();
				Predicate<PersonasVertex> goal = PersonasVertex.goal();


				// Grafo
				
				EGraph<PersonasVertex, PersonasEdge> graph = 
						EGraph.virtual(start,goal,PathType.Sum, Type.Max)
						.edgeWeight(x-> x.weight())
						.goalHasSolution(PersonasVertex.goalHasSolution())
						.heuristic(PersonasHeuristic::heuristic0)
						.build();

				Boolean conVoraz = false;

				System.out.println("\n\n#### Algoritmo PD ####");
				System.out.println(conVoraz?"\nCon Voraz":"\nSin Voraz");

				PDR<PersonasVertex, PersonasEdge,?> pdr = null;
				SolucionPersonas sv = null;
				List<PersonasEdge> lev = null;
				
				Optional<GraphPath<PersonasVertex, PersonasEdge>> gpv = Optional.empty();
				if (conVoraz) {
					GreedyOnGraph<PersonasVertex, PersonasEdge> ga = GreedyOnGraph.of(graph);
					//System.out.println(ga.getPath(graph));
					gpv = ga.search();
					if (gpv.isPresent()) {
						sv = SolucionPersonas.of(gpv.get().getEdgeList().stream().map(v-> v.action()).collect(Collectors.toList()));
						lev = gpv.get().getEdgeList();
					}
					//System.out.println("Sv = "+sv);
				}
				if(gpv.isPresent()) 
					pdr = PDR.of(graph, g-> SolucionPersonas.of(g.getEdgeList().stream().map(v-> v.action()).toList()),gpv.get().getWeight(),gpv.get(),true);
				else 
					pdr = PDR.of(graph, null, null, null, true);
				
				Optional<GraphPath<PersonasVertex, PersonasEdge>> gp = pdr.search();
				List<PersonasEdge> le = lev;
				
//				System.out.println(gp);
				SolucionPersonas s_pdr;
				if (gp.isPresent()) {
					List<Pair<Integer,Integer>> gp_pdr = gp.get().getVertexList().get(gp.get().getVertexList().size()-1).listaParejas();
					s_pdr = SolucionPersonas.ofPairs(gp_pdr);
				} else { 				
					s_pdr = sv;
				}

				System.out.println(s_pdr);
				
				GraphColors.toDot(pdr.outGraph,"ficheros_generados/PersonasPDGraph3.gv",
						v->v.toString(),
						e->e.action().toString(),
						v->GraphColors.colorIf(Color.red, PersonasVertex.goal().test(v)),
						e->GraphColors.colorIf(Color.red,(gp.isPresent()?gp.get().getEdgeList():le).contains(e))
						);
			}
		

	}



