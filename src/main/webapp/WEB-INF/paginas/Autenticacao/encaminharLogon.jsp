<%@ page language="java" %>

<%@ taglib prefix="bean"      uri="/tlds/struts-bean"  %>
<%@ taglib prefix="html"      uri="/tlds/struts-html"  %>

<br>
<html:form action="/avisarJavaScriptEncaminharLogon" focus="txtLogin" onsubmit="return submeter();">
    <center>
        <table class="detalhe">
            <tr class="tituloTabelaDetalhe">
                <td class="tituloTabelaDetalhe" colspan="2">
                    Informe seu usuário e senha de acesso
                </td>
            </tr>
            <tr class="trDetalhePar">
                <td align="right" width="50%">
                    <span title="<bean:message key="visao.Autenticacao.interface.txtLogin.dicapreenchimento" />" class="tooltip">
	                    <span class="campoObrigatorio">
	                        <bean:message key="visao.Autenticacao.interface.txtLogin" />
	                    </span>
	                    <bean:message key="sigesp.comum.interface.imagem.ajuda" bundle="comum" />
                    </span>
                </td>
                <td align="left" width="*">
                    <html:text property="txtLogin" />
                </td>
            </tr>
            <tr class="trDetalheImpar">
                <td align="right">
                    <span title="<bean:message key="visao.Autenticacao.interface.txtSenha.dicapreenchimento" />" class="tooltip">
	                    <span class="campoObrigatorio">
	                        <bean:message key="visao.Autenticacao.interface.txtSenha" />
	                    </span>
	                    <bean:message key="sigesp.comum.interface.imagem.ajuda" bundle="comum" />
                    </span>
                </td>
                <td align="left">
                    <html:password property="txtSenha" />
                </td>
            </tr>
            <tr class="trBotoes">
                <td align="right">
                    <html:submit><bean:message key="sigesp.comum.interface.operacao.confirmar" bundle="comum"/></html:submit>
                </td>
                <td align="left">
                    <html:reset><bean:message key="sigesp.comum.interface.operacao.limpar" bundle="comum"/></html:reset>
                </td>
            </tr>
        </table>
    </center>
</html:form>
