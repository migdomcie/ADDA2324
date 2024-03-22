package ejercicios;

import java.util.List;
import java.util.stream.IntStream;

import datos.DatosPersonas;
import us.lsi.ag.AuxiliaryAg;
import us.lsi.ag.SeqNormalData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.common.List2;
import us.lsi.common.Pair;

public class PermutationPersonasAG implements SeqNormalData<SolucionPersonas> {

	public PermutationPersonasAG(String fichero) {
		DatosPersonas.iniDatos(fichero);
	}
	
	@Override
	public ChromosomeType type() {
		return ChromosomeType.Permutation;
	}
	
	@Override
	public Integer itemsNumber() {
		return DatosPersonas.getNumPersonas();
	}
	
	@Override
	public SolucionPersonas solucion(List<Integer> ls) {
		return SolucionPersonas.of(ls);
	}
	
	@Override
	public Double fitnessFunction(List<Integer> ls) {
		
		List<Integer> lista= List2.copy(ls);
		if(lista.size()%2==1) {
			lista.remove(lista.size()-1);
		}
		
		Double vo= vo(lista);
		Integer k= 1000;
		Double distR1= distR1(lista);
		Double distR2= distR2(lista);
		Double distR3= distR3(lista);
		
		return vo - k * (distR1 + distR2 + distR3);
	}
	
	public static Double vo(List<Integer> ls) {
		return  IntStream.range(0, ls.size()-1).boxed()
				.map(i->{ 
					Pair<Integer,Integer> p= Pair.of(-1, -1);
					if(i%2==0) {
						p= Pair.of(ls.get(i), ls.get(i+1));
					}
					return p; //crea una pareja entre personas ficticias (-1, -1) si el indice no es par, e.o.c (Persona par dada, Persona impar dada)
				
				})
				.filter(p-> p.first()!=-1) //filtra las parejas inexistentes
				.mapToDouble(p-> DatosPersonas.getAfinidad(p.first(), p.second())) //obtiene la afinidad de la persona par con la persona impar de la pareja
				.sum(); //y la suma
	}
	
	public static Double distR1(List<Integer> ls) {
		return IntStream.range(0, ls.size()-1).boxed()
				.map(i->{ 
					Pair<Integer,Integer> p= Pair.of(-1, -1);
					if(i%2==0) {
						p= Pair.of(ls.get(i), ls.get(i+1));
					}
					return p;
				
				})
				.filter(p-> p.first()!=-1)
				.mapToDouble(p-> auxDistR1(p)) //calcula la distancia por cada pareja a la R1
				.sum(); //y la suma
	}
	
	public static Double auxDistR1 (Pair<Integer, Integer> p) {
		return AuxiliaryAg.distanceToGeZero(
				(double) DatosPersonas.getIdiomas(p.first())
				.stream() 
				.filter(id->{
					Boolean b= DatosPersonas.getIdiomas(p.second())
						.stream()
						.anyMatch(id2-> id2.equals(id)); //por cada idioma de la persona impar comprueba si es igual el idioma de la persona par
					
					return b;
				})
				.count() - 1); //y los cuenta, debiendo ser como minimo 1
				
	}
	
	public static Double distR2(List<Integer> ls) {
		return IntStream.range(0, ls.size()-1).boxed()
				.map(i->{ 
					Pair<Integer,Integer> p= Pair.of(-1, -1);
					if(i%2==0) {
						p= Pair.of(ls.get(i), ls.get(i+1));
					}
					return p;
				
				})
				.filter(p-> p.first()!=-1)
				.mapToDouble(p-> auxDistR2(p)) //calcula la distancia por cada pareja a la R2
				.sum();
				
	}
	
	public static Double auxDistR2(Pair<Integer, Integer> p) {
		return AuxiliaryAg.distanceToLeZero(
				(double) Math.abs(DatosPersonas.getEdad(p.first()) - DatosPersonas.getEdad(p.second())) //calcula el valor absoluto de la diferencia de las edades de cada persona
				- 5 ); //y comprueba si es menor que 5
	}
	
	public static Double distR3(List<Integer> ls) {
		return AuxiliaryAg.distanceToEqZero(
				IntStream.range(0, ls.size()-1).boxed()
				.map(i->{ 
					Pair<Integer,Integer> p= Pair.of(-1, -1);
					if(i%2==0) {
						p= Pair.of(ls.get(i), ls.get(i+1));
					}
					return p;
				
				})
				.filter(p-> p.first()!=-1)
				.mapToDouble(p-> auxDistR3(p)) //calcula la distancia por cada pareja a la R3
				.sum()); //y la suma, debiendo ser 0
				
	}
	
	public static Double auxDistR3(Pair<Integer, Integer> p) {
		return DatosPersonas.getNacionalidad(p.first()).equals(DatosPersonas.getNacionalidad(p.second()))?1.:0.; //si la nacionalidad de la persona par es igual a la de la persona impar devuelve un 1, si no, un 0
	}
}
