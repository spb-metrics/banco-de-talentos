<%@ page language="java" %>
<%@ taglib prefix="bean"      uri="/tlds/struts-bean"    %>
<%@ taglib prefix="html"      uri="/tlds/struts-html"    %>
<%@ taglib prefix="logic"     uri="/tlds/struts-logic"   %>
<%@ taglib prefix="display"   uri="/tlds/sigesp-display" %>
<%@ taglib prefix="sigesp"   uri="/tlds/sigesp-customtag" %>
<!-- replaced -->

<logic:present name="lstAtributoTalentoOpcoes">
        <table class="exclusao">
            <tr class="tituloTabelaExclusao">
                <td class="tituloTabelaExclusao" colspan="2">
                    Confirmação de exclusão de opção de atributo de talento
                </td>
            </tr>
            <tr class="displayTag">
                <td colspan="2">
                    <display:table name="lstAtributoTalentoOpcoes" align="center" width="100%">
	                    <display:column property="descricao" title="Descrição" align="center"/>
                    </display:table>
                </td>
            </tr>    
            <tr class="fundoExclusao">
                <td align="center">
                    <sigesp:parametro lista="lstAtributoTalentoOpcoes" propriedade="identificador" atributo="chave" contexto="request" />
                    <html:link page="/atributoTalentoOpcaoEfetuarExclusao.do" paramName="chave" paramId="chave"><bean:message key="sigesp.comum.interface.texto.sim" bundle="comum"/></html:link>
                </td>
                <td align="center">
                    <html:link page="/atributoTalentoOpcaoPrepararVisualizacao.do"><bean:message key="sigesp.comum.interface.texto.nao" bundle="comum"/></html:link>
                </td>
            </tr>
        </table>
</logic:present>