package datos;

public record Tarea(String tarea) implements Comparable<Tarea> {
	
	public static Tarea of(String tarea) {
		return new Tarea(tarea);
	}
	
	public static Tarea ofFormat(String [] str) {
		String tarea= str[0];
		return new Tarea(tarea);
	}

	@Override
	public String toString() {
		return this.tarea;
	}
	
	@Override
	public int compareTo(Tarea o) {
		return Integer.valueOf(this.tarea().replace("Tarea", ""))
				.compareTo(Integer.valueOf(o.tarea().replace("Tarea", "")));
	}
}
