package ejercicios.manualBT;

import java.util.List;
import java.util.function.Predicate;

import datos.DatosProductosDistribucion;
import ejercicios.SolucionProductosDistribucion;
import us.lsi.common.List2;

public class ProductosDistribucionState {
	
	ProductosDistribucionProblem actual;
	Double acumulado;
	List<Integer> acciones;
	List<ProductosDistribucionProblem> anteriores;
	
	private ProductosDistribucionState(ProductosDistribucionProblem prob, Double ac, List<Integer> acc, List<ProductosDistribucionProblem> ant){
		actual= prob;
		acumulado= ac;
		acciones= acc;
		anteriores= ant;
	}
	
	private static ProductosDistribucionState of(ProductosDistribucionProblem prob, Double ac, List<Integer> acc, List<ProductosDistribucionProblem> ant) {
		return new ProductosDistribucionState(prob, ac, acc, ant);
	}
	
	public static ProductosDistribucionState initial() {
		ProductosDistribucionProblem p0= ProductosDistribucionProblem.initial();
		return of(p0, 0., List2.empty(), List2.empty());
	}
	
	public List<Integer> acciones(){
		return actual.actions();
	}
	
	public void forward(Integer a) {
		acumulado= acumulado + a*DatosProductosDistribucion.getCosteAlmc(actual.numProducto(), actual.numDestino())*1.;
		acciones.add(a);
		anteriores.add(actual);
		actual= actual.neighbor(a);
	}
	
	public void back() {
		int ult= acciones.size()-1;
		int aUlt= acciones.get(ult);
		var probAnt= anteriores.get(ult);
		
		acumulado= acumulado - aUlt*DatosProductosDistribucion.getCosteAlmc(probAnt.numProducto(), probAnt.numDestino())*1.;
		acciones.remove(ult);
		anteriores.remove(ult);
		actual= probAnt;
	}
	
	public Double cota(Integer a) {
		Double peso= a*DatosProductosDistribucion.getCosteAlmc(actual.numProducto(), actual.numDestino())*1.;
		return acumulado + peso + actual.neighbor(a).heuristic1();
	}
	
	public static Predicate<ProductosDistribucionProblem> goal(){
		return prob-> prob.indice() == ProductosDistribucionProblem.numeroProductos*ProductosDistribucionProblem.numeroDestinos;
	}
	
	public static Predicate<ProductosDistribucionProblem> goalHasSolution(){
		return prob-> goal().test(prob) && prob.demandasRestantes().stream().allMatch(d-> d==0) && prob.unidadesRestantes().stream().allMatch(u-> u>=0);
	}
	
	public SolucionProductosDistribucion getSolucion() {
		return SolucionProductosDistribucion.of(acciones);
	}
	
}
