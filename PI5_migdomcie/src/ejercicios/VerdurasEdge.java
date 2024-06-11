package ejercicios;


import us.lsi.graphs.virtual.SimpleEdgeAction;

public record VerdurasEdge(VerdurasVertex source, VerdurasVertex target, Integer action, Double weight) implements SimpleEdgeAction<VerdurasVertex, Integer> {
			
	public static VerdurasEdge of(VerdurasVertex source, VerdurasVertex target, Integer action) {
		return new VerdurasEdge(source, target, action, action!=-1?1.:0.);
	}
}
