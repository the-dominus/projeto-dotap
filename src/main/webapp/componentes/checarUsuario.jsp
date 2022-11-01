<%@page import="model.Usuario"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%

RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");

if (session.getAttribute("usuario") == null) {
	request.setAttribute("message", "Usuário não autenticado.");
	rd.forward(request, response);
	return;
}

%>