<%@ page language="java" %>
<%@ taglib prefix="bean"      uri="/tlds/struts-bean"  %>
<%@ taglib prefix="html"      uri="/tlds/struts-html"  %>
<%@ taglib prefix="logic"     uri="/tlds/struts-logic" %>
<%@ taglib prefix="display"   uri="/tlds/sigesp-display" %>
<%@ taglib prefix="aa"        uri="/ajax/ajaxanywhere" %>

<!-- replaced -->

<html:form action="/avisarJavaScriptConsultaAtributoTalentoOpcao" focus="atributoTalentoOpcao">
    <table class="listagem">
        <tr class="tituloTabelaListagem">
            <td class="tituloTabelaListagem" colspan="3">
                Pesquisa
            </td>
        </tr>
	    <tr>
	        <td align="right">
                <span title="<bean:message key="visao.BancoTalentosGestao.interface.atributoTalentoOpcao.dicapreenchimento" />" class="tooltip">
		            <bean:message key="visao.BancoTalentosGestao.interface.atributoTalentoOpcao"  />
                </span>
	        </td>
	        <td align="left">
    	        <html:text property="atributoTalentoOpcao" style="width:500" onkeypress="return desabilitarEnter(event);" />
	        </td>
    	</tr>
        <tr>
           <td align="center" colspan="3">
               <html:submit onclick="JavaScript:direcionarAcao('');">Pesquisar</html:submit>
               <html:reset>Limpar</html:reset>
           </td>
        </tr>
	</table>
	<br>
	    <logic:present name="lstAtributoTalentoOpcoes">
	    <table class="listagem">
            <tr class="displayTag">
	            <td colspan="3">
                    <aa:zone name="aaListagem">
        	            <display:table name="lstAtributoTalentoOpcoes" align="center" pagesize="20" requestURI="consultaAtributoTalentoOpcaoEfetuarConsulta.do" totalRecords="strTotalRegistros" width="100%" id="dtListagem">
            		        <display:column property="atributoTalento.nome" title="Atributo" align="left" href="javascript:returnPopUp('chave');" hrefType="replace" paramId="chave" paramProperty="identificador" width="30%"/>
							<display:column property="descricao" title="Opção" align="left" href="javascript:returnPopUp('chave');" hrefType="replace" paramId="chave" paramProperty="identificador"/>
							<display:column title="Selecionar" align="center" href="javascript:returnPopUp('chave');" hrefType="replace" paramId="chave" paramProperty="identificador" width="10%">
								<img src=../biblioteca/imagens/paginas/selecionarOpcao.gif border=0>
							</display:column>
                        </display:table>
                    </aa:zone>
                    <script language="javascript">
                        configurarAADisplayTag('aaListagem');
                    </script>
    	        </td>
        	</tr>
		</table>
    	</logic:present>
</html:form>
