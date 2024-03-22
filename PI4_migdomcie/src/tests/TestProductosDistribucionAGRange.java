package tests;

import java.util.List;
import java.util.Locale;

import ejercicios.InRangeProductosDistribucionAG;
import ejercicios.SolucionProductosDistribucion;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;

public class TestProductosDistribucionAGRange {

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));

		AlgoritmoAG.ELITISM_RATE = 0.3; //0.3
		AlgoritmoAG.CROSSOVER_RATE = 0.85; //0.8
		AlgoritmoAG.MUTATION_RATE = 0.7; //0.7
		AlgoritmoAG.POPULATION_SIZE = 100; //50

		StoppingConditionFactory.NUM_GENERATIONS = 5000;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionFactory.StoppingConditionType.GenerationCount;

		for (int i = 1; i <= 3; i++) {
			InRangeProductosDistribucionAG p = new InRangeProductosDistribucionAG(
					String.format("ficheros/ejercicios/Ejercicio3DatosEntrada%d.txt", i));

			AlgoritmoAG<List<Integer>, SolucionProductosDistribucion> ap = AlgoritmoAG.of(p);
			ap.ejecuta();

			System.out.println("================================");
			System.out.println(ap.bestSolution());
			System.out.println("================================\n");
		}
		
	}

}


