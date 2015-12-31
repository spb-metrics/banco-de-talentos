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
        A barra de menus � dividida em tr�s partes e cada uma apresenta as opera��es que voc� pode realizar.
        <ul>
        <li>A primeira parte (Autentica��o, no momento) apresenta o nome do m�dulo no qual voc� se encontra e, abaixo dele, est�o as opera��es que voc� pode executar;</li><br>
        <li>A parte do meio (M�dulos) exibe os diversos m�dulos da aplica��o entre os quais voc� pode alternar; e</li><br>
        <li>A �ltima parte (Usu�rio) lhe permite entrar (<i>Login</i>) e sair (<i>Logout</i>) da aplica��o.</li><br>
        </ul>
        Obs: lembre-se de efetuar sempre um <i>Logout</i> quando n�o precisar mais utilizar a aplica��o.
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
