
<%@page import="model.RegistroPorData"%>
<%@page import="model.Solicitacao"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setTimeZone value="pt_BR" scope="session" />


<jsp:include page="../componentes/checarUsuario.jsp" />
<jsp:include page="componentes/checarPermissao.jsp" />


<%
ArrayList<Solicitacao> solicitacoes = (ArrayList<Solicitacao>) request.getAttribute("solicitacoes");

String data = request.getParameter("data");

RegistroPorData registro = (RegistroPorData) request.getAttribute("registro");
%>

<!DOCTYPE html>
<html>
<head>
<jsp:include page="/componentes/head.jsp" />
<link href="/projeto-dotap/colaborador/minhas-solicitacoes.css"
	rel="stylesheet" />

<title>Minhas Solicitações | DOTAP</title>
</head>

<body>

	<div id="modal-remove" class="modal">
		<!-- Modal content -->
		<div class="modal-container">
			<h1>Exclusão</h1>
			<form id="formModalRemove"
				action="/projeto-dotap/colaborador/excluir-ponto" method="POST"
				class="modal-content">
				<div class="modal-input">
					<label for="data-modal-remove">Data:</label> <input class="inputCustom"
						id="data-modal-remove" type="date" name="data"
						placeholder="00/00/0000" required value="<%=data%>" />
				</div>
				<div class="content-radio">
					<c:forEach var="hora" items="${registro.horas}">
						<div class="modal-radio">
							<label> <input type="radio" required
								value="<c:out	value="${hora.id}" />;<c:out	value="${hora.valor}" />"
								id="hora-modal-remove" name="hora">  <c:out
									value="${hora.valor}" />
							</label>
						</div>
					</c:forEach>
				</div>

				<button class="btnBlue">Solicitar Exclusão</button>
			</form>
		</div>
	</div>

	<div id="modal-add" class="modal">
		<div class="modal-container">
			<h1>Inclusão</h1>
			<form action="/projeto-dotap/colaborador/incluir-ponto" method="POST"
				class="modal-content">
				<jsp:include page="../componentes/message.jsp" />
				<div class="modal-input">
					<label for="data-modal-add">Data:</label> <input class="inputCustom" type="date"
						name="data" id="data-modal-add" />
				</div>
				<div class="modal-input">
					<label for="hour-modal-add">Hora:</label> <input class="inputCustom" type="time"
						id="hour-modal-add" name="hora" />
				</div>
				<button class="btnBlue">Solicitar Inclusão</button>
			</form>
		</div>
	</div>

	<jsp:include page="../componentes/navbar.jsp" />
	<div class="content">
		<jsp:include page="componentes/aside.jsp" />

		<main>

			<jsp:include page="../componentes/message.jsp" />

			<div class="tableHeader">
				<h1>Minhas Solicitações</h1>

				<div class="register-options">
					<button id="btnAddRegister">
						<img src="/projeto-dotap/assets/tipo-1.svg" alt="" /> Incluir
						ponto
					</button>
					<button id="btnRemoveRegister"
						href="/projeto-dotap/colaborador/excluir-ponto.jsp">
						<img src="/projeto-dotap/assets/tipo-2.svg" alt="" /> Excluir
						ponto
					</button>
				</div>
			</div>


			<table>
				<thead>
					<tr>
						<th>Tipo</th>
						<th>Data/Hora</th>
						<th>Status</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="solicitacao" items="${solicitacoes}">
						<tr>
							<td><p><c:out value="${solicitacao.tipo}"/></p></td>
							<td><p class="capitalize">
									<fmt:formatDate value="${solicitacao.dataHora}"
										pattern="EEE dd/MM/YYYY - HH:mm" />
								</p></td>
							<td><p><c:out value="${solicitacao.status}"/></p></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>


		</main>
	</div>
	<script type="text/javascript" src="minhas-solicitacoes.js"></script>
</body>
</html>
