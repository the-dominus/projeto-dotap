<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<jsp:include page="componentes/checarUsuario.jsp" />
<jsp:include page="componentes/checarPermissao.jsp" />

<%
SimpleDateFormat simpleDateformat = new SimpleDateFormat("YYYY-MM-DD");

%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Excluir ponto | DOTAP</title>
</head>

<body>
	<jsp:include page="../componentes/navbar.jsp" />
	<main>
		<jsp:include page="componentes/aside.jsp" />

		<section>

			<jsp:include page="../componentes/message.jsp" />

			<form id="formData" action="/projeto-dotap/colaborador/excluir-ponto"
				method="POST" align="center">
				<h3>Exclusão</h3>

				<div>
					<input id="data" type="date" name="data" placeholder="00/00/0000" />
				</div>
				<div>
					<fieldset>
						<legend>Select a maintenance drone:</legend>

						<div>
							<input type="radio" id="huey" name="drone" value="huey" checked>
							<label for="huey">Huey</label>
						</div>

						<div>
							<input type="radio" id="dewey" name="drone" value="dewey">
							<label for="dewey">Dewey</label>
						</div>

						<div>
							<input type="radio" id="louie" name="drone" value="louie">
							<label for="louie">Louie</label>
						</div>
					</fieldset>
				</div>

				<button>Solicitar exclusão</button>
			</form>
		</section>
	</main>


	<script type="text/javascript" src="excluir-ponto.js"></script>
</body>

</html>