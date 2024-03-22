package ejercicios;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import datos.DatosVerduras;
import datos.DatosVerduras.Huerto;
import datos.DatosVerduras.VariedadVerdura;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;


public class Ejercicio1PLE {
	public static List<Huerto> listaHuertos;
	public static List<VariedadVerdura> listaVerduras;
	
	public static Integer getNumVariedadesVerduras() {
		return listaVerduras.size();
	}
	
	public static Integer getNumHuertos() {
		return listaHuertos.size();
	}
	
	public static Integer getNumMetrosDispHuerto(Integer j) {
		return listaHuertos.get(j).nMetrosDisp();
	}
	
	public static Integer getNumMetrosReqVerdura(Integer i) {
		return listaVerduras.get(i).nMetrosReq();
	}
	
	public static Integer getIncompatibilidad(Integer i, Integer k) {
		return listaVerduras.get(i).incomp().contains(k)?1:0;
	}
	
	public static void ejercicio1_model() throws IOException {
		for(int i=1; i<=3; i++) {
			String ruta_lp= String.format("gurobi_models/Ejercicio1-%d.lp", i);
			DatosVerduras.iniDatos(String.format("ficheros/ejercicios/Ejercicio1DatosEntrada%d.txt", i));

			listaVerduras = DatosVerduras.getVariedadesVerduras();
			listaHuertos = DatosVerduras.getHuertos();
			
			//si cambia el fichero de datos de entrada, cambiar tambien el nº del .lp para no sobreescribirlo
			AuxGrammar.generate(Ejercicio1PLE.class, "modelos/ejercicio1.lsi", ruta_lp);
			GurobiSolution solution = GurobiLp.gurobi(ruta_lp);
			Locale.setDefault(Locale.of("en", "US"));
			System.out.println(solution.toString((s,d)->d>0.) + "\n");
		}
		
	}
	
	public static void main(String[] args) throws IOException {	
		ejercicio1_model();
	}
}

