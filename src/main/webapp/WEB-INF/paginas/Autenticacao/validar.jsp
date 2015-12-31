<%@ page language="java" %>
<%@ page import="br.gov.camara.seguranca.SegurancaWeb"%>

<% 
    if (request.getAttribute("redir") == null)
    {
        SegurancaWeb seguranca = SegurancaWeb.obterSegurancaWeb(session);
        if (seguranca != null && seguranca.temUsuarioLogado(session))
        {
%>
            <p class="nomeModulo">Bem-vindo <%=seguranca.obterUsuarioAutenticado().obterNome()%> !</p>
<%
        }
%>
        <p class="descricaoModulo">
        A barra de menus é dividida em três partes e cada uma apresenta as operações que você pode realizar.
        <ul>
        <li>A primeira parte (Autenticação, no momento) apresenta o nome do módulo no qual você se encontra e, abaixo dele, estão as operações que você pode executar;</li><br>
        <li>A parte do meio (Módulos) exibe os diversos módulos da aplicação entre os quais você pode alternar; e</li><br>
        <li>A última parte (Usuário) lhe permite entrar (<i>Login</i>) e sair (<i>Logout</i>) da aplicação.</li><br>
        </ul>
        Obs: lembre-se de efetuar sempre um <i>Logout</i> quando não precisar mais utilizar a aplicação.
        </p>
<%
    }
    else
    {
%>
		<script language="JavaScript">
	       window.location.href='<%= request.getAttribute("redir") %>';
    	</script>
<%
    }
%>
