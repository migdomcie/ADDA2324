package datos;

public record RelacionTarea(Integer id, String tarea1, String tarea2) {

	private static Integer num=0;
	
	public static RelacionTarea of(String tarea1, String tarea2) {
		Integer id= num;
		num= num + 1;
		
		return new RelacionTarea(id, tarea1, tarea2);
	}
	
	public static RelacionTarea ofFormat(String[] str) {
		Integer id= num;
		num= num + 1;
		String tarea1= str[0];
		String tarea2= str[1];
		
		return new RelacionTarea(id, tarea1, tarea2);
	}

	@Override
	public String toString() {
		return "Relacion-" + this.id;
	}
}
