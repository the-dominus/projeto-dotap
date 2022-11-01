<%@page import="java.util.Date"%>
<%@page import="model.RegistroPorData"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<jsp:include page="componentes/checarUsuario.jsp" />
<jsp:include page="componentes/checarPermissao.jsp" />

<%
String data = request.getParameter("data");

RegistroPorData registro = (RegistroPorData) request.getAttribute("registro");
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
					<input id="data" type="date" name="data" placeholder="00/00/0000" required
						value="<%=data%>" />


				</div>
				<div>
					<c:forEach var="hora" items="${registro.horas}">
						<div>
							<label>
							<input type="radio" required value="<c:out	value="${hora.id}" />;<c:out	value="${hora.valor}" />"	name="hora" >
							 <c:out	value="${hora.valor}" />
							</label>
						</div>
					</c:forEach>
				</div>

				<button>Solicitar exclusão</button>
			</form>
		</section>
	</main>


	<script type="text/javascript" src="excluir-ponto.js"></script>
</body>

</html>