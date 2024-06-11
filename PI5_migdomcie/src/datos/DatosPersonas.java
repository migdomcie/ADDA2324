package datos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.common.Files2;
import us.lsi.common.List2;
import us.lsi.common.Pair;
import us.lsi.common.String2;

public class DatosPersonas {

	public static List<Persona> listaPersonas;
	
	public static record Persona(Integer id, Integer edad, List<String> idiomas, String nacionalidad, Map<Integer,Integer> afinidades) {
		public static Persona of(Integer id, Integer edad, List<String> idiomas, String nacionalidad, Map<Integer,Integer> afinidades) {
			return new Persona(id, edad, idiomas, nacionalidad, afinidades);
		}
		
		public static Persona create(String s) {
			String [] v= s.split("->");
			String [] v1= v[1].split(";");
			
			return of(Integer.valueOf(v[0].trim().replace("P", "")), Integer.valueOf(v1[0].trim().replace("edad=", "")), List2.parse(v1[1].trim().replace("idiomas=", "").replace("(", "").replace(")", ""), ",", String::valueOf), v1[2].trim().replace("nacionalidad=", ""), createAfinidades(v1[3].trim()));
		}
		
		public static Map<Integer,Integer> createAfinidades(String s) {

			Map<Integer,Integer> afinidades= new HashMap<>();
			List<Pair<Integer,Integer>> list = List2.parse(s.replace("afinidades=", ""), ",", str ->{
				String[] cad= str.replace("(", "").replace(")", "").split(":");
				return Pair.of(Integer.valueOf(cad[0].trim()), Integer.valueOf(cad[1].trim()));
			});
			
			list.stream().forEach(p-> afinidades.put(p.first(), p.second()));
			
			return afinidades;
		}

		@Override
		public String toString() {
			return "P"+id;
		}
	}
	
	public static void iniDatos(String fichero) {
		List<Persona> listaPersonas= List2.empty();
		
		for(String linea: Files2.linesFromFile(fichero)) {
			if(linea.contains("->")) {
				Persona p= Persona.create(linea);
				listaPersonas.add(p);
			}
		}
		DatosPersonas.listaPersonas= listaPersonas;
		
		toConsole();
	}
	
	public static void toConsole() {
		String2.toConsole("Personas con las que realizar el emparejamiento: %s\n", listaPersonas);	
	}
	
	public static Integer getNumPersonas() {
		return listaPersonas.size();
	}
	
	public static Integer getAfinidad(Integer i, Integer j) {
		return listaPersonas.get(i).afinidades().get(j);
	}
	
	public static List<String> getIdiomas(Integer i){
		return listaPersonas.get(i).idiomas();
	}
	
	public static Integer getEdad(Integer i) {
		return listaPersonas.get(i).edad();
	}
	
	public static String getNacionalidad(Integer i) {
		return listaPersonas.get(i).nacionalidad();
	}
	
	public static List<Persona> getPersonas(){
		return listaPersonas;
	}
	
	public static Persona getPersona(Integer i) {
		return listaPersonas.get(i);
	}
	
	public static Set<Integer> getPersonasInd(){
		return listaPersonas.stream().map(p-> Integer.valueOf(p.id())).collect(Collectors.toSet());
	}
	
	public static void main(String[] args) {
		for(int i=1; i<=3; i++) {
			System.out.println(String.format("Fichero: Ejercicio4DatosEntrada%d.txt", i));
			iniDatos(String.format("ficheros/ejercicios/Ejercicio4DatosEntrada%d.txt", i));
			System.out.println("############################\n");

		}
	}

}
