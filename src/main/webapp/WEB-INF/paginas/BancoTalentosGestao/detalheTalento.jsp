<%@ page language="java" %>

<%@ taglib prefix="bean"      uri="/tlds/struts-bean"  %>
<%@ taglib prefix="html"      uri="/tlds/struts-html"  %>
<%@ taglib prefix="logic"     uri="/tlds/struts-logic" %>

<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/biblioteca/funcoes/tooltip/domLib.js"></script>
<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/biblioteca/funcoes/tooltip/domTT.js"></script>

<jsp:include page="/WEB-INF/paginas/Comum/calendario/calendario.jsp" />
<jsp:include page="/WEB-INF/paginas/Comum/tooltip/tooltip.jsp" />

<html:form action="/avisarJavaScriptDetalheTalento">
    <html:hidden property="identificador" />
    <table class="detalhe">
        <tr class="tituloTabelaDetalhe">
            <td colspan="3" class="tituloTabelaDetalhe">
                <bean:write name="strTituloTabelaDetalhe" bundle="comum" /> &gt;
                <logic:notEmpty name="objCategoriaTalento" property="descricao" > 
				    <span onmouseover="exibirTooltip('<bean:write name="objCategoriaTalento" property="descricao"/>');" class="tooltip">
	                    <bean:write name="objCategoriaTalento" property="nome"/>
	                    <img src="../biblioteca/imagens/paginas/interrogacao.gif" border="0">
	                </span>
                </logic:notEmpty> 
                <logic:empty name="objCategoriaTalento" property="descricao" > 
                    <bean:write name="objCategoriaTalento" property="nome"/>
                </logic:empty> 
            </td>
        </tr>
        <logic:present name="lstCategoriaAtributosTalento">
            <% int intContador = 0; %>
            <logic:iterate name="lstCategoriaAtributosTalento" id="objCategoriaAtributoTalento">
                <% intContador++; %>
                <% 
                    if(intContador%2==0)
                    {
                %>
                        <tr class="trDetalhePar"> 
                <%
                    }
                    else
                    {
                %>
                        <tr class="trDetalheImpar"> 
                <%
                    }
                %>
                    <td align="right">
						<logic:present name="objCategoriaAtributoTalento" property="dicaPreenchimento">
							<span onmouseover="exibirTooltip('<bean:write name="objCategoriaAtributoTalento" property="dicaPreenchimento"/>');" class="tooltip">
								<logic:equal name="objCategoriaAtributoTalento" property="apelido" value="">
		                            <bean:write name="objCategoriaAtributoTalento" property="atributoTalento.nome"/> <img src="../biblioteca/imagens/paginas/interrogacao.gif" border="0">
								</logic:equal>
								<logic:notEqual name="objCategoriaAtributoTalento" property="apelido" value=""> 
	            	                <bean:write name="objCategoriaAtributoTalento" property="apelido"/> <img src="../biblioteca/imagens/paginas/interrogacao.gif" border="0"> 
								</logic:notEqual>
							</span>	
						</logic:present>
						<logic:notPresent name="objCategoriaAtributoTalento" property="dicaPreenchimento">
							<logic:equal name="objCategoriaAtributoTalento" property="apelido" value="">
	                            <bean:write name="objCategoriaAtributoTalento" property="atributoTalento.nome"/> 
							</logic:equal>
							<logic:notEqual name="objCategoriaAtributoTalento" property="apelido" value="">
            	                <bean:write name="objCategoriaAtributoTalento" property="apelido"/> 
							</logic:notEqual>
						</logic:notPresent>
                    </td>
                    <td align="left" width="540">
                        <bean:define id="intIdentificadorCategoriaAtributoTalento" type="java.lang.Integer" name="objCategoriaAtributoTalento" property="identificador" />
                        <% String strCategoriaAtributoTalento = "atributoTalentoValorado(" + intIdentificadorCategoriaAtributoTalento + ")"; %>
                        <% String strAtributoTalentoOpcao =  String.valueOf(intIdentificadorCategoriaAtributoTalento); %>
                        <logic:equal name="objCategoriaAtributoTalento" property="atributoTalento.tipoHTML.descricao" value="SELECT-ONE">
                            <html:select property="<%= strCategoriaAtributoTalento %>" style="width:500"  onchange="JavaScript:verificaCategoriaAtributoTalentoComFilhos(this);">
								<html:option value="">&nbsp;</html:option>
                                <logic:present name="<%= strAtributoTalentoOpcao %>">
                                    <html:options collection="<%= strAtributoTalentoOpcao %>" labelProperty="descricao" property="identificador"/>
                                </logic:present>
                            </html:select>
                        </logic:equal>
                        <logic:equal name="objCategoriaAtributoTalento" property="atributoTalento.tipoHTML.descricao" value="SELECT-MULTIPLE">
                            <html:select property="<%= strCategoriaAtributoTalento %>" style="width:500" multiple="true">
								<html:option value="">&nbsp;</html:option>
                                <logic:present name="<%= strAtributoTalentoOpcao %>">
                                    <html:options collection="<%= strAtributoTalentoOpcao %>" labelProperty="descricao" property="identificador"/>
                                </logic:present>
                            </html:select>
                        </logic:equal>
                        <logic:equal name="objCategoriaAtributoTalento" property="atributoTalento.tipoHTML.descricao" value="TEXT">
							<logic:notEqual name="objCategoriaAtributoTalento" property="atributoTalento.tipoDado" value="D">
                                <html:textarea property="<%= strCategoriaAtributoTalento %>" rows="1" cols="255" styleClass="rolagem" style="width:500" onkeypress="return desabilitarEnterLimitacao(event, this, 255);" />
							</logic:notEqual>
							<logic:equal name="objCategoriaAtributoTalento" property="atributoTalento.tipoDado" value="D">
                                <html:textarea property="<%= strCategoriaAtributoTalento %>" rows="1" cols="255" styleClass="rolagem" style="width:100" onkeyup="javascript:return DateFormat(this,event,false)" onblur="javascript:return DateFormat(this,event,true)" onkeypress="return desabilitarEnterLimitacao(event, this, 255);" />
                                <a href="#" onclick="javascript:return showCalendar('<%= strCategoriaAtributoTalento %>');"><img src="<%=request.getContextPath()%>/biblioteca/imagens/calendario/calendario.gif" title="Calendário" /></a>
							</logic:equal>
                        </logic:equal>
                        <logic:equal name="objCategoriaAtributoTalento" property="atributoTalento.tipoHTML.descricao" value="TEXTAREA">
                            <html:textarea property="<%= strCategoriaAtributoTalento %>" rows="3" style="width:500" onkeypress="return verificarLimitacao(this, 3000);"/>
                        </logic:equal>
                        <logic:equal name="objCategoriaAtributoTalento" property="atributoTalento.tipoHTML.descricao" value="CHECKBOX">
							<logic:present name="<%= strAtributoTalentoOpcao %>">
								<logic:iterate name="<%= strAtributoTalentoOpcao %>" id="objAtributoTalentoOpcao">
                                                                <bean:define id="identificadorAtributoTalentoOpcao" name="objAtributoTalentoOpcao" property="identificador" />
                                                                    <html:multibox  property="<%= strCategoriaAtributoTalento %>"  value='<%=identificadorAtributoTalentoOpcao.toString()%>'  >
                                                                    <%-- <bean:write name="objAtributoTalentoOpcao" property="identificador"/>  --%>
                                                                    </html:multibox>
								    <bean:write name="objAtributoTalentoOpcao" property="descricao" />
									<br>
								</logic:iterate>
							</logic:present>
                        </logic:equal>
                        <logic:equal name="objCategoriaAtributoTalento" property="atributoTalento.tipoHTML.descricao" value="RADIO">
							<logic:present name="<%= strAtributoTalentoOpcao %>">
								<logic:iterate name="<%= strAtributoTalentoOpcao %>" id="objAtributoTalentoOpcao">
                                	<html:radio property="<%= strCategoriaAtributoTalento %>" idName="objAtributoTalentoOpcao" value="identificador" />
									<bean:write name="objAtributoTalentoOpcao" property="descricao" />
									<br>
								</logic:iterate>
							</logic:present>
        	            </logic:equal>
						<logic:present name="objCategoriaAtributoTalento" property="descricaoPesquisa">
							<logic:equal name="objCategoriaAtributoTalento" property="atributoTalento.indicativoPesquisa" value="S">
								<a href="JavaScript:void(0);"><img src="<%=request.getContextPath()%>/biblioteca/imagens/paginas/lupa.gif" onclick="javascript:openPopUp('/' + obterContextPath() + '/BancoTalentosGestao/consultaAtributoTalentoOpcaoPrepararVisualizacao.do?categoriaAtributoTalento=<%= intIdentificadorCategoriaAtributoTalento %>', capturarAtributoTalentoOpcao, 620, 500)" onmouseover="exibirTooltip('<bean:write name="objCategoriaAtributoTalento" property="descricaoPesquisa"/>');" border="0"></a>
							</logic:equal> 
						</logic:present>
						<logic:notPresent name="objCategoriaAtributoTalento" property="descricaoPesquisa">
							<logic:equal name="objCategoriaAtributoTalento" property="atributoTalento.indicativoPesquisa" value="S">
								<a href="JavaScript:void(0);"><img src="<%=request.getContextPath()%>/biblioteca/imagens/paginas/lupa.gif" onclick="javascript:openPopUp('/' + obterContextPath() + '/BancoTalentosGestao/consultaAtributoTalentoOpcaoPrepararVisualizacao.do?categoriaAtributoTalento=<%= intIdentificadorCategoriaAtributoTalento %>', capturarAtributoTalentoOpcao, 620, 500)" onmouseover="exibirTooltip('Pesquisar');" border="0"></a>
							</logic:equal> 
						</logic:notPresent>
                        &nbsp;
                    </td>
                    <td width="2%">
                        <logic:equal name="objCategoriaAtributoTalento" property="indicativoObrigatoriedade" value="S">
                            <bean:message key="sigesp.comum.interface.campo.obrigatorio" bundle="comum" />
                        </logic:equal>
                        <logic:equal name="objCategoriaAtributoTalento" property="indicativoObrigatoriedade" value="N">
                            &nbsp;
                        </logic:equal>
                    </td>
                </tr>
            </logic:iterate>
        </logic:present>
        <tr class="trBotoes">
            <td align="center" colspan="3">
                <html:submit onclick="JavaScript:selectEmptyTeam(); avisarProcessamento(); direcionarAcaoExtendida();"><bean:message key="sigesp.comum.interface.operacao.gravar" bundle="comum" /></html:submit>
                <html:reset><bean:message key="sigesp.comum.interface.operacao.limpar" bundle="comum" /></html:reset>
            </td>
        </tr>
    </table>
</html:form>

<br><br>
<center>
    <html:link page="/talentoPrepararVisualizacao.do"><bean:message key="sigesp.comum.interface.operacao.voltar" bundle="comum" /></html:link>
</center>
<br><br>

<script language="JavaScript">

    // Armazena atributos de talento vinculados
    var strCategoriaAtributoTalentoComFilhos = '<%= request.getAttribute("strCategoriaAtributosTalentoComFilhos") %>';
    
</script>
