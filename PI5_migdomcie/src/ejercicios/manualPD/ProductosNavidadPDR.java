package ejercicios.manualPD;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import datos.DatosProductosNavidad;
import ejercicios.SolucionProductosNavidad;
import us.lsi.common.List2;
import us.lsi.common.Map2;

public class ProductosNavidadPDR {
	
	public static record Spprod(Integer a, Double weight) implements Comparable<Spprod> {
		public static Spprod of(Integer a, Double weight) {
			return new Spprod(a, weight);
		}

		@Override
		public int compareTo(Spprod spprod) {
			return this.weight().compareTo(spprod.weight());
		}
		
	}
	
	public static Map<ProductosNavidadProblem, Spprod> map;
	public static Double mejorValor;
	
	public static SolucionProductosNavidad search() {
		map= Map2.empty();
		mejorValor= Double.MAX_VALUE;
		
		pdr_search(ProductosNavidadProblem.initial(), 0., map);
		return getSolucion();
	}
	
	private static Spprod pdr_search(ProductosNavidadProblem prob, Double acumulado, Map<ProductosNavidadProblem, Spprod> mem) {
		Spprod res= null;
		Boolean goal= ProductosNavidadProblem.goal().test(prob);
		Boolean goalHasSolution= ProductosNavidadProblem.goalHasSolution().test(prob);
		
		if(map.containsKey(prob)) {
			res= map.get(prob);
		}else if(goal && goalHasSolution) {
			res= Spprod.of(null, 0.);
			map.put(prob, res);
			if(acumulado < mejorValor) {
				mejorValor= acumulado;
			}
		}else {
			List<Spprod> soluciones= List2.empty();
			for(Integer a: prob.actions()) {
				Double cota= cota(acumulado, prob, a); //
				if(cota > mejorValor) {
					continue;
				}
				ProductosNavidadProblem vecino= prob.neighbor(a);
				Double peso= a==1?DatosProductosNavidad.getPrecio(prob.indice()):0.;
				Spprod s= pdr_search(vecino, acumulado + peso, map);
				if(s!=null) {
					Spprod spprodAct= Spprod.of(a, s.weight()+ peso);
					soluciones.add(spprodAct);
				}
			
			}
			res= soluciones.stream().min(Comparator.naturalOrder()).orElse(null);
			if(res!=null) {
				map.put(prob, res);
			}
		}
		return res;
	}
	
	private static Double cota(Double acumulado, ProductosNavidadProblem source, Integer action) {
		Double peso= action==1?DatosProductosNavidad.getPrecio(source.indice()):0.;
		return acumulado + peso + source.neighbor(action).heuristic0();
	}
	
	public static SolucionProductosNavidad getSolucion() {
		List<Integer> acciones= List2.empty();
		ProductosNavidadProblem prob= ProductosNavidadProblem.initial();
		Spprod spprod= map.get(prob);
		while(spprod !=null && spprod.a() != null) {
			ProductosNavidadProblem old= prob;
			acciones.add(spprod.a());
			prob= old.neighbor(spprod.a());
			spprod= map.get(prob);
		}
		return SolucionProductosNavidad.of(acciones);
	}

}
