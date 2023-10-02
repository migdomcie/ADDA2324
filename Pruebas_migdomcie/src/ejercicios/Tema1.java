package ejercicios;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Tema1 {
	
	public static List<String> filtraIguales(List<String> lista, Predicate<String> pred){
		List<String> res= new ArrayList<>(); 
		for(String elemento: lista) {
			if(pred.test(elemento)) {
				res.add(elemento);
			}
		}
		return res;
	}

}
