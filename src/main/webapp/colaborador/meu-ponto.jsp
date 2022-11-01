<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="model.Usuario"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<jsp:include page="../componentes/checarUsuario.jsp" />
<jsp:include page="componentes/checarPermissao.jsp" />

<%
Usuario usuario = (Usuario) session.getAttribute("usuario");

Date now = new Date();
SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEE dd/MM/YYYY");
%>

<!DOCTYPE html>
<html>
<head>
<jsp:include page="/componentes/head.jsp" />
<link href="/projeto-dotap/colaborador/meu-ponto.css" rel="stylesheet" />
<title>Meu Ponto | DOTAP</title>
</head>

<body>
	<jsp:include page="../componentes/navbar.jsp" />
	<div class="content">
		<jsp:include page="componentes/aside.jsp" />

		<main>
			<form action="/projeto-dotap/colaborador/bater-ponto" method="POST"
				class="card">
				<jsp:include page="../componentes/message.jsp" />
				<p>
					Olá <strong><%=usuario.getNome()%></strong>, registre seu ponto
					agora!
				</p>

				<div class="data">
					<h1 id="hora"></h1>
					<p><%=simpleDateformat.format(now)%></p>
				</div>

				<div class="inputContent">
					<label for="password">Digite a senha:</label> <input
						type="password" name="senha" id="password">
				</div>

				<button class="btnRegister">Bater ponto</button>
			</form>
		</main>

	</div>

	<script type="text/javascript" src="meu-ponto.js"></script>
</body>

</html>