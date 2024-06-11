package tests.PD;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
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
import us.lsi.graphs.alg.PDR;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;
import us.lsi.graphs.alg.GreedyOnGraph;


public class TestPDEj2_2 {

		public static void main(String[] args) {

			// Set up
			Locale.setDefault(Locale.of("en", "US"));

				String id_fichero = "Ejercicio2DatosEntrada2.txt";
				DatosProductosNavidad.iniDatos("ficheros/ejercicios/"+id_fichero);
				System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");
//				DatosSubconjuntos.toConsole();
				// V�rtices clave

				ProductosNavidadVertex start = ProductosNavidadVertex.initial();
				Predicate<ProductosNavidadVertex> goal = ProductosNavidadVertex.goal();


				// Grafo
				
				EGraph<ProductosNavidadVertex, ProductosNavidadEdge> graph = 
						EGraph.virtual(start,goal,PathType.Sum, Type.Min)
						.edgeWeight(x-> x.weight())
						.goalHasSolution(ProductosNavidadVertex.goalHasSolution())
						.heuristic(ProductosNavidadHeuristic::heuristic0)
						.build();

				Boolean conVoraz = false;

				System.out.println("\n\n#### Algoritmo PD ####");
				System.out.println(conVoraz?"\nCon Voraz":"\nSin Voraz");

				PDR<ProductosNavidadVertex, ProductosNavidadEdge,?> pdr = null;
				SolucionProductosNavidad sv = null;
				List<ProductosNavidadEdge> lev = null;
				
				Optional<GraphPath<ProductosNavidadVertex, ProductosNavidadEdge>> gpv = Optional.empty();
				if (conVoraz) {
					GreedyOnGraph<ProductosNavidadVertex, ProductosNavidadEdge> ga = GreedyOnGraph.of(graph);
					//System.out.println(ga.getPath(graph));
					gpv = ga.search();
					if (gpv.isPresent()) {
						sv = SolucionProductosNavidad.of(gpv.get().getEdgeList().stream().map(v-> v.action()).collect(Collectors.toList()));
						lev = gpv.get().getEdgeList();
					}
					//System.out.println("Sv = "+sv);
				}
				if(gpv.isPresent()) 
					pdr = PDR.of(graph, g-> SolucionProductosNavidad.of(g.getEdgeList().stream().map(v-> v.action()).toList()),gpv.get().getWeight(),gpv.get(),true);
				else 
					pdr = PDR.of(graph, null, null, null, true);
				
				Optional<GraphPath<ProductosNavidadVertex, ProductosNavidadEdge>> gp = pdr.search();
				List<ProductosNavidadEdge> le = lev;
				
//				System.out.println(gp);
				SolucionProductosNavidad s_pdr;
				if (gp.isPresent()) {
					List<Integer> gp_pdr = gp.get().getEdgeList().stream().map(x -> x.action())
							.collect(Collectors.toList());
					s_pdr = SolucionProductosNavidad.of(gp_pdr);
				} else { 				
					s_pdr = sv;
				}

				System.out.println(s_pdr);
				
				GraphColors.toDot(pdr.outGraph,"ficheros_generados/ProductosNavidadPDGraph2.gv",
						v->v.toString(),
						e->e.action().toString(),
						v->GraphColors.colorIf(Color.red,ProductosNavidadVertex.goal().test(v)),
						e->GraphColors.colorIf(Color.red,(gp.isPresent()?gp.get().getEdgeList():le).contains(e))
						);
			}
		

	}



