<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	<link href="/projeto-dotap/componentes/message.css"
	rel="stylesheet" />

<%
String message = (String) request.getAttribute("message");
if (message != null) {
%>
<div class="error">
	<p><%=message%></p>
</div>

<%}%>
