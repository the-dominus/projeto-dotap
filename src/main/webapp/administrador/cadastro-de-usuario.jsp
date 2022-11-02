<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/componentes/head.jsp" />
<link href="/projeto-dotap/administrador/cadastro-de-usuario.css"
	rel="stylesheet" />
<title>Cadastro de Usuários | DOTAP</title>
</head>
<body>
	<jsp:include page="../componentes/navbar.jsp" />
	<div class="content">
		<jsp:include page="componentes/aside.jsp" />

		<main>
			<h1>Cadastro de Usuário</h1>
			<form class="card">
				<div>
					<div class="inputContent">
						<label>Nome:</label> <input type="text">
					</div>
					<div class="inputContent">
						<label>Sobrenome:</label> <input type="text">
					</div>
					<div class="inputCheckbox">
						<input type="checkbox" id="administrador"> <label
							for="administrador">Administrador</label>
					</div>
				</div>

				<div>
					<div class="inputContent">
						<label>E-mail:</label> <input type="email">
					</div>
					<div class="inputContent">
						<label>Senha:</label> <input type="password">
					</div>
					<button class="btnRegister">Cadastrar</button>
				</div>
			</form>
		</main>

		<div>
</body>
</html>