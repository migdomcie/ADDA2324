package tests;

import java.util.List;
import java.util.stream.Collectors;

import ejercicios.Ejercicio3;
import ejercicios.Ejercicio4;
import us.lsi.streams.Stream2;

public class Ejercicio3Test {

	public static void main(String[] args) {
		for(int i=1; i<=3; i++) {
			String file1= String.format("ficheros/ejercicios/PI1Ej3DatosEntrada%dA.txt", i);
			String file2= String.format("ficheros/ejercicios/PI1Ej3DatosEntrada%dB.txt", i);
			
			List<String> ls1= Stream2.file(file1).collect(Collectors.toList());
			List<String> ls2= Stream2.file(file2).collect(Collectors.toList());
			
			System.out.println(String.format("Datos Entrada %d:\n"+ ls1 + 
						"\n" + ls2, i) + "\n");
			
			System.out.println(String.format("3.1) iterativo:\n" + Ejercicio3.solucionIt(file1, file2)));
			System.out.println(String.format("3.2) recursivo final:\n" + Ejercicio3.solucionRecFinal(file1, file2)));
			System.out.println(String.format("3.3) funcional:\n" + Ejercicio3.solucionFunc(file1, file2)) + "\n");
		}
	}
}


