package ejercicios;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import datos.DatosVerduras;

public class VerdurasHeuristic {
	
	public static Double heuristic0(VerdurasVertex v1, Predicate<VerdurasVertex> goal, VerdurasVertex v2) {
		Double res;
		if(goal.test(v1)) {
			res= 0.;
		}else {
			res= 10000.;
		}
		return res;
	}
		
	public static Double heuristic1(VerdurasVertex v1, Predicate<VerdurasVertex> goal, VerdurasVertex v2) {
		Double res=0.;
		for(int i= v1.indice(); i< VerdurasVertex.numeroVerduras; i++) {
			Integer verdura= i;
			for(int j=0; j< VerdurasVertex.numeroHuertos; j++) {
				Set<Integer> repartoHuerto= v1.reparto().get(j);
				Integer metrosDisponiblesNuevo= v1.metrosDisponibles().get(j)-DatosVerduras.getNumMetrosReqVerdura(i);
				if(metrosDisponiblesNuevo>=0 && repartoHuerto.stream().allMatch(v-> DatosVerduras.getIncompatibilidad(verdura, v)==0)) {
					res= res+1;
				}
			}
		}
		
		res =IntStream.range(v1.indice(), VerdurasVertex.numeroVerduras).boxed()
				 .mapToDouble(vInd-> calculaVerdurasPosibles(v1, vInd))
				 .sum();
		
		return res;
	}
	
	
	private static Double calculaVerdurasPosibles(VerdurasVertex v1, Integer vInd) {
		return (double) IntStream.range(0, VerdurasVertex.numeroHuertos)
				.boxed()
				.filter(h->{
					Integer metrosDispAct= v1.metrosDisponibles().get(h) - DatosVerduras.getVariedadVerdura(vInd).nMetrosReq();
					return metrosDispAct >=0 && v1.reparto().get(h).stream().allMatch(v-> DatosVerduras.getIncompatibilidad(vInd, v)==0);
				}).count();
	}
	

}
