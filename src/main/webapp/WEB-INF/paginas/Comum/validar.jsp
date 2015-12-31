<%@ page language="java" %>
<%@page import="java.util.Map"%>
<%@page import="java.util.Enumeration"%>

<%@ taglib prefix="c" uri="/jstl/c" %>

Validando a autentica��o externa. Aguarde...

<%
    Map parametrosRequest = request.getParameterMap();
    if (parametrosRequest != null && !parametrosRequest.isEmpty())
    {
        // Recuperar todos os par�metros da query string e armazen�-los na sess�o
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
