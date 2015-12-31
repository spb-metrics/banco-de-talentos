<%@ page language="java" %>

<%@ taglib prefix="bean"      uri="/tlds/struts-bean"  %>
<%@ taglib prefix="html"      uri="/tlds/struts-html"  %>
<%@ taglib prefix="logic"     uri="/tlds/struts-logic" %>

<!-- html:javascript/ -->

<!-- replaced -->


<html:form action="/avisarJavaScriptDetalheCategoriaAtributoTalento" onsubmit="return submeterForm()" focus="atributoTalento">

    <html:hidden property="identificador" />

    <table class="detalhe">
        <tr class="tituloTabelaDetalhe">
            <td class="tituloTabelaDetalhe"  colspan="3">
                <bean:write name="strTituloTabelaDetalhe" ignore="true" />&nbsp;
            </td>
        </tr>
        <tr class="trDetalheImpar">
            <td align="right" width="48%">
                <span title="<bean:message key="visao.BancoTalentosApoio.interface.atributoTalento.dicapreenchimento" />" class="dicapreenchimento">
                    <bean:message key="visao.BancoTalentosApoio.interface.atributoTalento" />
                </span>
            </td>
            <td align="left" width="48%">
                <html:select property="atributoTalento"  style="width=480">
                	<html:option value="">&nbsp;</html:option>
                    <logic:present name="lstAtributosTalento">
                        <html:options collection="lstAtributosTalento" labelProperty="nome" property="identificador"/>
                    </logic:present>
                </html:select>
            </td>
            <td align="center" width="*">
                <bean:message key="sigesp.comum.interface.campo.obrigatorio" bundle="comum" />
            </td>
        </tr>
        <tr class="trDetalhePar">
            <td align="right" width="48%">
                <span title="<bean:message key="visao.BancoTalentosApoio.interface.indicativoObrigatoriedade.dicapreenchimento" />" class="dicapreenchimento">
                    <bean:message key="visao.BancoTalentosApoio.interface.indicativoObrigatoriedade" />
                </span>
            </td>
            <td align="left" width="48%">
                <html:checkbox property="indicativoObrigatoriedade" />
            </td>
            <td align="center" width="*">
                &nbsp;
            </td>
        </tr>
        <tr class="trDetalheImpar">
            <td align="right" width="48%">
                <span title="<bean:message key="visao.BancoTalentosApoio.interface.formacaoDescricao.dicapreenchimento" />" class="dicapreenchimento">
                    <bean:message key="visao.BancoTalentosApoio.interface.formacaoDescricao" />
                </span>
            </td>
            <td align="left" width="48%">
                <html:checkbox property="formacaoDescricao" />
            </td>
            <td align="center" width="*">
                &nbsp;
            </td>
        </tr>
        <tr class="trDetalhePar">
            <td align="right" width="48%">
                <span title="<bean:message key="visao.BancoTalentosApoio.interface.dicaPreenchimento.dicapreenchimento" />" class="dicapreenchimento">
                    <bean:message key="visao.BancoTalentosApoio.interface.dicaPreenchimento" />
                </span>
            </td>
            <td align="left" width="48%">
                <html:textarea property="dicaPreenchimento" rows="3" cols="255" styleClass="rolagem" style="width:500" onkeypress="return desabilitarEnterLimitacao(event, this, 255);"/>
            </td>
            <td align="center" width="*">
                &nbsp;
            </td>
        </tr>
        <tr class="trDetalheImpar">
            <td align="right" width="48%">
                <span title="<bean:message key="visao.BancoTalentosApoio.interface.apelido.dicapreenchimento" />" class="dicapreenchimento">
                    <bean:message key="visao.BancoTalentosApoio.interface.apelido" />
                </span>
            </td>
            <td align="left" width="48%">
                <html:text property="apelido"  size="57"/>
            </td>
            <td align="center" width="*">
                &nbsp;
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
		<html:link page="/categoriaAtributoTalentoPrepararVisualizacao.do"><bean:message key="sigesp.comum.interface.operacao.voltar" bundle="comum"/></html:link>
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
