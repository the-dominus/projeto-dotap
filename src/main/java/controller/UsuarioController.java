package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Usuario;
import model.UsuarioDAO;
import util.Message;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

@WebServlet(urlPatterns = { "/usuario/login", "/usuario/sair" })
public class UsuarioController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	

		String action = request.getServletPath();

		switch (action) {
		case "/usuario/login":
			login(request, response);
			break;
		case "/usuario/sair":
			sair(request, response);
			break;
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}

	void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");
		
		Message message = new Message("Ocorreu com a conexão ao banco de dados!", Message.Tipo.error);

		try {
			Usuario usuario = UsuarioDAO.login(email, senha);

			HttpSession session = request.getSession();		
			session.setAttribute("usuario", usuario);
			
			if(usuario.eAdministrador())
				response.sendRedirect("/projeto-dotap/administrador/usuarios-cadastrados");
			else 
				response.sendRedirect("/projeto-dotap/colaborador/meu-ponto.jsp");
			
			return;
		} catch (SQLException e) {
			e.printStackTrace();		
			request.setAttribute("message", message);
		} catch (Exception e) {
			e.printStackTrace();	
			message.setValor(e.getMessage());
			request.setAttribute("message", message);		
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
		rd.forward(request, response);
	}

	void sair(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.invalidate();		
		response.sendRedirect("/projeto-dotap");
	}
}
