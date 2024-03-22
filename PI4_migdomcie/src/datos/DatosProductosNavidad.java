package datos;

import java.util.List;

import us.lsi.common.Files2;
import us.lsi.common.List2;
import us.lsi.common.String2;

public class DatosProductosNavidad {
	
	public static List<Integer> listaCategorias;
	public static List<Producto> listaProductos;
	public static Integer presupuesto;
	
	public static record Producto(Integer id, Integer precio, Integer categoria, Integer valoracion) {
		public static Producto of(Integer id, Integer precio, Integer categoria, Integer valoracion) {
			return new Producto(id, precio, categoria, valoracion);
		}
		
		public static Producto create(String s) {
			String[] v= s.trim().split(":");
			return of(Integer.valueOf(v[0].trim()), Integer.valueOf(v[1].trim()), Integer.valueOf(v[2].trim()), Integer.valueOf(v[3].trim()));
		}
		
		@Override
		public String toString() {
			return "Producto " + id;
		}
	}
	
	public static void iniDatos(String fichero) {
		List<Producto> listaProductos= List2.empty();
		List<Integer> listaCategorias= List2.empty();

		
		for(String linea: Files2.linesFromFile(fichero)) {
			if(linea.contains("Presupuesto")) {
				presupuesto= Integer.valueOf(linea.replace("Presupuesto =", "").trim());
			}else if(linea.contains(":") && !linea.contains("//")) {
				Producto p= Producto.create(linea);
				listaProductos.add(p);
				if(!listaCategorias.contains(p.categoria())) {
					listaCategorias.add(p.categoria);
				}
			}
		}
		DatosProductosNavidad.listaProductos= listaProductos;
		DatosProductosNavidad.listaCategorias= listaCategorias;
		
		toConsole();
	}
	
	public static void toConsole() {
		String2.toConsole("Productos disponibles para cesta de navidad: %s", listaProductos);	
	}
	
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
	
	public static Producto getProducto(Integer i) {
		return listaProductos.get(i);
	}
	
	public static void main(String[] args) {
		for(int i=1; i<=3; i++) {
			System.out.println(String.format("Fichero: Ejercicio2DatosEntrada%d.txt", i));
			iniDatos(String.format("ficheros/ejercicios/Ejercicio2DatosEntrada%d.txt", i));
			System.out.println("############################\n");

		}

	}

}
