package datos;

import java.util.HashSet;
import java.util.Set;

public record Usuario(String nombreUsuario,
		Double indiceAct, Set<String> cjtoAficiones) {
	
	public static Usuario of(String nombreUsuario,
			Double indiceAct, Set<String> cjtoAficiones) {
		return new Usuario(nombreUsuario, indiceAct, cjtoAficiones);
	}
	
	public static Usuario ofFormat(String [] str) {
		String nombreUsuario= str[0];
		Double indiceAct= Double.valueOf(str[1]);
		Set<String> cjtoAficiones= new HashSet<>();
		String [] str2= str[2].replace("[", "").replace("]", "").split(";");
		
		for(String e: str2) {
			cjtoAficiones.add(e);
		}
		
		return new Usuario(nombreUsuario, indiceAct, cjtoAficiones);
	}

	@Override
	public String toString() {
		return this.nombreUsuario;
	}
}
