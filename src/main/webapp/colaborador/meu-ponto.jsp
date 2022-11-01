<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="model.Usuario"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<jsp:include page="componentes/checarUsuario.jsp" />
<jsp:include page="componentes/checarPermissao.jsp" />

<%

Usuario usuario = (Usuario) session.getAttribute("usuario");

Date now = new Date();
SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEE, d/MM/YYYY");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Meu Ponto | DOTAP</title>
</head>

<body>
	<jsp:include page="../componentes/navbar.jsp" />
	<main>
		<jsp:include page="componentes/aside.jsp" />

		<section>

			<jsp:include page="../componentes/message.jsp" />

			<form action="/projeto-dotap/colaborador/bater-ponto" method="POST">


				Olá <strong><%=usuario.getNome()%></strong>, registre seu ponto
				agora! <br>

				<div id="hora"></div>

				<%=simpleDateformat.format(now)%>

				<br> <input type="password" name="senha"
					placeholder="insira sua senha" /><br>

				<button>Bater ponto</button>
			</form>
		</section>
	</main>



	<script type="text/javascript" src="meu-ponto.js"></script>
</body>

</html>