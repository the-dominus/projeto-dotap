package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.database.ConnectionManager;

public class ColaboradorDAO {

	public static void baterPonto(Usuario colaborador, String dataHora) throws SQLException, Exception {
		Connection connnection = ConnectionManager.getConnection();

		String sql = "INSERT INTO registros (data_hora, id_usuario) VALUES(?, ?)";

		PreparedStatement ps = connnection.prepareStatement(sql);

		ps.setString(1, dataHora);
		ps.setInt(2, colaborador.getId());

		boolean pontoBatido = ps.executeUpdate() == 1;

		ps.close();
		connnection.close();

		if (!pontoBatido)
			throw new Exception("Senha inválida!");
	}

	public static ArrayList<RegistroPorData> pegarRegistros(Usuario colaborador, String data)
			throws SQLException, ClassNotFoundException {

		Connection connnection = ConnectionManager.getConnection();
		ArrayList<RegistroPorData> registros = new ArrayList<RegistroPorData>();

		String sql = "SELECT";
		sql += " DATE_FORMAT(data_hora, '%Y-%m-%d') AS 'data',";
		sql += " GROUP_CONCAT(DATE_FORMAT(data_hora, '%H:%i') ORDER BY data_hora SEPARATOR ';' ) AS 'horas'";
		sql += " FROM registros";
		sql += " WHERE data_hora LIKE ? AND id_usuario = ? AND status = 1";
		sql += " GROUP BY data ORDER BY data";

		PreparedStatement ps = connnection.prepareStatement(sql);

		ps.setString(1, data + "%");
		ps.setInt(2, colaborador.getId());

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			String rawHoras = rs.getString("horas");

			String[] horas = rawHoras.split(";");

			RegistroPorData registro = new RegistroPorData(rs.getDate("data"), horas);

			registros.add(registro);
		}

		ps.close();
		connnection.close();

		return registros;
	}

	public static ArrayList<Solicitacao> pegarSolicitacoes(Usuario colaborador)
			throws SQLException, ClassNotFoundException {

		Connection connnection = ConnectionManager.getConnection();
		ArrayList<Solicitacao> solicitacoes = new ArrayList<Solicitacao>();

		String sql = "SELECT s.id, data_hora, t.nome AS 'tipo', st.nome 'status'";
		sql += " FROM solicitacoes s";
		sql += " INNER JOIN tipo_solicitacoes t";
		sql += " ON t.id = s.id_tipo";
		sql += " INNER JOIN status_solicitacoes st";
		sql += " ON st.id = s.id_status";
		sql += " WHERE id_usuario = ?;";

		PreparedStatement ps = connnection.prepareStatement(sql);

		ps.setInt(1, colaborador.getId());

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Solicitacao solicitacao = new Solicitacao();
			solicitacao.setId(rs.getInt("id"));
			solicitacao.setDataHora(rs.getTimestamp("data_hora"));
			solicitacao.setTipo(rs.getString("tipo"));
			solicitacao.setStatus(rs.getString("status"));
			
			solicitacoes.add(solicitacao);
		}

		ps.close();
		connnection.close();

		return solicitacoes;
	}
	
	public static void incluirPonto(Usuario colaborador, String dataHora) throws SQLException, Exception {
		Connection connnection = ConnectionManager.getConnection();

		String sql = "INSERT INTO solicitacoes (data_hora, id_usuario, id_tipo) VALUES(?, ?, 1)";

		PreparedStatement ps = connnection.prepareStatement(sql);

		ps.setString(1, dataHora);
		ps.setInt(2, colaborador.getId());

		boolean pontoIncluido = ps.executeUpdate() == 1;

		ps.close();
		connnection.close();

		if (!pontoIncluido)
			throw new Exception("Não foi possível incluir a solicitação!");
	}

	public static RegistroPorData pegarPontosPorData(Usuario colaborador, String data)
			throws SQLException, ClassNotFoundException {

		Connection connnection = ConnectionManager.getConnection();
		RegistroPorData registro = null;

		String sql = "SELECT";
		sql += " DATE_FORMAT(data_hora, '%Y-%m-%d') AS 'data',";
		sql += " GROUP_CONCAT(DATE_FORMAT(data_hora, '%H:%i') ORDER BY data_hora SEPARATOR ';' ) AS 'horas'";
		sql += " FROM registros";
		sql += " WHERE data_hora LIKE ? AND id_usuario = ? AND status = 1";
		sql += " GROUP BY data LIMIT 1";

		PreparedStatement ps = connnection.prepareStatement(sql);

		ps.setString(1, data + "%");
		ps.setInt(2, colaborador.getId());

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			String rawHoras = rs.getString("horas");

			String[] horas = rawHoras.split(";");

			registro = new RegistroPorData(rs.getDate("data"), horas);			
		}

		ps.close();
		connnection.close();

		return registro;
	}

}