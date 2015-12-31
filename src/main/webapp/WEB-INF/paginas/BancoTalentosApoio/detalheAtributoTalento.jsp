<%@ page language="java" %>

<%@ taglib prefix="bean"      uri="/tlds/struts-bean"  %>
<%@ taglib prefix="html"      uri="/tlds/struts-html"  %>
<%@ taglib prefix="logic"     uri="/tlds/struts-logic" %>

<!-- html:javascript/ -->

<!-- replaced -->


<html:form action="/avisarJavaScriptDetalheAtributoTalento" onsubmit="return submeterForm()" focus="atributoTalentoPai">

    <html:hidden property="identificador" />
    <table class="detalhe">
        <tr class="tituloTabelaDetalhe">
            <td class="tituloTabelaDetalhe" colspan="3">
                <bean:write name="strTituloTabelaDetalhe" ignore="true" />&nbsp;
            </td>
        </tr>
        <tr class="trDetalheImpar">
            <td align="right" width="48%">
                <span title="<bean:message key="visao.BancoTalentosApoio.interface.atributoTalentoPai.dicapreenchimento" />" class="dicapreenchimento">
                    <bean:message key="visao.BancoTalentosApoio.interface.atributoTalentoPai" />
                </span>
            </td>
            <td align="left" width="48%">
                <html:select property="atributoTalentoPai" style="width=480">
                	<html:option value="">&nbsp;</html:option>
                    <logic:present name="lstCandidatosAPai">
                        <html:options collection="lstCandidatosAPai" labelProperty="nome" property="identificador"/>
                    </logic:present>
                </html:select>
            </td>
            <td align="center" width="*">
                &nbsp;
            </td>
        </tr>
        <tr class="trDetalhePar">
            <td align="right" width="48%">
                <span title="<bean:message key="visao.BancoTalentosApoio.interface.nome.dicapreenchimento" />" class="dicapreenchimento">
                    <bean:message key="visao.BancoTalentosApoio.interface.nome" />
                </span>
            </td>
            <td align="left" width="48%">
                <html:text property="nome" size="57"/>
            </td>
            <td align="center" width="*">
                <bean:message key="sigesp.comum.interface.campo.obrigatorio" bundle="comum" />
            </td>
        </tr>
        <tr class="trDetalheImpar">
            <td align="right" width="48%">
                <span title="<bean:message key="visao.BancoTalentosApoio.interface.tipoHtml.dicapreenchimento" />" class="dicapreenchimento">
                    <bean:message key="visao.BancoTalentosApoio.interface.tipoHtml" />
                </span>
            </td>
            <td align="left" width="48%">
                <html:select property="tipoHTML" style="width=480">
                	<html:option value="">&nbsp;</html:option>
                    <logic:present name="lstTiposHTML">
                        <html:options collection="lstTiposHTML" labelProperty="descricao" property="identificador"/>
                    </logic:present>
                </html:select>
            </td>
            <td align="center" width="*">
                <bean:message key="sigesp.comum.interface.campo.obrigatorio" bundle="comum" />
            </td>
        </tr>
        <tr class="trDetalhePar">
            <td align="right" width="48%">
                <span title="<bean:message key="visao.BancoTalentosApoio.interface.mascara.dicapreenchimento" />" class="dicapreenchimento">
                    <bean:message key="visao.BancoTalentosApoio.interface.mascara" />
                </span>
            </td>
            <td align="left" width="48%">
                <html:text property="mascara" size="57"/>
            </td>
            <td align="center" width="*">
                &nbsp;
            </td>
        </tr>
<!--    <tr class="trDetalheImpar"> -->
<!--        <td align="right" width="48%"> -->
<!--            <span title="<bean:message key="visao.BancoTalentosApoio.interface.tabelaApoioMM.dicapreenchimento" />" class="dicapreenchimento"> -->
<!--                <bean:message key="visao.BancoTalentosApoio.interface.tabelaApoioMM" /> -->
<!--            </span> -->
<!--        </td> -->
<!--        <td align="left" width="48%"> -->
<!--            <html:select property="tabelaApoioMM" style="width=480"> -->
<!--            	<html:option value="">&nbsp;</html:option> -->
<!--                <logic:present name="lstTabelasApoioMM"> -->
<!--                    <html:options collection="lstTabelasApoioMM" labelProperty="nomeTabela" property="identificador"/> -->
<!--                </logic:present> -->
<!--            </html:select> -->
<!--        </td> -->
<!--        <td align="center" width="*"> -->
<!--            &nbsp; -->
<!--        </td> -->
<!--    </tr> -->
        <tr class="trDetalheImpar">
            <td align="right" width="48%">
                <span title="<bean:message key="visao.BancoTalentosApoio.interface.indicativoPesquisa.dicapreenchimento" />" class="dicapreenchimento">
                    <bean:message key="visao.BancoTalentosApoio.interface.indicativoPesquisa" />
                </span>
            </td>
            <td align="left" width="48%">
                <html:checkbox property="indicativoPesquisa" />
            </td>
            <td align="center" width="*">
                &nbsp;
            </td>
        </tr>
        <tr class="trDetalhePar">
            <td align="right" width="48%">
                <span title="<bean:message key="visao.BancoTalentosApoio.interface.descricaoPesquisa.dicapreenchimento" />" class="dicapreenchimento">
                    <bean:message key="visao.BancoTalentosApoio.interface.descricaoPesquisa" />
                </span>
            </td>
            <td align="left" width="48%">
                <html:text property="descricaoPesquisa" size="57"/>
            </td>
            <td align="center" width="*">
                &nbsp;
            </td>
        </tr>
        <tr class="trDetalheImpar">
            <td align="right" width="48%">
                <span title="<bean:message key="visao.BancoTalentosApoio.interface.tipoDado.dicapreenchimento" />" class="dicapreenchimento">
                    <bean:message key="visao.BancoTalentosApoio.interface.tipoDado" />
                </span>
            </td>
            <td align="left" width="48%">
                <html:select property="tipoDado" style="width=480">
                	<html:option value="">&nbsp;</html:option>
                    <logic:present name="lstTiposDado">
                        <html:options collection="lstTiposDado" labelProperty="descricao" property="identificador"/>
                    </logic:present>
				</html:select>                    
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
	<html:link page="/atributoTalentoPrepararVisualizacao.do"><bean:message key="sigesp.comum.interface.operacao.voltar" bundle="comum"/></html:link>
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
