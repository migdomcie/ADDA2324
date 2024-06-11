package ejercicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jgrapht.Graph;

import datos.DatosPersonas;
import datos.DatosPersonas.Persona;
import us.lsi.common.List2;
import us.lsi.common.Pair;
import us.lsi.common.Set2;
import us.lsi.graphs.virtual.VirtualVertex;

public record PersonasVertex(Integer indice, Set<Integer> restante, Integer ultima, List<Pair<Integer, Integer>> listaParejas) implements VirtualVertex<PersonasVertex, PersonasEdge, Integer> {
	
	public static Integer numeroPersonas= DatosPersonas.getNumPersonas();
	
	public Pair<Integer, Integer> parejaActual(Integer a) {
		return indice()%2==0?Pair.of(personaActual(), a):Pair.of(personaActual(), personaActual());
	}
	
	public Integer personaActual() {
		return indice()%2==0?restante().stream().findFirst().get():-1;
	}
	
	public static PersonasVertex of(Integer indice, Set<Integer> restante, Integer ultima, List<Pair<Integer, Integer>> listaParejas) {
		return new PersonasVertex(indice, restante, ultima, listaParejas);
	}
	
	@Override
	public String toString() {
		return String.format("ind=%d, rest=%s, ult=%d, lsPar=%s)", indice(), restante(), ultima(), listaParejas());
	}
	
	public static PersonasVertex initial() {
		Set<Integer> restante0= DatosPersonas.getPersonasInd();
		List<Pair<Integer, Integer>> listaParejas0= List2.empty();
		return PersonasVertex.of(0, restante0, numeroPersonas, listaParejas0);
	}
	
	@Override
	public List<Integer> actions() {
		List<Integer> alternativas= new ArrayList<>(restante());
		
		if(indice()<numeroPersonas) {
			if(indice()%2==1) {
				alternativas= List2.of(-1);
			}else {
				Persona persona= DatosPersonas.getPersona(personaActual());
				alternativas= List2.difference(alternativas, List2.of(personaActual()));
				if(alternativas.size()>1) {
					//System.out.println("Me meto");
					alternativas= alternativas.stream()
											  .map(a-> DatosPersonas.getPersona(a))
											  .filter(p-> List2.intersection(p.idiomas(), persona.idiomas()).size()>0 && Math.abs(persona.edad()-p.edad())<=5 && !persona.nacionalidad().equals(p.nacionalidad()))
											  .map(p-> p.id())
											  .collect(Collectors.toList());
				}
				
//				System.out.println(alternativasIdioma);
//				System.out.println(alternativasEdad);
//				System.out.println(alternativasNacionalidad);
				
			}
			//System.out.println(alternativas);

				
		}
		return alternativas;
	}

	@Override
	public PersonasVertex neighbor(Integer a) {
		Set<Integer> restanteNuevo= Set2.copy(restante());
		Pair<Integer, Integer> pareja= parejaActual( a);
		List<Pair<Integer, Integer>> listaParejas= List2.copy(listaParejas());
		Integer ultimaNuevo= numeroPersonas;
		
		if(indice()%2==0) {
			restanteNuevo= Set2.difference(restante(), Set.of(pareja.first(), pareja.second()));
			listaParejas.add(pareja);
			if(indice()!=0) {
				ultimaNuevo= listaParejas.get(listaParejas.size()-1).first();
			}
			else {
				ultimaNuevo= listaParejas.get(indice()).first();
			}
		}
		
		return PersonasVertex.of(indice()+1, restanteNuevo, ultimaNuevo, listaParejas);
	}

	@Override
	public PersonasEdge edge(Integer a) {
		return PersonasEdge.of(this, this.neighbor(a), a);
	}
	
	public static Predicate<PersonasVertex> goal(){
		return v-> v.indice() == numeroPersonas;
	}
	
	public static Predicate<PersonasVertex> goalHasSolution(){
		return v-> v.restante().isEmpty();
	}
}
