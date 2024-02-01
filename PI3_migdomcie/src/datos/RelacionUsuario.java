package datos;

public record RelacionUsuario(Integer id, String user1, String user2, Double indInter) {
	
	private static int num =0;
	
	public static RelacionUsuario of(String user1, String user2, Double indInter) {
		Integer id= num;
		num= num + 1;
		
		return new RelacionUsuario(id, user1, user2, indInter);
	}
	
	public static RelacionUsuario ofFormat(String [] str){
		Integer id= num;
		num= num + 1;
		String user1= str[0];
		String user2= str[1];
		Double indInter= Double.valueOf(str[2]);
		
		return new RelacionUsuario(id, user1, user2, indInter);
	}
	
	@Override
	public String toString() {
		return "(" + indInter + ")";
	}
}
