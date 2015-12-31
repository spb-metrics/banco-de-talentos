<%@ page language="java" %>
<%@ taglib prefix="bean"      uri="/tlds/struts-bean"    %>
<%@ taglib prefix="html"      uri="/tlds/struts-html"    %>
<%@ taglib prefix="logic"     uri="/tlds/struts-logic"   %>
<%@ taglib prefix="display"   uri="/tlds/sigesp-display" %>
<%@ taglib prefix="sigesp"   uri="/tlds/sigesp-customtag" %>

<!-- replaced -->

<logic:present name="lstFiltroConsulta">
        <table class="exclusao">
            <tr class="tituloTabelaExclusao">
                <td class="tituloTabelaExclusao" colspan="2">
                    Confirmação de exclusão do Filtro de consulta
                </td>
            </tr>
            <tr class="displayTag">
                <td colspan="2">
                    <display:table name="lstFiltroConsulta" align="center" width="100%">
	                    <display:column property="nomeFiltroConsulta" title="Nome" align="center"/>
                    </display:table>
                </td>
            </tr>    
            <tr class="fundoExclusao">
                <td align="center">
                    <sigesp:parametro lista="lstFiltroConsulta" propriedade="identificador" atributo="chaveExclusao" contexto="request" />
                    <html:link page="/filtroConsultaEfetuarExclusao.do" paramName="chaveExclusao" paramId="chaveExclusao"><bean:message key="sigesp.comum.interface.texto.sim" bundle="comum"/></html:link>
                </td>
                <td align="center">
                    <html:link page="/filtroConsultaPrepararVisualizacao.do"><bean:message key="sigesp.comum.interface.texto.nao" bundle="comum"/></html:link>
                </td>
            </tr>
        </table>
</logic:present>