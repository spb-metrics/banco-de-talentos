<%@ page language="java" %>
<%@ taglib prefix="c" uri="/jstl/c" %>

<script type="text/javascript" src="<c:out value='${pageContext.request.contextPath}' />/biblioteca/funcoes/tooltip/wz_tooltip.js"></script>
<script type="text/javascript" src="<c:out value='${pageContext.request.contextPath}' />/biblioteca/funcoes/tooltip/tip_balloon.js"></script>

<script type="text/javascript">
	function exibirTooltip(texto) {
		return Tip(texto, BALLOON, true, ABOVE, true, OFFSETX, -10, TEXTALIGN, 'justify', FADEIN, 600, FADEOUT, 600, PADDING, 8);
	}
</script>