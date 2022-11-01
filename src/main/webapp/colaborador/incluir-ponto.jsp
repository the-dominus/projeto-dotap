<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<jsp:include page="componentes/checarUsuario.jsp" />
<jsp:include page="componentes/checarPermissao.jsp" />


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Incluir ponto | DOTAP</title>
</head>

<body>
	<jsp:include page="../componentes/navbar.jsp" />
	<main>
		<jsp:include page="componentes/aside.jsp" />

		<section>

			<jsp:include page="../componentes/message.jsp" />

			<form action="/projeto-dotap/colaborador/incluir-ponto" method="POST"
				align="center">
				<h3>Inclusão</h3>

				<div>
					<input type="date" name="data" placeholder="00/00/0000" />
				</div>
				<div>
					<input type="time" name="hora" placeholder="13:00" />
				</div>

				<button>Solicitar inclusão</button>
			</form>
		</section>
	</main>
</body>

</html>