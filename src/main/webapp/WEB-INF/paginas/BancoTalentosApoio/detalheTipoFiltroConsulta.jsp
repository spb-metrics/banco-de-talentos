<%@ page language="java" %>

<%@ taglib prefix="bean"      uri="/tlds/struts-bean"  %>
<%@ taglib prefix="html"      uri="/tlds/struts-html"  %>
<%@ taglib prefix="logic"     uri="/tlds/struts-logic" %>

<!-- html:javascript/ -->

<!-- replaced -->


<html:form action="/avisarJavaScriptDetalheTipoFiltroConsulta" onsubmit="return submeterForm()" focus="nomeTipoFiltroConsulta">

    <html:hidden property="identificador" />

    <table class="detalhe">
        <tr class="tituloTabelaDetalhe">
            <td class="tituloTabelaDetalhe" colspan="3">
                <bean:write name="strTituloTabelaDetalhe" ignore="true" />&nbsp;
            </td>
        </tr>
        <tr class="trDetalheImpar">
            <td align="right" width="48%">
                <span title="<bean:message key="visao.BancoTalentosApoio.interface.nomeTipoFiltroConsulta.dicapreenchimento" />" class="dicapreenchimento">
                    <bean:message key="visao.BancoTalentosApoio.interface.nomeTipoFiltroConsulta" />
                </span>
            </td>
            <td align="left" width="48%">
                <html:text property="nomeTipoFiltroConsulta" size="57" />
            </td>
            <td align="center" width="*">
                <bean:message key="sigesp.comum.interface.campo.obrigatorio" bundle="comum" />
            </td>
        </tr>
        <tr class="trDetalhePar">
            <td align="right" width="48%">
                <span title="<bean:message key="visao.BancoTalentosApoio.interface.objetoControlado.dicapreenchimento" />" class="dicapreenchimento">
                    <bean:message key="visao.BancoTalentosApoio.interface.objetoControlado" />
                </span>
            </td>
            <td align="left" width="48%">
                <html:text property="objetoControlado" size="57" />
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
    </center>
</html:form>

<br><br>
<center>
	<html:link page="/tipoFiltroConsultaPrepararVisualizacao.do"><bean:message key="sigesp.comum.interface.operacao.voltar" bundle="comum"/></html:link>
</center>

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