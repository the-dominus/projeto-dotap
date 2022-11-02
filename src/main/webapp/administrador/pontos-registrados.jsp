<%@page import="model.RegistroPorData"%>
<%@page import="model.Usuario"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setTimeZone value="pt_BR" scope="session"/>
	
<jsp:include page="../componentes/checarUsuario.jsp" />
<jsp:include page="componentes/checarPermissao.jsp" />

<%

ArrayList<Usuario> colaboradores = (ArrayList<Usuario>) request.getAttribute("colaboradores");
ArrayList<RegistroPorData> registros = (ArrayList<RegistroPorData>) request.getAttribute("registros");
%>
	
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/componentes/head.jsp" />
<link href="/projeto-dotap/administrador/pontos-registrados.css"
	rel="stylesheet" />
<title>Pontos Registrados | DOTAP</title>
</head>
<body>
	<jsp:include page="../componentes/navbar.jsp" />
	<div class="content">
		<jsp:include page="componentes/aside.jsp" />

		<main>
			
			<jsp:include page="/componentes/message.jsp" />
		
			<div class="tableHeader">
				<h1>Pontos Registrados</h1>
				<form action="/projeto-dotap/administrador/registros-por-colaborador" >
					<input type="month" name="data" value="<%=request.getParameter("data") %>" />
					 <select id="selectColaborador" name="colaborador" >
						<option disabled selected>Colaborador</option>
						<c:forEach var="colaborador" items="${colaboradores}">
							<option value="<c:out value="${colaborador.id}" />">
								<c:out value="${colaborador.id}" /> - <c:out value="${colaborador.nome}" /> <c:out value="${colaborador.sobrenome}" />
							</option>						
						</c:forEach>
					</select>
					<button>
						<img src="/projeto-dotap/assets/magnifying-glass.svg" alt="Buscar pontos por colaborador" title="Buscar pontos por colaborador">
					</button>
				</form>
			</div>
			<table>
				<thead>
					<tr>
						<th>Data</th>
						<th>Registro</th>
						<th>Total de Horas</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="registro" items="${registros}">
					<tr>
						<td><fmt:formatDate value="${registro.data}" pattern="dd/MM/YYYY"/></td>
						<td class="registers">
							<c:forEach var="hora" items="${registro.horas}">
								<p class="point">
									<c:out value="${hora.valor}" />
								</p>
							</c:forEach>
						</td>
						<td><c:out value="${registro.totalDeHoras}" /></td>
					</tr>
				</c:forEach>
					
				</tbody>
			</table>
		</main>

	</div>
	
	<script type="text/javascript" src="pontos-registrados.js"></script>
</body>
</html>