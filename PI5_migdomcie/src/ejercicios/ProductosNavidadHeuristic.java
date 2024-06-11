package ejercicios;

import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import datos.DatosProductosNavidad;

public class ProductosNavidadHeuristic {
	
	public static Double heuristic0(ProductosNavidadVertex v1, Predicate<ProductosNavidadVertex> goal, ProductosNavidadVertex v2) {
		return 0.;
	}
	
	public static Double heuristic1(ProductosNavidadVertex v1, Predicate<ProductosNavidadVertex> goal, ProductosNavidadVertex v2) {
		Double res;
		if(v1.categoriasPorCubrir().isEmpty()) {
			res=0.;
		}else {
			res=IntStream.range(v1.indice(), ProductosNavidadVertex.numeroProductos)
					.filter(p-> v1.categoriasPorCubrir().contains(DatosProductosNavidad.getCategoria(p)))
					.mapToDouble(p-> DatosProductosNavidad.getPrecio(p))
					.sum();
		}
		return res;
	}

}
