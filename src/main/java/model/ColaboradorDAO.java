package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import model.database.ConnectionManager;

public class ColaboradorDAO {

	public static void baterPonto(Usuario colaborador, String dataHora) throws SQLException, Exception {
		Connection connection = ConnectionManager.getConnection();

		String sql = "INSERT INTO registros (data_hora, id_usuario) VALUES(?, ?)";

		PreparedStatement ps = connection.prepareStatement(sql);

		ps.setString(1, dataHora);
		ps.setInt(2, colaborador.getId());

		boolean pontoBatido = ps.executeUpdate() == 1;

		ps.close();
		connection.close();

		if (!pontoBatido)
			throw new Exception("Senha inválida!");
	}

	public static ArrayList<RegistroPorData> pegarRegistros(Usuario colaborador, String data)
			throws SQLException, ClassNotFoundException {

		Connection connection = ConnectionManager.getConnection();
		ArrayList<RegistroPorData> registros = new ArrayList<RegistroPorData>();

		String sql = "SELECT ";
		sql += "    DATE_FORMAT(r.data_hora, '%Y-%m-%d') AS 'data',";
		sql += "    GROUP_CONCAT(DATE_FORMAT(r.data_hora, '%H:%i')";
		sql += "        ORDER BY r.data_hora";
		sql += "        SEPARATOR ';') AS 'horas',";
		sql += "    GROUP_CONCAT(r.id";
		sql += "        ORDER BY r.data_hora";
		sql += "        SEPARATOR ';') AS 'id_horas'";
		sql += " FROM";
		sql += "    registros r";
		sql += "        LEFT JOIN";
		sql += "    solicitacoes_registros_altera sra ON sra.id_registro = r.id";
		sql += "        LEFT JOIN";
		sql += "    solicitacoes s ON s.id = sra.id_solicitacao";
		sql += " WHERE";
		sql += "    	r.data_hora LIKE ?";
		sql += "    	AND r.status = 1";
		sql += "        AND r.id_usuario = ?";
		sql += "        AND (";
		sql += "        sra.id_registro IS NULL "; // Quando não tem solicitação
		sql += "			OR (            "; 				// OU (se tem solicitação)
		sql += "                (s.id_tipo = 1 AND s.id_status = 2)"; // Verifica se é inclusão e aprovada
		sql += "                OR"; 									// OU
		sql += "				(s.id_tipo = 2 AND s.id_status != 2)"; // Verifica se é exclusão e não aprovado
		sql += "			)";
		sql += "		)";
		sql += " GROUP BY data";
		sql += " ORDER BY data";

		PreparedStatement ps = connection.prepareStatement(sql);

		ps.setString(1, data + "%");
		ps.setInt(2, colaborador.getId());

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			String rawIdHoras = rs.getString("id_horas");
			String rawHoras = rs.getString("horas");

			String[] listaIdHoras = rawIdHoras.split(";");
			String[] listaHoras = rawHoras.split(";");

			ArrayList<Hora> horas = new ArrayList<Hora>();

			for (int i = 0; i < listaIdHoras.length; i++) {
				Hora hora = new Hora();

				hora.setId(Integer.parseInt(listaIdHoras[i]));
				hora.setValor(listaHoras[i]);

				horas.add(hora);
			}

			RegistroPorData registro = new RegistroPorData(rs.getDate("data"), horas);

			registros.add(registro);
		}

		ps.close();
		connection.close();

		return registros;
	}

	public static ArrayList<Solicitacao> pegarSolicitacoes(Usuario colaborador)
			throws SQLException, ClassNotFoundException {

		Connection connection = ConnectionManager.getConnection();
		ArrayList<Solicitacao> solicitacoes = new ArrayList<Solicitacao>();

		String sql = "SELECT s.id, data_hora, id_tipo, id_status, t.nome AS 'tipo', st.nome 'status'";
		sql += " FROM solicitacoes s";
		sql += " INNER JOIN tipo_solicitacoes t";
		sql += " ON t.id = s.id_tipo";
		sql += " INNER JOIN status_solicitacoes st";
		sql += " ON st.id = s.id_status";
		sql += " WHERE id_usuario = ?";
		sql += " ORDER BY status DESC, data_hora DESC";

		PreparedStatement ps = connection.prepareStatement(sql);

		ps.setInt(1, colaborador.getId());

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Solicitacao solicitacao = new Solicitacao();
			solicitacao.setId(rs.getInt("id"));
			solicitacao.setDataHora(rs.getTimestamp("data_hora"));
			solicitacao.setTipo(rs.getString("tipo"));
			solicitacao.setStatus(rs.getString("status"));
			solicitacao.setIdTipo(rs.getInt("id_tipo"));
			solicitacao.setIdStatus(rs.getInt("id_status"));
			
			
			solicitacoes.add(solicitacao);
		}

		ps.close();
		connection.close();

		return solicitacoes;
	}

	public static void incluirPonto(Usuario colaborador, String dataHora) throws SQLException, Exception {
		Connection connection = ConnectionManager.getConnection();

		String sql = "INSERT INTO solicitacoes (data_hora, id_usuario, id_tipo) VALUES(?, ?, 1)";

		PreparedStatement ps = connection.prepareStatement(sql);

		ps.setString(1, dataHora);
		ps.setInt(2, colaborador.getId());

		boolean pontoIncluido = ps.executeUpdate() == 1;

		ps.close();
		connection.close();

		if (!pontoIncluido)
			throw new Exception("Não foi possível incluir a solicitação!");
	}

	public static RegistroPorData pegarPontosPorData(Usuario colaborador, String data)
			throws SQLException, ClassNotFoundException {

		Connection connection = ConnectionManager.getConnection();
		RegistroPorData registro = null;

		String sql = "SELECT";
		sql += " DATE_FORMAT(r.data_hora, '%Y-%m-%d') AS 'data',";
		sql += " GROUP_CONCAT(DATE_FORMAT(r.data_hora, '%H:%i') ORDER BY r.data_hora SEPARATOR ';' ) AS 'horas',";
		sql += " GROUP_CONCAT(r.id ORDER BY r.data_hora SEPARATOR ';') AS 'id_horas'";
		sql += " FROM";
		sql += "    registros r";
		sql += "        LEFT JOIN";
		sql += "    solicitacoes_registros_altera sra ON sra.id_registro = r.id";
		sql += "        LEFT JOIN";
		sql += "    solicitacoes s ON s.id = sra.id_solicitacao";
		sql += " WHERE";
		sql += "    	r.data_hora LIKE ?";
		sql += "    	AND r.status = 1";
		sql += "        AND r.id_usuario = ?";
		sql += "        AND sra.id_registro IS NULL"; // Quando não tem solicitação
		sql += " GROUP BY data LIMIT 1";

		PreparedStatement ps = connection.prepareStatement(sql);

		ps.setString(1, data + "%");
		ps.setInt(2, colaborador.getId());

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			String rawIdHoras = rs.getString("id_horas");
			String rawHoras = rs.getString("horas");

			String[] listaIdHoras = rawIdHoras.split(";");
			String[] listaHoras = rawHoras.split(";");

			ArrayList<Hora> horas = new ArrayList<Hora>();

			for (int i = 0; i < listaIdHoras.length; i++) {
				Hora hora = new Hora();

				hora.setId(Integer.parseInt(listaIdHoras[i]));
				hora.setValor(listaHoras[i]);

				horas.add(hora);
			}

			registro = new RegistroPorData(rs.getDate("data"), horas);
		}

		ps.close();
		connection.close();

		return registro;
	}

	public static void excluirPonto(Usuario colaborador, String dataHora, int idRegistro)
			throws SQLException, Exception {
		Connection connection = ConnectionManager.getConnection();

		String sqlSolicitacao = "INSERT INTO solicitacoes (data_hora, id_usuario, id_tipo) VALUES(?, ?, 2)";
		String sqlSolicitacaoRegistro = "INSERT INTO solicitacoes_registros_altera VALUES(?, ?)";

		try (PreparedStatement psSolicitacao = connection.prepareStatement(sqlSolicitacao,
				Statement.RETURN_GENERATED_KEYS);
				PreparedStatement psSolicitacaoRegistro = connection.prepareStatement(sqlSolicitacaoRegistro,
						Statement.RETURN_GENERATED_KEYS))

		{
			connection.setAutoCommit(false);

			psSolicitacao.setString(1, dataHora);
			psSolicitacao.setInt(2, colaborador.getId());

			boolean solicitacaoIncluida = psSolicitacao.executeUpdate() == 1;

			if (!solicitacaoIncluida)
				throw new Exception("Não foi possível incluir a solicitação!");

			ResultSet rs = psSolicitacao.getGeneratedKeys();

			rs.next();

			int idSolicitacaoCriada = rs.getInt(1);

			psSolicitacaoRegistro.setInt(1, idSolicitacaoCriada);
			psSolicitacaoRegistro.setInt(2, idRegistro);

			boolean solicitacaoRegistro = psSolicitacaoRegistro.executeUpdate() == 1;

			if (!solicitacaoRegistro)
				throw new Exception("Não foi possível incluir a solicitação! Erro ao inlcuir o registro.");

			connection.commit();

			psSolicitacao.close();
			psSolicitacaoRegistro.close();
			connection.close();

		} catch (Exception e) {
			connection.rollback();
			throw e;
		}
	}

}