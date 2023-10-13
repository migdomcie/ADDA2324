package ejercicios;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import us.lsi.streams.Stream2;

public class Ejercicio3 {
	
	public static List<String> solucionIt(String file1, String file2){
		Iterator<String> it1= Stream2.file(file1).iterator();
		Iterator<String> it2= Stream2.file(file2).iterator();
		List<String> ac= new ArrayList<>();
		
		while(it1.hasNext() || it2.hasNext()) {
			if(it1.hasNext()) {
				ac.add(it1.next());
				if(it1.hasNext()) {
					ac.add(it1.next());
				}
			}
			if(it2.hasNext()) {
				ac.add(it2.next());
				if(it2.hasNext()) {
					ac.add(it2.next());
				}
			}
		}
		return ac;
	}
	
	public static List<String> solucionRecFinal(String file1, String file2){
		Iterator<String> it1= Stream2.file(file1).iterator();
		Iterator<String> it2= Stream2.file(file2).iterator();
		List<String> ac= new ArrayList<>();
		
		return solucionRecFinalAux(it1, it2, ac);
		
	}
	
	private static List<String> solucionRecFinalAux(Iterator<String> it1, Iterator<String> it2,
			List<String> ac){
		if(it1.hasNext() || it2.hasNext()) {
			if(it1.hasNext()) {
				ac.add(it1.next());
				if(it1.hasNext()) {
					ac.add(it1.next());
				}
			}
			if(it2.hasNext()) {
				ac.add(it2.next());
				if(it2.hasNext()) {
					ac.add(it2.next());
				}
			}
			ac= solucionRecFinalAux(it1, it2, ac);
		}
		return ac;
	}
	
	public static List<String> solucionFunc(String file1, String file2){
		Iterator<String> it1= Stream2.file(file1).iterator();
		Iterator<String> it2= Stream2.file(file2).iterator();
		
		Tupla t= Stream.iterate(Tupla.first(it1, it2),
				e-> e.next())
				.filter(e-> !(e.it1().hasNext() || e.it2().hasNext()))
				.findFirst()
				.get();
		
		return t.ac();
	}
	
	public static record Tupla(Iterator<String> it1, Iterator<String> it2,
			List<String> ac) {
		public static Tupla of(Iterator<String> it1, Iterator<String> it2,
				List<String> ac) {
			return new Tupla(it1, it2, ac);
		}
		
		public static Tupla first(Iterator<String> it1, Iterator<String> it2) {
			return of(it1, it2, new ArrayList<>());	
		}
		
		public Tupla next() {
			if(it1.hasNext()) {
				ac.add(it1.next());
				if(it1.hasNext()) {
					ac.add(it1.next());
				}
			}
			if(it2.hasNext()) {
				ac.add(it2.next());
				if(it2.hasNext()) {
					ac.add(it2.next());
				}
			}
			return of(it1, it2, ac);
		}
	}
}
