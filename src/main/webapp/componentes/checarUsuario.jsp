<%@page import="util.Message"%>
<%@page import="model.Usuario"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%

RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");

if (session.getAttribute("usuario") == null) {
	Message message = new Message("Usuário não autenticado.", Message.Tipo.error);
	request.setAttribute("message", message);
	rd.forward(request, response);
	return;
}

%>