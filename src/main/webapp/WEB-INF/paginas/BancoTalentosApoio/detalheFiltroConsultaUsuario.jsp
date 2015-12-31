<%@ page language="java" %>

<%@ taglib prefix="bean"      uri="/tlds/struts-bean"  %>
<%@ taglib prefix="html"      uri="/tlds/struts-html"  %>
<%@ taglib prefix="logic"     uri="/tlds/struts-logic" %>

<!-- replaced -->


<html:form action="/avisarJavaScriptDetalheFiltroConsultaUsuario" onsubmit="return submeterForm()" focus="identificador">

    <table class="detalhe">
        <tr class="tituloTabelaDetalhe">
            <td class="tituloTabelaDetalhe" colspan="3">
                <bean:write name="strTituloTabelaDetalhe" ignore="true" />&nbsp;
            </td>
        </tr>
        <tr class="trDetalheImpar">
            <td align="right" width="48%">
                <span title="<bean:message key="visao.BancoTalentosApoio.interface.usuarioIdentificadorNome.dicapreenchimento" />" class="dicapreenchimento">
                    <bean:message key="visao.BancoTalentosApoio.interface.usuarioIdentificadorNome" />
                </span>
            </td>
            <td align="left" width="48%">
                <html:text property="identificador" size="12"/>
				<a href="JavaScript:void(0);"><img src="<%=request.getContextPath()%>/biblioteca/imagens/paginas/lupa.gif" onclick="javascript:openPopUp('/' + obterContextPath() + '/BancoTalentosApoio/consultaUsuarioPrepararVisualizacao.do', capturarUsuario, 620, 500)" title='<bean:message key="visao.BancoTalentosApoio.interface.consultaUsuario.dicapreenchimento" />' border="0"></a><br>
                <html:text property="nomeUsuario" size="57" readonly="true" />
            </td>
            <td align="center" width="*">
                <bean:message key="sigesp.comum.interface.campo.obrigatorio" bundle="comum" />
            </td>
        </tr>
        <tr class="trBotoes">
            <td align="center" colspan="3">
                <html:submit onclick="JavaScript:avisarProcessamento();"><bean:message key="sigesp.comum.interface.operacao.gravar" bundle="comum" /></html:submit>
                <html:reset><bean:message key="sigesp.comum.interface.operacao.limpar" bundle="comum" /></html:reset>
            </td>
        </tr>
    </table>
</html:form>

<p class="voltar">
		<html:link page="/filtroConsultaUsuarioPrepararVisualizacao.do"><bean:message key="sigesp.comum.interface.operacao.voltar" bundle="comum"/></html:link>
</p>

<script src="<%=request.getContextPath()%>/biblioteca/funcoes/biblio.js">
</script>

<script language="javascript">
    function capturarUsuario(retorno)
    {    
        if (retorno.indexOf(";") >= 0)
        {
            var temp = retorno.split(";");
            document.forms[0].identificador.value      = temp[0];
            document.forms[0].nomeUsuario.value = temp[1];
        }
    }

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