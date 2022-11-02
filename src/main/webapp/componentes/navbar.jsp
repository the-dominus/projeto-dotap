<%@page import="model.Usuario"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	
	<%
		Usuario usuario = (Usuario) session.getAttribute("usuario");	
	%>
	

  <header>
    <a href="<%=  usuario.eAdministrador() ? "/projeto-dotap/administrador/solicitacoes.jsp" : "/projeto-dotap/colaborador/meu-ponto.jsp" %>"><img class="logo-dotap" src="/projeto-dotap/assets/logo-dotap.svg" alt="Logo dotap" title="Logo dotap"></a>
	
    <a href="/projeto-dotap/usuario/sair" class="btnLogout">    
     	<img src="/projeto-dotap/assets/sign-out.svg" alt="logo-sair" title="Sair"></img>
    	Sair
    </a>
  </header>

