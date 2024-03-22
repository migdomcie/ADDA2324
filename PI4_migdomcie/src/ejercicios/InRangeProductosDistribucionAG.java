package ejercicios;

import java.util.List;
import java.util.stream.IntStream;

import datos.DatosProductosDistribucion;
import us.lsi.ag.AuxiliaryAg;
import us.lsi.ag.ValuesInRangeData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.common.Trio;

public class InRangeProductosDistribucionAG implements ValuesInRangeData<Integer, SolucionProductosDistribucion> {

	public InRangeProductosDistribucionAG(String fichero) {
		DatosProductosDistribucion.iniDatos(fichero);
	}
	@Override
	public ChromosomeType type() {
		return ChromosomeType.Range;
	}
	
	@Override
	public Integer size() {
		return DatosProductosDistribucion.getNumProductos() * DatosProductosDistribucion.getNumDestinos();
	}
	
	@Override
	public Integer max(Integer i) {
		return DatosProductosDistribucion.getNumMaxUnidades();
	}

	@Override
	public Integer min(Integer i) {
		return 0;
	}

	@Override
	public SolucionProductosDistribucion solucion(List<Integer> ls) {
		return SolucionProductosDistribucion.of(ls);
	}
	
	@Override
	public Double fitnessFunction(List<Integer> ls) {
		Double vo= vo(ls);
		Double k= 100.;
		Double distR1= distR1(ls);
		Double distR2= distR2(ls);
		
		return -vo -k * (distR1 + distR2);
	}
	
	public static Double vo(List<Integer> ls) {
		return IntStream.range(0, ls.size()).boxed()
				.map(num->{
					int i= num/DatosProductosDistribucion.getNumDestinos();
					int j= num%DatosProductosDistribucion.getNumDestinos();
					
					return Trio.of(i, j, ls.get(num)); //Trio de (Producto,Destino,NumUnidades)
					})
				.filter(t-> t.third()>0) //filtra los productos que no son destinados 
				.mapToDouble(t-> DatosProductosDistribucion.getCosteAlmc(t.first(), t.second()) * t.third()) //multiplica  el coste de almacenamiento del producto i en el destino j por las unidades destinadas
				.sum() //y los suma
				;
				
	}
	
	public static Double distR1(List<Integer> ls) {
		return IntStream.range(0, DatosProductosDistribucion.getNumDestinos()).boxed()
				.mapToDouble(j-> auxDistR1(ls, j)) // calcula la distancia para cada destino a la R1
				.sum(); //y la suma
	}
	
	public static Double auxDistR1(List<Integer> ls, Integer j) {
		return AuxiliaryAg.distanceToGeZero(
				IntStream.range(0, ls.size()).boxed()
				.map(num->{
					int i= num/DatosProductosDistribucion.getNumDestinos();
					int jj= num%DatosProductosDistribucion.getNumDestinos();
					
					return Trio.of(i, jj, ls.get(num));
					})
				.filter(t-> t.second().equals(j)) //filtra los productos cuyos destinos son iguales a los pasados como parametro
				.filter(t-> t.third()>0) //filtra los productos que no son destinados
				.mapToDouble(t-> t.third()) //obtiene las unidades de productos destinadas
				.sum() - DatosProductosDistribucion.getDemandaMin(j)); // y las suma, debiendo ser mayor a la demanda minima por destino
	}
	
	public static Double distR2(List<Integer> ls) {
		return IntStream.range(0, DatosProductosDistribucion.getNumProductos()).boxed()
				.mapToDouble(i-> auxDistR2(ls, i)) //calcula la distancia por cada producto a la R2
				.sum(); //y la suma
	}
	
	public static Double auxDistR2(List<Integer> ls, Integer i) {
		return AuxiliaryAg.distanceToLeZero(
				IntStream.range(0, ls.size()).boxed()
				.map(num->{
					int ii= num/DatosProductosDistribucion.getNumDestinos();
					int j= num%DatosProductosDistribucion.getNumDestinos();
					
					return Trio.of(ii, j, ls.get(num));
					})
				.filter(t-> t.first().equals(i)) //filtra los productos que sean iguales a los pasados como parametro
				.filter(t-> t.third()>0) //filtra los productos que no son destinados
				.mapToDouble(t-> t.third()) //obtiene las unidades de productos destinadas
				.sum() - DatosProductosDistribucion.getCantidadDisp(i)); //y las suma, debiendo ser inferior a la cantidad disponible por producto
				
	}
}
