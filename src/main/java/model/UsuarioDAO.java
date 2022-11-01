package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.database.ConnectionManager;

public class UsuarioDAO {	

	public static Usuario login(String email, String senha) throws SQLException, Exception {
		Connection connnection = ConnectionManager.getConnection();
	
		
		Usuario user = new Usuario();

		String sql = "SELECT u.id, u.nome, u.sobrenome, u.email, u.status, u.id_perfil,";
		sql += " p.nome AS 'perfil'";
		sql += " FROM usuarios u";
		sql += " INNER JOIN perfis p";
		sql += " ON p.id = u.id_perfil";
		sql += " WHERE email = ? AND senha = ? LIMIT 1";

		PreparedStatement ps = connnection.prepareStatement(sql);

		ps.setString(1, email);
		ps.setString(2, senha);

		ResultSet rs = ps.executeQuery();

		if (!rs.next())
			throw new Exception("Usuário não encontrado!");

		int status = rs.getInt("status");

		if (status == 0)
			throw new Exception("Usuário desativado!");

		user.setId(rs.getInt("id"));
		user.setNome(rs.getString("nome"));
		user.setSobrenome(rs.getString("sobrenome"));
		user.setEmail(rs.getString("email"));
		user.setStatus(rs.getInt("status"));
		user.setIdPerfil(rs.getInt("id_perfil"));
		user.setPerfil(rs.getString("perfil"));

		ps.close();
		connnection.close();

		return user;
	}

	public static boolean autentica(Usuario usuario, String senha) throws SQLException, ClassNotFoundException {
		Connection connnection = ConnectionManager.getConnection();
		

		String sql = "SELECT id FROM usuarios WHERE email = ? AND senha = ? LIMIT 1";

		PreparedStatement ps = connnection.prepareStatement(sql);

		ps.setString(1, usuario.getEmail());
		ps.setString(2, senha);

		ResultSet rs = ps.executeQuery();
		
		boolean autenticado = false;
		if (rs.next())
			autenticado = true;

		ps.close();
		connnection.close();

		return autenticado;
	}
}