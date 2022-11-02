package util;

public class Message {
	private String valor;
	private Tipo tipo;

	public enum Tipo {
		success, error
	}

	public Message(String valor, Tipo tipo) {
		super();
		this.valor = valor;
		this.tipo = tipo;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	
	public void setValorETipo(String valor, Tipo tipo) {
		this.valor = valor;
		this.tipo = tipo;
	}
	
}
