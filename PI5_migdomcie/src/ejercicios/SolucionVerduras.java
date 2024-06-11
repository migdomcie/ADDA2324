package ejercicios;

import java.util.List;
import java.util.stream.Collectors;

import datos.DatosVerduras;
import datos.DatosVerduras.Huerto;
import datos.DatosVerduras.VariedadVerdura;
import us.lsi.common.List2;
import us.lsi.common.Pair;

public class SolucionVerduras {
	
	private Integer totalVerdurasPlantadas;
	private List<Pair<VariedadVerdura, Huerto>> solucion;
	
	public static SolucionVerduras of(List<Integer> ls) {
		return new SolucionVerduras(ls);
	}
	public static SolucionVerduras empty() {
		return new SolucionVerduras();
	}
	
	private SolucionVerduras(List<Integer> ls) {
		totalVerdurasPlantadas = 0;
		solucion = List2.empty();
		for(int i=0; i<ls.size(); i++) {
			if(ls.get(i)<DatosVerduras.getNumHuertos()) {
				if(ls.get(i)!=-1) {
					solucion.add(Pair.of(DatosVerduras.getVariedadVerdura(i), DatosVerduras.getHuerto(ls.get(i))));
				}
				totalVerdurasPlantadas = totalVerdurasPlantadas + 1;
			}
		}
	}
	
	private SolucionVerduras() {
		totalVerdurasPlantadas = 0;
		solucion = List2.empty();
	}

	@Override
	public String toString() {
		String str = solucion.stream().map(e -> String.format("%s: %s", e.first().nombre(), e.second().toString()))
				.collect(Collectors.joining("\n", "Reparto de verduras y huerto en el que es plantada cada una de ellas\n (si procede):\n",
						String.format("\nVariedades cultivadas: %d", solucion.size())));
		return str;
	}
	
}
