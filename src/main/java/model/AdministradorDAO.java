package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import model.database.ConnectionManager;

public class AdministradorDAO {
	public static ArrayList<Usuario> pegarUsuariosCadastrados(Usuario administrador) throws SQLException, Exception {
		Connection connection = ConnectionManager.getConnection();
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

		String sql = "SELECT";
		sql += "    u.id,";
		sql += "    u.nome,";
		sql += "    u.sobrenome,";
		sql += "    u.id_perfil,";
		sql += "    p.nome AS 'perfil'";
		sql += " FROM";
		sql += "    usuarios u";
		sql += "        INNER JOIN";
		sql += "    perfis p ON p.id = u.id_perfil";
		sql += " WHERE";
		sql += "    u.status = 1 AND u.id != ?";

		PreparedStatement ps = connection.prepareStatement(sql);

		ps.setInt(1, administrador.getId());

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Usuario usuario = new Usuario();

			usuario.setId(rs.getInt("id"));
			usuario.setNome(rs.getString("nome"));
			usuario.setSobrenome(rs.getString("sobrenome"));
			usuario.setIdPerfil(rs.getInt("id_perfil"));
			usuario.setPerfil(rs.getString("perfil"));

			usuarios.add(usuario);
		}

		ps.close();
		connection.close();

		return usuarios;
	}

	public static void removerUsuarioPorId(int id) throws SQLException, Exception {
		Connection connection = ConnectionManager.getConnection();

		String sql = "UPDATE usuarios SET status = 0 WHERE id = ?";

		PreparedStatement ps = connection.prepareStatement(sql);

		ps.setInt(1, id);

		boolean usuarioDeletado = ps.executeUpdate() == 1;

		ps.close();
		connection.close();

		if (!usuarioDeletado)
			throw new Exception("Não foi possível encontrar o usuário!");
	}

	public static void cadastrarUsuario(String nome, String sobrenome, String email, String senha, int idPerfil)
			throws SQLException, Exception {
		Connection connection = ConnectionManager.getConnection();

		String sql = "INSERT INTO usuarios VALUES (0, ?, ?, ?, ?, 1, ?)";

		PreparedStatement ps = connection.prepareStatement(sql);

		ps.setString(1, nome);
		ps.setString(2, sobrenome);
		ps.setString(3, email);
		ps.setString(4, senha);
		ps.setInt(5, idPerfil);

		boolean usuarioCadastrado = ps.executeUpdate() == 1;

		ps.close();
		connection.close();

		if (!usuarioCadastrado)
			throw new Exception("Não foi possível cadastrar o usuário!");
	}

	public static ArrayList<SolicitacaoComUsuario> pegarSolicitacoes() throws SQLException, ClassNotFoundException {

		Connection connection = ConnectionManager.getConnection();
		ArrayList<SolicitacaoComUsuario> solicitacoes = new ArrayList<SolicitacaoComUsuario>();

		String sql = "SELECT ";
		sql += "    u.nome,";
		sql += "    u.sobrenome,";
		sql += "    s.id,";
		sql += "    data_hora,";
		sql += "    s.id_tipo,";
		sql += "    t.nome AS 'tipo',";
		sql += "    st.nome 'status'";
		sql += " FROM";
		sql += "    solicitacoes s";
		sql += "        INNER JOIN";
		sql += "    tipo_solicitacoes t ON t.id = s.id_tipo";
		sql += "        INNER JOIN";
		sql += "    status_solicitacoes st ON st.id = s.id_status";
		sql += "        INNER JOIN";
		sql += "    usuarios u ON u.id = s.id_usuario";
		sql += " WHERE";
		sql += "    s.id_status = 1";
		sql += " ORDER BY status DESC , data_hora DESC";

		PreparedStatement ps = connection.prepareStatement(sql);

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			SolicitacaoComUsuario solicitacao = new SolicitacaoComUsuario();
			Usuario usuario = new Usuario();

			usuario.setNome(rs.getString("nome"));
			usuario.setSobrenome(rs.getString("sobrenome"));

			solicitacao.setUsuario(usuario);

			solicitacao.setId(rs.getInt("id"));
			solicitacao.setDataHora(rs.getTimestamp("data_hora"));
			solicitacao.setIdTipo(rs.getInt("id_tipo"));
			solicitacao.setTipo(rs.getString("tipo"));
			solicitacao.setStatus(rs.getString("status"));

			solicitacoes.add(solicitacao);
		}

		ps.close();
		connection.close();

		return solicitacoes;
	}

	public static void aprovarSolicitacaoDeInclusaoPorId(int idSolicitacao) throws SQLException, Exception {
		Connection connection = ConnectionManager.getConnection();

		String sqlPegarSolicitacao = "SELECT * FROM solicitacoes WHERE id = ? LIMIT 1";

		PreparedStatement psPegarSolicitacao = connection.prepareStatement(sqlPegarSolicitacao);

		psPegarSolicitacao.setInt(1, idSolicitacao);

		ResultSet rsSolicitacao = psPegarSolicitacao.executeQuery();

		int idUsuario = 0;
		String dataHora = null;

		if (rsSolicitacao.next()) {
			idUsuario = rsSolicitacao.getInt("id_usuario");
			dataHora = rsSolicitacao.getString("data_hora");
		}

		String sqlRegistro = "INSERT INTO registros VALUES(0, ?, 1, ?)";
		String sqlSolicitacaoRegistro = "INSERT INTO solicitacoes_registros_altera VALUES(?, ?)";
		String sqlAtualizarSolicitacao = "UPDATE solicitacoes SET id_status = 2 WHERE id = ?;";

		try (PreparedStatement psRegistro = connection.prepareStatement(sqlRegistro, Statement.RETURN_GENERATED_KEYS);
				PreparedStatement psSolicitacaoRegistro = connection.prepareStatement(sqlSolicitacaoRegistro,
						Statement.RETURN_GENERATED_KEYS);
				PreparedStatement psAtualizarSolicitacao = connection.prepareStatement(sqlAtualizarSolicitacao,
						Statement.RETURN_GENERATED_KEYS);)

		{
			connection.setAutoCommit(false);

			psRegistro.setString(1, dataHora);
			psRegistro.setInt(2, idUsuario);

			boolean registroIncluido = psRegistro.executeUpdate() == 1;

			if (!registroIncluido)
				throw new Exception("Não foi possível incluir o registro ao usuário!");

			ResultSet rsRegistro = psRegistro.getGeneratedKeys();

			rsRegistro.next();

			int idRegistro = rsRegistro.getInt(1);

			psSolicitacaoRegistro.setInt(1, idSolicitacao);
			psSolicitacaoRegistro.setInt(2, idRegistro);

			boolean solicitacaoRegistro = psSolicitacaoRegistro.executeUpdate() == 1;

			if (!solicitacaoRegistro)
				throw new Exception("Não foi possível vincular a solicitação ao registro.");

			psAtualizarSolicitacao.setInt(1, idSolicitacao);

			boolean solicitacaoAtualizada = psAtualizarSolicitacao.executeUpdate() == 1;

			if (!solicitacaoAtualizada)
				throw new Exception("Não foi possível atualizar o status da solicitação!");

			connection.commit();

			psPegarSolicitacao.close();
			psRegistro.close();
			psSolicitacaoRegistro.close();
			psAtualizarSolicitacao.close();

			connection.close();

		} catch (Exception e) {
			connection.rollback();
			throw e;
		}
	}

	public static void aprovarSolicitacaoExclusaoPorId(int idSolicitacao) throws SQLException, Exception {
		Connection connection = ConnectionManager.getConnection();

		String sqlAtualizarSolicitacao = "UPDATE solicitacoes SET id_status = 2 WHERE id = ?;";

		PreparedStatement psAtualizarSolicitacao = connection.prepareStatement(sqlAtualizarSolicitacao);

		psAtualizarSolicitacao.setInt(1, idSolicitacao);

		boolean solicitacaoAtualizada = psAtualizarSolicitacao.executeUpdate() == 1;

		psAtualizarSolicitacao.close();
		connection.close();

		if (!solicitacaoAtualizada)
			throw new Exception("Não foi possível atualizar o status da solicitação!");
	}

	public static void reprovarSolicitacaoPorId(int idSolicitacao) throws SQLException, Exception {
		Connection connection = ConnectionManager.getConnection();

		String sqlAtualizarSolicitacao = "UPDATE solicitacoes SET id_status = 3 WHERE id = ?;";

		PreparedStatement psAtualizarSolicitacao = connection.prepareStatement(sqlAtualizarSolicitacao);

		psAtualizarSolicitacao.setInt(1, idSolicitacao);

		boolean solicitacaoAtualizada = psAtualizarSolicitacao.executeUpdate() == 1;

		psAtualizarSolicitacao.close();
		connection.close();

		if (!solicitacaoAtualizada)
			throw new Exception("Não foi possível atualizar o status da solicitação!");
	}

	public static ArrayList<Usuario> pegarColaboradores() throws SQLException, Exception {
		Connection connection = ConnectionManager.getConnection();
		ArrayList<Usuario> colaboradores = new ArrayList<Usuario>();

		String sql = "SELECT";
		sql += "    id,";
		sql += "    nome,";
		sql += "    sobrenome";
		sql += " FROM";
		sql += "    usuarios";
		sql += " WHERE";
		sql += "    status = 1 AND id_perfil = 1";

		PreparedStatement ps = connection.prepareStatement(sql);

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Usuario usuario = new Usuario();

			usuario.setId(rs.getInt("id"));
			usuario.setNome(rs.getString("nome"));
			usuario.setSobrenome(rs.getString("sobrenome"));

			colaboradores.add(usuario);
		}

		ps.close();
		connection.close();

		return colaboradores;
	}

	public static ArrayList<RegistroPorData> pegarRegistrosPorColaboradorEData(int idColaborador, String data) throws SQLException, Exception  {
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
		sql += "			OR (            "; // OU (se tem solicitação)
		sql += "                (s.id_tipo = 1 AND s.id_status = 2)"; // Verifica se é inclusão e aprovada
		sql += "                OR"; // OU
		sql += "				(s.id_tipo = 2 AND s.id_status != 2)"; // Verifica se é exclusão e não aprovado
		sql += "			)";
		sql += "		)";
		sql += " GROUP BY data";
		sql += " ORDER BY data";

		PreparedStatement ps = connection.prepareStatement(sql);

		ps.setString(1, data + "%");
		ps.setInt(2, idColaborador);

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
}
