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
import model.Hora;
import model.RegistroPorData;
import model.SolicitacaoComUsuario;
import model.Usuario;
import util.Message;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(urlPatterns = { "/administrador/usuarios-cadastrados", "/administrador/remover-usuario",
		"/administrador/cadastrar-usuario", "/administrador/solicitacoes", "/administrador/solicitacao/aprovar",
		"/administrador/solicitacao/reprovar", "/administrador/pontos-registrados",
		"/administrador/registros-por-colaborador" })
public class AdministradorController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");

		Message message = new Message("Usuário não autenticado.", Message.Tipo.error);

		if (session.getAttribute("usuario") == null) {
			request.setAttribute("message", message);
			rd.forward(request, response);
			return;
		}

		if (!usuario.eAdministrador()) {
			message.setValor("Usuário sem permissão para essa rota.");
			request.setAttribute("message", message);
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
		case "/administrador/solicitacoes":
			pegarSolicitacoes(request, response);
			break;
		case "/administrador/solicitacao/aprovar":
			aprovarSolicitacaoPorId(request, response);
			break;
		case "/administrador/solicitacao/reprovar":
			reprovarSolicitacaoPorId(request, response);
			break;
		case "/administrador/pontos-registrados":
			listarColaboradores(request, response);
			break;
		case "/administrador/registros-por-colaborador":
			listarRegistrosPorColaborador(request, response);
			break;
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void pegarTodosUsuarios(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Usuario colaborador = (Usuario) session.getAttribute("usuario");
		RequestDispatcher rd = request.getRequestDispatcher("/administrador/usuarios-cadastrados.jsp");
		Message message = new Message("Ocorreu com a conexão ao banco de dados!", Message.Tipo.error);

		try {
			ArrayList<Usuario> usuarios = AdministradorDAO.pegarUsuariosCadastrados(colaborador);

			request.setAttribute("usuarios", usuarios);
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("message", message);
		} catch (Exception e) {
			e.printStackTrace();
			message.setValor(e.getMessage());
			request.setAttribute("message", message);
		}

		rd.forward(request, response);
	}

	protected void removerUsuario(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idUsuario = request.getParameter("id");
		RequestDispatcher rd = request.getRequestDispatcher("/administrador/usuarios-cadastrados");
		Message message = new Message("Usuário removido com sucesso!", Message.Tipo.success);

		try {
			AdministradorDAO.removerUsuarioPorId(Integer.parseInt(idUsuario));

		} catch (SQLException e) {
			e.printStackTrace();
			message.setValorETipo("Ocorreu com a conexão ao banco de dados!", Message.Tipo.error);
		} catch (Exception e) {
			e.printStackTrace();
			message.setValorETipo(e.getMessage(), Message.Tipo.error);
		}

		request.setAttribute("message", message);
		rd.forward(request, response);
	}

	protected void cadastrarUsuario(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nome = request.getParameter("nome");
		String sobrenome = request.getParameter("sobrenome");
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");
		String eAdministrador = request.getParameter("administrador");

		RequestDispatcher rd = request.getRequestDispatcher("/administrador/cadastro-de-usuario.jsp");
		Message message = new Message("Usuário adicionado com sucesso!", Message.Tipo.success);

		try {
			AdministradorDAO.cadastrarUsuario(nome, sobrenome, email, senha, eAdministrador != null ? 2 : 1);

		} catch (SQLException e) {
			e.printStackTrace();
			message.setValorETipo("Ocorreu com a conexão ao banco de dados!", Message.Tipo.error);
		} catch (Exception e) {
			e.printStackTrace();
			message.setValorETipo(e.getMessage(), Message.Tipo.error);
		}

		request.setAttribute("message", message);
		rd.forward(request, response);
	}

	protected void pegarSolicitacoes(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/administrador/solicitacoes.jsp");
		Message message = new Message("Ocorreu com a conexão ao banco de dados!", Message.Tipo.error);

		try {
			ArrayList<SolicitacaoComUsuario> solicitacoes = AdministradorDAO.pegarSolicitacoes();

			request.setAttribute("solicitacoes", solicitacoes);
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("message", message);
		} catch (Exception e) {
			e.printStackTrace();
			message.setValor(e.getMessage());
			request.setAttribute("message", message);
		}

		rd.forward(request, response);
	}

	void aprovarSolicitacaoPorId(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		String idTipo = request.getParameter("idTipo");

		RequestDispatcher rd = request.getRequestDispatcher("/administrador/solicitacoes");
		Message message = new Message("Solicitação aprovada com sucesso!", Message.Tipo.success);

		try {
			if (Integer.parseInt(idTipo) == 1)
				AdministradorDAO.aprovarSolicitacaoDeInclusaoPorId(Integer.parseInt(id));
			else
				AdministradorDAO.aprovarSolicitacaoExclusaoPorId(Integer.parseInt(id));

		} catch (SQLException e) {
			e.printStackTrace();
			message.setValorETipo("Ocorreu com a conexão ao banco de dados!", Message.Tipo.error);
		} catch (Exception e) {
			e.printStackTrace();
			message.setValorETipo(e.getMessage(), Message.Tipo.error);
		}

		request.setAttribute("message", message);
		rd.forward(request, response);
	}

	void reprovarSolicitacaoPorId(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");

		RequestDispatcher rd = request.getRequestDispatcher("/administrador/solicitacoes");
		Message message = new Message("Solicitação reprovada com sucesso!", Message.Tipo.success);

		try {
			AdministradorDAO.reprovarSolicitacaoPorId(Integer.parseInt(id));
		} catch (SQLException e) {
			e.printStackTrace();
			message.setValorETipo("Ocorreu com a conexão ao banco de dados!", Message.Tipo.error);
		} catch (Exception e) {
			e.printStackTrace();
			message.setValorETipo(e.getMessage(), Message.Tipo.error);
		}

		request.setAttribute("message", message);
		rd.forward(request, response);
	}

	void listarColaboradores(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/administrador/pontos-registrados.jsp");
		Message message = new Message("Ocorreu com a conexão ao banco de dados!", Message.Tipo.error);

		try {
			ArrayList<Usuario> colaboradores = AdministradorDAO.pegarColaboradores();
			
			request.setAttribute("colaboradores", colaboradores);
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("message", message);
		} catch (Exception e) {
			e.printStackTrace();
			message.setValor(e.getMessage());
			request.setAttribute("message", message);
		}

		rd.forward(request, response);
	}

	void listarRegistrosPorColaborador(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String colaboradorId = request.getParameter("colaborador");
		String data = request.getParameter("data");

		RequestDispatcher rd = request.getRequestDispatcher("/administrador/pontos-registrados");
		Message message = new Message("Ocorreu com a conexão ao banco de dados!", Message.Tipo.error);

		try {
			if (colaboradorId == null) throw new Exception("Selecione um colaborador!");
			if (data == null || data.isEmpty()) throw new Exception("Selecione uma data!");
			
			ArrayList<RegistroPorData> registros = AdministradorDAO.pegarRegistrosPorColaboradorEData(Integer.parseInt(colaboradorId), data);
			
			request.setAttribute("registros", registros);
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("message", message);
		} catch (Exception e) {
			e.printStackTrace();
			message.setValor(e.getMessage());
			request.setAttribute("message", message);
		}

		rd.forward(request, response);
	}
}
