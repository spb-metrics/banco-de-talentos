<%@ taglib uri="/tlds/struts-menu" prefix="menu"%>
<%@ taglib prefix="c" uri="/jstl/c" %>

<%@ page import="br.gov.camara.seguranca.implementacaobt.UsuarioAutenticadoBT"%>
<%@ page import="br.gov.camara.seguranca.SegurancaWeb"%>
<%@ page import="br.gov.camara.util.strutsmenu.SegurancaPermissionsAdapter"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="org.apache.struts.config.ModuleConfig"%>

<br><br>
<script language="JavaScript1.2" src="<c:out value='${pageContext.request.contextPath}' />/biblioteca/funcoes/menu/coolmenus3.js">
// Construção do menu
</script>

<script src="<c:out value='${pageContext.request.contextPath}' />/biblioteca/funcoes/menu/coolmenu-config.js">
// Configurações do menu
</script>

<script language="JavaScript1.2">
// Variáveis JavaScript específicas para o Struts Menu
cmTopMenuImage='&nbsp;&nbsp;&nbsp;<img src="<c:out value='${pageContext.request.contextPath}' />/biblioteca/imagens/menu/Darrow.gif">'
cmSubMenuImage='&nbsp;&nbsp;&nbsp;<img src="<c:out value='${pageContext.request.contextPath}' />/biblioteca/imagens/menu/Rarrow.gif">'
cmImageRootPath='<c:out value='${pageContext.request.contextPath}' />/biblioteca/imagens/'
</script>

<%
    String modulo = ((ModuleConfig) request.getAttribute(Globals.MODULE_KEY)).getPrefix();

    if ("/Sigesp".equals(modulo))
    {
        modulo = "/Autenticacao";
    }
    else if (modulo == null || "".equals(modulo))
    {
        modulo = "/Comum";
    }

    modulo = "mnu" + modulo.substring(1);
%>

<% 
    SegurancaWeb seguranca = SegurancaWeb.obterSegurancaWeb(session);
    if (seguranca != null && seguranca.temUsuarioLogado(session))
    {
        if (session.getAttribute("SegurancaPermissionsAdapter") == null)
        {
            SegurancaPermissionsAdapter spaSigesp = new SegurancaPermissionsAdapter(session);
            session.setAttribute("SegurancaPermissionsAdapter", spaSigesp);
        }
%>
<menu:useMenuDisplayer name="SigespCoolMenu" permissions="SegurancaPermissionsAdapter" id="primary-nav">
    <menu:displayMenu name="<%= modulo %>" />
<%
        
%>
    <menu:displayMenu name="mnuModulosLogado"/>
    <menu:displayMenu name="mnuUsuarioLogado"/>
<%      
        
%>
</menu:useMenuDisplayer>
<%
    }
    else
    {
       if (session.getAttribute("SegurancaPermissionsAdapter") != null)
       {
           session.removeAttribute("SegurancaPermissionsAdapter");
       }
%>
<menu:useMenuDisplayer name="SigespCoolMenu" id="primary-nav">
<%
	    String queryString = request.getQueryString();
	    if (queryString == null || queryString.indexOf("nored") == -1)
	    {
%>
	<menu:displayMenu name="mnuAutenticacao"/>
	<menu:displayMenu name="mnuModulosNaoLogado"/>
	<menu:displayMenu name="mnuUsuarioNaoLogado"/>
<%
        }
        else
        {
%>
    <menu:displayMenu name="mnuUsuarioSomente"/>
<%
        }
%>
</menu:useMenuDisplayer>
<%
    }
%>
