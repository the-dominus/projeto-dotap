package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	
	public static void cadastrarUsuario(String nome, String sobrenome, String email, String senha, int idPerfil) throws SQLException, Exception {
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
}
