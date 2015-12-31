<%@ page language="java" %>
<%@ taglib prefix="bean"      uri="/tlds/struts-bean"    %>
<%@ taglib prefix="html"      uri="/tlds/struts-html"    %>
<%@ taglib prefix="logic"     uri="/tlds/struts-logic"   %>
<%@ taglib prefix="display"   uri="/tlds/sigesp-display" %>
<%@ taglib prefix="sigesp"   uri="/tlds/sigesp-customtag" %>

<!-- replaced -->

<logic:present name="lstFiltroConsultaUsuario">
        <table class="exclusao">
            <tr class="tituloTabelaExclusao">
                <td class="tituloTabelaExclusao" colspan="2">
                    Confirmação de exclusão de filtro de consulta de usuário
                </td>
            </tr>
            <tr class="displayTag">
                <td colspan="2">
                    <display:table name="lstFiltroConsultaUsuario" align="center" width="100%">
	                    <display:column property="nome" title="Nome" align="center"/>
                    </display:table>
                </td>
            </tr>    
            <tr class="fundoExclusao">
                <td align="center">
                    <sigesp:parametro lista="lstFiltroConsultaUsuario" propriedade="identificador" atributo="chaveExclusao" contexto="request" />
                    <html:link page="/filtroConsultaUsuarioEfetuarExclusao.do" paramName="chaveExclusao" paramId="chaveExclusao"><bean:message key="sigesp.comum.interface.texto.sim" bundle="comum"/></html:link>
                </td>
                <td align="center">
                    <html:link page="/filtroConsultaUsuarioPrepararVisualizacao.do"><bean:message key="sigesp.comum.interface.texto.nao" bundle="comum"/></html:link>
                </td>
            </tr>
        </table>
</logic:present>