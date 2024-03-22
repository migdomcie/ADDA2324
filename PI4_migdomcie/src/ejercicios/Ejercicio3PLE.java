package ejercicios;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import datos.DatosProductosDistribucion;
import datos.DatosProductosDistribucion.Destino;
import datos.DatosProductosDistribucion.Producto;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class Ejercicio3PLE {

	public static List<Destino> listaDestinos;
	public static List<Producto> listaProductos;

	public static Integer getNumProductos() {
		return listaProductos.size();
	}
	
	public static Integer getNumDestinos() {
		return listaDestinos.size();
	}
	
	public static Integer getCosteAlmc(Integer i, Integer j) {
		return listaProductos.get(i).costeAlmacenamiento().get(j);
	}
	
	public static Integer getDemandaMin(Integer j) {
		return listaDestinos.get(j).demandaMinima();
	}
	
	public static Integer getCantidadDisp(Integer i) {
		return listaProductos.get(i).unidades();
	}
	
	public static List<Producto> getProductos() {
		return listaProductos;
	}
	
	public static List<Destino> getDestinos(){
		return listaDestinos;
	}

	public static void ejercicio3_model() throws IOException {
		for (int i = 1; i <= 3; i++) {
			String ruta_lp = String.format("gurobi_models/Ejercicio3-%d.lp", i);
			DatosProductosDistribucion.iniDatos(String.format("ficheros/ejercicios/Ejercicio3DatosEntrada%d.txt", i));

			listaProductos = DatosProductosDistribucion.getProductos();
			listaDestinos = DatosProductosDistribucion.getDestinos();
			
			// si cambia el fichero de datos de entrada, cambiar tambien el nº del .lp para
			// no sobreescribirlo
			AuxGrammar.generate(Ejercicio3PLE.class, "modelos/ejercicio3.lsi", ruta_lp);
			GurobiSolution solution = GurobiLp.gurobi(ruta_lp);
			Locale.setDefault(Locale.of("en", "US"));
			System.out.println(solution.toString((s, d) -> d > 0.) + "\n");
		}

	}

	public static void main(String[] args) throws IOException {
		ejercicio3_model();
	}
	
}
