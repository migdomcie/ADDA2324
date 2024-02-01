package datos;

public record Atraccion(String nombreAtraccion, Integer tEspera, Double popularidad, Integer tDuracion) {
	
	public static Atraccion of(String nombreAtraccion, Integer tEspera, Double popularidad, Integer tDuracion) {
		return new Atraccion(nombreAtraccion, tEspera, popularidad, tDuracion);
	}
	
	public static Atraccion ofFormat(String[] str) {
		String nombreAtraccion= str[0];
		Integer tEspera= Integer.valueOf(str[1]);
		Double popularidad= Double.valueOf(str[2]);
		Integer tDuracion= Integer.valueOf(str[3]);
		
		return new Atraccion(nombreAtraccion, tEspera, popularidad, tDuracion);
	}

	@Override
	public String toString() {
		return this.nombreAtraccion;
	}
}
