<%@ page language="java" %>

<%@ taglib prefix="bean"      uri="/tlds/struts-bean"  %>
<%@ taglib prefix="html"      uri="/tlds/struts-html"  %>
<%@ taglib prefix="logic"     uri="/tlds/struts-logic" %>

<jsp:include page="/WEB-INF/paginas/Comum/tooltip/tooltip.jsp" />

<html:form action="/avisarJavaScriptDetalheFiltroConsulta" onsubmit="return submeterForm()" focus="nomeFiltroConsulta">

    <html:hidden property="identificador" />

    <table class="detalhe">
        <tr class="tituloTabelaDetalhe">
            <td class="tituloTabelaDetalhe" colspan="3">
                <font size="3"><bean:write name="strTituloTabelaDetalhe" ignore="true" />&nbsp;</font>
            </td>
        </tr>
        <tr class="trDetalheImpar">
            <td align="right" width="48%">
                <span title="<bean:message key="visao.BancoTalentosApoio.interface.nomeFiltroConsulta.dicapreenchimento" />" class="dicapreenchimento">
                    <bean:message key="visao.BancoTalentosApoio.interface.nomeFiltroConsulta" />
                </span>
            </td>
            <td align="left" width="48%">
                <html:text property="nomeFiltroConsulta" size="78"/>
            </td>
            <td align="center" width="*">
                <bean:message key="sigesp.comum.interface.campo.obrigatorio" bundle="comum" />
            </td>
        </tr>
        <tr class="trDetalhePar">
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
        <tr class="trDetalheImpar">
            <td align="right">
				<span onmouseover="exibirTooltip('<bean:message key="visao.BancoTalentosApoio.interface.grupoDestino.dicapreenchimento" />');" class="tooltip">
                    <!-- bean:message key="visao.BancoTalentosApoio.interface.grupoDestino" /-->Grupos que serão apresentados como resultado de uma pesquisa para este filtro de consulta <img src="../biblioteca/imagens/paginas/interrogacao.gif" border="0">
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
                <bean:message key="sigesp.comum.interface.campo.obrigatorio" bundle="comum" />
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
		<html:link page="/filtroConsultaPrepararVisualizacao.do"><bean:message key="sigesp.comum.interface.operacao.voltar" bundle="comum"/></html:link>
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