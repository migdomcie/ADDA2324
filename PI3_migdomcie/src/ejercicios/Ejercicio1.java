package ejercicios;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.vertexcover.RecursiveExactVCImpl;
import org.jgrapht.traverse.BreadthFirstIterator;

import datos.RelacionUsuario;
import datos.Usuario;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.views.SubGraphView;

public class Ejercicio1 {
	
	// Crea vista del grafo
	public static void apartadoA(String file, Graph<Usuario,RelacionUsuario> g,
			Predicate<Usuario> pv, Predicate<RelacionUsuario> pa,
			String nombreVista) {
		
		Graph<Usuario, RelacionUsuario> vista = SubGraphView.of(g, pv, pa);

		GraphColors.toDot(g,"resultados/ejercicios/ejercicio1/" + file + nombreVista + ".gv",
				x-> x.nombreUsuario(), x-> x.toString(),
				v->GraphColors.colorIf(Color.blue, vista.containsVertex(v)),
				e->GraphColors.colorIf(Color.blue, vista.containsEdge(e)));
		
		System.out.println(file + nombreVista + ".gv generado en " + "resultados/ejercicios/ejercicio1/");
		System.out.println("\nLos usuarios que siguen a mas de 3 usuarios y"
				+ " con un indice de interaccion de media de mas de 2.5 son: "
				+ vista.vertexSet());
	
	}
	
	public static void apartadoB(String file, Graph<Usuario,RelacionUsuario> g,
			String nombreVista) {
		
		var c= new ConnectivityInspector(g);
		List<Set<Usuario>> comps= c.connectedSets();
		Map<Usuario, Integer> compsConexasColores= new HashMap<>();
		Integer num=0;
		
		for(Set<Usuario> st: comps) {
			for(Usuario user: st) {
				compsConexasColores.put(user, num);
			}
			num= num + 1;
		}
		
		GraphColors.toDot(g,"resultados/ejercicios/ejercicio1/" + file + nombreVista + ".gv",
				x-> x.nombreUsuario(), x-> x.toString(),
				v->GraphColors.color(compsConexasColores.get(v)),
				e->GraphColors.color(compsConexasColores.get(g.getEdgeSource(e))));
		
		System.out.println(file + nombreVista + ".gv generado en " + "resultados/ejercicios/ejercicio1/");
		
		System.out.println("\nHay " + comps.size() + " componente(s) conexa(s).");
		Integer num_= 1;
		for(Set<Usuario> st: comps) {
			System.out.println("Componente conexa " + num_ + ": " + st);
			num_= num_ + 1;
		}
	}
	
	public static void apartadoC(String file, Graph<Usuario,RelacionUsuario> g,
			Graph<Usuario,RelacionUsuario> gsimple,
			String nombreVista) {
		
		var vc= new RecursiveExactVCImpl<>(gsimple);
				
		GraphColors.toDot(g,"resultados/ejercicios/ejercicio1/" + file + nombreVista + ".gv",
				x-> x.nombreUsuario(), x-> x.toString(),
				v->GraphColors.colorIf(Color.red, vc.getVertexCover().contains(v)),
				e->GraphColors.color(Color.black));
		
		System.out.println(file + nombreVista + ".gv generado en " + "resultados/ejercicios/ejercicio1/");
		System.out.println("\nUsuarios seleccionados: " + vc.getVertexCover());

	}
	
	public static void apartadoD(String file, Graph<Usuario,RelacionUsuario> g,
			String nombreVista) {
		
		List<Usuario> lista= g.vertexSet().stream()
							   .filter(v-> g.inDegreeOf(v) >=5 && v.cjtoAficiones().size() > 3 && v.indiceAct() > 4)
							   .sorted(Comparator.comparing(u -> promedio(g.edgesOf((Usuario) u))).reversed())
							   .limit(2)
							   .collect(Collectors.toList());
		
		GraphColors.toDot(g,"resultados/ejercicios/ejercicio1/" + file + nombreVista + ".gv",
				x-> x.nombreUsuario(), x-> x.toString(),
				v->GraphColors.colorIf(Color.red, lista.contains(v)),
				e->GraphColors.color(Color.black));
		
		System.out.println(file + nombreVista + ".gv generado en " + "resultados/ejercicios/ejercicio1/");
		System.out.println("\nLos usuarios con mayor interaccion media son: " + lista);

	}
	
	public static Double promedio(Set<RelacionUsuario> usuariosSeguidos) {
		Double suma=0.;
		for(RelacionUsuario r: usuariosSeguidos) {
			suma= suma + r.indInter();
		}
		return suma/usuariosSeguidos.size();
	}
}
