package ejercicios;

import datos.DatosProductosNavidad;
import us.lsi.graphs.virtual.SimpleEdgeAction;

public record ProductosNavidadEdge(ProductosNavidadVertex source, ProductosNavidadVertex target, Integer action, Double weight) implements SimpleEdgeAction<ProductosNavidadVertex, Integer> {

	public static ProductosNavidadEdge of(ProductosNavidadVertex source, ProductosNavidadVertex target, Integer action) {
		return new ProductosNavidadEdge(source, target, action, action==1?DatosProductosNavidad.getPrecio(source.indice()):0.);
	}
}
