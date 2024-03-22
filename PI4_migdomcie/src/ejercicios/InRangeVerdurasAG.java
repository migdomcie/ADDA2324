package ejercicios;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import datos.DatosVerduras;
import us.lsi.ag.AuxiliaryAg;
import us.lsi.ag.ValuesInRangeData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.common.Pair;

public class InRangeVerdurasAG implements ValuesInRangeData<Integer, SolucionVerduras> {

	public InRangeVerdurasAG(String fichero) {
		DatosVerduras.iniDatos(fichero);
	}
	
	@Override
	public ChromosomeType type() {
		return ChromosomeType.Range;
	}
	
	@Override
	public Integer size() {
		return DatosVerduras.getNumVariedadesVerduras();
	}
	
	@Override
	public Integer max(Integer i) {
		return DatosVerduras.getNumHuertos()+1;
	}

	@Override
	public Integer min(Integer i) {
		return 0;
	}
	
	@Override
	public SolucionVerduras solucion(List<Integer> ls) {
		return SolucionVerduras.of(ls);
	}

	@Override
	public Double fitnessFunction(List<Integer> ls) {
		Double vo= vo(ls);
		Integer k= 1000;
		Double distR1= distR1(ls);
		Double distR2= distR2(ls);
		
		return vo - k * (distR1 + distR2);
		}
	
	public static Double vo(List<Integer>ls) {
		return (double) IntStream.range(0, ls.size()).boxed()
				.filter(i-> ls.get(i)< DatosVerduras.getNumHuertos())
				.count();	
				
	}
	
	public static Double distR1(List<Integer> ls) {
		return IntStream.range(0, DatosVerduras.getNumHuertos()).boxed()
				.mapToDouble(j-> auxDistR1(ls, j)) //por cada huerto calcula la distancia a la R1
				.sum(); //y la suma
	}
	
	public static Double auxDistR1(List<Integer> ls, Integer j) {
		return AuxiliaryAg.distanceToLeZero(
				IntStream.range(0, ls.size()).boxed()
				.filter(i-> ls.get(i).equals(j)) //filtra para los huertos que sean iguales al pasado
				.mapToDouble(i-> DatosVerduras.getNumMetrosReqVerdura(i)) //obtiene los metros necesarios por cada verdura seleccionada
				.sum() - DatosVerduras.getNumMetrosDispHuerto(j)); //y los suma comprobando que es menor que el numero de metros disponibles por huerto
	}
	
	public static Double distR2(List<Integer> ls) {
		return IntStream.range(0, ls.size()).boxed()
				.map(i-> Pair.of(i, ls.get(i))) //obtiene una pareja de (Verdura,Huerto)
				.filter(p-> p.second()< DatosVerduras.getNumHuertos()) //filtra los huertos imaginarios inexistentes
				.mapToDouble(p-> auxDistR2(ls, p.second())) //calcula la distancia por cada huerto a la R2
				.sum(); //y la suma
	}
	
	public static Double auxDistR2(List<Integer> ls, Integer j) {
		//Dicho conjunto es el conjunto de todas las verduras incompatibles de cada verdura para cada huerto dado
		Set<Integer> setIncomp= IntStream.range(0, ls.size()).boxed()
				.map(i-> Pair.of(i, ls.get(i)))
				.filter(p-> p.second().equals(j))
				.flatMap(p-> DatosVerduras.getVariedadVerdura(p.first()).incomp().stream())
				.collect(Collectors.toSet());
		
		
		
		return AuxiliaryAg.distanceToEqZero(
				IntStream.range(0, ls.size()).boxed()
				.map(i-> Pair.of(i, ls.get(i))) //obtiene la pareja de (Verdura,Huerto)
				.filter(p-> p.second().equals(j)) //filtra para los huertos que sean iguales al pasado
				.mapToDouble(p-> setIncomp.contains(p.first())==true?1:0) //obtiene un 1 si el cjto de incompatibles contiene la verdura dada, 0 eoc
				.sum());
	}
	
}
