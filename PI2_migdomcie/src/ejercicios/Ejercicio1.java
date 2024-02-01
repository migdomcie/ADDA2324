package ejercicios;

import java.math.BigInteger;

public class Ejercicio1 {

	public static Double fRecDouble(Integer a) {
		Double res;
		if(a<6) {
			res= 10.;
		}else {
			res= Math.pow(a, 3) * fRecDouble(a-1);
		}
		return res;
	}
	
/*	public static Double fRecFinalDouble(Integer a) {
		return fRecFinalDouble(a, 1.);
	}
	
	public static Double fRecFinalDouble(Integer a, Double ac) {
		if(a<6) {
			ac= ac * 10.;
		}else {
			ac= fRecFinalDouble(a-1, ac * Math.pow(a, 3));
		}
		return ac;
	}
*/	
	public static Double fItDouble(Integer a) {
		Double res=1.;
		while (a>=6) {
			res = res * Math.pow(a, 3);
			a= a-1;
		}
		return res*10.;
	}
	
	
	public static BigInteger fRecBInteger(Integer a) {
		BigInteger res;
		if(a<6) {
			res= BigInteger.valueOf(10);
		}else {
			res= (BigInteger.valueOf(a).pow(3)).multiply(fRecBInteger(a-1));
		}
		return res;
	}
	
	public static BigInteger fItBInteger(Integer a) {
		BigInteger res= BigInteger.ONE;
		while (a>=6) {
			res = res.multiply((BigInteger.valueOf((long)Math.pow(a, 3)))) ;
			a= a-1;
		}
		return res.multiply(BigInteger.valueOf(10));
	}
}
