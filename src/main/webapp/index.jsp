<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>DOTAP</title>
</head>

<body>

	<main>
		<jsp:include page="componentes/message.jsp" />


		<form action="/projeto-dotap/usuario/login" method="POST">


			<input type="email" name="email" placeholder="insira seu username" /><br>


			<input type="password" name="senha" placeholder="insira sua senha" />

			<button>Acessar</button>

		</form>
	</main>
</body>
</html>
