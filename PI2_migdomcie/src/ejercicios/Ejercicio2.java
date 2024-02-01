package ejercicios;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import us.lsi.common.List2;

public class Ejercicio2 {
	
	public static Integer umbral = 4;
	
	private static <E> void copia(List<E> lista, int i, int j, List<E> ls){
		for(int k = i; k<j; k++){
			lista.set(k, ls.get(k));
		}
	}
	
	private static <E> void mezcla(List<E> l1, int i1, int j1, List<E> l2, int i2, int j2,List<E> l3, int i3, int j3, Comparator<? super E> ord){
		int k1= i1;
		int k2= i2;
		int k3= i3;
		while(k3<j3){
			if(k1<j1 && k2<j2){
				if(ord.compare(l1.get(k1), l2.get(k2))<=0){
					l3.set(k3, l1.get(k1));
					k1++;
					k3++;
				}else{
					l3.set(k3, l2.get(k2));
					k2++;
					k3++;
				}
			}else if(k2==j2){
				l3.set(k3, l1.get(k1));
				k1++;
				k3++;
			}else{
				l3.set(k3, l2.get(k2));
				k2++;
				k3++;
			}
		}
	}
	
	private static <E> void mgSort(List<E> lista, int i, int j, Comparator<? super E> ord, List<E> ls, Integer umbral){
		if(j-i <= umbral){
			ordenaBase(lista, i, j, ord);
		}
		else {
			int k = (j+i)/2;
			mgSort(lista,i,k,ord,ls,umbral);
			mgSort(lista,k,j,ord,ls,umbral);
			mezcla(lista,i,k,lista,k,j,ls,i,j,ord);
			copia(lista,i,j,ls);
		}
	}
	
	public static <E> void ordenaBase(List<E> lista, Integer inf, Integer sup, Comparator<? super E> ord) {		
		for (int i = inf; i < sup; i++) {
		      for(int j = i+1; j < sup; j++){
		    	  if(ord.compare(lista.get(i),lista.get(j))>0){
		    		  List2.intercambia(lista, i, j);
		    	  }
		      }
		}
	}
	
	public static <E extends Comparable<? super E>> void mergeSort(List<E> lista, Integer umbral){
		Comparator<? super E> ord = Comparator.naturalOrder();
		List<E> ls = List2.ofCollection(lista);
		mgSort(lista,0,lista.size(),ord,ls,umbral);	
	}
	
	public static List<Integer> generaListaAleatoria(Integer n, Integer inf, Integer sup){
		List<Integer> res= new ArrayList<>();
		for(int i=0; i<n; i++) {
			Random rnd= new Random();
			res.add(Integer.valueOf(rnd.nextInt(inf, sup)));
		}
		return res;
	}
}
