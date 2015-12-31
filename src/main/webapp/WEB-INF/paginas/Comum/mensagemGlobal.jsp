<%@ page language="java" %>

<%@ taglib prefix="c" uri="/jstl/c" %>
<%@ taglib prefix="fmt" uri="/jstl/fmt" %>
<%@ taglib prefix="html"      uri="/tlds/struts-html"  %>
<%@ taglib prefix="logic"     uri="/tlds/struts-logic" %>
<%@ taglib prefix="bean"      uri="/tlds/struts-bean"  %>

<span id="autorMessagemGlobalTeste"></span>
<span id="messagemGlobalTeste"></span>
<br/>
<html:form action="/avisarJavaScriptMensagemGlobal" onsubmit="return submeterForm()" focus="mensagemGlobal">
    <table class="detalhe">
        <tr class="tituloTabelaDetalhe">
            <td class="tituloTabelaDetalhe" colspan="2">
				Mensagem Global a ser exibida para todos que est�o conectados na Aplica��o
            </td>
        </tr>
        <tr class="trDetalheImpar">
        	<td align="right">
        		Exemplo de mensagem
        	</td>
        	<td>
				Por motivo de manuten��o esta aplica��o ficar� indispon�vel 5min ap�s&lt;br/&gt;o registro desta mensagem e voltar� depois de 15min. &lt;font size="1"&gt;D�vidas ligue 6-3715.&lt;/font&gt;
        	</td>
        </tr>
		<tr class="trDetalhePar">
            <td align="right" width="30%">
                <span title="<bean:message key="visao.Comum.interface.mensagemGlobal.dicapreenchimento" />" class="dicapreenchimento">
                    <bean:message key="visao.Comum.interface.mensagemGlobal" />
                </span>
            </td>
            <td align="left" width="70%">
                <html:textarea property="mensagemGlobal" cols="80" rows="5"/>
            </td>
        </tr>
        <tr class="trBotoes">
            <td align="center" colspan="2">
                <html:button property="Visualizar" onclick="javascript:visualizarMensagemGlobal()">Visualizar</html:button>&nbsp;
                <html:submit><bean:message key="visao.Comum.interface.operacao.gravar" bundle="comum" /></html:submit>
            </td>
        </tr>
    </table>
</html:form>
