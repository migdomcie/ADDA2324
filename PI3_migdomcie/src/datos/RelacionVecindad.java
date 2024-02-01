package datos;

public record RelacionVecindad(Integer id, String atraccion1, String atraccion2, Double distancia, Double tiempo) {
	
	private static Integer num=0;
	
	public static RelacionVecindad of(String atraccion1, String atraccion2, Double distancia, Double tiempo) {
		Integer id= num;
		num= num + 1;
		
		return new RelacionVecindad(id, atraccion1, atraccion2, distancia, tiempo);
	}
	
	public static RelacionVecindad ofFormat(String [] str) {
		Integer id= num;
		num= num + 1;
		String atraccion1= str[0];
		String atraccion2= str[1];
		Double distancia= Double.valueOf(str[2]);
		Double tRecorrido= Double.valueOf(str[3]);
		
		return new RelacionVecindad(id, atraccion1, atraccion2, distancia, tRecorrido);
	}
	
	public String toString() {
		return "(distancia: " + this.distancia + ", tiempo: " + this.tiempo + ")";
	}
}
