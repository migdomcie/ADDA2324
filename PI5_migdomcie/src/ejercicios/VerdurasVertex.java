package ejercicios;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import datos.DatosVerduras;
import us.lsi.common.List2;
import us.lsi.common.Set2;
import us.lsi.graphs.virtual.VirtualVertex;

public record VerdurasVertex(Integer indice, List<Set<Integer>> reparto, List<Integer> metrosDisponibles) implements VirtualVertex<VerdurasVertex, VerdurasEdge, Integer> {

	public static Integer numeroVerduras= DatosVerduras.getNumVariedadesVerduras();
	public static Integer numeroHuertos= DatosVerduras.getNumHuertos();

	
	public static VerdurasVertex of(Integer indice, List<Set<Integer>> reparto, List<Integer> metrosDisponibles) {
		return new VerdurasVertex(indice, reparto, metrosDisponibles);
	}
	
	@Override
	public String toString() {
		return String.format("(ind=%d, rep=%s, metrosDisp=%s)", indice(), reparto(), metrosDisponibles());
	}

	public static VerdurasVertex initial() {
		List<Set<Integer>> reparto0= List2.nCopies(new HashSet<>(), numeroHuertos);
		List<Integer> metrosDisponibles0= IntStream.range(0, numeroHuertos).boxed()
				.map(h-> DatosVerduras.getHuerto(h).nMetrosDisp())
				.collect(Collectors.toList());
		
		return of(0, reparto0, metrosDisponibles0);
	}
	
	@Override
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

	@Override
	public VerdurasVertex neighbor(Integer a) {
		List<Set<Integer>> repartoNuevo= List2.copy(reparto);
		List<Integer> metrosDisponiblesNuevo= List2.copy(metrosDisponibles);
		if(a!=-1) {
			Set<Integer> cjtoNuevo= Set2.copy(repartoNuevo.get(a));
			cjtoNuevo.add(indice());
			repartoNuevo.set(a, cjtoNuevo);
			metrosDisponiblesNuevo.set(a, metrosDisponiblesNuevo.get(a) - DatosVerduras.getNumMetrosReqVerdura(indice()));
		}
		
		return VerdurasVertex.of(indice()+1, repartoNuevo, metrosDisponiblesNuevo);
	}

	@Override
	public VerdurasEdge edge(Integer a) {
		return VerdurasEdge.of(this, this.neighbor(a), a);
	}
	
	public static Predicate<VerdurasVertex> goal(){
		return v-> v.indice() == numeroVerduras;
	}
	
	public static Predicate<VerdurasVertex> goalHasSolution(){
		return v-> v.indice() == numeroVerduras && v.metrosDisponibles().stream().allMatch(m-> m>=0); //&& reparto().stream().map(r-> r.));
	}
	
	public Boolean esHuertoIncompatible(Set<Integer> repartoHuerto) {
		return repartoHuerto.stream().allMatch(v-> DatosVerduras.getIncompatibilidad(indice(), v)==0);
	}
}
