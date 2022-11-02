
<%@page import="model.Usuario"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="../componentes/checarUsuario.jsp" />
<jsp:include page="componentes/checarPermissao.jsp" />


<%
ArrayList<Usuario> usuarios = (ArrayList<Usuario>) request.getAttribute("usuarios");
%>

<!DOCTYPE html>
<html>
<head>
<jsp:include page="/componentes/head.jsp" />
<link href="/projeto-dotap/administrador/usuarios-cadastrados.css"
	rel="stylesheet" />
<title>Usuários Cadastrados | DOTAP</title>
</head>
<body>
	<jsp:include page="../componentes/navbar.jsp" />
	<div class="content">
		<jsp:include page="componentes/aside.jsp" />

		<main>
			<jsp:include page="/componentes/message.jsp" />
			<div class="tableHeader">
				<h1>Usuários Cadastrados</h1>
			</div>

			<table>
				<thead>
					<tr>
						<th>Nome</th>
						<th>Sobrenome</th>
						<th>Perfil</th>
						<th>Deletar</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="usuarioItem" items="${usuarios}">
						<tr>
							<td><p>
									<c:out value="${usuarioItem.nome}" />
								</p></td>
							<td>
								<p>
									<c:out value="${usuarioItem.sobrenome}" />
								</p>
							</td>
							<td>
								<p>
									<c:out value="${usuarioItem.perfil}" />
								</p>
							</td>
							<td><a
								href="/projeto-dotap/administrador/remover-usuario?id=<c:out value="${usuarioItem.id}" />"><img
									src="/projeto-dotap/assets/trash.svg" alt="Remover usuário"
									title="Remover usuário" /></a></td>
						</tr>
					</c:forEach>

				</tbody>
			</table>
		</main>

		<div>
</body>
</html>