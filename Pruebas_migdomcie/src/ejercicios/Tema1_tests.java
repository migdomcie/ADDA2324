package ejercicios;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Tema1_tests {

	public static void main(String[] args) {
		List<String> ls= new ArrayList<>();
		ls.add("--");
		ls.add("__");
		ls.add("..");
		ls.add(",,");
		Predicate<String> pred= l-> l.equals("--");
		System.out.println(Tema1.filtraIguales(ls, pred).size()>0?Tema1.filtraIguales(ls, pred):"Nati");

	}

}
