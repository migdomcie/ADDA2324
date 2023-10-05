package ejemplos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import us.lsi.geometria.Cuadrante;
import us.lsi.geometria.Punto2D;

public class Ejemplo1 {
	public static Map<Cuadrante, Double> ejemplo1(List<Punto2D> ls) {
		return ls.stream().collect(
				Collectors.groupingBy(Punto2D::cuadrante,
						Collectors.reducing(0., p -> p.x(), (x, y) -> x + y)));
	}
	
	public static Map<Cuadrante, Double> ejemplo1It(List<Punto2D> ls){
		Integer i=0;
		Map<Cuadrante, Double> ac= new HashMap<>();
		while(i< ls.size()) {
			Punto2D p= ls.get(i);
			Cuadrante c= p.cuadrante();
			if(ac.containsKey(c)) {
				ac.put(c, ac.get(c)+p.x());
			}else {
				ac.put(c, p.x());
			}
			i= i+1;
		}
		return ac;
	}
	
	public static Map<Cuadrante, Double> ejemplo1RecFinal(List<Punto2D> ls){
		Map<Cuadrante, Double> ac= new HashMap<>();
		return ejemplo1RecFinalAux(ls, 0, ac);
	}
	
	public static Map<Cuadrante, Double> ejemplo1RecFinalAux(List<Punto2D> ls,
			Integer i, Map<Cuadrante, Double> ac){
		if(i < ls.size()) {
			Punto2D p= ls.get(i);
			Cuadrante c= p.cuadrante();
			if(ac.containsKey(c)) {
				ac.put(c, ac.get(c)+p.x());
			}else {
				ac.put(c, p.x());
			}
			ejemplo1RecFinalAux(ls, i+1, ac);
		}
		return ac;
	}
	
	
	
}
