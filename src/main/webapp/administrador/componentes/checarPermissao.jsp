<%@page import="model.Usuario"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%

RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
Usuario usuario = (Usuario) session.getAttribute("usuario");

if (!usuario.eAdministrador()) {
	request.setAttribute("message", "Usuário sem permissão para essa rota.");
	rd.forward(request, response);
	return;
}
%>