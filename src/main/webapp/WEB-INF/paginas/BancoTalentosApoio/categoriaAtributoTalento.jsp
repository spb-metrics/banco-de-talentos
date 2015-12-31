<%@ page language="java" %>
<%@ taglib prefix="bean"      uri="/tlds/struts-bean"    %>
<%@ taglib prefix="html"      uri="/tlds/struts-html"    %>
<%@ taglib prefix="logic"     uri="/tlds/struts-logic"   %>
<%@ taglib prefix="display"   uri="/tlds/sigesp-display" %>
<%@ taglib prefix="aa"        uri="/ajax/ajaxanywhere" %>

<!-- replaced -->

<logic:present name="lstCategoriaAtributosTalento">
        <table class="listagem">
            <tr class="tituloTabelaListagem">
                <td class="tituloTabelaListagem">
                    Atributos de talento da categoria - <bean:write name="objCategoriaTalento" property="nome" />
                </td>
                <td class="tableCellHeaderButton">
                    <html:link page="/detalheCategoriaAtributoTalentoPrepararInclusao.do"><bean:message key="sigesp.comum.interface.operacao.incluir" bundle="comum" /></html:link>
                </td>
                <td class="tableCellHeaderButton">
                    <html:link page="/ordenacaoCategoriaAtributoTalentoPrepararVisualizacao.do">Ordenar</html:link>
                </td>
            </tr>
            <tr class="displayTag">
                <td colspan="3">
                    <aa:zone name="aaListagem">
                        <display:table name="lstCategoriaAtributosTalento" align="center" pagesize="20" requestURI="categoriaAtributoTalentoPrepararVisualizacao.do" totalRecords="strTotalRegistros" width="100%" id="dtListagem">
                          <display:column property="atributoTalento.nome" title="Atributo" align="left" sortable="true" width="50%"/>
                          <display:column property="apelido" title="Apelido" align="left" sortable="true"/>
                          <display:column title="Alt." align="center" href="detalheCategoriaAtributoTalentoPrepararAlteracao.do" paramId="chave" paramProperty="identificador" width="10%">
    	                      <img src="../biblioteca/imagens/paginas/escrever.gif" border="0">
                          </display:column>
                          <display:column title="Exc." align="center" href="exclusaoCategoriaAtributoTalentoPrepararExclusao.do" paramId="chave" paramProperty="identificador" width="10%">
    	                      <img src="../biblioteca/imagens/paginas/excluir.gif" border="0">
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

<p class="voltar">
		<html:link page="/categoriaTalentoPrepararVisualizacao.do"><bean:message key="sigesp.comum.interface.operacao.voltar" bundle="comum"/></html:link>
</p>
