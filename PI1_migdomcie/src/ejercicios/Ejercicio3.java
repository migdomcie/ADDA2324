package ejercicios;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Ejercicio3 {
	
	public static List<String> solucionIt(List<String> ls1, List<String> ls2){
		Iterator<String> it1= ls1.iterator();
		Iterator<String> it2= ls2.iterator();
		List<String> ac= new ArrayList<>();
		
		Integer i= 0;
		Integer j= 0;
		
		while(it1.hasNext() || it2.hasNext()) {
			if(it1.hasNext() && (i==0 || i%2==1) && i!=ls1.size()-1) {
				ac.add(it1.next());
				i= i+1;
			}else if(it2.hasNext() && (j==0 || j%2==1) && j!=ls2.size()-1) {
				ac.add(it2.next());
				j= j+1;
			}
			if(i!=0 && i%2==0 && i-j==0) {
				i= i+1;
			}else if(j!=0 && j%2==0){
				j= j+1;
			}
				
		}
		
		return ac;
			
	}
	
	/*tendriamos que iterar sobre las listas y hacer varios casos:
	->caso normal: en caso de que haya siguiente de la primera lista y
	el indice no sea par y
	el indice no sea igual al tamaño de la lista,
	se debe añadir los elementos de la primera;
	
	en el otro caso de que haya siguiente de la 2nd lista y
    el indice no sea par y
	el indice no sea igual al tamaño de la lista,
	se debe añadir los elementos de la 2nd;
	
	**-> debe haber una condicion que compruebe
	 que la lista ya se ha iterado por completo para iterar sólo sobre la otra
		-> debe haber una condicion en cada rama, que compruebe
		 que es el único elemento por añadir a la lista, y no dos** 
	*/
}
