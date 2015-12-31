<%@ page language="java" %>

<%@ taglib prefix="bean"      uri="/tlds/struts-bean"  %>
<%@ taglib prefix="html"      uri="/tlds/struts-html"  %>
<%@ taglib prefix="logic"     uri="/tlds/struts-logic" %>

<!-- html:javascript/ -->

<!-- replaced -->


<html:form action="/avisarJavaScriptOrdenacaoCategoriaAtributoTalento" onsubmit="return submeterForm()" focus="sequencialPreenchimento">
    <table class="detalhe">
        <tr class="tituloTabelaDetalhe">
            <td class="tituloTabelaDetalhe" colspan="3">
                <bean:write name="strTituloTabelaDetalhe" ignore="true" />&nbsp;
            </td>
        </tr>
        <tr class="trDetalheImpar">
            <td align="right">
				<span title="<bean:message key="visao.BancoTalentosApoio.interface.sequencialPreenchimento.dicapreenchimento" />" class="tooltip">
	                <bean:message key="visao.BancoTalentosApoio.interface.sequencialPreenchimento" />
				</span>
            </td>
            <td align="left">
                <table width="5%">
                    <tr rowspan="2" border="1">
                        <td rowspan="3">
                            <html:select property="sequencialPreenchimento" multiple="true" style="width:490" size="10">
                                <logic:present name="lstCategoriaAtributosTalento">
                                    <html:options collection="lstCategoriaAtributosTalento" labelProperty="atributoTalento.nome" property="identificador"/>
                                </logic:present>
                            </html:select>
                        </td>
                        <td>
                            <img src="../biblioteca/imagens/setas/ocultar.gif" border=0 onclick="ordenar(0, 'sequencialPreenchimento');" title="Mover para cima">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <img src="../biblioteca/imagens/setas/exibir.gif" border=0 onclick="ordenar(1, 'sequencialPreenchimento');" title="Mover para baixo">
                        </td>
                    </tr>
                </table>
            </td>
            <td align="center">
                <bean:message key="sigesp.comum.interface.campo.obrigatorio" bundle="comum" />
            </td>
        </tr> 
        <tr class="trBotoes">
            <td align="center" colspan="3">
                <html:submit onclick="JavaScript:return selectTeam('É necessário selecionar um item', 'sequencialPreenchimento'); avisarProcessamento(); direcionarAcao('');"><bean:message key="sigesp.comum.interface.operacao.gravar" bundle="comum" /></html:submit>
            </td>
        </tr>
    </table>
</html:form>

<p class="voltar">
    <html:link href="javascript:history.back(1);"><bean:message key="sigesp.comum.interface.operacao.voltar" bundle="comum" /></html:link>
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
