<%@page import="util.Message"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
Message message = (Message) request.getAttribute("message");
if (message != null) {
%>
<div class="<%=message.getTipo()%>">
	<p><%=message.getValor()%></p>
</div>

<%
}
%>