package ejercicios.manualBT;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import datos.DatosProductosDistribucion;
import ejercicios.ProductosDistribucionVertex;
import us.lsi.common.List2;

public record ProductosDistribucionProblem(Integer indice, List<Integer> unidadesRestantes, List<Integer> demandasRestantes) {

	public static Integer numeroProductos= DatosProductosDistribucion.getNumProductos();
	public static Integer numeroDestinos= DatosProductosDistribucion.getNumDestinos();
	
	public Integer numProducto() {
		return indice()/numeroDestinos;
	}
	
	public Integer numDestino() {
		return indice()%numeroDestinos;
	}
	
	public static ProductosDistribucionProblem of(Integer indice, List<Integer> unidadesRestantes, List<Integer> demandasRestantes) {
		return new ProductosDistribucionProblem(indice, unidadesRestantes, demandasRestantes);
	}
	
	@Override
	public String toString() {
		return String.format("P%d, udsRest=%s, demRest=%s)", indice(), unidadesRestantes(), demandasRestantes());
	}

	public static ProductosDistribucionProblem initial() {
		List<Integer> unidades0= DatosProductosDistribucion.getUnidades();
		List<Integer> demandas0= DatosProductosDistribucion.getDemandas();
		return of(0, unidades0, demandas0);
	}
	
	public List<Integer> actions() {
		List<Integer> alternativas= List2.empty();
//		System.out.println("z: "+indice());
//		System.out.println("i: " +numProducto());
//		System.out.println("j: " +numDestino());
//		System.out.println("restantes: " +unidadesRestantes());
		if(indice()< numeroProductos*numeroDestinos) {
			if(unidadesRestantes().get(numProducto()) == 0) {
				alternativas= List2.of(0);
			}else {
				if(unidadesRestantes().get(numProducto()) - demandasRestantes().get(numDestino()) >= 0){
					if(numProducto() == DatosProductosDistribucion.getProductos().get(numeroProductos-1).id()){
						alternativas= List2.of(demandasRestantes().get(numDestino()));
					}else{
						alternativas= IntStream.range(0, demandasRestantes().get(numDestino())+1).boxed().collect(Collectors.toList());
					}
				}else{
					if(numProducto() == DatosProductosDistribucion.getProductos().get(numeroProductos-1).id()){
						alternativas= List2.of(unidadesRestantes().get(numProducto())+1);
					}else{
						alternativas= IntStream.range(0, unidadesRestantes().get(numProducto())+1).boxed().collect(Collectors.toList());		
					}
				}
			}
		}
		return alternativas;
	}

	public ProductosDistribucionProblem neighbor(Integer a) {
		List<Integer> unidadesRestantesNuevo= List2.copy(unidadesRestantes());
		List<Integer> demandasRestantesNuevo= List2.copy(demandasRestantes());
		
		if(a>0) {
			unidadesRestantesNuevo.set(numProducto(), unidadesRestantesNuevo.get(numProducto())- a);
			demandasRestantesNuevo.set(numDestino(), demandasRestantesNuevo.get(numDestino())- a);
		}
		return ProductosDistribucionProblem.of(indice()+1, unidadesRestantesNuevo, demandasRestantesNuevo);
	}
	
	public Double heuristic0() {
		return 0.;
	}
	
	public Double heuristic1() {
		Double res;
		if(this.demandasRestantes().stream().allMatch(d-> d<=0)) {
			res=0.;
		}else {
			res= IntStream.range(this.indice(), ProductosDistribucionVertex.numeroProductos*ProductosDistribucionVertex.numeroDestinos)
						.filter(ind-> this.demandasRestantes().get(ind%ProductosDistribucionVertex.numeroDestinos)>0)
						.mapToDouble(ind-> DatosProductosDistribucion.getCosteAlmc(ind/ProductosDistribucionVertex.numeroDestinos, ind%ProductosDistribucionVertex.numeroDestinos))
						.min()
						.orElse(Double.MAX_VALUE);
		}
		return res;
	}

}
