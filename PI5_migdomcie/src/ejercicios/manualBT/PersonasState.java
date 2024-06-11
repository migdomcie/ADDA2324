package ejercicios.manualBT;

import java.util.List;
import java.util.function.Predicate;

import ejercicios.SolucionPersonas;
import us.lsi.common.List2;

public class PersonasState {
	
	PersonasProblem actual;
	Double acumulado;
	List<Integer> acciones;
	List<PersonasProblem> anteriores;
	
	private PersonasState(PersonasProblem prob, Double ac, List<Integer> acc, List<PersonasProblem> ant){
		actual= prob;
		acumulado= ac;
		acciones= acc;
		anteriores= ant;
	}
	
	private static PersonasState of(PersonasProblem prob, Double ac, List<Integer> acc, List<PersonasProblem> ant) {
		return new PersonasState(prob, ac, acc, ant);
	}
	
	public static PersonasState initial() {
		PersonasProblem p0= PersonasProblem.initial();
		return of(p0, 0., List2.empty(), List2.empty());
	}
	
	public List<Integer> acciones(){
		return actual.actions();
	}
	
	public void forward(Integer a) {
		acumulado= acumulado + PersonasProblem.getPeso(actual, a);//habria que sumar solo cuando sea distinto de -1 la persona actual
		acciones.add(a);
		anteriores.add(actual);
		actual= actual.neighbor(a);
	}
	
	public void back() {
		int ult= acciones.size()-1;
		int aUlt= acciones.get(ult);
		var probAnt= anteriores.get(ult);
		
		acumulado= acumulado - PersonasProblem.getPeso(probAnt, aUlt);
		acciones.remove(ult);
		anteriores.remove(ult);
		actual= probAnt;
	}
	
	public Double cota(Integer a) {
		Double peso= PersonasProblem.getPeso(actual, a);
		return acumulado + peso + actual.neighbor(a).heuristic0();
	}
	
	public static Predicate<PersonasProblem> goal(){
		return prob-> prob.indice() == PersonasProblem.numeroPersonas;
	}
	
	public static Predicate<PersonasProblem> goalHasSolution(){
		return prob-> prob.restante().isEmpty();
	}
	
	public SolucionPersonas getSolucion() {
		return SolucionPersonas.ofPairs(actual.listaParejas());
	}
	
}
