<%@ page language="java" %>

<%@ taglib prefix="bean"      uri="/tlds/struts-bean"  %>
<%@ taglib prefix="html"      uri="/tlds/struts-html"  %>
<%@ taglib prefix="logic"     uri="/tlds/struts-logic" %>
<%@ taglib prefix="tiles"     uri="/tlds/struts-tiles" %>

<html:messages id="erro" header="errors.header" footer="errors.footer" bundle="comum">
    <bean:write name="erro" filter="false"/><br>
</html:messages>
<html:messages id="mensagem" message="true" header="messages.header" footer="messages.footer" bundle="comum">
    <bean:write name="mensagem" filter="false"/><br>
</html:messages>
<br>

<CENTER><H2>
Comum - Página inicial
</H2></CENTER><BR>
<BR>
Bem-vindo(a) ao módulo Comum.<p>

