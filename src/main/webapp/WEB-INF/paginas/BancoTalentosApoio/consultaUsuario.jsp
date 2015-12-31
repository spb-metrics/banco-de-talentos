<%@ page language="java" %>
<%@ taglib prefix="bean"      uri="/tlds/struts-bean"  %>
<%@ taglib prefix="html"      uri="/tlds/struts-html"  %>
<%@ taglib prefix="logic"     uri="/tlds/struts-logic" %>
<%@ taglib prefix="display"   uri="/tlds/sigesp-display" %>
<%@ taglib prefix="aa"        uri="/ajax/ajaxanywhere" %>

<!-- replaced -->

<html:form action="/avisarJavaScriptConsultaUsuario" focus="identificador">
    <table class="detalhe">
        <tr class="tituloTabelaDetalhe">
            <td class="tituloTabelaDetalhe" colspan="3">
                Consulta
            </td>
        </tr>
	    <tr class="trDetalheImpar">
	        <td align="right">
                <span title="<bean:message key="visao.BancoTalentosApoio.interface.ponto.dicapreenchimento" />" class="tooltip">
		            <bean:message key="visao.BancoTalentosApoio.interface.ponto"  />
                </span>
	        </td>
	        <td align="left">
    	        <html:text property="identificador" style="width:60"/>
	        </td>
    	</tr>
	    <tr class="trDetalhePar">
	        <td align="right">
                <span title="<bean:message key="visao.BancoTalentosApoio.interface.nome.dicapreenchimento" />" class="tooltip">
		            <bean:message key="visao.BancoTalentosApoio.interface.nome"  />
                </span>
	        </td>
	        <td align="left">
    	        <html:text property="nome" style="width:500"/>
	        </td>
    	</tr>
        <tr class="trBotoes">
           <td align="center" colspan="3">
               <html:submit onclick="JavaScript:direcionarAcao('');">Consultar</html:submit>
               <html:reset>Limpar</html:reset>
           </td>
        </tr>
	</table>
	<br>
	    <logic:present name="lstUsuario">
	    <table class="listagem">
            <tr class="displayTag">
	            <td colspan="3">
    	            <center>
                        <aa:zone name="aaListagem">
            	            <display:table name="lstUsuario" align="center" pagesize="20" requestURI="consultaUsuarioEfetuarConsulta.do" totalRecords="strTotalRegistros" width="100%" id="dtListagem">
                		        <display:column property="identificador" title="Ponto" align="right" width="10%" />
    							<display:column property="nome" title="Nome" align="left"/>
    							<display:column title="Selecionar" align="center" href="javascript:returnPopUp('chave');" hrefType="replace" paramId="chave" paramProperty="chaveConsulta" width="10%">
    								<img src=../biblioteca/imagens/paginas/selecionarOpcao.gif border=0>
    							</display:column>
                            </display:table>
                        </aa:zone>
                        <script language="javascript">
                            configurarAADisplayTag('aaListagem');
                        </script>
                    </center>    
    	        </td>
        	</tr>
		</table>
    	</logic:present>
</html:form>
