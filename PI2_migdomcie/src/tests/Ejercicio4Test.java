package tests;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import ejercicios.Ejercicio4;
import us.lsi.common.Files2;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.Tree;

public class Ejercicio4Test {

	public static void main(String[] args) {
		System.out.println(
				".....................................................................................................");
		System.out.println("EJERCICIO 4 ");
		System.out.println(
				".....................................................................................................\n");

		testEjercicio4BTree();
		testEjercicio4Tree();
	}
	
	public static void testEjercicio4BTree() {

		String file = "ficheros/ejercicios/PI2Ej4DatosEntradaBinary.txt";

		List<BinaryTree<Integer>> inputs = Files2.streamFromFile(file).map(linea -> {
			return BinaryTree.parse(linea, s -> Integer.valueOf(s));
		}).toList();

		System.out.println("-----------------------");
		System.out.println("ARBOLES BINARIOS" + "");
		System.out.println("-----------------------\n");
		
		for(BinaryTree<Integer> tree: inputs) {
			List<List<Integer>> res= Ejercicio4.caminosDivisiblesEntreLongitudBTree(tree);
			System.out.println("Arbol de entrada: " + tree);
			System.out.println("Caminos:" + "\n" + res + "\n");	
		}
	}		
	
	public static void testEjercicio4Tree() {

		String file = "ficheros/ejercicios/PI2Ej4DatosEntradaNary.txt";

		List<Tree<Integer>> inputs = Files2.streamFromFile(file).map(linea -> {
			return Tree.parse(linea, s -> Integer.valueOf(s));
		}).toList();

		System.out.println("\n-----------------------");
		System.out.println("ARBOLES N-ARIOS" + "");
		System.out.println("-----------------------\n");
		
		for(Tree<Integer> tree: inputs) {
			List<List<Integer>> res= Ejercicio4.caminosDivisiblesEntreLongitudTree(tree);
			System.out.println("Arbol de entrada: " + tree);
			System.out.println("Caminos:" + "\n" + res + "\n");
		}
	}
	
	public static BinaryTree<String> parseaArbolBinario(String s){
		String str= s.replace("_", "empty");
		return BinaryTree.parse(str, t-> String.valueOf(t));
	}
	
	public static Tree<String> parseaArbolNario(String s){
		String str= s.replace("_", "empty");
		return Tree.parse(str, t-> String.valueOf(t));
	}
}
