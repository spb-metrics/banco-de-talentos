<%@ page language="java" %>
<%@ taglib prefix="bean"      uri="/tlds/struts-bean"    %>
<%@ taglib prefix="html"      uri="/tlds/struts-html"    %>
<%@ taglib prefix="logic"     uri="/tlds/struts-logic"   %>
<%@ taglib prefix="display"   uri="/tlds/sigesp-display" %>
<%@ taglib prefix="aa"        uri="/ajax/ajaxanywhere" %>

<!-- replaced -->

<logic:present name="lstAtributoTalentoOpcoes">
        <table class="listagem">
            <tr class="tituloTabelaListagem">
                <td class="tituloTabelaListagem">
                    Opções de atributo de talento - <bean:write name="objAtributoTalento" property="nome" />
                </td>
                <td class="tableCellHeaderButton">
                    <html:link page="/detalheAtributoTalentoOpcaoPrepararInclusao.do"><bean:message key="sigesp.comum.interface.operacao.incluir" bundle="comum" /></html:link>
                </td>
            </tr>
            <tr class="displayTag">
                <td colspan="2">
                    <aa:zone name="aaOpcoes">
                        <display:table name="lstAtributoTalentoOpcoes" align="center" pagesize="20" requestURI="atributoTalentoOpcaoPrepararVisualizacao.do" totalRecords="strTotalRegistros" width="100%" id="dtOpcoes">
                          <display:column property="descricao" title="Descrição" align="left" sortable="true"/>
                          <display:column title="Alt." align="center" href="detalheAtributoTalentoOpcaoPrepararAlteracao.do" paramId="chave" paramProperty="identificador" width="10%" export="false">
    	                      <img src="../biblioteca/imagens/paginas/escrever.gif" border="0">
                          </display:column>
                          <display:column title="Exc." align="center" href="exclusaoAtributoTalentoOpcaoPrepararExclusao.do" paramId="chave" paramProperty="identificador" width="10%" export="false">
    	                      <img src="../biblioteca/imagens/paginas/excluir.gif" border="0">
                          </display:column>
                        </display:table>
                    </aa:zone>
                    <script language="javascript">
                        configurarAADisplayTag('aaOpcoes');
                    </script>
                </td>
            </tr>
        </table>
</logic:present>

<p class="voltar">
		<html:link page="/atributoTalentoPrepararVisualizacao.do"><bean:message key="sigesp.comum.interface.operacao.voltar" bundle="comum"/></html:link>
</p>
