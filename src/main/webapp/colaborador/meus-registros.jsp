
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
<jsp:include page="/componentes/head.jsp" />
<link href="/projeto-dotap/colaborador/meus-registros.css"
	rel="stylesheet" />
<title>Meus Registros | DOTAP</title>
</head>

<body>
	<jsp:include page="../componentes/navbar.jsp" />
	<div class="content">
		<jsp:include page="componentes/aside.jsp" />

		<main>

			<jsp:include page="../componentes/message.jsp" />


			<form class="tableHeader" id="formData"
				action="/projeto-dotap/colaborador/meus-registros">
				<h1>Meus Registros</h1>
				<input id="data" name="data" type="month"
					value="<%=(request.getParameter("data") == null) ? simpleDateformat.format(now) : request.getParameter("data")%>" />
			</form>



			<table>
				<thead>
					<tr>
						<th>Data</th>
						<th>Registros</th>
						<th>Total de Horas</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="registro" items="${registros}">
					<tr>
						<td><c:out value="${registro.data}" /></td>
						<td class="registers"><c:forEach var="hora" items="${registro.horas}"><p class="point">
									<c:out value="${hora}" />
								</p></c:forEach></td>
						<td><c:out value="${registro.totalDeHoras}" /></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>


		</main>
	</div>

	<script type="text/javascript" src="meus-registros.js"></script>
</body>
</html>
