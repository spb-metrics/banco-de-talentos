<%@ page language="java" %>

<%@ taglib prefix="bean"      uri="/tlds/struts-bean"  %>
<%@ taglib prefix="html"      uri="/tlds/struts-html"  %>
<%@ taglib prefix="logic"     uri="/tlds/struts-logic" %>

<jsp:include page="/WEB-INF/paginas/Comum/calendario/calendario.jsp" />

<!-- replaced -->
<br>
<html:form action="/avisarJavaScriptConsultaEstatisticaCadastro" onsubmit="return submeterForm()" focus="dataInicio">
    <table class="listagem">
        <tr class="tituloTabelaListagem">
            <td class="tituloTabelaListagem" colspan="3">
                <bean:write name="strTituloTabelaDetalhe" ignore="true" />&nbsp;
            </td>
        </tr>
        <tr class="trDetalheImpar">
            <td align="right" width="48%">
                <span title="<bean:message key="visao.BancoTalentosGestao.interface.dataInicio.dicapreenchimento" />" class="dicapreenchimento">
                    <bean:message key="visao.BancoTalentosGestao.interface.dataInicio" />
                </span>
            </td>
            <td align="left" width="48%">
                <html:text property="dataInicio" size="10" onkeydown="return DateFormat(this,event,false,3)" onblur="return DateFormat(this,event,false,3)" />
                <img src="<%=request.getContextPath()%>/biblioteca/imagens/calendario/calendario.gif" onclick="return showCalendar('dataInicio');" title="Calendário">
            </td>
            <td align="center" width="*">
                &nbsp;
            </td>
        </tr>
        <tr class="trDetalhePar">
            <td align="right" width="48%">
                <span title="<bean:message key="visao.BancoTalentosGestao.interface.dataTermino.dicapreenchimento" />" class="dicapreenchimento">
                    <bean:message key="visao.BancoTalentosGestao.interface.dataTermino" />
                </span>
            </td>
            <td align="left" width="48%">
                <html:text property="dataTermino" size="10" onkeydown="return DateFormat(this,event,false,3)" onblur="return DateFormat(this,event,false,3)" />
                <img src="<%=request.getContextPath()%>/biblioteca/imagens/calendario/calendario.gif" onclick="return showCalendar('dataTermino');" title="Calendário">
            </td>
            <td align="center" width="*">
                &nbsp;
            </td>
        </tr>
        <tr class="trDetalheImpar">
            <td align="right" width="48%">
                <span title="<bean:message key="visao.BancoTalentosGestao.interface.quebrasDisponiveis.dicapreenchimento" />" class="dicapreenchimento">
                    <bean:message key="visao.BancoTalentosGestao.interface.quebrasDisponiveis" />
                </span>
            </td>
            <td align="left" width="48%">
                <table width="5%">
                    <tr rowspan="2" border="1">
                        <td rowspan="3">
                            <html:select property="quebrasDisponiveis" multiple="true" style="width:400">
                                <html:option value="grupo">Grupo</html:option>
                                <html:option value="categoria">Categoria</html:option>
                                <html:option value="data">Data</html:option>
                            </html:select>
                        </td>
                        <td>
              				&nbsp
                        </td>
                        <td>
                            <a href="JavaScript:void(0);"><img src=../biblioteca/imagens/setas/icon_para_baixo.gif border=0 onClick="moveIt('quebrasDisponiveis', 'quebrasSelecionadas');" title="Adicionar"></a>
                        </td>                
                    </tr>
                </table>
            </td>
            <td align="center" width="*">
                &nbsp;
            </td>
        </tr>
        <tr class="trDetalhePar">
            <td align="right" width="48%">
                <span title="<bean:message key="visao.BancoTalentosGestao.interface.quebrasSelecionadas.dicapreenchimento" />" class="dicapreenchimento">
                    <bean:message key="visao.BancoTalentosGestao.interface.quebrasSelecionadas" />
                </span>
            </td>
            <td align="left" width="48%">
                <table width="5%">
                    <tr rowspan="2" border="1">
                        <td rowspan="3">
                            <html:select property="quebrasSelecionadas" multiple="true" style="width:400">
                            </html:select>
                        </td>
                        <td>
                        	<table align="left" width="48%">
                        		<tr>
                        			<td>
										<img src="../biblioteca/imagens/setas/ocultar.gif" border=0 onclick="ordenar(0, 'quebrasSelecionadas');" title="Mover para cima">                        	
									<td>
								</tr>
                        		<tr>
                        			<td>
			                            <img src="../biblioteca/imagens/setas/exibir.gif" border=0 onclick="ordenar(1, 'quebrasSelecionadas');" title="Mover para baixo">
									<td>
								</tr>
							</table>
                        </td>
                        <td>
                            <a href="JavaScript:void(0);"><img src=../biblioteca/imagens/setas/icon_para_cima.gif border=0 onclick="moveIt('quebrasSelecionadas', 'quebrasDisponiveis');" title="Remover"></a>
                        </td>                
                    </tr>
                </table>
            </td>
            <td align="center" width="*">
                &nbsp;
            </td>
        </tr>
        <tr class="trBotoes">
            <td align="center" colspan="3">
                <html:submit>Consultar</html:submit>
            </td>
        </tr>
    </table>
</html:form>

<p class="voltar">
    <a href="indexBancoTalentosGestao.do"><bean:message key="sigesp.comum.interface.operacao.voltar" bundle="comum" /></a>
</p>

<script language="javascript">
    <!--
    function submeterForm()
    {
        var retorno;
        avisarProcessamento(true);
        selectTeam(null, 'quebrasSelecionadas');
        direcionarAcao('');
        retorno = true;
        return retorno;
    }
    -->
</script>
