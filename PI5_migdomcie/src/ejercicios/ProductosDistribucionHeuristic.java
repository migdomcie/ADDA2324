package ejercicios;

import java.util.function.Predicate;
import java.util.stream.IntStream;

import datos.DatosProductosDistribucion;

public class ProductosDistribucionHeuristic {
	
	public static Double heuristic0(ProductosDistribucionVertex v1, Predicate<ProductosDistribucionVertex> goal, ProductosDistribucionVertex v2) {
		return 0.;
	}
	
	public static Double heuristic1(ProductosDistribucionVertex v1, Predicate<ProductosDistribucionVertex> goal, ProductosDistribucionVertex v2) {
		Double res;
		if(v1.demandasRestantes().stream().allMatch(d-> d<=0)) {
			res=0.;
		}else {
			res= IntStream.range(v1.indice(), ProductosDistribucionVertex.numeroProductos*ProductosDistribucionVertex.numeroDestinos)
						.filter(ind-> v1.demandasRestantes().get(ind%ProductosDistribucionVertex.numeroDestinos)>0)
						.mapToDouble(ind-> DatosProductosDistribucion.getCosteAlmc(ind/ProductosDistribucionVertex.numeroDestinos, ind%ProductosDistribucionVertex.numeroDestinos))
						.min()
						.orElse(Double.MAX_VALUE);
		}
		return res;
	}
	


}
