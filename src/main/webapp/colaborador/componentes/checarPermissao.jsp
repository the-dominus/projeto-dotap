<%@page import="util.Message"%>
<%@page import="model.Usuario"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%

RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
Usuario usuario = (Usuario) session.getAttribute("usuario");

if (!usuario.eColaborador()) {
	Message message = new Message("Usuário sem permissão para essa rota.", Message.Tipo.error);
	request.setAttribute("message", message);
	rd.forward(request, response);
	return;
}
%>