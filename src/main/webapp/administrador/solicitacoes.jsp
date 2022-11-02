<%@page import="model.SolicitacaoComUsuario"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setTimeZone value="pt_BR" scope="session" />


<jsp:include page="../componentes/checarUsuario.jsp" />
<jsp:include page="componentes/checarPermissao.jsp" />


<%
ArrayList<SolicitacaoComUsuario> solicitacoes = (ArrayList<SolicitacaoComUsuario>) request.getAttribute("solicitacoes");
%>

	
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
		
		<jsp:include page="/componentes/message.jsp" />
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
            <c:forEach var="solicitacao" items="${solicitacoes}">
	            <tr>
	              <td><p><c:out value="${solicitacao.usuario.nome}" /></p></td>
	              <td>
	                <p><c:out value="${solicitacao.usuario.sobrenome}" /></p>
	              </td>
	              <td>
	                <c:out value="${solicitacao.tipo}" />
	               </td>
	               <td>
	                <p><fmt:formatDate value="${solicitacao.dataHora}" pattern="EEE dd/MM/YYYY - HH:mm" /></p>
	              </td>
	              <td>
	                <a href="/projeto-dotap/administrador/solicitacao/aprovar?id=<c:out value="${solicitacao.id}" />&idTipo=<c:out value="${solicitacao.idTipo}" />">
	                	<img src="/projeto-dotap/assets/check-circle.svg" alt="" />
                	</a>
	                <a href="/projeto-dotap/administrador/solicitacao/reprovar?id=<c:out value="${solicitacao.id}" />">
	                	<img src="/projeto-dotap/assets/x-circle.svg" alt="" />
                	</a>
	              </td>
	            </tr>         
			</c:forEach>						
          </tbody>
        </table>
      </main>
		
	</div>
</body>
</html>