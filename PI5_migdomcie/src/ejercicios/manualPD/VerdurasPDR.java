package ejercicios.manualPD;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import datos.DatosProductosNavidad;
import ejercicios.SolucionProductosNavidad;
import ejercicios.SolucionVerduras;
import us.lsi.common.List2;
import us.lsi.common.Map2;

public class VerdurasPDR {
	
	public static record Spverd(Integer a, Double weight) implements Comparable<Spverd> {
		public static Spverd of(Integer a, Double weight) {
			return new Spverd(a, weight);
		}

		@Override
		public int compareTo(Spverd spverd) {
			return this.weight().compareTo(spverd.weight());
		}
		
	}
	
	public static Map<VerdurasProblem, Spverd> map;
	public static Double mejorValor;
	
	public static SolucionVerduras search() {
		map= Map2.empty();
		mejorValor= Double.MIN_VALUE;
		
		pdr_search(VerdurasProblem.initial(), 0., map);
		return getSolucion();
	}
	
	private static Spverd pdr_search(VerdurasProblem prob, Double acumulado, Map<VerdurasProblem, Spverd> mem) {
		Spverd res= null;
		Boolean goal= VerdurasProblem.goal().test(prob);
		Boolean goalHasSolution= VerdurasProblem.goalHasSolution().test(prob);
		
		if(map.containsKey(prob)) {
			res= map.get(prob);
		}else if(goal && goalHasSolution) {
			res= Spverd.of(null, 0.);
			map.put(prob, res);
			if(acumulado < mejorValor) {
				mejorValor= acumulado;
			}
		}else {
			List<Spverd> soluciones= List2.empty();
			for(Integer a: prob.actions()) {
				Double cota= cota(acumulado, prob, a); //
				if(cota < mejorValor) {
					continue;
				}
				VerdurasProblem vecino= prob.neighbor(a);
				Double peso= a!=-1?1.:0.;
				Spverd s= pdr_search(vecino, acumulado + peso, map);
				if(s!=null) {
					Spverd spprodAct= Spverd.of(a, s.weight()+ peso);
					soluciones.add(spprodAct);
				}
			
			}
			res= soluciones.stream().max(Comparator.naturalOrder()).orElse(null);
			if(res!=null) {
				map.put(prob, res);
			}
		}
		return res;
	}
	
	private static Double cota(Double acumulado, VerdurasProblem source, Integer action) {
		Double peso= action!=-1?1.:0.; 
		return acumulado + peso + source.neighbor(action).heuristic1();
	}
	
	public static SolucionVerduras getSolucion() {
		List<Integer> acciones= List2.empty();
		VerdurasProblem prob= VerdurasProblem.initial();
		Spverd spverd= map.get(prob);
		while(spverd !=null && spverd.a() != null) {
			VerdurasProblem old= prob;
			acciones.add(spverd.a());
			prob= old.neighbor(spverd.a());
			spverd= map.get(prob);
		}
		return SolucionVerduras.of(acciones);
	}

}
