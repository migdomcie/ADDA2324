package ejercicios.manualBT;

import ejercicios.SolucionPersonas;
import ejercicios.SolucionProductosDistribucion;

public class PersonasBT {
	
	private static Double mejorValor;
	private static PersonasState estado;
	private static SolucionPersonas solucion;
	
	public static void search() {
		solucion= null;
		mejorValor= Double.MIN_VALUE;
		estado= PersonasState.initial();
		bt_search();
	}
	
	private static void bt_search() {
		if(PersonasState.goalHasSolution().test(estado.actual)) {
			Double valorAct= estado.acumulado;
			if(valorAct>mejorValor) {
				mejorValor= valorAct;
				solucion= estado.getSolucion();
			}
		}else {
			for(Integer a: estado.acciones()) {
				if(estado.cota(a)> mejorValor) {
					estado.forward(a);
					bt_search();
					estado.back();
				}
			}
		}
	}
	
	public static SolucionPersonas getSolucion() {
		return solucion;
	}

}
