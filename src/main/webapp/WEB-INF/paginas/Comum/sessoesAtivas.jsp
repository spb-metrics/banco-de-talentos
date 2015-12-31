<%@ page language="java" %>
<%@ taglib prefix="bean"      uri="/tlds/struts-bean"    %>
<%@ taglib prefix="html"      uri="/tlds/struts-html"    %>
<%@ taglib prefix="logic"     uri="/tlds/struts-logic"   %>
<%@ taglib prefix="sigesp"    uri="/tlds/sigesp-customtag"   %>
<%@ taglib prefix="display"   uri="/tlds/sigesp-display" %>
<%@ taglib prefix="fmt"       uri="/jstl/fmt" %>
<%@ taglib prefix="c"         uri="/jstl/c" %>
<%@ taglib prefix="aa"        uri="/ajax/ajaxanywhere" %>
<!-- replaced -->

<html:form action="/avisarJavaScriptSessoesAtivas">
	<table class="listagem">
	    <tr class="tituloTabelaListagem">
	        <td class="tituloTabelaListagem" width="90%">
	            Registro de sessões ativas desta aplicação - Id deste Serv. Aplic.: <c:out value="${applicationScope['IdInstanciaServidorAplicacao']}" />
	        </td>
            <logic:notEmpty name="sessoesAtivas">
                <td class="tableCellHeaderButton">
                    <a href="#" onclick="submeterForm()"><bean:message key="sigesp.comum.interface.operacao.excluir.semmodulo" bundle="comum"/> Excluir</a>
                </td>
            </logic:notEmpty>
	    </tr>
	    <tr class="displayTag">
	        <td colspan="2">
	            <aa:zone name="aaListagem">
	                <display:table name="sessoesAtivas" align="center" requestURI="sessoesAtivasPrepararVisualizacao.do" width="100%" id="dtListagem" pagesize="20" export="true" excludedParams="aazones aa_rand aaxmlrequest">
	                    <display:column property="servidorAplicacao" title="Serv. Aplic." align="right" sortable="true" width="7%" />
	                    <display:column property="ponto" title="Ponto" align="right" sortable="true" width="7%" />
	                    <display:column property="login" title="Login" align="center" sortable="true" width="9%"/>
	                    <display:column property="grupo" title="Gr." align="center" sortable="true" width="4%" />
	                    <display:column property="nomeServidor" title="Nome" align="left" sortable="true" />
	                    <display:column property="acao" title="Ação" align="left" sortable="true" />
	                    <display:column title="Ultimo acesso" align="center" sortable="true" width="18%">
	                        <fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${dtListagem.ultimoAcesso}" />
	                    </display:column>
	                    <display:column title="Excluir<input type='checkbox' name='checkUncked' style='margin: 1 1 1 1' onclick='checkUnckeAll();' />" align="center" width="5%">
	                      <bean:define id="idSessao" type="java.lang.String" name="dtListagem" property="sessao" />
	                      <bean:define id="servidor" type="java.lang.String" name="dtListagem" property="servidorAplicacao" />
	                      <input type="checkbox" name="ideSessao" value="<%=idSessao + "ø" + servidor%>" style='margin: 1 1 1 1'/>
	                    </display:column>
	                </display:table>
	            </aa:zone>
	            <script language="javascript">
	                configurarAADisplayTag('aaListagem');
	            </script>
	        </td>
	    </tr>
	    <tr>
	        <td>
	            <center><html:button property="Atualizar" onclick="javascript:window.location.href='sessoesAtivasPrepararVisualizacao.do'">Atualizar</html:button></center>
	        </td>
	    </tr>
	</table>
</html:form>
