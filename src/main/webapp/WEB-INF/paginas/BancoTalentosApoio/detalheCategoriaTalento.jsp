<%@ page language="java" %>

<%@ taglib prefix="bean"      uri="/tlds/struts-bean"  %>
<%@ taglib prefix="html"      uri="/tlds/struts-html"  %>
<%@ taglib prefix="logic"     uri="/tlds/struts-logic" %>
<!-- %@ taglib prefix="sigesp"     uri="/tlds/sigesp-customtag" % -->
<!-- %@ taglib prefix="display"   uri="/tlds/sigesp-display" % -->

<!-- html:javascript/ -->

<jsp:include page="/WEB-INF/paginas/Comum/tooltip/tooltip.jsp" />


<html:form action="/avisarJavaScriptDetalheCategoriaTalento" onsubmit="return submeterForm()" focus="nome">

    <html:hidden property="identificador" />

    <table class="detalhe">
        <tr class="tituloTabelaDetalhe">
            <td class="tituloTabelaDetalhe" colspan="3">
                <bean:write name="strTituloTabelaDetalhe" ignore="true" />&nbsp;
            </td>
        </tr>
        <tr class="trDetalheImpar">
            <td align="right" width="48%">
                <span title="<bean:message key="visao.BancoTalentosApoio.interface.nome.dicapreenchimento" />" class="dicapreenchimento">
                    <bean:message key="visao.BancoTalentosApoio.interface.nome" />
                </span>
            </td>
            <td align="left" width="48%">
                <html:text property="nome" size="78"/>
            </td>
            <td align="center" width="*">
                <bean:message key="sigesp.comum.interface.campo.obrigatorio" bundle="comum" />
            </td>
        </tr>
        <tr class="trDetalhePar">
            <td align="right" width="48%">
                <span title="<bean:message key="visao.BancoTalentosApoio.interface.descricao.dicapreenchimento" />" class="dicapreenchimento">
                    <bean:message key="visao.BancoTalentosApoio.interface.descricao" />
                </span>
            </td>
            <td align="left" width="48%">
                <html:textarea property="descricao" rows="3" cols="255" styleClass="rolagem" style="width:485" onkeypress="return desabilitarEnterLimitacao(event, this, 255);"/>
            </td>
            <td align="center" width="*">
                &nbsp;
            </td>
        </tr>
        <tr class="trDetalheImpar">
            <td align="right" width="48%">
                <span title="<bean:message key="visao.BancoTalentosApoio.interface.dicaPreenchimento.dicapreenchimento" />" class="dicapreenchimento">
                    <bean:message key="visao.BancoTalentosApoio.interface.dicaPreenchimento" />
                </span>
            </td>
            <td align="left" width="48%">
                <html:textarea property="dicaPreenchimento" rows="3" cols="255" styleClass="rolagem" style="width:485" onkeypress="return desabilitarEnterLimitacao(event, this, 255);"/>
            </td>
            <td align="center" width="*">
                &nbsp;
            </td>
        </tr>
        <tr class="trDetalhePar">
            <td align="right" width="48%">
                <span title="<bean:message key="visao.BancoTalentosApoio.interface.indicativoUnicidade.dicapreenchimento" />" class="dicapreenchimento">
                    <bean:message key="visao.BancoTalentosApoio.interface.indicativoUnicidade" />
                </span>
            </td>
            <td align="left" width="48%">
                <html:checkbox property="indicativoUnicidade" />
            </td>
            <td align="center" width="*">
                &nbsp;
            </td>
        </tr>
        <tr class="trDetalheImpar">
            <td align="right">
				<span onmouseover="exibirTooltip('<bean:message key="visao.BancoTalentosApoio.interface.grupoOrigem.dicapreenchimento" />');" class="tooltip">
                    <bean:message key="visao.BancoTalentosApoio.interface.grupoOrigem" /> <img src="../biblioteca/imagens/paginas/interrogacao.gif" border="0">
				</span>
            </td>
            <td align="left">
                <table width="5%">
                    <tr rowspan="2" border="1">
                        <td rowspan="3">
                            <html:select property="grupoOrigem" multiple="true" style="width:480">
                                <logic:present name="lstGruposDisponiveis">
                                    <html:options collection="lstGruposDisponiveis" labelProperty="descricao" property="codigo"/>
                                </logic:present>
                            </html:select>
                        </td>
                        <td>
                            <a href="JavaScript:void(0);"><img src=../biblioteca/imagens/setas/icon_para_baixo.gif border=0 onClick="moveIt('grupoOrigem', 'grupoDestino');" title="Adicionar"></a>
                        </td>                
                    </tr>
                </table>
            </td>
            <td align="center">&nbsp;
                                    
            </td>
        </tr>                                    
        <tr class="trDetalhePar">
            <td align="right">
				<span onmouseover="exibirTooltip('<bean:message key="visao.BancoTalentosApoio.interface.grupoDestino.dicapreenchimento" />');" class="tooltip">
                    <bean:message key="visao.BancoTalentosApoio.interface.grupoDestino" /> <img src="../biblioteca/imagens/paginas/interrogacao.gif" border="0">
				</span>
            </td>
            <td align="left">
                <table width="5%">
                    <tr rowspan="2" border="1">
                        <td rowspan="3">
                            <html:select property="grupoDestino" multiple="true" style="width:480">
                                <logic:present name="lstGruposSelecionados">
                                    <html:options collection="lstGruposSelecionados" labelProperty="descricao" property="codigo"/>
                                </logic:present>
                            </html:select>
                        </td>
                        <td>
                            <a href="JavaScript:void(0);"><img src=../biblioteca/imagens/setas/icon_para_cima.gif border=0 onclick="moveIt('grupoDestino', 'grupoOrigem');" title="Remover"></a>
                        </td>                
                    </tr>
                </table>
            </td>
            <td align="center">
                &nbsp;
            </td>
        </tr>
        <tr class="trBotoes">
            <td align="center" colspan="3">
                <html:submit onclick="JavaScript:avisarProcessamento(); selectTeam(null, 'grupoDestino');direcionarAcao('');"><bean:message key="sigesp.comum.interface.operacao.gravar" bundle="comum" /></html:submit>
                <html:reset><bean:message key="sigesp.comum.interface.operacao.limpar" bundle="comum" /></html:reset>
            </td>
        </tr>
    </table>
</html:form>

<p class="voltar">
		<html:link page="/categoriaTalentoPrepararVisualizacao.do"><bean:message key="sigesp.comum.interface.operacao.voltar" bundle="comum"/></html:link>
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
