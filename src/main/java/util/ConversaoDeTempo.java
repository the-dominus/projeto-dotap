package util;

public class ConversaoDeTempo {
	public static int converteStringDeHorasParaMinutos(String rawHoras) {
		String[] splitHoras = rawHoras.split(":");

		int horas = Integer.parseInt(splitHoras[0]);
		int minutos = Integer.parseInt(splitHoras[1]);

		int totalDeMinutos = horas * 60 + minutos;

		return totalDeMinutos;
	}

	public static String converteMinutosParaStringDeHoras(int totalDeMinutos) {

		int horas = totalDeMinutos / 60;
		int minutos = totalDeMinutos % 60;

		String rawHoras = String.format("%02d:%02d", horas, minutos);

		return rawHoras;
	}
}