<%@ page language="java" %>
<%@ taglib prefix="bean"      uri="/tlds/struts-bean"    %>
<%@ taglib prefix="html"      uri="/tlds/struts-html"    %>
<%@ taglib prefix="logic"     uri="/tlds/struts-logic"   %>
<%@ taglib prefix="display"   uri="/tlds/sigesp-display" %>
<%@ taglib prefix="aa"        uri="/ajax/ajaxanywhere" %>

<!-- replaced -->

<logic:present name="lstAtributosTalento">
        <table class="listagem">
            <tr class="tituloTabelaListagem">
                <td class="tituloTabelaListagem">
                    Atributos de talento
                </td>
                <td class="tableCellHeaderButton">
                    <html:link page="/detalheAtributoTalentoPrepararInclusao.do"><bean:message key="sigesp.comum.interface.operacao.incluir" bundle="comum" /></html:link>
                </td>
            </tr>
            <tr class="displayTag">
                <td colspan="2">
                    <aa:zone name="aaAtributos">
                        <display:table name="lstAtributosTalento" align="center" pagesize="20" requestURI="atributoTalentoPrepararVisualizacao.do" totalRecords="strTotalRegistros" width="100%" id="dtAtributos">
                          <display:column property="nome" title="Nome" align="left" sortable="true"/>
                          <display:column property="tipoHTML.descricao" title="Tipo HTML" align="left" sortable="true" width="17%"/>
                          <display:column title="Opções" align="center" href="atributoTalentoOpcaoPrepararVisualizacao.do" paramId="chave" paramProperty="identificador" width="10%">
    					      <img src="../biblioteca/imagens/paginas/ver.gif" border="0">
    					  </display:column>
                          <display:column title="Alt." align="center" href="detalheAtributoTalentoPrepararAlteracao.do" paramId="chave" paramProperty="identificador" width="10%">
    	                      <img src="../biblioteca/imagens/paginas/escrever.gif" border="0">
                          </display:column>
                          <display:column title="Exc." align="center" href="exclusaoAtributoTalentoPrepararExclusao.do" paramId="chave" paramProperty="identificador" width="10%">
    	                      <img src="../biblioteca/imagens/paginas/excluir.gif" border="0">
                          </display:column>
                        </display:table>
                    </aa:zone>
                    <script language="javascript">
                        configurarAADisplayTag('aaAtributos');
                    </script>
                </td>
            </tr>
        </table>
</logic:present>

<p class="voltar">
    <html:link page="/indexBancoTalentosApoio.do"><bean:message key="sigesp.comum.interface.operacao.voltar" bundle="comum"/></html:link>
</p>
