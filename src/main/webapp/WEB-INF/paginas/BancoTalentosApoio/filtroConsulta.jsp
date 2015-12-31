<%@ page language="java" %>
<%@ taglib prefix="bean"      uri="/tlds/struts-bean"    %>
<%@ taglib prefix="html"      uri="/tlds/struts-html"    %>
<%@ taglib prefix="logic"     uri="/tlds/struts-logic"   %>
<%@ taglib prefix="display"   uri="/tlds/sigesp-display" %>
<%@ taglib prefix="aa"        uri="/ajax/ajaxanywhere" %>

<!-- replaced -->

<logic:present name="lstFiltroConsulta">
        <table class="listagem">
            <tr class="tituloTabelaListagem">
                <td class="tituloTabelaListagem">
                    Tipo de Filtro - <bean:write name="objTipoFiltroConsulta" property="nomeTipoFiltroConsulta" />
                </td>
                <td class="tableCellHeaderButton">
                    <html:link page="/detalheFiltroConsultaPrepararInclusao.do"><bean:message key="sigesp.comum.interface.operacao.incluir" bundle="comum" /></html:link>
                </td>
            </tr>
            <tr class="displayTag">
                <td colspan="3">
                    <aa:zone name="aaListagem">
                        <display:table name="lstFiltroConsulta" align="center" pagesize="20" requestURI="filtroConsultaPrepararVisualizacao.do" totalRecords="strTotalRegistros" width="100%" id="dtListagem">
                          <display:column property="nomeFiltroConsulta" title="Nome do Filtro" align="left" sortable="true"/>
                          <display:column title="Grupos" align="center" href="filtroConsultaGrupoPrepararVisualizacao.do" paramId="chave" paramProperty="identificador" width="10%">
    	                      <img src="../biblioteca/imagens/paginas/ver.gif" border="0">
                          </display:column>
                          <display:column title="Usuários" align="center" href="filtroConsultaUsuarioPrepararVisualizacao.do" paramId="chave" paramProperty="identificador" width="10%">
    	                      <img src="../biblioteca/imagens/paginas/ver.gif" border="0">
                          </display:column>
                          <display:column title="Critérios / Alterar" align="center" href="detalheFiltroConsultaPrepararAlteracao.do" paramId="chave" paramProperty="identificador" width="20%">
    	                      <img src="../biblioteca/imagens/paginas/escrever.gif" border="0">
                          </display:column>
                          <display:column title="Exc." align="center" href="exclusaoFiltroConsultaPrepararExclusao.do" paramId="chave" paramProperty="identificador" width="10%">
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
		<html:link page="/tipoFiltroConsultaPrepararVisualizacao.do"><bean:message key="sigesp.comum.interface.operacao.voltar" bundle="comum"/></html:link>
</p>
