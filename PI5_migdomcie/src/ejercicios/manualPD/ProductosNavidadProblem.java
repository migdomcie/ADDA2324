package ejercicios.manualPD;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import datos.DatosProductosNavidad;
import ejercicios.ProductosNavidadVertex;
import us.lsi.common.List2;
import us.lsi.common.Set2;

public record ProductosNavidadProblem(Integer indice, Set<Integer> categoriasPorCubrir, List<Integer> presupuestoRestante, Integer acumValoracion) {

	public static Integer numeroProductos= DatosProductosNavidad.getNumProductos();
	
	public static ProductosNavidadProblem of(Integer indice, Set<Integer> categoriasPorCubrir, List<Integer> presupuestoRestante, Integer acumValoracion) {
		return new ProductosNavidadProblem(indice, categoriasPorCubrir, presupuestoRestante, acumValoracion);
	}
	
	@Override
	public String toString() {
		return String.format("(ind=%d, catPorCub=%s, prepR=%s, acumV=%d)", indice(), categoriasPorCubrir(), presupuestoRestante(), acumValoracion());
	}
	
	public static ProductosNavidadProblem initial() {
		Set<Integer> categoriasPorCubrir0= new HashSet<>(DatosProductosNavidad.getCategorias());
		List<Integer> presupuestoRestante0= Collections.nCopies(DatosProductosNavidad.getNumCategorias(), DatosProductosNavidad.getPresupuesto());
		return ProductosNavidadProblem.of(0, categoriasPorCubrir0, presupuestoRestante0, 0);
	}
	
	public List<Integer> actions() {
		List<Integer> alternativas= List2.of(1,0);
		if(indice() < numeroProductos) {
			if(indice() == numeroProductos-1) {
				if ((presupuestoRestante().get(DatosProductosNavidad.getCategoria(indice()))-DatosProductosNavidad.getPrecio(indice()))>=0 && categoriasPorCubrir().contains(DatosProductosNavidad.getCategoria(indice()))) {
					alternativas = List2.of(1);
				}else {
					alternativas= List2.of(0);
				}
			}else if((presupuestoRestante().get(DatosProductosNavidad.getCategoria(indice()))-DatosProductosNavidad.getPrecio(indice()))<0) {
				alternativas= List2.of(0);
			}
		}else {
			alternativas= List2.empty();
		}
//		System.out.println(this);
//		System.out.println(alternativas);
		return alternativas;
		
	}

	public ProductosNavidadProblem neighbor(Integer a) {
		Set<Integer> categoriasPorCubrirNuevo= Set2.copy(categoriasPorCubrir());
		List<Integer> presupuestoRestanteNuevo= List2.copy(presupuestoRestante);
		Integer acumValoracionNuevo= acumValoracion();
		
		if(a==1) {
			categoriasPorCubrirNuevo= Set2.difference(categoriasPorCubrir(), Set2.of(DatosProductosNavidad.getCategoria(indice())));
			presupuestoRestanteNuevo.set(DatosProductosNavidad.getCategoria(indice()), presupuestoRestanteNuevo.get(DatosProductosNavidad.getCategoria(indice()))-DatosProductosNavidad.getPrecio(indice()));
			acumValoracionNuevo= acumValoracionNuevo + DatosProductosNavidad.getValoracion(indice()) - 3;
		}
		
		return ProductosNavidadProblem.of(indice()+1, categoriasPorCubrirNuevo, presupuestoRestanteNuevo, acumValoracionNuevo);
	}
	
	public Double heuristic0() {
		return 0.;
	}
	
	public Double heuristic1() {
		Double res;
		if(this.categoriasPorCubrir().isEmpty()) {
			res=0.;
		}else {
			res=IntStream.range(this.indice(), ProductosNavidadVertex.numeroProductos)
					.filter(p-> this.categoriasPorCubrir().contains(DatosProductosNavidad.getCategoria(p)))
					.mapToDouble(p-> DatosProductosNavidad.getPrecio(p))
					.sum();
		}
		return res;
	}
	
	public static Predicate<ProductosNavidadProblem> goal(){
		return prob-> prob.indice() == numeroProductos;
	}
	
	public static Predicate<ProductosNavidadProblem> goalHasSolution(){
		return prob-> prob.categoriasPorCubrir().isEmpty() && prob.acumValoracion()>=0;
	}

}
