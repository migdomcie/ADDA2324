package ejercicios;

import datos.DatosPersonas;
import us.lsi.graphs.virtual.SimpleEdgeAction;

public record PersonasEdge(PersonasVertex source, PersonasVertex target, Integer action, Double weight) implements SimpleEdgeAction<PersonasVertex, Integer> {
	
	public static Double getPeso(PersonasVertex source, Integer a) {
		return source.personaActual()==-1?0.:DatosPersonas.getAfinidad(source.personaActual(), a)*1.;
	}
	
	public static PersonasEdge of(PersonasVertex source, PersonasVertex target, Integer action) {
		return new PersonasEdge(source, target, action, getPeso(source, action));
	}
}
