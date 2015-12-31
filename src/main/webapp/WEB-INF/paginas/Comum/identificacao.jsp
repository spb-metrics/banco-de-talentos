<%@ page language="java" %>
<%@ taglib prefix="c" uri="/jstl/c" %>

<%@ taglib uri="/tlds/struts-tiles" prefix="tiles" %>

<%@ page import="br.gov.camara.seguranca.SegurancaWeb" %>
<%@ page import="br.gov.camara.seguranca.UsuarioAutenticado" %>

<!-------------------------------->
<!-- IDENTIFICAÇÃO DA APLICAÇÃO -->
<!-------------------------------->

<span class="identificacaoAplicacao">
	<span class="identificacaoPagina">
    	<tiles:getAsString name="titulo" />
	</span>
    <span class="identificacaoUsuario">
	    <%
	        if (SegurancaWeb.obterSegurancaWeb(session) != null)
	        {
	            if (SegurancaWeb.obterSegurancaWeb(session).temUsuarioLogado(session))
	            {
	                // Recuperar o usuário logado na sessão
	                UsuarioAutenticado usuario = SegurancaWeb.obterSegurancaWeb(session).obterUsuarioAutenticado();
	                out.print(usuario.obterLogin() + " - " + usuario.obterNome());
	            }
	        }
    	%>
    </span>
    <span class="mensagemExibicao"><c:out value="${mensagemExibicao}" escapeXml="false" /></span>
</span>
