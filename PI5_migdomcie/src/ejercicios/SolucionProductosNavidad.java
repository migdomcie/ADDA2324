package ejercicios;

import java.util.List;
import java.util.stream.Collectors;

import datos.DatosProductosNavidad;
import datos.DatosProductosNavidad.Producto;
import us.lsi.common.List2;


public class SolucionProductosNavidad {
	
	private Integer totalPrecioCesta;
	private List<Producto> solucion;
	
	public static SolucionProductosNavidad of(List<Integer> ls) {
		return new SolucionProductosNavidad(ls);
	}
	public static SolucionProductosNavidad empty() {
		return new SolucionProductosNavidad();
	}
	
	private SolucionProductosNavidad() {
		totalPrecioCesta = 0;
		solucion = List2.empty();
	}
	
	private SolucionProductosNavidad(List<Integer> ls) {
		totalPrecioCesta = 0;
		solucion = List2.empty();
		for(int i=0; i<ls.size(); i++) {
			if(ls.get(i)>0) {
				totalPrecioCesta = totalPrecioCesta + DatosProductosNavidad.getProducto(i).precio();
				solucion.add(DatosProductosNavidad.getProducto(i));
			}
		}
	}

	@Override
	public String toString() {
		String str= solucion.stream().map(Producto::toString)
				.collect(Collectors.joining(",", "Productos elegidos: {",
						String.format("}\nPrecio total de la cesta: %d", totalPrecioCesta)));
		return str;
	}
	
}
