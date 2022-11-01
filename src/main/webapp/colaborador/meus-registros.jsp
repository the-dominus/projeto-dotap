
<%@page import="model.RegistroPorData"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
Date now = new Date();
SimpleDateFormat simpleDateformat = new SimpleDateFormat("YYYY-MM");

ArrayList<RegistroPorData> registros = (ArrayList<RegistroPorData>) request.getAttribute("registros");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Meus Registros | DOTAP</title>
</head>

<body>
	<jsp:include page="../componentes/navbar.jsp" />
	<main>
		<jsp:include page="componentes/aside.jsp" />

		<section>
		
			<jsp:include page="../componentes/message.jsp" />


			<form id="formData"
				action="/projeto-dotap/colaborador/meus-registros">
				<input id="data" name="data" type="month"
					value="<%=(request.getParameter("data") == null) ? simpleDateformat.format(now) : request.getParameter("data")%>" />
			</form>


			<div align="center">
				<table border="1" cellpadding="5">
					<caption>
						<h2>Meus Registros</h2>
					</caption>
					<tr>
						<th>Data</th>
						<th>Registros</th>
						<th>Total de horas</th>
					</tr>
					<c:forEach var="registro" items="${registros}">
						<tr>
							<td><c:out value="${registro.data}" /></td>
							<td>
								<c:forEach var="hora" items="${registro.horas}">
									| <c:out value="${hora}" /> |
								</c:forEach>
							</td>
							<td><c:out value="${registro.totalDeHoras}" /></td>
						</tr>
					</c:forEach>
				</table>
			</div>

		</section>
	</main>

	<script type="text/javascript" src="meus-registros.js"></script>
</body>
</html>
