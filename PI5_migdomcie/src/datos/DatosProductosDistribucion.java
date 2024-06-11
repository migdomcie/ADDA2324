package datos;

import java.util.List;

import us.lsi.common.Files2;
import us.lsi.common.List2;
import us.lsi.common.String2;

public class DatosProductosDistribucion {

	public static List<Producto> listaProductos;
	public static List<Destino> listaDestinos;
	public static Integer numMaxUnidades;
	
	public static record Producto(Integer id, Integer unidades, List<Integer> costeAlmacenamiento) {
		public static Producto of(Integer id, Integer unidades, List<Integer> costeAlmacenamiento) {
			return new Producto(id, unidades, costeAlmacenamiento);
		}
		
		public static Producto create(String s) {
			String[] v= s.trim().split("->");
			String[] v1= v[1].trim().split(";");
			return of(Integer.valueOf(v[0].trim().replace("P", "")), Integer.valueOf(v1[0].trim().replace("uds=", "")), createCosteAlmacenamiento(v1[1].trim()));
		}
		
		public static List<Integer> createCosteAlmacenamiento(String s){
			
			List<Integer> list= List2.parse(s.replace("coste_almacenamiento=", ""), ",",
											str-> Integer.valueOf(str.substring(str.indexOf(":")+1,str.indexOf(")"))));
			return list;
		}
		
		@Override
		public String toString() {
			return String.valueOf(id);
		}
	}
	
	public static record Destino(Integer id, Integer demandaMinima) {
		public static Destino of(Integer id, Integer demandaMinima) {
			return new Destino(id, demandaMinima);
		}
		
		public static Destino create(String s) {
			String v[]= s.split(":");
			return of(Integer.valueOf(v[0].trim().replace("D", "")), Integer.valueOf(v[1].trim().replace("demandaminima=", "").replace(";", "")));
		}

		@Override
		public String toString() {
			return String.valueOf(id);
		}
		
	}
	
	public static void iniDatos(String fichero) {
		List<Producto> listaProductos= List2.empty();
		List<Destino> listaDestinos= List2.empty();
		Integer numMaxUnidades= 0;
		
		for(String linea: Files2.linesFromFile(fichero)) {
			if(linea.contains("D") && !linea.contains("//")) {
				Destino d= Destino.create(linea);
				listaDestinos.add(d);
			}else if(linea.contains("->") && !linea.contains("//")) {
				Producto p= Producto.create(linea);
				listaProductos.add(p);
				numMaxUnidades= numMaxUnidades + p.unidades();
			}
		}
		DatosProductosDistribucion.listaProductos= listaProductos;
		DatosProductosDistribucion.listaDestinos= listaDestinos;
		DatosProductosDistribucion.numMaxUnidades= numMaxUnidades;
		
		toConsole();
	}
	
	public static void toConsole() {
		String2.toConsole("Productos destinados para su distribucion: %s\nDestinos de distribucion: %s", listaProductos, listaDestinos);	
	}
	
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
	
	public static Producto getProducto(Integer i) {
		return listaProductos.get(i);
	}
	
	public static Destino getDestino(Integer j) {
		return listaDestinos.get(j);
	}
	
	public static Integer getNumMaxUnidades() {
		return numMaxUnidades;
	}
	
	public static List<Integer> getUnidades(){
		return listaProductos.stream().map(p-> p.unidades()).toList();
	}
	
	public static List<Integer> getDemandas(){
		return listaDestinos.stream().map(d-> d.demandaMinima()).toList();
	}
	
	public static void main(String[] args) {
		for(int i=1; i<=3; i++) {
			System.out.println(String.format("Fichero: Ejercicio3DatosEntrada%d.txt", i));
			iniDatos(String.format("ficheros/ejercicios/Ejercicio3DatosEntrada%d.txt", i));
			System.out.println("############################\n");
		}
	}

}
