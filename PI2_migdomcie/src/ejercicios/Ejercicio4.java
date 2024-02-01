package ejercicios;

import java.util.ArrayList;
import java.util.List;

import us.lsi.common.List2;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.BinaryTree.BEmpty;
import us.lsi.tiposrecursivos.BinaryTree.BLeaf;
import us.lsi.tiposrecursivos.BinaryTree.BTree;
import us.lsi.tiposrecursivos.Tree;
import us.lsi.tiposrecursivos.Tree.TEmpty;
import us.lsi.tiposrecursivos.Tree.TLeaf;
import us.lsi.tiposrecursivos.Tree.TNary;

public class Ejercicio4 {
	
	public static List<List<Integer>> caminosDivisiblesEntreLongitudBTree(BinaryTree<Integer> tree){
		List<Integer> list= new ArrayList<>();
		List<List<Integer>> res= new ArrayList<>();
		return caminosDivisiblesEntreLongitudBTree(tree, list, res, 0);	
	}
	
	private static List<List<Integer>> caminosDivisiblesEntreLongitudBTree(BinaryTree<Integer> tree,
			List<Integer> list, List<List<Integer>> res, Integer ac){
		return switch(tree) {
			case BEmpty<Integer> t-> res;
			case BLeaf<Integer> t->{
				Integer etiqueta= t.label();
				ac= ac + etiqueta;
				list.add(etiqueta);
				if(ac%(list.size()-1)==0){
					res.add(list);
				}
				yield res;
			}
			case BTree<Integer> t->{
				list.add(t.label());
				ac= ac + t.label();
				List<Integer> list1= new ArrayList<>();
				List<Integer> list2= new ArrayList<>();
				list1.addAll(list);
				list2.addAll(list);
				caminosDivisiblesEntreLongitudBTree(t.left(), list1, res, ac);
				caminosDivisiblesEntreLongitudBTree(t.right(), list2, res, ac);
				yield res;
			}
		};
	}
	
	public static List<List<Integer>> caminosDivisiblesEntreLongitudTree(Tree<Integer> tree){
		List<Integer> list= new ArrayList<>();
		List<List<Integer>> res= new ArrayList<>();
		return caminosDivisiblesEntreLongitudTree(tree, list, res, 0);	
	}
	
	private static List<List<Integer>> caminosDivisiblesEntreLongitudTree(Tree<Integer> tree,
			List<Integer> list, List<List<Integer>> res, Integer ac){
		return switch(tree) {
			case TEmpty<Integer> t-> res;
			case TLeaf<Integer> t->{
				list.add(t.label());
				ac= ac + t.label();
				if(ac%(list.size()-1)==0){
					res.add(list);
				}
				yield res;
			}
			case TNary<Integer> t->{
				list.add(t.label());
				ac= ac + t.label();
				for(Tree<Integer> tt: t.children()) {
					List<Integer> list_= new ArrayList<>();
					list_.addAll(list);
					caminosDivisiblesEntreLongitudTree(tt, list_, res, ac);
				}
			yield res;
			}
		};
	}
	//	  SI FUERA NECESARIO TENER EN CUENTA LAS HOJAS QUE SON VACÍAS,
	//	  HABRÍA QUE CAMBIAR EL TIPO DE RETORNO DEL ALGORITMO Y TRATAR COMO
	//	  CEROS LAS HOJAS VACÍAS
	
	//SERÍA NECESARIO CAMBIAR EL TIPO DE ARBOL A ARBOL DE STRING!!!
	
	public static List<List<String>> caminosDivisiblesEntreLongitudBTreeStr(BinaryTree<String> tree){
		List<String> list= new ArrayList<>();
		List<List<String>> res= new ArrayList<>();
		return caminosDivisiblesEntreLongitudBTreeStr(tree, list, res, 0);	
	}
	
	private static List<List<String>> caminosDivisiblesEntreLongitudBTreeStr(BinaryTree<String> tree,
			List<String> list, List<List<String>> res, Integer ac){
		return switch(tree) {
			case BEmpty<String> t->res;
			case BLeaf<String> t->{
				String etiqueta= t.label();
				if(etiqueta.equals("empty")) {
					etiqueta="0";
					ac= ac + Integer.valueOf(etiqueta);
					etiqueta="_";
					list.add(etiqueta);
				}else {
					ac= ac+ Integer.valueOf(etiqueta);
					list.add(etiqueta);
				}
				
				if(ac%(list.size()-1)==0){
					res.add(list);
				}
				yield res;
			}
			case BTree<String> t->{
				list.add(t.label());
				ac= ac + Integer.valueOf(t.label());
				List<String> list1= new ArrayList<>();
				List<String> list2= new ArrayList<>();
				list1.addAll(list);
				list2.addAll(list);
				caminosDivisiblesEntreLongitudBTreeStr(t.left(), list1, res, ac);
				caminosDivisiblesEntreLongitudBTreeStr(t.right(), list2, res, ac);
				yield res;
			}
		};
	}
	
	
}
