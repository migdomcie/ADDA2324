package ejercicios;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.traverse.TopologicalOrderIterator;


import datos.RelacionTarea;
import datos.Tarea;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;

public class Ejercicio3 {
	
	public static void apartadoA(String file, Graph<Tarea, RelacionTarea> g) {
		
		var it= new TopologicalOrderIterator<>(g);
		List<Tarea> ls= new ArrayList<>();
		it.forEachRemaining(v-> ls.add(v));
		
		System.out.println("\nOrden de tareas: " + ls);
	}
	
	public static void apartadoB(String file, Graph<Tarea, RelacionTarea> g,
			Tarea tarea, String nombreVista) {
		
		List<Tarea> listaPrecedentes= new ArrayList<>();
		Map<Tarea, Color> precedentesColores= new HashMap<>();
		listaPrecedentes.add(tarea);
		precedentesColores.put(tarea, Color.magenta);
		
		for(int i=0; i<listaPrecedentes.size(); i++) {
			List<Tarea> ls= Graphs.predecessorListOf(g, listaPrecedentes.get(i));
			for(Tarea t: ls) {
				if(!listaPrecedentes.contains(t)) {
					listaPrecedentes.add(t);
					precedentesColores.put(t, Color.blue);
				}
			}
		}
		listaPrecedentes.remove(0);
		
		GraphColors.toDot(g,"resultados/ejercicios/ejercicio3/" + file + nombreVista + ".gv",
				x-> x.tarea(), x-> x.toString(),
				v->GraphColors.colorIf(precedentesColores.get(v), precedentesColores.containsKey(v)),
				e->GraphColors.color(Color.black));
		
		System.out.println(file + nombreVista + ".gv generado en " + "resultados/ejercicios/ejercicio3/");
		System.out.println("\nTareas que deben realizarse previamente a " +
							tarea.tarea() + ": " + listaPrecedentes);
	}
	
	public static void apartadoC(String file, Graph<Tarea, RelacionTarea> g, String nombreVista) {
		
		Map<Tarea, List<Tarea>> tareasDependientesPorTarea= new HashMap<>();
		//necesito crear un map en el que por cada tarea, se asocie a ella la lista
		//de dependientes de cada una de ellas. Para ello, habría que utilizar el método
		//los sucesores por cada una de los tareas añadidas
		
		for(Tarea t: g.vertexSet()) {
			List<Tarea> tareasDependientes= new ArrayList<>();
			tareasDependientes.add(t);
			for(int i=0; i<tareasDependientes.size(); i++) {
				List<Tarea> ls= Graphs.successorListOf(g, tareasDependientes.get(i));
				for(Tarea tt: ls) {
					if(!tareasDependientes.contains(tt)) {
						tareasDependientes.add(tt);
					}
				}
			}
			tareasDependientes.remove(0);
			tareasDependientesPorTarea.put(t, tareasDependientes);
		}
		List<Tarea> listaMayoresDependientes= new ArrayList<>();
		Integer nMaxTareasDep= Integer.valueOf(tareasDependientesPorTarea.entrySet().stream()
			.collect(Collectors.maxBy(Comparator.comparing(e-> e.getValue().size())))
			.get()
			.getValue()
			.size());
		tareasDependientesPorTarea.entrySet().stream().forEach(e ->{
			if(e.getValue().size()==nMaxTareasDep) {
				listaMayoresDependientes.add(e.getKey());
			}
		});
		
		
		GraphColors.toDot(g,"resultados/ejercicios/ejercicio3/" + file + nombreVista + ".gv",
				x-> x.tarea(), x-> x.toString(),
				v->GraphColors.colorIf(Color.magenta, listaMayoresDependientes.contains(v)),
				e->GraphColors.color(Color.black));
		
		System.out.println(file + nombreVista + ".gv generado en " + "resultados/ejercicios/ejercicio3/");
		System.out.println("\nTareas mas dependientes" + ": " + listaMayoresDependientes);
	}
}
