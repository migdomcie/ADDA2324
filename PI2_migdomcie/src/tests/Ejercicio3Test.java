package tests;

import java.util.List;

import ejercicios.Ejercicio3;
import us.lsi.common.Files2;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.Tree;

public class Ejercicio3Test {

	public static void main(String[] args) {
		System.out.println(
				".....................................................................................................");
		System.out.println("EJERCICIO 3 ");
		System.out.println(
				".....................................................................................................\n");

		testEjercicio3BTree();
		testEjercicio3Tree();
	}
	
	public static void testEjercicio3BTree() {

		String file = "ficheros/ejercicios/PI2Ej3DatosEntradaBinary.txt";

		List<BinaryTree<Character>> inputs = Files2.streamFromFile(file).map(linea -> {
			return BinaryTree.parse(linea, s -> s.charAt(0));
		}).toList();

		System.out.println("-----------------------");
		System.out.println("ARBOLES BINARIOS" + "");
		System.out.println("-----------------------");

		inputs.stream().forEach(tree -> {
			System.out.println("Arbol de entrada: " + tree
					+ "\nEquilibrado??: " 
					//+ Ejercicio3.estaEquilibradoBTree(tree) + "\n");
					+ Ejercicio3.estaEquilibradoBTreeAbajoArriba(tree) + "\n");
		});
	}
	
	public static void testEjercicio3Tree() {

		String file = "ficheros/ejercicios/PI2Ej3DatosEntradaNary.txt";

		List<Tree<Character>> inputs = Files2.streamFromFile(file).map(linea -> {
			return Tree.parse(linea, s -> s.charAt(0));
		}).toList();

		System.out.println("\n-----------------------");
		System.out.println("ARBOLES N-ARIOS" + "");
		System.out.println("-----------------------");
		
		inputs.stream().forEach(tree -> {
			System.out.println("Arbol de entrada: " + tree 
					+ "\nEquilibrado??: "
					+ Ejercicio3.estaEquilibradoTree(tree) + "\n");
					//+ Ejercicio3.estaEquilibradoTreeAbajoArriba(tree) + "\n");

		});
	}
}
