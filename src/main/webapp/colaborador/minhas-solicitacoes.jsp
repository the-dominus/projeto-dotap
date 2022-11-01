
<%@page import="model.Solicitacao"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<%
Date now = new Date();

SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEE, dd/MM/YYYY - HH:mm");

ArrayList<Solicitacao> solicitacoes = (ArrayList<Solicitacao>) request.getAttribute("solicitacoes");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Minhas Solicitações | DOTAP</title>
</head>

<body>
	<jsp:include page="../componentes/navbar.jsp" />
	<main>
		<jsp:include page="componentes/aside.jsp" />

		<section>

			<jsp:include page="../componentes/message.jsp" />


			<div align="right">
				<a href="/projeto-dotap/colaborador/incluir-ponto.jsp">Incluir ponto</a>				
				<a href="/projeto-dotap/colaborador/excluir-ponto.jsp">Excluir ponto</a>
			</div>

			<div align="center">
				<table border="1" cellpadding="5">
					<caption>
						<h2>Meus Registros</h2>
					</caption>
					<tr>
						<th>Tipo</th>
						<th>Data/Hora</th>
						<th>Status</th>
					</tr>
					<c:forEach var="solicitacao" items="${solicitacoes}">
						<tr>
							<td><c:out value="${solicitacao.tipo}" /></td>
							<td><fmt:formatDate value="${solicitacao.dataHora}" pattern="EEE, dd/MM/YYYY - HH:mm"/></td>
							<td><c:out value="${solicitacao.status}" /></td>
						</tr>
					</c:forEach>
				</table>
			</div>

		</section>
	</main>

</body>
</html>
