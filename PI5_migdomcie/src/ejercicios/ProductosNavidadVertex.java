package ejercicios;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import datos.DatosProductosNavidad;
import us.lsi.common.List2;
import us.lsi.common.Set2;
import us.lsi.graphs.virtual.VirtualVertex;

public record ProductosNavidadVertex(Integer indice, Set<Integer> categoriasPorCubrir, List<Integer> presupuestoRestante, Integer acumValoracion) implements VirtualVertex<ProductosNavidadVertex, ProductosNavidadEdge, Integer> {

	public static Integer numeroProductos= DatosProductosNavidad.getNumProductos();
	
	public static ProductosNavidadVertex of(Integer indice, Set<Integer> categoriasPorCubrir, List<Integer> presupuestoRestante, Integer acumValoracion) {
		return new ProductosNavidadVertex(indice, categoriasPorCubrir, presupuestoRestante, acumValoracion);
	}
	
	@Override
	public String toString() {
		return String.format("(ind=%d, catPorCub=%s, prepR=%s, acumV=%d)", indice(), categoriasPorCubrir(), presupuestoRestante(), acumValoracion());
	}
	
	public static ProductosNavidadVertex initial() {
		Set<Integer> categoriasPorCubrir0= new HashSet<>(DatosProductosNavidad.getCategorias());
		List<Integer> presupuestoRestante0= Collections.nCopies(DatosProductosNavidad.getNumCategorias(), DatosProductosNavidad.getPresupuesto());
		return ProductosNavidadVertex.of(0, categoriasPorCubrir0, presupuestoRestante0, 0);
	}
	
	@Override
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

	@Override
	public ProductosNavidadVertex neighbor(Integer a) {
		Set<Integer> categoriasPorCubrirNuevo= Set2.copy(categoriasPorCubrir());
		List<Integer> presupuestoRestanteNuevo= List2.copy(presupuestoRestante);
		Integer acumValoracionNuevo= acumValoracion();
		
		if(a==1) {
			categoriasPorCubrirNuevo= Set2.difference(categoriasPorCubrir(), Set2.of(DatosProductosNavidad.getCategoria(indice())));
			presupuestoRestanteNuevo.set(DatosProductosNavidad.getCategoria(indice()), presupuestoRestanteNuevo.get(DatosProductosNavidad.getCategoria(indice()))-DatosProductosNavidad.getPrecio(indice()));
			acumValoracionNuevo= acumValoracionNuevo + DatosProductosNavidad.getValoracion(indice()) - 3;
		}
		
		return ProductosNavidadVertex.of(indice()+1, categoriasPorCubrirNuevo, presupuestoRestanteNuevo, acumValoracionNuevo);
	}

	@Override
	public ProductosNavidadEdge edge(Integer a) {
		return ProductosNavidadEdge.of(this, this.neighbor(a), a);
	}
	
	public static Predicate<ProductosNavidadVertex> goal(){
		return v-> v.indice() == numeroProductos;
	}
	
	public static Predicate<ProductosNavidadVertex> goalHasSolution(){
		return v-> v.categoriasPorCubrir().isEmpty() && v.acumValoracion()>=0;
	}

}
