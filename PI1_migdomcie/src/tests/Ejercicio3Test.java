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
						"\n" + ls2, i));
			
			System.out.println("3) iterativo:\n" + Ejercicio3.solucionIt(ls1, ls2) + "\n");
		}
	}
}


