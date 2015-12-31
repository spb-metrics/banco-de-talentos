<%@ page language="java" %>
<%@page import="java.util.Map"%>
<%@page import="java.util.Enumeration"%>

<%@ taglib prefix="c" uri="/jstl/c" %>

Validando a autenticação externa. Aguarde...

<%
    Map parametrosRequest = request.getParameterMap();
    if (parametrosRequest != null && !parametrosRequest.isEmpty())
    {
        // Recuperar todos os parâmetros da query string e armazená-los na sessão
        HttpSession sessao = request.getSession(true);
        
        Enumeration e = request.getParameterNames();
        while (e.hasMoreElements())
        {
            String nomeParametro = (String) e.nextElement();
            sessao.setAttribute(nomeParametro, request.getParameter(nomeParametro));
        }
    }
%>

<script language="JavaScript">
    window.location.href='<c:out value='${pageContext.request.contextPath}' />/Autenticacao/validarAutenticacaoExterna.do';
</script>
