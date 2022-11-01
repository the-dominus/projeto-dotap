package model;

public class Hora {
	private int id;
	private String valor;
	
	public Hora() {
		super();
	}
	
	public Hora(int id, String valor) {
		super();
		this.id = id;
		this.valor = valor;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public static Hora gerarHora(String rawHora) {
		String[] items = rawHora.split(";");

		int id = Integer.parseInt(items[0]);
		String valor = items[1];

		Hora hora = new Hora(id, valor);
		
		return hora;
	}
}
