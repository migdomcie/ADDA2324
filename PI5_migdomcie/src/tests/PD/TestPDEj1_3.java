package tests.PD;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
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
import us.lsi.graphs.alg.PDR;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;
import us.lsi.graphs.alg.GreedyOnGraph;


public class TestPDEj1_3 {

		public static void main(String[] args) {

			// Set up
			Locale.setDefault(Locale.of("en", "US"));

				String id_fichero = "Ejercicio1DatosEntrada3.txt";
				DatosVerduras.iniDatos("ficheros/ejercicios/"+id_fichero);
				System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");
//				DatosSubconjuntos.toConsole();
				// V�rtices clave

				VerdurasVertex start = VerdurasVertex.initial();
				Predicate<VerdurasVertex> goal = VerdurasVertex.goal();


				// Grafo
				
				EGraph<VerdurasVertex, VerdurasEdge> graph = 
						EGraph.virtual(start,goal,PathType.Sum, Type.Max)
						.edgeWeight(x-> x.weight())
						.goalHasSolution(VerdurasVertex.goalHasSolution())
						.heuristic(VerdurasHeuristic::heuristic1)
						.build();

				Boolean conVoraz = false;

				System.out.println("\n\n#### Algoritmo PD ####");
				System.out.println(conVoraz?"\nCon Voraz":"\nSin Voraz");

				PDR<VerdurasVertex, VerdurasEdge,?> pdr = null;
				SolucionVerduras sv = null;
				List<VerdurasEdge> lev = null;
				
				Optional<GraphPath<VerdurasVertex, VerdurasEdge>> gpv = Optional.empty();
				if (conVoraz) {
					GreedyOnGraph<VerdurasVertex, VerdurasEdge> ga = GreedyOnGraph.of(graph);
					//System.out.println(ga.getPath(graph));
					gpv = ga.search();
					if (gpv.isPresent()) {
						sv = SolucionVerduras.of(gpv.get().getEdgeList().stream().map(v-> v.action()).collect(Collectors.toList()));
						lev = gpv.get().getEdgeList();
					}
					//System.out.println("Sv = "+sv);
				}
				if(gpv.isPresent()) 
					pdr = PDR.of(graph, g-> SolucionVerduras.of(g.getEdgeList().stream().map(v-> v.action()).toList()),gpv.get().getWeight(),gpv.get(),true);
				else 
					pdr = PDR.of(graph, null, null, null, true);
				
				Optional<GraphPath<VerdurasVertex, VerdurasEdge>> gp = pdr.search();
				List<VerdurasEdge> le = lev;
				
//				System.out.println(gp);
				SolucionVerduras s_pdr;
				if (gp.isPresent()) {
					List<Integer> gp_pdr = gp.get().getEdgeList().stream().map(x -> x.action())
							.collect(Collectors.toList());
					s_pdr = SolucionVerduras.of(gp_pdr);
				} else { 				
					s_pdr = sv;
				}

				System.out.println(s_pdr);
				
				GraphColors.toDot(pdr.outGraph,"ficheros_generados/VerdurasPDGraph3.gv",
						v->v.toString(),
						e->e.action().toString(),
						v->GraphColors.colorIf(Color.red,VerdurasVertex.goal().test(v)),
						e->GraphColors.colorIf(Color.red,(gp.isPresent()?gp.get().getEdgeList():le).contains(e))
						);
			}
		

	}



