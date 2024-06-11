package ejercicios.manualPD;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import datos.DatosVerduras;
import ejercicios.VerdurasVertex;
import us.lsi.common.List2;
import us.lsi.common.Set2;
import us.lsi.graphs.virtual.VirtualVertex;

public record VerdurasProblem(Integer indice, List<Set<Integer>> reparto, List<Integer> metrosDisponibles) {

	public static Integer numeroVerduras= DatosVerduras.getNumVariedadesVerduras();
	public static Integer numeroHuertos= DatosVerduras.getNumHuertos();

	
	public static VerdurasProblem of(Integer indice, List<Set<Integer>> reparto, List<Integer> metrosDisponibles) {
		return new VerdurasProblem(indice, reparto, metrosDisponibles);
	}
	
	@Override
	public String toString() {
		return String.format("(ind=%d, rep=%s, metrosDisp=%s)", indice(), reparto(), metrosDisponibles());
	}

	public static VerdurasProblem initial() {
		List<Set<Integer>> reparto0= List2.nCopies(new HashSet<>(), numeroHuertos);
		List<Integer> metrosDisponibles0= IntStream.range(0, numeroHuertos).boxed()
				.map(h-> DatosVerduras.getHuerto(h).nMetrosDisp())
				.collect(Collectors.toList());
		
		return of(0, reparto0, metrosDisponibles0);
	}
	
	public List<Integer> actions() {
		List<Integer> alternativas= List2.copy(IntStream.range(0, numeroHuertos).boxed()
				.collect(Collectors.toList()));		
		
		if(indice() < numeroVerduras) { 
			List<Integer> alternativasNoIncomp= List2.copy(alternativas);
			List<Integer> alternativasNoLlenas= List2.copy(alternativas);	

//			List<Integer> verdurasIncomp= DatosVerduras.getIncompatibilidades(indice());

			alternativasNoLlenas= alternativasNoLlenas.stream()
					.filter(h-> (metrosDisponibles().get(h)-DatosVerduras.getVariedadVerdura(indice()).nMetrosReq())>=0)
					.toList();
			
			alternativasNoIncomp= alternativasNoIncomp.stream()
					.filter(h-> esHuertoIncompatible(reparto().get(h)))
					.toList();

			alternativas= List2.intersection(alternativasNoLlenas, alternativasNoIncomp);
			alternativas.add(-1);
			
		}else {
			alternativas= List2.empty();
		}
		
		return alternativas;
	}

	public VerdurasProblem neighbor(Integer a) {
		List<Set<Integer>> repartoNuevo= List2.copy(reparto);
		List<Integer> metrosDisponiblesNuevo= List2.copy(metrosDisponibles);
		if(a!=-1) {
			Set<Integer> cjtoNuevo= Set2.copy(repartoNuevo.get(a));
			cjtoNuevo.add(indice());
			repartoNuevo.set(a, cjtoNuevo);
			metrosDisponiblesNuevo.set(a, metrosDisponiblesNuevo.get(a) - DatosVerduras.getNumMetrosReqVerdura(indice()));
		}
		
		return VerdurasProblem.of(indice()+1, repartoNuevo, metrosDisponiblesNuevo);
	}

	public Double heuristic0() {
		Double res;
		if(goal().test(this)) {
			res= 0.;
		}else {
			res= 10000.;
		}
		return res;
	}
		
	public Double heuristic1() {
		Double res=0.;
		for(int i= this.indice(); i< VerdurasVertex.numeroVerduras; i++) {
			Integer verdura= i;
			for(int j=0; j< VerdurasVertex.numeroHuertos; j++) {
				Set<Integer> repartoHuerto= this.reparto().get(j);
				Integer metrosDisponiblesNuevo= this.metrosDisponibles().get(j)-DatosVerduras.getNumMetrosReqVerdura(i);
				if(metrosDisponiblesNuevo>=0 && repartoHuerto.stream().allMatch(v-> DatosVerduras.getIncompatibilidad(verdura, v)==0)) {
//					System.out.println(verdura + ":" + j);
//					System.out.println(res);
					res= res+1;
				}
			}
		}
		
		res =IntStream.range(this.indice(), VerdurasVertex.numeroVerduras).boxed()
				 .mapToDouble(vInd-> calculaVerdurasPosibles(this, vInd))
				 .sum();
		
		return res;
	}
	
	private static Double calculaVerdurasPosibles(VerdurasProblem v1, Integer vInd) {
		return (double) IntStream.range(0, VerdurasVertex.numeroHuertos)
				.boxed()
				.filter(h->{
					Integer metrosDispAct= v1.metrosDisponibles().get(h) - DatosVerduras.getVariedadVerdura(vInd).nMetrosReq();
					return metrosDispAct >=0 && v1.reparto().get(h).stream().allMatch(v-> DatosVerduras.getIncompatibilidad(vInd, v)==0);
				}).count();
	}
	
	public static Predicate<VerdurasProblem> goal(){
		return prob-> prob.indice() == numeroVerduras;
	}
	
	public static Predicate<VerdurasProblem> goalHasSolution(){
		return prob-> prob.indice() == numeroVerduras && prob.metrosDisponibles().stream().allMatch(m-> m>=0); //&& reparto().stream().map(r-> r.));
	}
	
	public Boolean esHuertoIncompatible(Set<Integer> repartoHuerto) {
		return repartoHuerto.stream().allMatch(v-> DatosVerduras.getIncompatibilidad(indice(), v)==0);
	}
	
}
