<%@ page language="java" %>  

<%@ taglib prefix="bean"      uri="/tlds/struts-bean"  %>
<%@ taglib prefix="html"      uri="/tlds/struts-html"  %>
<%@ taglib prefix="logic"     uri="/tlds/struts-logic" %>
<%@ taglib prefix="display"   uri="/tlds/sigesp-display" %>
<%@ taglib prefix="aa"        uri="/ajax/ajaxanywhere" %>
<%@ taglib prefix="c"         uri="/jstl/c" %>

<!-- replaced -->

<jsp:include page="/WEB-INF/paginas/Comum/calendario/calendario.jsp" />

<html:form action="/avisarJavaScriptConsultaTalento" focus="categoriaTalento">
    <% String strPreenchimentoMap = "N"; %>
    <logic:present name="strTotalPessoasComTalentos">
		    <table class="listagem">
		        <tr class="even">
		            <td class="tituloTabelaListagem" width="80%">
		                <font size="2">Total de pessoas com talentos cadastrados: <bean:write name="strTotalPessoasComTalentos" /></font>
		            </td>
		        </tr>
			</table>    
		<br>
    </logic:present>	
    <logic:present name="lstCategoriasTalentoConsulta">
        <table class="listagem">
            <tr class="tituloTabelaListagem">
                <td class="tituloTabelaListagem" width="80%">Consulta de talentos</td>
            </tr>
            
            
            <!-- Texto para consulta no Lucene   -->
            
            <tr>
                <td><center><strong>Entre com os critérios da consulta. Aqui você pode intercalar quaisquer termos para sua busca.</strong></center></td>
            </tr>
            
            <tr>
                <td>
                 <center>   <input type="text" width="500" style="width:400px;   height:25px" height="200" name="criterioConsultaLucene" id="criterioConsultaLucene" > </center>
                </td>
            </tr>
            
            
            <tr style="height: 30px ">  </tr>
                
            <tr>
                       
                <td class="tituloTabelaListagem"> ou, faça a busca pelas categorias existentes </td>
            
            </tr>
            <tr >
                <td  >  
                    <html:select property="categoriaTalento" style="width:100%" onchange="javascript:submeterAADisplayTag('aaTalentos', 'consultaTalentoPrepararConsulta.do?limpar=true', document.forms[0])">
                        <html:option value="">Selecione uma categoria</html:option>
                        <html:options collection="lstCategoriasTalentoConsulta" labelProperty="nome" property="identificador"/>
                    </html:select>
                </td>
            </tr>
            <% int intIteracao = 0; %>
            <tr>
                <td class="tituloTabelaListagem" >
                    <aa:zone name="aaTalentos" >
                        <logic:present name="lstCategoriaAtributosTalentoConsulta">
                            <table class="listagem">
                                <% String cor0 = "even"; %>
                                <logic:iterate name="lstCategoriaAtributosTalentoConsulta" id="objCategoriaAtributoTalentoConsulta">
                                    <bean:define id="intIdeCategoriaAtributoTalentoConsulta" type="java.lang.Integer" name="objCategoriaAtributoTalentoConsulta" property="identificador" />
                                    <% String strAtributoConsulta = "atributoTalentoValorado(" + intIdeCategoriaAtributoTalentoConsulta + ")"; %>
                                    <% String strTipoConsulta = "tipoConsulta(" + intIdeCategoriaAtributoTalentoConsulta + ")"; %>
                                    <% String strCriterioConsulta = "criterioConsulta(" + intIdeCategoriaAtributoTalentoConsulta + ")"; %>
                                    <% String strAtributoTalentoOpcao = String.valueOf(intIdeCategoriaAtributoTalentoConsulta);  %>
                                    <% String strCategoriaAtributosTalentoConsultaComFilhos = "verificaCategoriaAtributosTalentoConsultaComFilhos(this, '" + (String) request.getAttribute("strCategoriaAtributosTalentoConsultaComFilhos") + "');"; %>
                                    <% 
                                    if (intIteracao==0)
                                    {
                                        intIteracao = 1;
                                    %>                                    
                                        <tr>
                                            <td colspan="2" bgcolor="#F0F0F0">
                                                <logic:equal name="objCategoriaAtributoTalentoConsulta" property="categoriaTalento.indicativoUnicidade" value="N">
                                                    <bean:message key="visao.BancoTalentosGestao.interface.existenciaPreenchimento" />:
                                                    <html:select property="tipoConsulta" value="1">
                                                        <html:option value="">&nbsp;</html:option>
                                                        <html:option value="1"><bean:message key="visao.BancoTalentosGestao.interface.sim" /></html:option>
                                                        <html:option value="2"><bean:message key="visao.BancoTalentosGestao.interface.nao" /></html:option>
                                                    </html:select>
                                                </logic:equal>
                                                <logic:notEqual name="objCategoriaAtributoTalentoConsulta" property="categoriaTalento.indicativoUnicidade" value="N">
                                                    <bean:message key="visao.BancoTalentosGestao.interface.existenciaPreenchimento" />:
                                                    <html:select property="tipoConsulta" value="1">
                                                        <html:option value="1"><bean:message key="visao.BancoTalentosGestao.interface.sim" /></html:option>
                                                    </html:select>
                                                </logic:notEqual>
                                            </td>
                                            <% int intQtdeCriteriosDefinidos = 0; %>
                                            <logic:present name="mapConsultasTalento">
                                                <logic:iterate name="mapConsultasTalento" id="lstConsultasTalento">
                                                    <% intQtdeCriteriosDefinidos++; %>
                                                </logic:iterate>
                                            </logic:present>
                                            <% if (intQtdeCriteriosDefinidos > 0)
                                            {
                                            %>
                                                <td colspan="2" bgcolor="#F0F0F0">
                                                    Operador de pesquisa: 
                                                    <html:select property="criterioConsulta" value="1">
                                                        <html:option value="">&nbsp;</html:option>
                                                        <html:option value="1">E</html:option>
                                                        <html:option value="2">OU</html:option>
                                                    </html:select>
                                                </td>
                                            <%
                                            }
                                            else
                                            {
                                            %>
                                                <html:hidden property="criterioConsulta" value="1"/>
                                            <%
                                            }
                                            %>
                                        </tr>                
                                    <%
                                    }
                                    %>                                    
                                    <tr class="<%= cor0 %>">
                                        <% if ("even".equals(cor0)) cor0 = "odd"; else cor0 = "even"; %>
                                        <td colspan="4">
											<logic:equal name="objCategoriaAtributoTalentoConsulta" property="apelido" value="">
	                                            <bean:write name="objCategoriaAtributoTalentoConsulta" property="atributoTalento.nome"/>
											</logic:equal>
											<logic:notEqual name="objCategoriaAtributoTalentoConsulta" property="apelido" value="">
				            	                <bean:write name="objCategoriaAtributoTalentoConsulta" property="apelido"/> 
											</logic:notEqual>
                                        </td>
                                    </tr>
                                    <tr class="<%= cor0 %>">
                                        <% if ("even".equals(cor0)) cor0 = "odd"; else cor0 = "even"; %>
                                        <td colspan="4">
                                            <logic:equal name="objCategoriaAtributoTalentoConsulta" property="atributoTalento.tipoHTML.descricao" value="SELECT-ONE">
                                                <html:select property="<%= strAtributoConsulta %>" style="width:705" onchange="<%= strCategoriaAtributosTalentoConsultaComFilhos %>" >
                                                    <html:option value="">&nbsp;</html:option>
                                                    <logic:present name="<%= strAtributoTalentoOpcao %>">
                                                        <html:options collection="<%= strAtributoTalentoOpcao %>" labelProperty="descricao" property="identificador"/>
                                                    </logic:present>
                                                </html:select>
                                            </logic:equal>
                                            <logic:equal name="objCategoriaAtributoTalentoConsulta" property="atributoTalento.tipoHTML.descricao" value="SELECT-MULTIPLE">
                                                <html:select property="<%= strAtributoConsulta %>" style="width:705" size="10" multiple="true">
                                                    <html:option value="">&nbsp;</html:option>
                                                    <logic:present name="<%= strAtributoTalentoOpcao %>">
                                                        <html:options collection="<%= strAtributoTalentoOpcao %>" labelProperty="descricao" property="identificador"/>
                                                    </logic:present>
                                                </html:select>
                                            </logic:equal>
                                            <logic:equal name="objCategoriaAtributoTalentoConsulta" property="atributoTalento.tipoHTML.descricao" value="TEXT">
                                                <c:choose>
                                                    <c:when test="${objCategoriaAtributoTalentoConsulta.atributoTalento.tipoDado == 'D'}">
	                                                    Inicial: <html:textarea property="<%= strAtributoConsulta %>" rows="1" cols="255" styleClass="rolagem" style="width:100" onkeyup="javascript:return DateFormat(this,event,false)" onblur="javascript:return DateFormat(this,event,true)" onkeypress="return desabilitarEnterLimitacao(event, this, 255);" />
	                                                    <a href="#" onclick="javascript:return showCalendar('<%= strAtributoConsulta %>');"><img src="<%=request.getContextPath()%>/biblioteca/imagens/calendario/calendario.gif" title="Calendario" /></a>
	                                                    &nbsp;&nbsp;&nbsp;
	                                                    Final: <html:textarea property='<%= strAtributoConsulta.substring(0, strAtributoConsulta.length() - 1) + "_complementar)" %>' rows="1" cols="255" styleClass="rolagem" style="width:100" onkeyup="javascript:return DateFormat(this,event,false)" onblur="javascript:return DateFormat(this,event,true)" onkeypress="return desabilitarEnterLimitacao(event, this, 255);" />
	                                                    <a href="#" onclick="javascript:return showCalendar('<%= strAtributoConsulta.substring(0, strAtributoConsulta.length() - 1) + "_complementar)" %>');"><img src="<%=request.getContextPath()%>/biblioteca/imagens/calendario/calendario.gif" title="Calendario" /></a>
                                                    </c:when>
                                                    <c:when test="${objCategoriaAtributoTalentoConsulta.atributoTalento.tipoDado == 'N'}">
	                                                    Inicial: <html:textarea property="<%= strAtributoConsulta %>" rows="1" styleClass="rolagem" style="font-family:arial;width:150"  onkeypress="return desabilitarEnterLimitacao(event, this, 10);" />
                                                        &nbsp;&nbsp;&nbsp;
	                                                    Final: <html:textarea property='<%= strAtributoConsulta.substring(0, strAtributoConsulta.length() - 1) + "_complementar)" %>' rows="1" styleClass="rolagem" style="font-family:arial;width:150" onkeypress="return desabilitarEnterLimitacao(event, this, 10);" />
                                                    </c:when>
                                                    <c:otherwise>
                                                        <html:textarea property="<%= strAtributoConsulta %>" styleClass="rolagem" rows="1" style="font-family:arial;width:705" onkeypress="return desabilitarEnterLimitacao(event, this, 255);" />
                                                    </c:otherwise>
                                                </c:choose>
                                            </logic:equal>
                                            <logic:equal name="objCategoriaAtributoTalentoConsulta" property="atributoTalento.tipoHTML.descricao" value="TEXTAREA">
                                                <html:textarea property="<%= strAtributoConsulta %>" rows="3" style="width:705" onkeypress="return verificarLimitacao(this, 3000);"/>
                                            </logic:equal>
											<logic:equal name="objCategoriaAtributoTalentoConsulta" property="atributoTalento.tipoHTML.descricao" value="CHECKBOX">
												<logic:present name="<%= strAtributoTalentoOpcao %>">
													<logic:iterate name="<%= strAtributoTalentoOpcao %>" id="beaAtributoTalentoOpcao">

                                                                                                            <bean:define id="identificadorAtributoTalentoOpcao" name="beaAtributoTalentoOpcao" property="identificador" />
                                                                                                                <html:multibox  property="<%= strAtributoConsulta %>"  value='<%=identificadorAtributoTalentoOpcao.toString()%>'  >
                                                                                                                <%-- <bean:write name="objAtributoTalentoOpcao" property="identificador"/>  --%>
                                                                                                                </html:multibox>                                                                                            

														<bean:write name="beaAtributoTalentoOpcao" property="descricao" />
														<br>
													</logic:iterate>
												</logic:present>
											</logic:equal>
											<logic:equal name="objCategoriaAtributoTalentoConsulta" property="atributoTalento.tipoHTML.descricao" value="RADIO">
												<logic:present name="<%= strAtributoTalentoOpcao %>">
													<logic:iterate name="<%= strAtributoTalentoOpcao %>" id="objCategoriaAtributoTalentoConsultaRadio">
														<html:radio property="<%= strAtributoConsulta %>" idName="objCategoriaAtributoTalentoConsultaRadio" value="identificador" ondblclick="this.checked=false;"/>
														<bean:write name="objCategoriaAtributoTalentoConsultaRadio" property="descricao" />
														<br>
													</logic:iterate>
												</logic:present>
											</logic:equal>
											<logic:present name="objCategoriaAtributoTalentoConsulta" property="atributoTalento.descricaoPesquisa">
												<logic:equal name="objCategoriaAtributoTalentoConsulta" property="atributoTalento.indicativoPesquisa" value="S">
													<a href="JavaScript:void(0);"><img src="<%=request.getContextPath()%>/biblioteca/imagens/paginas/lupa.gif" onclick="javascript:openPopUp('<%= request.getContextPath()  %>' + '/BancoTalentosGestao/consultaAtributoTalentoOpcaoPrepararVisualizacao.do?categoriaAtributoTalento=<%= intIdeCategoriaAtributoTalentoConsulta %>', splitAtributo, 620, 500)" title="<bean:write name="objCategoriaAtributoTalentoConsulta" property="atributoTalento.descricaoPesquisa"/>" border="0"></a>
												</logic:equal> 
											</logic:present>
											<logic:notPresent name="objCategoriaAtributoTalentoConsulta" property="atributoTalento.descricaoPesquisa">
												<logic:equal name="objCategoriaAtributoTalentoConsulta" property="atributoTalento.indicativoPesquisa" value="S">
													<a href="JavaScript:void(0);"><img src="<%=request.getContextPath()%>/biblioteca/imagens/paginas/lupa.gif" onclick="javascript:openPopUp('<%= request.getContextPath()  %>' + '/BancoTalentosGestao/consultaAtributoTalentoOpcaoPrepararVisualizacao.do?categoriaAtributoTalento=<%= intIdeCategoriaAtributoTalentoConsulta  %>', splitAtributo, 620, 500)" title="Selecionar" border="0"></a>
												</logic:equal> 
											</logic:notPresent>
                		            		&nbsp;
                                        </td>
                                    </tr>
                                </logic:iterate>
                            </table>
                            <center style="background-color: white;"> 
                                <br />
                                <html:submit styleClass="botaoPrincipal" onclick="avisarProcessamento(); document.forms[0].action='consultaTalentoAdicionarCriterio.do'">Adicionar</html:submit>
                                <br />&nbsp;
                            </center>
                        </logic:present>
                    </aa:zone>
                    <script language="javascript">
                        configurarAADisplayTag('aaTalentos');
                    </script>
                </td>
            </tr>
            </table>
            
            <logic:present name="mapConsultasTalento">
                	<% int intContadorConsultas = 0; %>
                    <logic:iterate name="mapConsultasTalento" id="objMapConsultaTalento">
                        <bean:define id="objConsultaTalento" name="objMapConsultaTalento" property="value" />
                        <br>
                        <% strPreenchimentoMap = "S";
                           intContadorConsultas++; %>
                        <bean:define id="strKey" type="java.lang.String" name="objMapConsultaTalento" property="key" />
                        <table class="listagem">
                            <tr>
                                <% if (intContadorConsultas != 1) {%>
                                    <td width="5%" align="center">
                                        <bean:write name="objConsultaTalento" property="criterioConsultaTalento.descricao" />
                                    </td>
	                                <td width="15%"align="center">
	                                    <bean:write name="objConsultaTalento" property="tipoConsultaTalento.descricao" />
	                                </td>
                                <% }else{%>         
	                                <td width="20%"align="center" colspan="2">
	                                    <bean:write name="objConsultaTalento" property="tipoConsultaTalento.descricao" />
	                                </td>
                                <% } %>         
                                <td class="tituloTabelaListagem" colspan="2">
                                    <% String strDesCategoria = "objCategoriaTalento" + strKey; %>
					   				<bean:write name="<%= strDesCategoria %>" property="nome" />
                                </td>
                                <td width="5%"align="right">
                                    <a href="JavaScript:avisarProcessamento(); document.forms[0].action='consultaTalentoRemoverCriterio.do?key=<%= strKey %>'; document.forms[0].submit();"><bean:message key="sigesp.comum.interface.operacao.excluir" bundle="comum" />&nbsp;&nbsp;</a>
                                </td>
                            </tr>

                            <c:if test="${not empty objConsultaTalento.listaParametrosConsulta}">
	                            <% String cor = "even"; %>
	                            <% int intContadorCriterios = 0; %>
	                            <% Integer intAtributoAnterior = null; %>
	                            <logic:iterate name="objConsultaTalento" property="listaParametrosConsulta" id="objParametroConsulta">
	                                <% intContadorCriterios++; %>
	                                <bean:define id="intAtributoAtual" type="java.lang.Integer" name="objParametroConsulta" property="categoriaAtributoTalento.identificador" />
	                                <% if (!intAtributoAtual.equals(intAtributoAnterior)) { %>
	                                    <% if ("even".equals(cor)) cor = "odd"; else cor = "even"; %>
                                        <% if (intAtributoAnterior != null) { %>
                                                </td>
                                            </tr>    
                                        <% } %>
	                                    <tr class="<%= cor %>">
                                            <td colspan="2">
                                                &nbsp;
                                            </td>
	                                        <td width="40%">
	                                            <logic:equal name="objParametroConsulta" property="categoriaAtributoTalento.apelido" value="" >
	                                                <bean:write name="objParametroConsulta" property="categoriaAtributoTalento.atributoTalento.nome" />
	                                            </logic:equal>
	                                            <logic:notEqual name="objParametroConsulta" property="categoriaAtributoTalento.apelido" value="" >
	                                                <bean:write name="objParametroConsulta" property="categoriaAtributoTalento.apelido" />
	                                            </logic:notEqual>
	                                        </td>
	                                        <td colspan="2">
                                    <% } %>
                                    <c:choose>
                                        <c:when test="${(objParametroConsulta.categoriaAtributoTalento.atributoTalento.tipoDado == 'D' || objParametroConsulta.categoriaAtributoTalento.atributoTalento.tipoDado == 'N') && objParametroConsulta.opcaoComplementar != null}">
		                                    <c:choose>
		                                        <c:when test="${not empty objParametroConsulta.atributoTalentoOpcao.descricao && not empty objParametroConsulta.opcaoComplementar}">
                                                    <bean:write name="objParametroConsulta" property="atributoTalentoOpcao.descricao" />
                                                    a <bean:write name="objParametroConsulta" property="opcaoComplementar" /> 
		                                        </c:when>
                                                <c:when test="${empty objParametroConsulta.atributoTalentoOpcao.descricao && not empty objParametroConsulta.opcaoComplementar}">
                                                    <= <bean:write name="objParametroConsulta" property="opcaoComplementar" /> 
                                                </c:when>
                                                <c:when test="${not empty objParametroConsulta.atributoTalentoOpcao.descricao && empty objParametroConsulta.opcaoComplementar}">
                                                    >= <bean:write name="objParametroConsulta" property="atributoTalentoOpcao.descricao" /> 
                                                </c:when>
		                                    </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            <bean:write name="objParametroConsulta" property="atributoTalentoOpcao.descricao" />
                                        </c:otherwise>
                                    </c:choose><br/>
	                                <bean:define id="intAtributoAnteriorTemp" type="java.lang.Integer" name="objParametroConsulta" property="categoriaAtributoTalento.identificador" />
	                                <% intAtributoAnterior = intAtributoAnteriorTemp; %>
	                            </logic:iterate>
                                <% if (intContadorCriterios > 0) { %>
                                        </td>
                                    </tr>    
                                <% } %>
                            </c:if>

                        </table>
                    </logic:iterate>
                    <% //if ("S".equals(strPreenchimentoMap)) { %>
                        <br>
                        <center> 
                        <html:submit styleClass="botaoPrincipal" onclick="avisarProcessamento(); document.forms[0].action='resultadoConsultaTalentoEfetuarConsulta.do'">Consultar</html:submit>
                        </center>
                    <% //} %>
            </logic:present>
    </logic:present>
</html:form>

<p class="voltar">
    <input type="button" class="botaoPrincipal" onclick="javascript:voltarPrincipal();" value="Voltar" bundle="comum"/>
    <!--<a href="indexBancoTalentosGestao.do"><bean:message key="sigesp.comum.interface.operacao.voltar" bundle="comum" /></a>-->
</p>
<br>
<script src="<%=request.getContextPath()%>/biblioteca/funcoes/validaData.js">
</script>

<script type="text/javascript" language="JavaScript">

    function verificaCategoriaAtributosTalentoConsultaComFilhos(campo, strCategoriaAtributosTalentoConsultaComFilhos)
    {
        if (strCategoriaAtributosTalentoConsultaComFilhos.indexOf(campo.name.substring(24, campo.name.length - 1) + ",") >=0 )
        {
            var strIdeAtributoConsulta = campo.name.substring(24, campo.name.length - 1);
            var strIdeAtributoTalentoOpcao = campo.value;
            document.forms[0].action = 'consultaTalentoBuscarOpcao.do?categoriaAtributoTalento=' + strIdeAtributoConsulta +
                    '&atributoTalentoOpcao=' + strIdeAtributoTalentoOpcao;
            avisarProcessamento();
            document.forms[0].submit();
        }
    }

    function voltarPrincipal()
    {    
       
			document.forms[0].action = 'indexBancoTalentosGestao.do';
			document.forms[0].submit();
            
			
        
    }
    function splitAtributo(retorno)
    {
        if (retorno != '')
        {
			avisarProcessamento();
            document.forms[0].action = 'consultaTalentoBuscarHierarquia.do?atributoTalentoOpcao=' + retorno;
			document.forms[0].submit();


        }
    }
    
</script>
