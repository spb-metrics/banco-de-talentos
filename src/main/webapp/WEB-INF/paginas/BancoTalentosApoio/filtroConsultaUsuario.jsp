<%@ page language="java" %>
<%@ taglib prefix="bean"      uri="/tlds/struts-bean"    %>
<%@ taglib prefix="html"      uri="/tlds/struts-html"    %>
<%@ taglib prefix="logic"     uri="/tlds/struts-logic"   %>
<%@ taglib prefix="display"   uri="/tlds/sigesp-display" %>
<%@ taglib prefix="aa"        uri="/ajax/ajaxanywhere" %>

<!-- replaced -->

<logic:present name="lstFiltroConsultaUsuario">
        <table class="listagem">
            <tr class="tituloTabelaListagem">
                <td class="tituloTabelaListagem">
                    <bean:write name="strTituloTabelaDetalhe" ignore="true" />
                </td>
                <td class="tableCellHeaderButton">
                    <html:link page="/detalheFiltroConsultaUsuarioPrepararInclusao.do"><bean:message key="sigesp.comum.interface.operacao.incluir" bundle="comum" /></html:link>
                </td>
            </tr>
            <tr class="displayTag">
                <td colspan="3">
                    <aa:zone name="aaListagem">
                        <display:table name="lstFiltroConsultaUsuario" align="center" pagesize="20" requestURI="filtroConsultaUsuarioPrepararVisualizacao.do" width="100%" id="dtListagem">
                          <display:column property="identificador" title="Ponto" align="right" sortable="true" width="10%"/>
                          <display:column property="nome" title="Nome" align="left" sortable="true" />
                          <display:column title="Exc." align="center" href="exclusaoFiltroConsultaUsuarioPrepararExclusao.do" paramId="chave" paramProperty="identificador" width="10%">
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
		<html:link page="/filtroConsultaPrepararVisualizacao.do"><bean:message key="sigesp.comum.interface.operacao.voltar" bundle="comum"/></html:link>
</p>
<br><br>
