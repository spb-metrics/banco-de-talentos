<%@ page language="java" %>
<%@ taglib prefix="bean"      uri="/tlds/struts-bean"    %>
<%@ taglib prefix="html"      uri="/tlds/struts-html"    %>
<%@ taglib prefix="logic"     uri="/tlds/struts-logic"   %>
<%@ taglib prefix="display"   uri="/tlds/sigesp-display" %>
<%@ taglib prefix="aa"        uri="/ajax/ajaxanywhere" %>
<%@ taglib prefix="c"         uri="/jstl/c" %>

<html:form action="/avisarJavaScriptResultadoConsultaTalento">
	<logic:present name="lstConsultaTalentos">
	        <table class="listagem">
	            <tr class="tituloTabelaListagem">
	                <td class="tituloTabelaListagem" width="90%">
	                    Resultado da consulta
	                </td>
	                <c:if test="${!empty sessionScope.lstConsultaTalentos}">
	                    <td class="tableCellHeaderButton">
	                        <a href="#" onclick="submeterForm();">Imprimir</a>
	                    </td>
                    </c:if>
	            </tr>
	            <tr class="displayTag">
	                <td colspan="2">
	                    <aa:zone name="aaListagem">
	                        <display:table name="sessionScope.lstConsultaTalentos" align="center" pagesize="20" requestURI="resultadoConsultaTalentoEfetuarConsulta.do" width="100%" id="dtListagem">
	                            <display:column property="identificador" title="Ponto" align="right" href="curriculoPrepararVisualizacaoConsulta.do?ret=rescons" paramId="chave" paramProperty="identificador"  sortable="true" width="10%"/>
	                            <display:column property="nome" title="Descrição" align="left" href="curriculoPrepararVisualizacaoConsulta.do?ret=rescons" paramId="chave" paramProperty="identificador" sortable="true"/>
	                            <display:column property="grupo.descricao" title="Grupo" align="center" href="curriculoPrepararVisualizacaoConsulta.do?ret=rescons" paramId="chave" paramProperty="identificador" sortable="true" width="25%"/>
		                        <display:column title="Imprimir<input type='checkbox' name='checkUncked' style='margin: 1 1 1 1' onclick='checkUnckeAll();' />" align="center" width="5%">
		                            <bean:define id="intIdentificador" type="java.lang.Integer" name="dtListagem" property="identificador" />
		                            <input type="checkbox" name="idePessoa" value="<%=intIdentificador%>" style='margin: 1 1 1 1'/>
		                        </display:column>
	                        </display:table>
	                    </aa:zone>
	                    <script language="javascript">
	                        configurarAADisplayTag('aaListagem');
	                    </script>
	                </td>
	            </tr>
	        </table>
		    <logic:equal name="lstConsultaTalentos" value="null">
		        <center>
		            <h3>
		                Sua consulta não retornou resultados.
		            </h3>
		        </center>
		    </logic:equal>
	</logic:present>
</html:form>

<logic:notPresent name="lstConsultaTalentos">
	<logic:present name="stbTotalRegistros">
    	<center>
			<br>
			<h3>
				Sua consulta retornou mais resultados (<bean:write name="stbTotalRegistros"/>) que o permitido (<%= br.gov.camara.util.aplicacao.AplicacaoPlugIn.obterConfiguracao("MAXIMO_REGISTROS_CONSULTA_TALENTOS") %>).<br/>
				Refine sua pesquisa e tente novamente.
			</h3>
	    </center>
	</logic:present>
</logic:notPresent>

<p class="voltar">
    <input type="button" onclick="javascript:avisarProcessamento(); direcionarURL('consultaTalentoPrepararVisualizacao.do?limpar=true');" value='<bean:message key="sigesp.comum.interface.operacao.novaconsulta" bundle="comum"/>'>
    &nbsp;&nbsp;
    <input type="button" onclick="javascript:avisarProcessamento(); direcionarURL('consultaTalentoPrepararVisualizacao.do?limpar=false');" value='<bean:message key="sigesp.comum.interface.operacao.editarconsulta" bundle="comum"/>'>
</p>

<script>

    function checkUnckeAll()
    {
        // É necessário habilitar o primeiro campo no caso de não haver mais de um registro/checkbox, ou seja
        // havendo um checkbox não será feito um array de ideFuncionalidadeSistema
        if (document.forms[0].idePessoa.length==void(0))
        {
            document.forms[0].idePessoa.checked = document.forms[0].checkUncked.checked;
        }
        else
        {
            for (i = 0; i < document.forms[0].idePessoa.length; i++) 
            {
                document.forms[0].idePessoa[i].checked = document.forms[0].checkUncked.checked;
            }
        }
    }

    function submeterForm()
    {
        avisarProcessamento(true);
        var existeSelecao = false;
        var idCurriculoSelecionado = '';
        
        if (document.forms[0].idePessoa.length==void(0))
        {
            // Só foi retornado um registro, portanto tratar como não sendo um array
            if (document.forms[0].idePessoa.checked == true)
            {
                existeSelecao = true;
                idCurriculoSelecionado += document.forms[0].idePessoa.value + ','; 
            }
        }
        else
        {
            // Foi retornado mais de um registro, portanto tratar como um array
	        for (i = 0; i < document.forms[0].idePessoa.length; i++) 
	        {
	            if (document.forms[0].idePessoa[i].checked == true)
	            {
	                existeSelecao = true;
	                idCurriculoSelecionado += document.forms[0].idePessoa[i].value + ','; 
	            }
	        }
        }

        if (!existeSelecao)
        {
            alert('Selecione pelo menos um currículo para gerar o arquivo PDF');
        }
        else
        {
	        openPopUp('resultadoConsultaTalentoGerarRelatorio.do?curriculos=' + idCurriculoSelecionado, '', 600, 400, 10, 10, true);
        }
        avisarProcessamento(false); 
        return existeSelecao;
    }
    
</script>
