package ejercicios;

import java.util.function.Predicate;

public class PersonasHeuristic {
	
	public static Double heuristic0(PersonasVertex v1, Predicate<PersonasVertex> goal, PersonasVertex v2) {
		Double res;
		if(goal.test(v1)) {
			res= 0.;
		}else {
			res= 10000.;
		}
		return res;
	}		
}
