package tests;

import java.util.List;
import java.util.Locale;

import ejercicios.PermutationPersonasAG;
import ejercicios.SolucionPersonas;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;

public class TestPersonasAGPermutation {

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));

		AlgoritmoAG.ELITISM_RATE = 0.30;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.7;
		AlgoritmoAG.POPULATION_SIZE = 50;

		StoppingConditionFactory.NUM_GENERATIONS = 5000;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionFactory.StoppingConditionType.GenerationCount;

		for (int i = 1; i <= 3; i++) {
			PermutationPersonasAG p = new PermutationPersonasAG(
					String.format("ficheros/ejercicios/Ejercicio4DatosEntrada%d.txt", i));

			AlgoritmoAG<List<Integer>, SolucionPersonas> ap = AlgoritmoAG.of(p);
			ap.ejecuta();

			System.out.println("================================");
			System.out.println(ap.bestSolution());
			System.out.println("================================\n");
		}

	}

}
