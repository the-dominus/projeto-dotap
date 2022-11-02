package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.AdministradorDAO;
import model.ColaboradorDAO;
import model.RegistroPorData;
import model.Usuario;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(urlPatterns = { "/administrador/usuarios-cadastrados", "/administrador/remover-usuario", "/administrador/cadastrar-usuario" })
public class AdministradorController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");

		if (session.getAttribute("usuario") == null) {
			request.setAttribute("message", "Usuário não autenticado.");
			rd.forward(request, response);
			return;
		}

		if (!usuario.eAdministrador()) {
			request.setAttribute("message", "Usuário sem permissão para essa rota.");
			rd.forward(request, response);
			return;
		}

		String action = request.getServletPath();

		switch (action) {
		case "/administrador/usuarios-cadastrados":
			pegarTodosUsuarios(request, response);
			break;
		case "/administrador/remover-usuario":
			removerUsuario(request, response);
			break;
		case "/administrador/cadastrar-usuario":
			cadastrarUsuario(request, response);
			break;
//		case "/colaborador/incluir-ponto":
//			incluirPonto(request, response);
//			break;
//		case "/colaborador/pegar-pontos-por-data":
//			pegarPontosPorData(request, response);
//			break;
//		case "/colaborador/excluir-ponto":
//			excluirPonto(request, response);
//			break;
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	protected void pegarTodosUsuarios(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Usuario colaborador = (Usuario) session.getAttribute("usuario");
		RequestDispatcher rd = request.getRequestDispatcher("/administrador/usuarios-cadastrados.jsp");

		try {
			ArrayList<Usuario> usuarios = AdministradorDAO.pegarUsuariosCadastrados(colaborador);

			request.setAttribute("usuarios", usuarios);
		} catch (SQLException e) {
			e.printStackTrace();
			String message = "Ocorreu com a conexão ao banco de dados!";
			request.setAttribute("message", message);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", e.getMessage());
		}

		rd.forward(request, response);
	}
	
	protected void removerUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idUsuario = request.getParameter("id");
		RequestDispatcher rd = request.getRequestDispatcher("/administrador/usuarios-cadastrados");

		try {
			AdministradorDAO.removerUsuarioPorId(Integer.parseInt(idUsuario));
			
			String message = "Usuário removido com sucesso!";
			request.setAttribute("message", message);
		} catch (SQLException e) {
			e.printStackTrace();
			String message = "Ocorreu com a conexão ao banco de dados!";
			request.setAttribute("message", message);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", e.getMessage());
		}

		rd.forward(request, response);
	}
	
	protected void cadastrarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nome = request.getParameter("nome");
		String sobrenome = request.getParameter("sobrenome");
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");
		String eAdministrador = request.getParameter("administrador");
		
		RequestDispatcher rd = request.getRequestDispatcher("/administrador/cadastro-de-usuario.jsp");
		
		try {
			AdministradorDAO.cadastrarUsuario(nome, sobrenome, email, senha, eAdministrador != null ? 2: 1);
			
			String message = "Usuário adicionado com sucesso!";
			request.setAttribute("message", message);
		} catch (SQLException e) {
			e.printStackTrace();
			String message = "Ocorreu com a conexão ao banco de dados!";
			request.setAttribute("message", message);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", e.getMessage());
		}
		
		rd.forward(request, response);
	}
}
