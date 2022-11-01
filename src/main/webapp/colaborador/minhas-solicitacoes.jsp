
<%@page import="model.Solicitacao"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setTimeZone value="pt_BR" scope="session"/>


<%
Date now = new Date();

SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEE, dd/MM/YYYY - HH:mm");

ArrayList<Solicitacao> solicitacoes = (ArrayList<Solicitacao>) request.getAttribute("solicitacoes");
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
			<form  action="/projeto-dotap/colaborador/excluir-ponto"
				method="POST" class="modal-content">
				<div class="modal-input">
					<label for="Data:" id="Data">Data:</label> <input type="date"
						id="password" name="data"/>
				</div>
				<div class="modal-input">
					<label for="Hora:" id="hour">Hora:</label> <select>
						<option>13:00</option>
						<option>13:00</option>
						<option>13:00</option>
					</select>
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
					<label for="Data:" id="data">Data:</label> <input type="date"
						name="data" id="data" />
				</div>
				<div class="modal-input">
					<label for="Hora:" id="hour">Hora:</label> <input type="time"
						id="hour" name="hora" />
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
							<td><p>
									<img
										src="/projeto-dotap/assets/tipo-<c:out value="${solicitacao.idTipo}"/>.svg"
										alt="<c:out value="${solicitacao.tipo}"/>" />
								</p></td>
							<td><p>
									<fmt:formatDate value="${solicitacao.dataHora}"
										pattern="EEE, dd/MM/YYYY - HH:mm" />
								</p></td>
							<td><img
								src="/projeto-dotap/assets/tipo-<c:out value="${solicitacao.status}"/>.svg"
								alt="<c:out value="${solicitacao.status}"/>" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>


		</main>
	</div>
	<script type="text/javascript" src="minhas-solicitacoes.js"></script>
</body>
</html>
