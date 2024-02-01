package ejercicios;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.tour.HeldKarpTSP;

import datos.Atraccion;
import datos.RelacionVecindad;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;

public class Ejercicio2 {
	
	public static void apartadoA(String file, Graph<Atraccion, RelacionVecindad> g,
			Atraccion a1, Atraccion a2,
			String nombreVista) {
		
		var alg= new DijkstraShortestPath<>(g);
		var camino= alg.getPath(a1, a2);
				
		GraphColors.toDot(g,"resultados/ejercicios/ejercicio2/" + file + nombreVista + ".gv",
				x-> x.nombreAtraccion(), x-> x.toString(),
				v->GraphColors.colorIf(Color.magenta, camino.getVertexList().contains(v)),
				e->GraphColors.colorIf(Color.magenta, camino.getEdgeList().contains(e)));
		
		System.out.println(file + nombreVista + ".gv generado en " + "resultados/ejercicios/ejercicio2/");
		System.out.println("\nAtraccion origen: " + a1.nombreAtraccion());
		System.out.println("Atraccion destino: " + a2.nombreAtraccion());
		System.out.println("Camino mas corto en distancia: " + camino.getVertexList());

	}
	
	public static void apartadoB(String file, Graph<Atraccion, RelacionVecindad> g,
			String nombreVista) {
		
		HeldKarpTSP<Atraccion, RelacionVecindad> alg= new HeldKarpTSP<>();
		GraphPath<Atraccion, RelacionVecindad> camino= alg.getTour(g);
		Map<Atraccion, Color> coloresCircuito= new HashMap<>();
		for(Atraccion atr: camino.getVertexList()) {
			if(atr.equals(camino.getStartVertex())) {
				coloresCircuito.put(atr, Color.magenta);
			}else {
				coloresCircuito.put(atr, Color.blue);
			}
		}
				
		GraphColors.toDot(g,"resultados/ejercicios/ejercicio2/" + file + nombreVista + ".gv",
				x-> x.nombreAtraccion(), x-> x.toString(),
				v->GraphColors.color(coloresCircuito.get(v)),
				e->GraphColors.colorIf(Color.blue, camino.getEdgeList().contains(e)));
		
		System.out.println(file + nombreVista + ".gv generado en " + "resultados/ejercicios/ejercicio2/");
		System.out.println("\nCamino mas corto en tiempo pasando por todas las Atracciones una sola vez: "
		+ camino.getVertexList());

	}
	
	public static void apartadoC(String file, Graph<Atraccion, RelacionVecindad> g, Integer nHoras,
			String nombreVista) {
		
		Atraccion a= getAtraccionMasPopular(g.vertexSet()); 
		List<Atraccion> caminoV= new ArrayList<>();
		List<RelacionVecindad> caminoE= new ArrayList<>();
		caminoV.add(a);
		
		Map<Atraccion, Color> coloresPopulares= new HashMap<>();
		Double tiempo= nHoras*60.;
		tiempo= tiempo - (a.tEspera() + a.tDuracion());
		
		while(tiempo>0) { 
			List<Atraccion> listaVecinos= Graphs.neighborListOf(g, a);
			Atraccion atrSelec= getAtraccionMasPopularCercana(new HashSet<>(listaVecinos), caminoV);
			if(atrSelec!=null) {
				tiempo= tiempo - 
						(g.getEdge(a, atrSelec).tiempo() +
						atrSelec.tEspera() 
						+ atrSelec.tDuracion());
				if(tiempo>0) {
					caminoV.add(atrSelec);
					caminoE.add(g.getEdge(a, atrSelec));
				}
			}else {
				break;
			}
			a= atrSelec;
		}
		
		for(Atraccion atr: caminoV) {
			if(atr==caminoV.get(0)) {
				coloresPopulares.put(atr, Color.magenta);
			}else if(caminoV.contains(atr)) {
				coloresPopulares.put(atr, Color.blue);
			}
		}
		
		GraphColors.toDot(g,"resultados/ejercicios/ejercicio2/" + file + nombreVista + ".gv",
				x-> x.nombreAtraccion(), x-> x.toString(),
				v->GraphColors.colorIf(coloresPopulares.get(v), coloresPopulares.keySet().contains(v)),
				e->GraphColors.colorIf(Color.blue, caminoE.contains(e)));
		
		System.out.println(file + nombreVista + ".gv generado en " + "resultados/ejercicios/ejercicio2/");
		System.out.println("\nCamino de Atracciones mas populares con "
				+ nHoras + " horas disponibles: "
				+ caminoV);

	}
	
	private static Atraccion getAtraccionMasPopular(Set<Atraccion> cjtoAtracciones) {
		Atraccion a= null;
		for(Atraccion atr: cjtoAtracciones) {
			if(a==null || atr.popularidad()>a.popularidad()) {
				a= atr;
			}
		}
		return a;
	}
	
	private static List<Atraccion> getAtraccionesMasPopulares(Set<Atraccion> conjuntoAtracciones) {
		
		List<Atraccion> listaAtraccionesMasPopulares= conjuntoAtracciones.stream()
				.sorted(Comparator.comparing(Atraccion::popularidad).reversed())
				.collect(Collectors.toList());
		
		return listaAtraccionesMasPopulares;
		
	}
	
	private static Atraccion getAtraccionMasPopularCercana(Set<Atraccion> cjtoAtracciones, List<Atraccion> camino) {
		List<Atraccion> atraccionesMasPopularesCercanas= getAtraccionesMasPopulares(cjtoAtracciones);
		Atraccion atr= atraccionesMasPopularesCercanas
				.stream()
				.filter(a-> !camino.contains(a))
				.findFirst()
				.orElse(null);
		return atr;
		
	}
}
