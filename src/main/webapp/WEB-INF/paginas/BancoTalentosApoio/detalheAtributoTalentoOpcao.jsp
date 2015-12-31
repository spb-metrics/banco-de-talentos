<%@ page language="java" %>

<%@ taglib prefix="bean"      uri="/tlds/struts-bean"  %>
<%@ taglib prefix="html"      uri="/tlds/struts-html"  %>
<%@ taglib prefix="logic"     uri="/tlds/struts-logic" %>

<!-- html:javascript/ -->

<!-- replaced -->


<html:form action="/avisarJavaScriptDetalheAtributoTalentoOpcao" onsubmit="return submeterForm()" focus="atributoTalentoOpcaoPai">

    <html:hidden property="identificador" />

    <table class="detalhe">
        <tr class="tituloTabelaDetalhe">
            <td class="tituloTabelaDetalhe" colspan="3">
                <bean:write name="strTituloTabelaDetalhe" ignore="true" />&nbsp;
            </td>
        </tr>
        <tr class="trDetalheImpar">
            <td align="right" width="48%">
                <span title="<bean:message key="visao.BancoTalentosApoio.interface.atributoTalentoOpcaoPai.dicapreenchimento" />" class="dicapreenchimento">
                    <bean:message key="visao.BancoTalentosApoio.interface.atributoTalentoOpcaoPai" />
                </span>
            </td>
            <td align="left" width="48%">
                <html:select property="atributoTalentoOpcaoPai" style="width=480">
                	<html:option value="">&nbsp;</html:option>
                    <logic:present name="lstCandidatosAPai">
                        <html:options collection="lstCandidatosAPai" labelProperty="descricao" property="identificador"/>
                    </logic:present>
                </html:select>
            </td>
            <td align="center" width="*">
                &nbsp;
            </td>
        </tr>
        <tr class="trDetalhePar">
            <td align="right" width="48%">
                <span title="<bean:message key="visao.BancoTalentosApoio.interface.descricao.dicapreenchimento" />" class="dicapreenchimento">
                    <bean:message key="visao.BancoTalentosApoio.interface.descricao" />
                </span>
            </td>
            <td align="left" width="48%"  style="width=480">
                <html:text property="descricao" size="57"/>
            </td>
            <td align="center" width="*">
                <bean:message key="sigesp.comum.interface.campo.obrigatorio" bundle="comum" />
            </td>
        </tr>
        <tr class="trBotoes">
            <td align="center" colspan="3">
                <html:submit><bean:message key="sigesp.comum.interface.operacao.gravar" bundle="comum" /></html:submit>
                <html:reset><bean:message key="sigesp.comum.interface.operacao.limpar" bundle="comum" /></html:reset>
            </td>
        </tr>
    </table>
</html:form>

<p class="voltar">
		<html:link page="/atributoTalentoOpcaoPrepararVisualizacao.do"><bean:message key="sigesp.comum.interface.operacao.voltar" bundle="comum"/></html:link>
</p>

<script language="javascript">
    <!--
    function submeterForm()
    {
        var retorno;
        avisarProcessamento(true);
        direcionarAcao('');
        retorno = true;
        return retorno;
    }
    -->
</script>
