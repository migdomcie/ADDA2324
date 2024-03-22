package ejemplos;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

import datos.DatosSubconjunto;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import datos.DatosSubconjunto.Subconjunto;
import us.lsi.solve.AuxGrammar;

public class Ejemplo2PLE {
	
	public static List<Integer> universo;
	public static List<Subconjunto> subconjuntos;
	
	public static Integer getNumSubconjuntos() {
		return subconjuntos.size();
	}
	public static Integer getNumElementos() {
		return universo.size();
	}
	public static Double getPeso(Integer i) {
		return subconjuntos.get(i).peso();
	}
	public static Integer contieneElemento(Integer i, Integer j) {
		return subconjuntos.get(i).elementos().contains(universo.get(j))? 1: 0;
	}
	
	public static void ejemplo2_model() throws IOException {
		DatosSubconjunto.iniDatos("ficheros/ejemplos/Ejemplo2DatosEntrada1.txt");
		
		subconjuntos = DatosSubconjunto.getSubconjuntos();
		universo = DatosSubconjunto.getUniverso();
		
		AuxGrammar.generate(Ejemplo2PLE.class,"modelos/ejemplo2.lsi","gurobi_models/Ejemplo2-1.lp");
		GurobiSolution solution = GurobiLp.gurobi("gurobi_models/Ejemplo2-1.lp");
		Locale.setDefault(Locale.of("en", "US"));
		System.out.println(solution.toString((s,d)->d>0.));
	}
	
	public static void main(String[] args) throws IOException {	
		ejemplo2_model();
	}

}
