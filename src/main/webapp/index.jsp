<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="componentes/head.jsp" />
<link href="/projeto-dotap/styles.css" rel="stylesheet" />
<title>DOTAP</title>
</head>

<body>
	<main>
      <div class="logo">
        <img src="/projeto-dotap/assets/logo-dotap.svg" alt="logo dotap" />
      </div>
      <form action="/projeto-dotap/usuario/login" method="POST" class="form">
		<jsp:include page="componentes/message.jsp" />
        <h2>Faça login e comece a usar!</h2>
        <div>
          <label for="email">Digite seu e-mail:</label>
          <input class="inputCustom" type="email" id="email" name="email"/>
        </div>
        <div>
          <label for="Digite sua senha:" id="password">Digite sua senha:</label>
          <input class="inputCustom" type="password" name="senha" id="password" />
        </div>

        <button>Acessar</button>
      </form>
    </main>
</body>
</html>
