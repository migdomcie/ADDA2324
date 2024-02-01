package ejercicios;

import java.util.ArrayList;
import java.util.List;

import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.BinaryTree.BEmpty;
import us.lsi.tiposrecursivos.BinaryTree.BLeaf;
import us.lsi.tiposrecursivos.BinaryTree.BTree;
import us.lsi.tiposrecursivos.Tree;
import us.lsi.tiposrecursivos.Tree.TEmpty;
import us.lsi.tiposrecursivos.Tree.TLeaf;
import us.lsi.tiposrecursivos.Tree.TNary;

public class Ejercicio3 {
	
	public static record Tupla(Boolean b, Integer h) {
		public static Tupla of(Boolean b, Integer h) {
			return new Tupla(b, h);
		}
	}
	
	//utilizada
	public static Boolean estaEquilibradoBTreeAbajoArriba(BinaryTree<Character> tree) {
		return estaEquilibradoBTreeAbajoArriba(tree, Tupla.of(true, 0)).b();
	}
	
	private static Tupla estaEquilibradoBTreeAbajoArriba(BinaryTree<Character> tree, Tupla tupla) {
		return switch(tree) {
			case BEmpty<Character> t-> Tupla.of(true, 0);
			case BLeaf<Character> t -> Tupla.of(true, 0);
			case BTree<Character> t ->{
				Tupla t1= estaEquilibradoBTreeAbajoArriba(t.left(), Tupla.of(tupla.b(), tupla.h()));
				Tupla t2= estaEquilibradoBTreeAbajoArriba(t.right(), Tupla.of(tupla.b(), tupla.h()));
				Boolean res= Math.abs(t1.h() - t2.h())<=1 
						&& t1.b() 
						&& t2.b();
				yield Tupla.of(res, 1 + Math.max(t1.h(), t2.h()));
			}
		};
	}
	
	public static Boolean estaEquilibradoBTree(BinaryTree<Character> tree) {
		return estaEquilibradoBTree(tree, Tupla.of(true, 0), new ArrayList<>()).b();
	}
	
	private static Tupla estaEquilibradoBTree(BinaryTree<Character> tree, Tupla tupla, List<Integer> alturas) {
		return switch(tree) {
			case BEmpty<Character> t-> Tupla.of(true, tupla.h()); //considerar 0 la altura
			case BLeaf<Character> t ->{
				Integer alturaMax= tupla.h(); 
				Integer alturaMin= tupla.h();
				alturas.add(alturaMax);
				for(Integer alt: alturas) {
					if(alt>alturaMax) {
						alturaMax=alt;
					}
					if(alt<alturaMin) {
						alturaMin=alt;
					}
				}
				yield Tupla.of(Math.abs(alturaMax- alturaMin)<=1, tupla.h());
			}
			case BTree<Character> t ->{
				Tupla t1= estaEquilibradoBTree(t.left(), Tupla.of(tupla.b(), tupla.h()+1), alturas);
				Tupla t2= estaEquilibradoBTree(t.right(), Tupla.of(tupla.b(), tupla.h()+1), alturas);
				Boolean res= Math.abs(t1.h() - t2.h())<=1 
						&& t1.b() 
						&& t2.b();
				yield Tupla.of(res, tupla.h()+1);
			}
		};
	}
	
	//---------------------------------------------------
	
	//utilizada
	public static Boolean estaEquilibradoTree(Tree<Character> tree) {
		return estaEquilibradoTree(tree, Tupla.of(true, 0), new ArrayList<>()).b();
	}
	
	private static Tupla estaEquilibradoTree(Tree<Character> tree, Tupla tupla, List<Integer> alturas) {
		return switch(tree) {
			case TEmpty<Character> t-> Tupla.of(true, tupla.h());
			case TLeaf<Character> t ->{
				Integer alturaMax= tupla.h();
				Integer alturaMin= tupla.h();
				alturas.add(alturaMax);
				for(Integer alt: alturas) {
					if(alt>alturaMax) {
						alturaMax=alt;
					}
					if(alt<alturaMin) {
						alturaMin=alt;
					}
				}
				yield Tupla.of(Math.abs(alturaMax- alturaMin)<=1, tupla.h());
			}
			case TNary<Character> t ->{
				Boolean res=true;
				Tupla t1=tupla;
				for(Tree<Character> tt: t.children()) {
					Tupla t2= estaEquilibradoTree(tt, Tupla.of(tupla.b(), tupla.h()+1), alturas);
					
					res= Math.abs(t1.h() - t2.h())<=1 
							&& t1.b() 
							&& t2.b();
					t1=t2;
				}
				yield Tupla.of(res, tupla.h()+1);
			}
		};
	}
	
	public static Boolean estaEquilibradoTreeAbajoArriba(Tree<Character> tree) {
		return estaEquilibradoTreeAbajoArriba(tree, Tupla.of(true, 0)).b();
	}
	
	private static Tupla estaEquilibradoTreeAbajoArriba(Tree<Character> tree, Tupla tupla) {
		return switch(tree) {
			case TEmpty<Character> t-> Tupla.of(true, 0); 
			case TLeaf<Character> t -> Tupla.of(true, 0); 
			case TNary<Character> t ->{
				Boolean res=true;
				Integer alturaMax=0;
				Integer alturaMin= tupla.h();
				Tupla t1= Tupla.of(true, 0);
				for(Tree<Character> tt: t.children()) {
					Tupla t2= estaEquilibradoTreeAbajoArriba(tt, Tupla.of(tupla.b(), tupla.h()));
					
					alturaMax= t1.h()>t2.h()?t1.h():t2.h();
					alturaMin= t1.h()<t2.h()?t1.h():t2.h();
					
					res= Math.abs(alturaMax-alturaMin)<=1 
							&& t1.b() 
							&& t2.b();
					
					t1=t2;
				}
				yield Tupla.of(res, 1 + alturaMax);
			}
		};
	}
	
}
