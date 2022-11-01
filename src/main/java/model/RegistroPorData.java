package model;

import java.util.Date;

import util.ConversaoDeTempo;

public class RegistroPorData {
	private Date data;
	private String[] horas;
	private String totalDeHoras;

	public RegistroPorData(Date data, String[] horas) {
		super();
		this.data = data;
		this.horas = horas;
		this.setTotalDeHoras();
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String[] gethoras() {
		return horas;
	}

	public void sethoras(String[] horas) {
		this.horas = horas;
	}

	public String getTotalDeHoras() {
		return totalDeHoras;
	}

	public void setTotalDeHoras() {

		if (!temHorasCorretas())
			return;

		int horaInicial = 0;
		int auxTotalDeHorasEmMinutos = 0;

		for (int i = 0; i < this.horas.length; i++) {
			String hora = horas[i];
			int horaFinal = ConversaoDeTempo.converteStringDeHorasParaMinutos(hora);

			boolean indexDaPrimeiraHora = i == 0;
			boolean indexEImpar = i % 2 != 0;

			if (!indexDaPrimeiraHora && indexEImpar)
				auxTotalDeHorasEmMinutos += horaFinal - horaInicial;

			horaInicial = horaFinal;
		}

		this.totalDeHoras = ConversaoDeTempo.converteMinutosParaStringDeHoras(auxTotalDeHorasEmMinutos);
	}

	public boolean temHorasCorretas() {
		return horas.length % 2 == 0;
	}

}
