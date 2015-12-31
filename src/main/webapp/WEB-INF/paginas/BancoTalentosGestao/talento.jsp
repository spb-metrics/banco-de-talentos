<%@ page language="java" %>

<%@ taglib prefix="bean"      uri="/tlds/struts-bean"  %>
<%@ taglib prefix="html"      uri="/tlds/struts-html"  %>
<%@ taglib prefix="logic"     uri="/tlds/struts-logic" %>
<%@ taglib prefix="display"   uri="/tlds/sigesp-display" %>
<%@ taglib prefix="aa"        uri="/ajax/ajaxanywhere" %>

<html:form action="/avisarJavaScriptTalento" onsubmit="return submeterForm()" focus="categoriaTalento">
    <logic:present name="lstTalentos">
        <center>
            <table class="listagem">
                <tr class="tituloTabelaListagem">
                    <td class="tituloTabelaListagem" width="*">
                        <font size="3">Meus talentos</font>
                    </td>
                    <td class="tableCellHeaderButton" id="botaoNovo" style="display:none;">
                        <a href="JavaScript:if (valida()) {avisarProcessamento(); prepararInclusao();}" ><bean:message key="sigesp.comum.interface.operacao.incluir" bundle="comum" /></a>
                    </td>
                </tr>
                <tr>
                    <td class="tituloTabelaListagem" colspan="2">
                        <html:select property="categoriaTalento" style="width:100%" onchange="efetuarFiltro(this)">
                            <html:option value="0">Exibindo todos. Selecione uma categoria para incluir...</html:option>
                            <logic:present name="lstCategoriasTalento">
                                <html:options collection="lstCategoriasTalento" labelProperty="nome" property="identificador"/>
                            </logic:present>
                        </html:select>
                    </td>
                </tr>
                <tr class="displayTag">
                    <td colspan="2">
                        <aa:zone name="aaTalentos">
                            <display:table name="lstTalentos" align="center" requestURI='<%= (String) request.getAttribute("strPaginacao") %>' totalRecords="strTotalRegistros" width="100%" id="dtTalentos" pagesize="20">
                              <display:column property="descricao" title="Descrição" align="left" />
                              <display:column title="Alt." align="center" href="detalheTalentoPrepararAlteracao.do" paramId="chave" paramProperty="identificador" width="10%">
    						  		<img src=../biblioteca/imagens/paginas/escrever.gif border=0>
                              </display:column>
                              <display:column title="Exc." align="center" href="exclusaoTalentoPrepararExclusao.do" paramId="chave" paramProperty="identificador" width="10%">
    	                            <img src=../biblioteca/imagens/paginas/excluir.gif border=0>
                              </display:column>
                            </display:table>
                        </aa:zone>
                        <script language="javascript">
                            configurarAADisplayTag('aaTalentos');
                        </script>
                    </td>
                </tr>
            </table>
        </center>
    </logic:present>
</html:form>

<br><br>
<center>
    <a href="indexBancoTalentosGestao.do"><bean:message key="sigesp.comum.interface.operacao.voltar" bundle="comum"  /></a>
</center>
<br><br>

<script language="JavaScript">

    function valida()
    {
        if (document.forms[0].categoriaTalento.value == 0)
        {
            alert('É necessário selecionar uma categoria para a inclusão de um novo talento');
            document.forms[0].categoriaTalento.focus();
            return false;
        }
        return true;
    }

    function prepararInclusao()
    {
        document.location.href = 'detalheTalentoPrepararInclusao.do?categoriaTalento=' + document.forms[0].categoriaTalento.value + '&limpar=true';
    }

    function efetuarFiltro(botao)
    {
        if (document.forms[0].categoriaTalento.selectedIndex == 0)
        {
            $('botaoNovo').style.display = "none";
        }
        else
        {
            $('botaoNovo').style.display = "";
        }
        submeterAADisplayTag('aaTalentos', 'talentoEfetuarFiltro.do', document.forms[0]);
    }

</script>
