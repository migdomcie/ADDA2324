package ejercicios;

import java.util.List;
import java.util.stream.Collectors;


import datos.DatosPersonas;
import us.lsi.common.List2;
import us.lsi.common.Pair;

public class SolucionPersonas {
	private Integer totalAfinidades;
	private List<Pair<Integer,Integer>> solucion;
	
	public static SolucionPersonas of(List<Integer> ls) {
		return new SolucionPersonas(ls);
	}
	
	public static SolucionPersonas empty() {
		return new SolucionPersonas();
	}
	
	private SolucionPersonas() {
		totalAfinidades = 0;
		solucion = List2.empty();
	}
	
	private SolucionPersonas(List<Integer> ls) {
		totalAfinidades = 0;
		solucion = List2.empty();
		
		List<Integer> lista= List2.copy(ls);
		if(lista.size()%2==1) {
			lista.remove(lista.size()-1);
		}
		for(int i=0; i<lista.size()-1; i= i+2) {
			Pair<Integer, Integer> p = Pair.of(lista.get(i), lista.get(i + 1));
			totalAfinidades = totalAfinidades + DatosPersonas.getAfinidad(p.first(), p.second());
			solucion.add(p);
		}
	}
	
	@Override
	public String toString() {
		String str= solucion.stream().map(e-> String.format("(%d, %d)", e.first(), e.second()))
						.collect(Collectors.joining(", ", "Relacion de parejas:\n[",
								String.format("]\n\nSuma de afinidades: %d", totalAfinidades)));
		return str;
	}
}
