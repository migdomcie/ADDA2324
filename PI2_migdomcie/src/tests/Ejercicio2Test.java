package tests;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;

import org.apache.commons.math3.fitting.WeightedObservedPoint;

import ejercicios.Ejercicio2;
import us.lsi.common.Pair;
import us.lsi.curvefitting.DataFile;
import us.lsi.curvefitting.Fit;
import us.lsi.curvefitting.GenData;
import us.lsi.curvefitting.PowerLog;
import us.lsi.graphics.MatPlotLib;

public class Ejercicio2Test {

	public static void main(String[] args) {
		
//		System.out.println(list +"\n" + "----------"+ "\n");
//		Ejercicio2.mergeSort(list, umbral1);
//		System.out.println(list +"\n" + "----------"+ "\n");
//		genDataMergeSortUmbral1();
//		genDataMergeSortUmbral4();
//		genDataMergeSortUmbral16();
//		genDataMergeSortUmbral64();
//		genDataMergeSortUmbral256();
	
		showMergeSortUmbral1();
		showMergeSortUmbral4();
		showMergeSortUmbral16();
		showMergeSortUmbral64();
		showMergeSortUmbral256();
		showCombined();
	}
	
	public static List<Integer> list= new ArrayList<>();
	public static Integer umbral1= 1;
	public static Integer umbral4= 4;
	public static Integer umbral16= 16;
	public static Integer umbral64= 64;
	public static Integer umbral256= 256;

	private static Integer nMin = 100; // n mínimo para el cálculo de la función
	private static Integer nMax = 10000; // n máximo para el cálculo de la función
	private static Integer nIncr = 200; // incremento en los valores de n del cálculo de la función
	private static Integer nIter = 100; // número de iteraciones para cada medición de tiempo
	private static Integer nIterWarmup = 1000; // número de iteraciones para warmup
	
	private static Random rr = new Random(System.nanoTime());
	private static Integer e;
	
	public static void generaListaEnteros(Integer t, Boolean ord) {
		List <Integer> ls = new ArrayList<Integer>();
		for (int i=0;i<t;i++) {
			ls.add(rr.nextInt(t*1000));
		}
		if (ord)
			ls.sort(Comparator.naturalOrder());
		list = ls;
	}
	
	private static Consumer<Integer> pre_noord = t -> {
		e = rr.nextInt(t*1000);
		if (t != list.size())
			generaListaEnteros(t,false);
	};
	
	public static void genDataMergeSortUmbral1() {
		String file = "ficheros_generados/mergeSort1.txt";
		Function<Integer,Long> f1 = GenData.time(pre_noord, t -> Ejercicio2.mergeSort(list, umbral1));
//		Integer tMin,Integer tMax,Integer tInc,Integer numIter,Integer numIterWarmup
		GenData.tiemposEjecucionAritmetica(f1,file,nMin,nMax,nIncr,nIter,nIterWarmup);
	}
	
	public static void genDataMergeSortUmbral4() {
		String file = "ficheros_generados/mergeSort4.txt";
		Function<Integer,Long> f1 = GenData.time(pre_noord, t-> Ejercicio2.mergeSort(list, umbral4));
//		Integer tMin,Integer tMax,Integer tInc,Integer numIter,Integer numIterWarmup
		GenData.tiemposEjecucionAritmetica(f1,file,nMin,nMax,nIncr,nIter,nIterWarmup);
	}
	
	public static void genDataMergeSortUmbral16() {
		String file = "ficheros_generados/mergeSort16.txt";
		Function<Integer,Long> f1 = GenData.time(pre_noord, t -> Ejercicio2.mergeSort(list, umbral16));
//		Integer tMin,Integer tMax,Integer tInc,Integer numIter,Integer numIterWarmup
		GenData.tiemposEjecucionAritmetica(f1,file,nMin,nMax,nIncr,nIter,nIterWarmup);
	}
	
	public static void genDataMergeSortUmbral64() {
		String file = "ficheros_generados/mergeSort64.txt";
		Function<Integer,Long> f1 = GenData.time(pre_noord, t -> Ejercicio2.mergeSort(list, umbral64));
//		Integer tMin,Integer tMax,Integer tInc,Integer numIter,Integer numIterWarmup
		GenData.tiemposEjecucionAritmetica(f1,file,nMin,nMax,nIncr,nIter,nIterWarmup);
	}
	
	public static void genDataMergeSortUmbral256() {
		String file = "ficheros_generados/mergeSort256.txt";
		Function<Integer,Long> f1 = GenData.time(pre_noord, t -> Ejercicio2.mergeSort(list, umbral256));
//		Integer tMin,Integer tMax,Integer tInc,Integer numIter,Integer numIterWarmup
		GenData.tiemposEjecucionAritmetica(f1,file,nMin,nMax,nIncr,nIter,nIterWarmup);
	}
	
	public static void showMergeSortUmbral1() {//habria que editar todos los show
		String file = "ficheros_generados/mergeSort1.txt";
		List<WeightedObservedPoint> data = DataFile.points(file);
		Fit pl = PowerLog.of(List.of(Pair.of(1, 1.), Pair.of(3, 0.)));
		pl.fit(data);
		System.out.println(pl.getExpression());
		System.out.println(pl.getEvaluation().getRMS());
		MatPlotLib.show(file, pl.getFunction(), "Umbral 1: " + pl.getExpression());
	}
	
	public static void showMergeSortUmbral4() {
		String file = "ficheros_generados/mergeSort4.txt";
		List<WeightedObservedPoint> data = DataFile.points(file);
		Fit pl = PowerLog.of(List.of(Pair.of(1, 1.), Pair.of(3, 0.)));
		pl.fit(data);
		System.out.println(pl.getExpression());
		System.out.println(pl.getEvaluation().getRMS());
		MatPlotLib.show(file, pl.getFunction(), "Umbral 4: " + pl.getExpression());
	}
	
	public static void showMergeSortUmbral16() {
		String file = "ficheros_generados/mergeSort16.txt";
		List<WeightedObservedPoint> data = DataFile.points(file);
		Fit pl = PowerLog.of(List.of(Pair.of(1, 1.), Pair.of(3, 0.)));
		pl.fit(data);
		System.out.println(pl.getExpression());
		System.out.println(pl.getEvaluation().getRMS());
		MatPlotLib.show(file, pl.getFunction(), "Umbral 16: " + pl.getExpression());
	}
	
	public static void showMergeSortUmbral64() {
		String file = "ficheros_generados/mergeSort64.txt";
		List<WeightedObservedPoint> data = DataFile.points(file);
		Fit pl = PowerLog.of(List.of(Pair.of(1, 1.), Pair.of(3, 0.)));
		pl.fit(data);
		System.out.println(pl.getExpression());
		System.out.println(pl.getEvaluation().getRMS());
		MatPlotLib.show(file, pl.getFunction(), "Umbral 64: " + pl.getExpression());
	}
	
	public static void showMergeSortUmbral256() {
		String file = "ficheros_generados/mergeSort256.txt";
		List<WeightedObservedPoint> data = DataFile.points(file);
		Fit pl = PowerLog.of(List.of(Pair.of(1, 1.), Pair.of(3, 0.)));
		pl.fit(data);
		System.out.println(pl.getExpression());
		System.out.println(pl.getEvaluation().getRMS());
		MatPlotLib.show(file, pl.getFunction(), "Umbral 256: " + pl.getExpression());
	}
	
	public static void showCombined() {
		MatPlotLib.showCombined("Tiempos",
				List.of("ficheros_generados/mergeSort1.txt","ficheros_generados/mergeSort4.txt",
						"ficheros_generados/mergeSort16.txt","ficheros_generados/mergeSort64.txt",
						"ficheros_generados/mergeSort256.txt"), 
				List.of("Umbral 1","Umbral 4",
						"Umbral 16","Umbral 64",
						"Umbral 256"));
	}
}
