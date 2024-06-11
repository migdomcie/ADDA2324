package ejercicios;

import datos.DatosProductosDistribucion;
import us.lsi.graphs.virtual.SimpleEdgeAction;

public record ProductosDistribucionEdge(ProductosDistribucionVertex source, ProductosDistribucionVertex target, Integer action, Double weight) implements SimpleEdgeAction<ProductosDistribucionVertex, Integer> {
	public static ProductosDistribucionEdge of(ProductosDistribucionVertex source, ProductosDistribucionVertex target, Integer action) {
		return new ProductosDistribucionEdge(source, target, action, action *DatosProductosDistribucion.getCosteAlmc(source.numProducto(), source.numDestino())*1.);
	}
}
