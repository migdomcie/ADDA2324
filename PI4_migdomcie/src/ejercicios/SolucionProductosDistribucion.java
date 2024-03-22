package ejercicios;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import datos.DatosProductosDistribucion;
import us.lsi.common.List2;
import us.lsi.common.Trio;

public class SolucionProductosDistribucion {
	
	private Integer totalCosteAlmac;
	private List<Trio<Integer, Integer, Integer>> solucion;
	
	public static SolucionProductosDistribucion of(List<Integer> ls) {
		return new SolucionProductosDistribucion(ls);
	}
	
	public static SolucionProductosDistribucion empty() {
		return new SolucionProductosDistribucion();
	}
	
	private SolucionProductosDistribucion() {
		totalCosteAlmac = 0;
		solucion = List2.empty();
	}
	
	private SolucionProductosDistribucion(List<Integer> ls) {
		totalCosteAlmac = 0;
		solucion = List2.empty();
		for(int n=0; n<ls.size(); n++) {
			int i = n/DatosProductosDistribucion.getNumDestinos();
			int j = n%DatosProductosDistribucion.getNumDestinos();
			if(ls.get(n)>0) {
				totalCosteAlmac= totalCosteAlmac + DatosProductosDistribucion.getCosteAlmc(i,j) * ls.get(n);
			}
			solucion.add(Trio.of(i, j, ls.get(n)));
		}
	}

	@Override
	public String toString() {
		Map<Integer, List<Integer>> m= solucion.stream()
							.collect(Collectors.groupingBy(t-> t.first(), Collectors.mapping(t-> t.third(), Collectors.toList())));
		String str= m.entrySet().stream()
				.map(e-> e.getKey() + ": " + e.getValue())
				.collect(Collectors.joining("\n", "Reparto obtenido (cantidad de producto repartido a cada destino):"
						+ "\n//Destino m:[cantidad de producto n, cantidad de producto n+1, ...]\n",
						String.format("\nCoste total de almacenamiento: %d", totalCosteAlmac)));
						
		return str;
	}

}
