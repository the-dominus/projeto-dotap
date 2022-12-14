package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ColaboradorDAO;
import model.Hora;
import model.RegistroPorData;
import model.Solicitacao;
import model.Usuario;
import model.UsuarioDAO;
import util.Message;

@WebServlet(urlPatterns = { "/colaborador/bater-ponto", "/colaborador/meus-registros",
		"/colaborador/minhas-solicitacoes", "/colaborador/incluir-ponto", "/colaborador/pegar-pontos-por-data",
		"/colaborador/excluir-ponto" })
public class ColaboradorController extends HttpServlet {
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

		if (!usuario.eColaborador()) {
			message.setValor("Usuário sem permissão para essa rota.");
			request.setAttribute("message", message);
			rd.forward(request, response);
			return;
		}

		String action = request.getServletPath();

		switch (action) {
		case "/colaborador/bater-ponto":
			baterPonto(request, response);
			break;
		case "/colaborador/meus-registros":
			meusRegistros(request, response);
			break;
		case "/colaborador/minhas-solicitacoes":
			minhasSolicitacoes(request, response);
			break;
		case "/colaborador/incluir-ponto":
			incluirPonto(request, response);
			break;
		case "/colaborador/pegar-pontos-por-data":
			pegarPontosPorData(request, response);
			break;
		case "/colaborador/excluir-ponto":
			excluirPonto(request, response);
			break;
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	void baterPonto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String senha = request.getParameter("senha");
		HttpSession session = request.getSession();

		Usuario colaborador = (Usuario) session.getAttribute("usuario");
		Message message = new Message("Ponto batido com sucesso!", Message.Tipo.success);
		

		try {
			boolean autenticado = UsuarioDAO.autentica(colaborador, senha);

			if (!autenticado)
				throw new Exception("Não foi possível autenticar este usuário!");

			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();

			ColaboradorDAO.baterPonto(colaborador, dateFormat.format(date));
		} catch (SQLException e) {
			e.printStackTrace();
			message.setValorETipo(e.getMessage(), Message.Tipo.error);

		} catch (Exception e) {
			e.printStackTrace();
			message.setValorETipo(e.getMessage(), Message.Tipo.error);
		}

		RequestDispatcher rd = request.getRequestDispatcher("/colaborador/meu-ponto.jsp");
		request.setAttribute("message", message);
		rd.forward(request, response);
	}

	void meusRegistros(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String data = request.getParameter("data");
		HttpSession session = request.getSession();
		Usuario colaborador = (Usuario) session.getAttribute("usuario");
		RequestDispatcher rd = request.getRequestDispatcher("/colaborador/meu-ponto.jsp");
		Message message = new Message("Ocorreu com a conexão ao banco de dados!", Message.Tipo.error);

		try {

			if (data == null) {
				Date now = new Date();
				SimpleDateFormat simpleDateformat = new SimpleDateFormat("YYYY-MM");
				data = simpleDateformat.format(now);
			}

			ArrayList<RegistroPorData> registros = ColaboradorDAO.pegarRegistros(colaborador, data);

			request.setAttribute("registros", registros);
			rd = request.getRequestDispatcher("/colaborador/meus-registros.jsp");
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

	void minhasSolicitacoes(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Usuario colaborador = (Usuario) session.getAttribute("usuario");
		RequestDispatcher rd = request.getRequestDispatcher("/colaborador/minhas-solicitacoes.jsp");
		Message message = new Message("Ocorreu com a conexão ao banco de dados!", Message.Tipo.error);

		try {

			ArrayList<Solicitacao> solicitacoes = ColaboradorDAO.pegarSolicitacoes(colaborador);

			request.setAttribute("solicitacoes", solicitacoes);
			rd = request.getRequestDispatcher("/colaborador/minhas-solicitacoes.jsp");
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

	void incluirPonto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String data = request.getParameter("data");
		String hora = request.getParameter("hora");

		HttpSession session = request.getSession();
		Usuario colaborador = (Usuario) session.getAttribute("usuario");
		RequestDispatcher rd = request.getRequestDispatcher("/colaborador/minhas-solicitacoes");
		Message message = new Message("Solicitação feita com sucesso!", Message.Tipo.success);

		try {
			String dataHora = data + " " + hora;

			ColaboradorDAO.incluirPonto(colaborador, dataHora);			
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

	void pegarPontosPorData(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String data = request.getParameter("data");

		HttpSession session = request.getSession();
		Usuario colaborador = (Usuario) session.getAttribute("usuario");
		RequestDispatcher rd = request.getRequestDispatcher("/colaborador/minhas-solicitacoes");
		Message message = new Message("Ocorreu com a conexão ao banco de dados!", Message.Tipo.error);

		try {

			if (data != null && !data.isEmpty()) {
				RegistroPorData registro = ColaboradorDAO.pegarPontosPorData(colaborador, data);
				request.setAttribute("registro", registro);
			}

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

	void excluirPonto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String data = request.getParameter("data");
		String rawHora = request.getParameter("hora");

		HttpSession session = request.getSession();
		Usuario colaborador = (Usuario) session.getAttribute("usuario");
		RequestDispatcher rd = request.getRequestDispatcher("/colaborador/minhas-solicitacoes");
		Message message = new Message("Solicitação feita com sucesso!", Message.Tipo.success);

		try {

			Hora hora = Hora.gerarHora(rawHora);
			String dataHora = data + " " + hora.getValor();

			ColaboradorDAO.excluirPonto(colaborador, dataHora, hora.getId());
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
}
