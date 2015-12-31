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

<table class="listagem">
    <tr class="tituloTabelaListagem">
        <td class="tituloTabelaListagem" width="90%">
            Propriedades da versão do build neste Serv. Aplic. (<c:out value="${applicationScope['IdInstanciaServidorAplicacao']}" />):
        </td>
    </tr>
    <tr class="displayTag">
        <td>
            <aa:zone name="aaListagem">
                <display:table name="propriedadesVersao" align="center" requestURI="versaoPrepararVisualizacao.do" width="100%" id="dtListagem" pagesize="20" export="true" excludedParams="aazones aa_rand aaxmlrequest">
                    <display:column property="propriedade" title="Propriedade" align="right" sortable="true" width="50%" />
                    <display:column property="valor" title="Valor" align="left" sortable="true" width="50%" />
                </display:table>
            </aa:zone>
            <script language="javascript">
                configurarAADisplayTag('aaListagem');
            </script>
        </td>
    </tr>
    <tr>
        <td>
            <center><html:button property="Atualizar" onclick="javascript:window.location.href='versaoPrepararVisualizacao.do'">Atualizar</html:button></center>
        </td>
    </tr>
</table>
<br><br>