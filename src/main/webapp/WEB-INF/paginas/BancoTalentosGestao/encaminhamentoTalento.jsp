<%@ page language="java" %>
<%@ taglib prefix="logic"     uri="/tlds/struts-logic"   %>

<logic:equal name="strEncaminhamento" value="talento">
	<script language="JavaScript">
		window.location.href="talentoPrepararVisualizacao.do?mensagemInclusao=sim"
	</script>				
</logic:equal>
<logic:equal name="strEncaminhamento" value="detalheTalento">
	<script language="JavaScript">
		window.location.href="detalheTalentoPrepararInclusao.do?categoriaTalento=<%= request.getAttribute("strCategoriaTalento") %>&limpar=true&mensagemInclusao=sim"
	</script>				
</logic:equal>

