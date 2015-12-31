<%@ page language="java" %>
<%@ taglib prefix="bean"      uri="/tlds/struts-bean"    %>
<%@ taglib prefix="html"      uri="/tlds/struts-html"    %>
<%@ taglib prefix="logic"     uri="/tlds/struts-logic"   %>
<%@ taglib prefix="display"   uri="/tlds/sigesp-display" %>
<%@ taglib prefix="sigesp"   uri="/tlds/sigesp-customtag" %>
<!-- replaced -->

<logic:present name="lstCategoriaAtributosTalento">
        <table class="exclusao">
            <tr class="tituloTabelaExclusao">
                <td class="tituloTabelaExclusao" colspan="2">
                <logic:present name="strConfirma">
                	<bean:write name="strConfirma" ignore="true" />
                </logic:present>
                <logic:notPresent name="strConfirma">
                    Confirmação de exclusão de categoria/atributo de talento
                </logic:notPresent>
                </td>
            </tr>
            <tr class="displayTag">
                <td colspan="2">
                    <display:table name="lstCategoriaAtributosTalento" align="center" width="100%">
	                    <display:column property="atributoTalento.nome" title="Nome" align="center"/>
                    </display:table>
                </td>
            </tr>    
            <tr class="fundoExclusao">
                <td align="center">
                    <sigesp:parametro lista="lstCategoriaAtributosTalento" propriedade="identificador" atributo="chave" contexto="request" />
                    <html:link page="/categoriaAtributoTalentoEfetuarExclusao.do" paramName="chave" paramId="chave"><bean:message key="sigesp.comum.interface.texto.sim" bundle="comum"/></html:link>
                </td>
                <td align="center">
                    <html:link page="/categoriaAtributoTalentoPrepararVisualizacao.do"><bean:message key="sigesp.comum.interface.texto.nao" bundle="comum"/></html:link>
                </td>
            </tr>
        </table>
</logic:present>