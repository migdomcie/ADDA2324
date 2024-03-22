package ejercicios;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

import datos.DatosProductosNavidad;
import datos.DatosProductosNavidad.Producto;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class Ejercicio2PLE {
	
	public static List<Integer> listaCategorias;
	public static List<Producto> listaProductos;
	public static Integer presupuesto;
	
	public static Integer getNumProductos() {
		return listaProductos.size();
	}
	public static Integer getNumCategorias() {
		return listaCategorias.size();
	}
	
	public static Integer getPresupuesto() {
		return presupuesto;
	}
	
	public static Integer getPrecio(Integer i) {
		return listaProductos.get(i).precio();
	}
	
	public static Integer getValoracion(Integer i) {
		return listaProductos.get(i).valoracion();
	}
	
	public static Integer getCategoria(Integer i) {
		return listaProductos.get(i).categoria();
	}
	
	public static Integer cubreCategoria(Integer i, Integer j) {
		return listaProductos.get(i).categoria().equals(listaCategorias.get(j))?1:0;
	}
	
	public static List<Producto> getProductos() {
		return listaProductos;
	}
	
	public static List<Integer> getCategorias(){
		return listaCategorias;
	}	
	public static void ejercicio2_model() throws IOException {
		for(int i=1; i<=3; i++) {
			String ruta_lp= String.format("gurobi_models/Ejercicio2-%d.lp", i);
			DatosProductosNavidad.iniDatos(String.format("ficheros/ejercicios/Ejercicio2DatosEntrada%d.txt", i));

			listaProductos = DatosProductosNavidad.getProductos();
			listaCategorias = DatosProductosNavidad.getCategorias();
			presupuesto= DatosProductosNavidad.getPresupuesto();
			
			//si cambia el fichero de datos de entrada, cambiar tambien el nº del .lp para no sobreescribirlo
			AuxGrammar.generate(Ejercicio2PLE.class, "modelos/ejercicio2.lsi", ruta_lp);
			GurobiSolution solution = GurobiLp.gurobi(ruta_lp);
			Locale.setDefault(Locale.of("en", "US"));
			System.out.println(solution.toString((s,d)->d>0.) + "\n");
		}
		
	}
	
	public static void main(String[] args) throws IOException {	
		ejercicio2_model();
	}
}
