<%@ page language="java" %>
<%@ taglib prefix="bean"      uri="/tlds/struts-bean"    %>
<%@ taglib prefix="html"      uri="/tlds/struts-html"    %>
<%@ taglib prefix="logic"     uri="/tlds/struts-logic"   %>
<%@ taglib prefix="display"   uri="/tlds/sigesp-display" %>
<%@ taglib prefix="aa"        uri="/ajax/ajaxanywhere" %>

<!-- replaced -->

<logic:present name="lstResultadoConsulta">
        <table class="listagem">
            <tr class="tituloTabelaListagem">
                <td class="tituloTabelaListagem" width="90%">
					Resultado da consulta
                </td>
            </tr>
            <tr class="displayTag">
                <td>
                    <aa:zone name="aaListagem">
                        <display:table name="lstResultadoConsulta" align="center" requestURI="resultadoConsultaTalentoEfetuarConsulta.do" width="100%" id="dtListagem">
    					  <display:column property="descricao" title="Descrição" align="left" href="listagemConsultaEstatisticaEfetuarListagem.do" paramId="chave" paramProperty="parametro" />
                          <display:column property="quantidade" title="Total de Pessoas" align="center" href="listagemConsultaEstatisticaEfetuarListagem.do" paramId="chave" paramProperty="parametro" width="20%"/>
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

<br><br>
<center>
    <html:submit onclick="avisarProcessamento(); window.location.href='consultaEstatisticaCadastroPrepararVisualizacao.do?limpar=true';"><bean:message key="sigesp.comum.interface.operacao.novaconsulta" bundle="comum" /></html:submit>
</center>

<br><br>
<center>
    <html:link href="javascript:history.back(1);"><bean:message key="sigesp.comum.interface.operacao.voltar" bundle="comum" /></html:link>
</center>
