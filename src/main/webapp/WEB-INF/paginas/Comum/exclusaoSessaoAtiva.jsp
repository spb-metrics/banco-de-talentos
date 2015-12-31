<%@ page language="java" %>
<%@ taglib prefix="bean"      uri="/tlds/struts-bean"    %>
<%@ taglib prefix="html"      uri="/tlds/struts-html"    %>
<%@ taglib prefix="logic"     uri="/tlds/struts-logic"   %>
<%@ taglib prefix="display"   uri="/tlds/sigesp-display" %>
<%@ taglib prefix="sigesp"   uri="/tlds/sigesp-customtag" %>
<%@ taglib prefix="fmt"       uri="/jstl/fmt" %>
<%@ taglib prefix="c"         uri="/jstl/c" %>

<%@page import="org.apache.struts.Globals"%>
<%@page import="org.apache.struts.taglib.html.Constants"%>
<logic:present name="lstDadosSessao">
    <center>    
        <table class="exclusao">
            <tr class="tituloTabelaExclusao">
                <td class="tituloTabelaExclusao" colspan="2">
                    Confirma a exclusão da sessão abaixo ?
                </td>
            </tr>
            <c:if test="${lstDadosSessao[0].servidorAplicacao!=applicationScope.IdInstanciaServidorAplicacao}" >
	            <tr class="tituloTabelaExclusao">
	                <td class="tituloTabelaExclusao" colspan="2" align="center">
                        <font color="red" style="font-size: medium;">
	                       ATENÇÃO: Esta(s) sessão(ões) pode(m) estar ativa(s) em outro servidor de aplicação. Se o registro dela(s) for excluído a partir deste servidor, pode ser que ela(s) ainda continue(m) ativa(s) no outro servidor de aplicação.
	                    </font>
	                </td>
	            </tr>
            </c:if>
            <tr class="displayTag">
                <td colspan="2">
                    <display:table name="lstDadosSessao" align="center" width="100%" id="dtListagem">
                        <display:column property="servidorAplicacao" title="Serv. Aplic." align="right" sortable="true" width="8%" />
	                    <display:column property="ponto" title="Ponto" align="right" sortable="true" width="8%" />
	                    <display:column property="login" title="Login" align="center" sortable="true" width="10%"/>
	                    <display:column property="nomeServidor" title="Nome" align="left" sortable="true" />
	                    <display:column property="grupo" title="Grupo" align="center" sortable="true" width="7%" />
                        <display:column property="acao" title="Ultima ação" align="center" sortable="true" width="7%" />
	                    <display:column title="Ultimo acesso" align="center" sortable="true" width="20%">
	                        <fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${dtListagem.ultimoAcesso}" />
	                    </display:column>
                    </display:table>
                </td>
            </tr>
            <tr class="fundoExclusao">
                <td align="center">
                    <c:set var="idTokenStruts" scope="request">
                        <%= Constants.TOKEN_KEY %>
                    </c:set>
                    <c:set var="valTokenStruts" scope="request">
                        <%= session.getAttribute(Globals.TRANSACTION_TOKEN_KEY) %>
                    </c:set>
                    <sigesp:parametro lista="lstDadosSessao" propriedade="sessao" atributo="idSessao" contexto="request" />
                    <bean:define id="id" type="java.lang.String" name="idSessao"/>
                    <sigesp:parametro lista="lstDadosSessao" propriedade="servidorAplicacao" atributo="servidor" contexto="request" />
                    <bean:define id="idSA" type="java.lang.String" name="servidor"/>
                    
                    <a href="<c:out value='${pageContext.request.contextPath}/sessoesAtivasEfetuarExclusao.do?id=${id}&idSA=${servidor}&${requestScope.idTokenStruts}=${requestScope.valTokenStruts}' />" ><img src="biblioteca/imagens/paginas/Sim.gif" border=0></a>
                </td>
                <td align="center">
                    <html:link page="/sessoesAtivasPrepararVisualizacao.do"><img src="biblioteca/imagens/paginas/Nao.gif" border=0></html:link>
                </td>
            </tr>
        </table>
    </center>    
</logic:present>

<p class='voltar'>
    <html:link page="/sessoesAtivasPrepararVisualizacao.do"><img src="biblioteca/imagens/paginas/voltar.gif" border=0></html:link>
</p>
<br><br>
