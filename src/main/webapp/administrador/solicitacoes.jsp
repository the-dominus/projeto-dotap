<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/componentes/head.jsp" />
<link href="/projeto-dotap/administrador/solicitacoes.css"
	rel="stylesheet" />
<title>Solicitações | DOTAP</title>
</head>
<body>
	<jsp:include page="../componentes/navbar.jsp" />
	<div class="content">
		<jsp:include page="componentes/aside.jsp" />
		
		<main>
        <div class="tableHeader">
          <h1>Solicitações</h1>
        </div>

        <table>
          <thead>
            <tr>
              <th>Nome</th>
              <th>Sobrenome</th>
              <th>Tipo</th>
              <th>Data/Hora</th>
              <th>Ação</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td><p>John</p></td>
              <td>
                <p>Doe</p>
              </td>
              <td>
                <p>Inclusão</p>
               </td>
               <td>
                <p>Seg, 03/10/2022 - 08:00</p>
              </td>
              <td>
                <a href=""><img src="../assets/check-circle.svg" alt="Aceitar" title="Aceitar" /></a>
                <a href=""><img src="../assets/x-circle.svg" alt="Recusar" title="Recusar" /></a>
              </td>
            

            
          </tbody>
        </table>
      </main>
		
		<div>
</body>
</html>