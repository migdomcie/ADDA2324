package datos;

import java.util.List;

import us.lsi.common.Files2;
import us.lsi.common.List2;
import us.lsi.common.String2;

public class DatosVerduras {
	
	private static List<Huerto> listaHuertos;
	private static List<VariedadVerdura> listaVerduras;
	
	public static record Huerto(String nombre, Integer nMetrosDisp) {
		public static Huerto of(String nombre, Integer nMetrosDisp) {
			return new Huerto(nombre, nMetrosDisp);
		}
		
		public static Huerto create(String s) {
			String[] v = s.split(":");
			return of(v[0].trim(), Integer.valueOf(v[1].trim().replace("metrosdisponibles=", "").replace(";", "")));
		}

		@Override
		public String toString() {
			return "Huerto " + nombre.replace("H", "");
		}
		
	}
	
	public static record VariedadVerdura(String nombre, Integer nMetrosReq, List<Integer> incomp) {
		public static VariedadVerdura of(String nombre, Integer nMetrosReq, List<Integer> incomp) {
			return new VariedadVerdura(nombre, nMetrosReq, incomp);
		}
		
		public static VariedadVerdura create(String s) {
			String[] v = s.split("->");
			String[] v1= v[1].trim().split(";");
			return of(v[0].trim(), Integer.valueOf(v1[0].trim().replace("metrosrequeridos=", "")),
					List2.parse(v1[1].trim().replace("incomp=", "").replace("V", ""), ",", Integer::valueOf));
			
		}

		@Override
		public String toString() {
			return nombre;
		}
	}
	
	public static void iniDatos(String fichero) {
		List<VariedadVerdura> listaVerduras= List2.empty();
		List<Huerto> listaHuertos= List2.empty();
		
		for(String linea: Files2.linesFromFile(fichero)) {
			if(linea.contains("->")) {
				VariedadVerdura v= VariedadVerdura.create(linea);
				listaVerduras.add(v);
			}else if(linea.contains(":")) {
				Huerto h= Huerto.create(linea);
				listaHuertos.add(h);
			}
		}
		DatosVerduras.listaHuertos= listaHuertos;
		DatosVerduras.listaVerduras= listaVerduras;
		
		toConsole();
	}
	
	public static void toConsole() {
		String2.toConsole("Variedades de verduras a plantar: %s\nHuertos disponibles: %s\n", listaVerduras, listaHuertos);	
	}
	
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
	
	public static List<VariedadVerdura> getVariedadesVerduras() {
		return listaVerduras;
	}
	
	public static List<Huerto> getHuertos(){
		return listaHuertos;
	}
	
	public static Huerto getHuerto(Integer j) {
		return listaHuertos.get(j);
	}
	
	public static VariedadVerdura getVariedadVerdura(Integer i) {
		return listaVerduras.get(i);
	}
	
	public static List<Integer> getIncompatibilidades(Integer i){
		return listaVerduras.get(i).incomp();
	}
	
	public static void main(String[] args) {
		for(int i=1; i<=3; i++) {
			System.out.println(String.format("Fichero: Ejercicio1DatosEntrada%d.txt", i));
			iniDatos(String.format("ficheros/ejercicios/Ejercicio1DatosEntrada%d.txt", i));
			System.out.println("############################\n");

		}
	}
}
