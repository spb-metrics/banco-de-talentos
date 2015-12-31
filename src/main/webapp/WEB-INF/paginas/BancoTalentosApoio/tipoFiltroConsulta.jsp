<%@ page language="java" %>
<%@ taglib prefix="bean"      uri="/tlds/struts-bean"    %>
<%@ taglib prefix="html"      uri="/tlds/struts-html"    %>
<%@ taglib prefix="logic"     uri="/tlds/struts-logic"   %>
<%@ taglib prefix="display"   uri="/tlds/sigesp-display" %>
<%@ taglib prefix="aa"        uri="/ajax/ajaxanywhere" %>

<!-- replaced -->

<logic:present name="lstTipoFiltroConsulta">
        <table class="listagem">
            <tr class="tituloTabelaListagem">
                <td class="tituloTabelaListagem">
                    Tipos de Filtro de Consulta
                </td>
                <td class="tableCellHeaderButton">
                    <html:link page="/detalheTipoFiltroConsultaPrepararInclusao.do"><bean:message key="sigesp.comum.interface.operacao.incluir" bundle="comum" /></html:link>
                </td>
            </tr>
            <tr class="displayTag">
                <td colspan="3">
                    <aa:zone name="aaListagem">
                        <display:table name="lstTipoFiltroConsulta" align="center" pagesize="20" requestURI="tipoFiltroConsultaPrepararVisualizacao.do" totalRecords="strTotalRegistros" width="100%" id="dtListagem">
                          <display:column property="nomeTipoFiltroConsulta" title="Nome" align="left" sortable="true"/>
                          <display:column title="Filtros" align="center" href="filtroConsultaPrepararVisualizacao.do" paramId="chave" paramProperty="identificador" width="10%">
    					      <img src="../biblioteca/imagens/paginas/ver.gif" border="0">
    					  </display:column>
                          <display:column title="Alt." align="center" href="detalheTipoFiltroConsultaPrepararAlteracao.do" paramId="chave" paramProperty="identificador" width="10%">
    	                      <img src="../biblioteca/imagens/paginas/escrever.gif" border="0">
                          </display:column>
                          <display:column title="Exc." align="center" href="exclusaoTipoFiltroConsultaPrepararExclusao.do" paramId="chave" paramProperty="identificador" width="10%">
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
	<html:link page="/indexBancoTalentosApoio.do"><bean:message key="sigesp.comum.interface.operacao.voltar" bundle="comum"/></html:link>
</p>
