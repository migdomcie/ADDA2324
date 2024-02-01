package tests;

import java.util.List;
import java.util.function.Function;

import org.apache.commons.math3.fitting.WeightedObservedPoint;

import ejemplos.Ejemplo1;
import ejercicios.Ejercicio1;
import us.lsi.common.Pair;
import us.lsi.curvefitting.DataFile;
import us.lsi.curvefitting.Fit;
import us.lsi.curvefitting.GenData;
import us.lsi.curvefitting.PowerLog;
import us.lsi.graphics.MatPlotLib;
import us.lsi.iterativorecursivos.IterativosyRecursivosSimples;

public class Ejercicio1Test {

	public static void main(String[] args) {
		//genDataFRecD();
		//genDataFItD();
		
		//genDataFRecBI();
		//genDataFItBI();

		showFRecD();
		showFItD();
		
		showFRecBI();
		showFItBI();
		
		showCombined();
	
	}
	
	private static Integer nMin = 100; // n mínimo para el cálculo de la función
	private static Integer nMax = 2000; // n máximo para el cálculo de la función
	private static Integer nIncr = 50; // incremento en los valores de n del cálculo de la función
	private static Integer nIter = 100; // número de iteraciones para cada medición de tiempo
	private static Integer nIterWarmup = 50000; // número de iteraciones para warmup
	private static Integer nIterWarmupBI = 2000; // número de iteraciones para warmup
	
	public static void genDataFRecD() {
		String file = "ficheros_generados/FRecD.txt";
		Function<Integer,Long> f1 = GenData.time(t -> Ejercicio1.fRecDouble(t));
//		Integer tMin,Integer tMax,Integer tInc,Integer numIter,Integer numIterWarmup
		GenData.tiemposEjecucionAritmetica(f1,file,nMin,nMax,nIncr,nIter,nIterWarmup);
	}
	
	public static void genDataFItD() {
		String file = "ficheros_generados/FItD.txt";
		Function<Integer,Long> f1 = GenData.time(t -> Ejercicio1.fItDouble(t));
//		Integer tMin,Integer tMax,Integer tInc,Integer numIter,Integer numIterWarmup
		GenData.tiemposEjecucionAritmetica(f1,file,nMin,nMax,nIncr,nIter,nIterWarmup);
	}
	
	public static void genDataFRecBI() {
		String file = "ficheros_generados/FRecBI.txt";
		Function<Integer,Long> f1 = GenData.time(t -> Ejercicio1.fRecBInteger(t));
//		Integer tMin,Integer tMax,Integer tInc,Integer numIter,Integer numIterWarmup
		GenData.tiemposEjecucionAritmetica(f1,file,nMin,nMax,nIncr,nIter,nIterWarmupBI);
	}
	
	public static void genDataFItBI() {
		String file = "ficheros_generados/FItBI.txt";
		Function<Integer,Long> f1 = GenData.time(t -> Ejercicio1.fItBInteger(t));
//		Integer tMin,Integer tMax,Integer tInc,Integer numIter,Integer numIterWarmup
		GenData.tiemposEjecucionAritmetica(f1,file,nMin,nMax,nIncr,nIter,nIterWarmupBI);
	}
	
	public static void showFRecD() {//habria que editar todos los show
		String file = "ficheros_generados/FRecD.txt";
		List<WeightedObservedPoint> data = DataFile.points(file);
		Fit pl = PowerLog.of(List.of(Pair.of(2, 0.),Pair.of(3, 0.)));
		pl.fit(data);
		System.out.println(pl.getExpression());
		System.out.println(pl.getEvaluation().getRMS());
		MatPlotLib.show(file, pl.getFunction(), "FRecD: " + pl.getExpression());
	}
	
	public static void showFItD() {
		String file = "ficheros_generados/FItD.txt";
		List<WeightedObservedPoint> data = DataFile.points(file);
		Fit pl = PowerLog.of(List.of(Pair.of(2, 0.),Pair.of(3, 0.)));
		pl.fit(data);
		System.out.println(pl.getExpression());
		System.out.println(pl.getEvaluation().getRMS());
		MatPlotLib.show(file, pl.getFunction(), "FItD: " + pl.getExpression());
	}
	
	public static void showFRecBI() {
		String file = "ficheros_generados/FRecBI.txt";
		List<WeightedObservedPoint> data = DataFile.points(file);
		Fit pl = PowerLog.of(List.of(Pair.of(2, 0.),Pair.of(3, 0.)));
		pl.fit(data);
		System.out.println(pl.getExpression());
		System.out.println(pl.getEvaluation().getRMS());
		MatPlotLib.show(file, pl.getFunction(), "FRecBI: " + pl.getExpression());
	}
	
	public static void showFItBI() {
		String file = "ficheros_generados/FItBI.txt";
		List<WeightedObservedPoint> data = DataFile.points(file);
		Fit pl = PowerLog.of(List.of(Pair.of(2, 0.),Pair.of(3, 0.)));
		pl.fit(data);
		System.out.println(pl.getExpression());
		System.out.println(pl.getEvaluation().getRMS());
		MatPlotLib.show(file, pl.getFunction(), "FItBI: " + pl.getExpression());
	}
	
	
	public static void showCombined() {
		MatPlotLib.showCombined("Tiempos",
				List.of("ficheros_generados/FRecD.txt","ficheros_generados/FItD.txt", "ficheros_generados/FRecBI.txt", "ficheros_generados/FItBI.txt"), 
				List.of("Recursivo-Double","Iterativo-Double",
						"Recursivo-BigInteger","Iterativo-BigInteger"));
	}
}
