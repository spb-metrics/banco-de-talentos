<%@ page language="java" %>
<%@ taglib prefix="bean"      uri="/tlds/struts-bean"    %>
<%@ taglib prefix="html"      uri="/tlds/struts-html"    %>
<%@ taglib prefix="logic"     uri="/tlds/struts-logic"   %>
<%@ taglib prefix="sigesp"     uri="/tlds/sigesp-customtag"  %>

<style>table.listagem {background-color:#f0f0f0;}</style>
<logic:present name="objPessoa">
    <logic:empty name="objPessoa">
        <br>
        <center>
            <h3>
                Não há dados para a exibição do currículo
            </h3>
        </center>
    </logic:empty>
    <logic:notEmpty name="objPessoa">
			<p align="center">
                <html:button property="imprimir" onclick='imprimir();'>Imprimir</html:button>
			</p>
            <table class="listagem">
                <tr class="tituloTabelaListagem">
                    <td colspan="2" width="100%" class="tituloTabelaListagem"><font size="3">Dados pessoais</font></td>
                </tr>
                <tr class="odd">
                    <td width="28%" class="tableCell">Ponto:</td>
                    <td width="72%" class="tableCell"><bean:write name="objPessoa" property="identificador" />&nbsp;</td>
                </tr>
                <tr class="even">
                    <td width="28%" class="tableCell">Nome:</td>
                    <td width="72%" class="tableCell"><bean:write name="objPessoa" property="nome" />&nbsp;</td>
                </tr>
                <tr class="odd">
                    <td width="28%" class="tableCell">Data de nascimento:</td>
                    <td width="72%" class="tableCell"><bean:write name="objPessoa" property="dataNascimentoFormatada" />&nbsp;</td>
                </tr>
                <tr class="even">
                    <td width="28%" class="tableCell">Sexo:</td>
                    <td width="72%" class="tableCell"><bean:write name="objPessoa" property="sexo" />&nbsp;</td>
                </tr>
                <tr class="odd">
                    <td width="28%" class="tableCell">CPF:</td>
                    <td width="72%" class="tableCell"><bean:write name="objPessoa" property="cpf" />&nbsp;</td>
                </tr>
                <tr class="even">
                    <td width="28%" class="tableCell">RG:</td>
                    <td width="72%" class="tableCell"><bean:write name="objPessoa" property="rg" />&nbsp;</td>
                </tr>
                <tr height="1">
                    <td colspan="2" height="1" >
                        &nbsp;
                    </td>
                </tr>
            </table>
        	<br>
    </logic:notEmpty>
</logic:present>

<logic:present name="lstLotacoes">
    <logic:notEmpty name="lstLotacoes">
            <table class="listagem">
                <tr class="tituloTabelaListagem">
                    <td colspan="2" width="100%" class="tituloTabelaListagem"><font size="3">Lotação</font></td>
                </tr>
                <logic:iterate name="lstLotacoes" id="objLotacao">
                    <tr class="odd">
                        <td width="28%" class="tableCell">Lota&ccedil;&atilde;o:</td>
                        <td width="72%" class="tableCell"><bean:write name="objLotacao" property="descricao" />&nbsp;</td>
                    </tr>
                    <tr class="even">
                        <td width="28%" class="tableCell">Data in&iacute;cio:</td>
                        <td width="72%" class="tableCell"><bean:write name="objLotacao" property="dataInicioFormatada" />&nbsp;</td>
                    </tr>
                    <tr class="odd">
                        <td width="28%" class="tableCell">Data fim:</td>
                        <td width="72%" class="tableCell"><bean:write name="objLotacao" property="dataTerminoFormatada" />&nbsp;</td>
                    </tr>
                    <tr height="1">
                        <td colspan="2" height="1" >
                            &nbsp;
                        </td>
                    </tr>
                </logic:iterate>
            </table> 
    </logic:notEmpty>
    <br>
</logic:present>

<logic:present name="lstTalentos">
    <center>
        <table class="listagem">
            <bean:define id="strCategoriaAnterior" type="java.lang.String" value="" />
    		<bean:define id="strCategoriaAtributoAnterior" type="java.lang.String" value="" />
            <% String strValoracao = ""; %>
            <logic:iterate name="lstTalentos" id="objTalento">
                <logic:notEqual name="objTalento" property="categoriaTalento.identificador" value="<%= strCategoriaAnterior %>">
                    <% if (!"".equals(strCategoriaAnterior)) { %>
                        </table>
                        </center>
                        <br>
                        <center>
                        <table class="listagem">
                    <% } %>
                    <tr class="tituloTabelaListagem">
                        <td colspan="2" width="100%" class="tituloTabelaListagem"><font size="3"><bean:write name="objTalento" property="categoriaTalento.nome" /></font></td>
                    </tr>        
                </logic:notEqual>
				<tr class="odd">
					<td width="28%" class="tableCell">Data de lan&ccedil;amento:</td>
					<td width="72%" class="tableCell"><bean:write name="objTalento" property="dataLancamentoFormatada" />&nbsp;</td>
				</tr>
				<bean:define id="intAtributoTalentoValorado" type="java.lang.Integer" name="objTalento" property="identificador" />
				<% String lstAtributoTalentoValorados = String.valueOf(intAtributoTalentoValorado); %>
				<logic:present name="<%= lstAtributoTalentoValorados %>">
					<bean:define id="estilo" type="java.lang.String" value="odd"/>
					<logic:iterate name="<%= lstAtributoTalentoValorados %>" id="objAtributoTalentoValorado">           
                        <% if ("even".equals(estilo)) estilo = "odd"; else estilo = "even";%>
						<logic:notEqual name="objAtributoTalentoValorado" property="categoriaAtributoTalento.identificador" value="<%= strCategoriaAtributoAnterior %>">
							<logic:equal name="objAtributoTalentoValorado" property="valoracao" value="Sim">
								<tr class="<%= estilo %>">
									<td colspan="2" class="tableCell">
										<logic:equal name="objAtributoTalentoValorado" property="categoriaAtributoTalento.apelido" value="">
											<bean:write name="objAtributoTalentoValorado" property="categoriaAtributoTalento.atributoTalento.nome" />
										</logic:equal>
										<logic:notEqual name="objAtributoTalentoValorado" property="categoriaAtributoTalento.apelido" value="">
			            	                <bean:write name="objAtributoTalentoValorado" property="categoriaAtributoTalento.apelido"/> 
										</logic:notEqual>
									</td>
								</tr>           
							</logic:equal>
							<logic:notEqual name="objAtributoTalentoValorado" property="valoracao" value="Sim">
								<logic:notEqual name="objAtributoTalentoValorado" property="valoracao" value="Não">
									<tr class="<%= estilo %>">
										<td width="28%" class="tableCell">
											<logic:equal name="objAtributoTalentoValorado" property="categoriaAtributoTalento.apelido" value="">
												<bean:write name="objAtributoTalentoValorado" property="categoriaAtributoTalento.atributoTalento.nome" />
											</logic:equal>
											<logic:notEqual name="objAtributoTalentoValorado" property="categoriaAtributoTalento.apelido" value="">
				            	                <bean:write name="objAtributoTalentoValorado" property="categoriaAtributoTalento.apelido"/> 
											</logic:notEqual>
											:
										</td>
										<logic:notEmpty name="objAtributoTalentoValorado" property="valoracao">
                                            <% strValoracao = ((br.gov.camara.negocio.bancotalentos.pojo.AtributoTalentoValorado) objAtributoTalentoValorado).getValoracao(); %>
										</logic:notEmpty>
										<logic:empty name="objAtributoTalentoValorado" property="valoracao">
                                            <% strValoracao = ""; %>
										</logic:empty>
										<td width="72%" class="tableCell"><%= strValoracao.replaceAll("\n","<br>")  %>&nbsp;</td>
									</tr>        
								</logic:notEqual>
							</logic:notEqual>                                
						</logic:notEqual>
						<logic:equal name="objAtributoTalentoValorado" property="categoriaAtributoTalento.identificador" value="<%= strCategoriaAtributoAnterior %>">
							<tr class="<%= estilo %>">
								<td width="28%" class="tableCell">&nbsp;</td>
    								<logic:notEmpty name="objAtributoTalentoValorado" property="valoracao">
                                        <% strValoracao = ((br.gov.camara.negocio.bancotalentos.pojo.AtributoTalentoValorado) objAtributoTalentoValorado).getValoracao(); %>
    								</logic:notEmpty>
    								<logic:empty name="objAtributoTalentoValorado" property="valoracao">
                                        <% strValoracao = ""; %>
    								</logic:empty>
								<td width="72%" class="tableCell"><%= strValoracao.replaceAll("\n","<br>")  %>&nbsp;</td>
							</tr>     
						</logic:equal>
						<bean:define id="intCategoriaAtributoTemp" type="java.lang.Integer" name="objAtributoTalentoValorado" property="categoriaAtributoTalento.identificador" />
						<% strCategoriaAtributoAnterior = String.valueOf(intCategoriaAtributoTemp); %>
					</logic:iterate>      
					<tr height="1">
						<td colspan="2" height="1" >
							&nbsp;
						</td>
					</tr>
                </logic:present>
                <bean:define id="intCategoriaTemp" type="java.lang.Integer" name="objTalento" property="categoriaTalento.identificador" />
                <% strCategoriaAnterior = String.valueOf(intCategoriaTemp); %>
            </logic:iterate>
        </table>   
        <logic:notEmpty name="objPessoa">
            <center>
                <h3>
                    <bean:message key="visao.BancoTalentosGestao.interface.mensagem.curriculo"/>
                </h3>
            </center>
	        <p align="center">
                <html:button property="imprimir" onclick='imprimir();'>Imprimir</html:button>
	        </p>
        </logic:notEmpty>
    </center>
</logic:present>    
<br/>
<logic:equal parameter="ret" value="rescons">
	<p class="voltar">
	    <a href="resultadoConsultaTalentoEfetuarConsulta.do"><bean:message key="sigesp.comum.interface.operacao.voltar" bundle="comum" /></a>
	</p>
</logic:equal>
<logic:equal parameter="ret" value="back">
    <p class="voltar">
        <a href="javascript:history.back(1);"><bean:message key="sigesp.comum.interface.operacao.voltar" bundle="comum" /></a>
    </p>
</logic:equal>

<logic:present name="objPessoa">
	<script type="text/javascript" language="javascript">
	    function imprimir()
	    {
	        avisarProcessamento(true); 
	        var urlImprimir = "curriculoGerarRelatorio.do?chave=<bean:write name="objPessoa" property="identificador" />";
	        openPopUp(urlImprimir, '', 600, 400, 10, 10, true);
	        avisarProcessamento(false); 
	    }
	</script>
</logic:present>
