package ejercicios;

import java.util.List;
import java.util.stream.IntStream;

import datos.DatosProductosNavidad;
import us.lsi.ag.AuxiliaryAg;
import us.lsi.ag.BinaryData;

public class BinProductosNavidadAG implements BinaryData<SolucionProductosNavidad> {

	public BinProductosNavidadAG(String fichero) {
		DatosProductosNavidad.iniDatos(fichero);
	}
	
	@Override
	public Integer size() {
		return DatosProductosNavidad.getNumProductos();
	}
	
	@Override
	public SolucionProductosNavidad solucion(List<Integer> ls) {
		return SolucionProductosNavidad.of(ls);
	}
	
	@Override
	public Double fitnessFunction(List<Integer> ls) {
		Double vo= vo(ls);
		Double k= 1000.;
		Double distR1= distR1(ls);
		Double distR2= distR2(ls);
		Double distR3= distR3(ls);

		return -vo - k * (distR1 + distR2 + distR3);
	}
	
	public static Double vo(List<Integer> ls) {
		return IntStream.range(0, ls.size()).boxed()
				.filter(i-> ls.get(i)>0)
				.mapToDouble(i-> DatosProductosNavidad.getPrecio(i))
				.sum();
	}
	
	public static Double distR1(List<Integer> ls) {
		return IntStream.range(0, DatosProductosNavidad.getCategorias().size())
				.mapToDouble(j -> auxDistR1(ls, j)) //obtiene por cada categoria la distancia que existe para R1 
				.sum(); //y la suma
	}
	
	public static Double auxDistR1(List<Integer> ls, Integer j) {
		return AuxiliaryAg.distanceToGeZero(
				IntStream.range(0, ls.size()).boxed()
				.filter(i-> ls.get(i)>0 && DatosProductosNavidad.cubreCategoria(i, j)==1?true:false) //filtra los productos imaginarios inexistentes y si el producto dado cubre la categoria pasada como parametro
				.count()-1.); //y cuenta dichos productos, debiendo ser mayor que 1 el recuento
	}
	
	public static Double distR2(List<Integer> ls) {
		return AuxiliaryAg.distanceToGeZero(
				IntStream.range(0, ls.size()).boxed()
				.filter(i-> ls.get(i)>0) //filtra los productos imaginarios inexistentes
				.mapToDouble(i-> DatosProductosNavidad.getValoracion(i) - 3.) //obtiene la diferencia de la valoracion con 3
				.sum()) //y la suma (asi se expresa para determinar la media)
				;
	}
	
	public static Double distR3(List<Integer> ls) {
		Double d = DatosProductosNavidad.getCategorias().stream()
				.mapToDouble(j -> auxDistR3(ls, j)) //obtiene la distancia por cada categoria a la R3
				.sum(); //y la suma
					
		return d;
	}
	
	public static Double auxDistR3(List<Integer> ls, Integer j) {
		return AuxiliaryAg.distanceToLeZero(
				IntStream.range(0, ls.size()).boxed()
				.filter(i-> ls.get(i)>0 && DatosProductosNavidad.cubreCategoria(i, j)==1?true:false) //filtra los productos imaginarios inexistentes y si el producto dado cubre la categoria pasada como parametro
				.mapToDouble(i-> DatosProductosNavidad.getPrecio(i)) //obtiene el precio del producto
				.sum()-DatosProductosNavidad.getPresupuesto()); //y lo suma, debiendo de ser la suma menor que el presupuesto
	}
	
}
