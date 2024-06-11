package ejercicios.manualBT;

import ejercicios.SolucionProductosDistribucion;

public class ProductosDistribucionBT {
	
	private static Double mejorValor;
	private static ProductosDistribucionState estado;
	private static SolucionProductosDistribucion solucion;
	
	public static void search() {
		solucion= null;
		mejorValor= Double.MAX_VALUE;
		estado= ProductosDistribucionState.initial();
		bt_search();
	}
	
	private static void bt_search() {
		if(ProductosDistribucionState.goalHasSolution().test(estado.actual)) {
			Double valorAct= estado.acumulado;
			if(valorAct<mejorValor) {
				mejorValor= valorAct;
				solucion= estado.getSolucion();
			}
		}else {
			for(Integer a: estado.acciones()) {
				if(estado.cota(a)< mejorValor) {
					estado.forward(a);
					bt_search();
					estado.back();
				}
			}
		}
	}
	
	public static SolucionProductosDistribucion getSolucion() {
		return solucion;
	}

}
