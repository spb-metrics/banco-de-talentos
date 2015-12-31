<%@ taglib uri="/tlds/struts-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="/jstl/c" %>

<html>
    <head>
        <link rel="stylesheet" href="<c:out value='${pageContext.request.contextPath}' />/biblioteca/estilos/camara.css" type="text/css" />
        <title>
            <tiles:getAsString name="titulo"/>
        </title>
        <meta http-equiv="pragma" content="no-cache" />
        <meta http-equiv="expires" content="0" />

        <script type="text/javascript">
        function obterCodigoTeclaPressionada (event) 
        {
            var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;
            return keyCode;
        }
        </script>
    </head>

    <body id="popup">

        <script src="<c:out value='${pageContext.request.contextPath}' />/biblioteca/funcoes/biblio.js"></script>
        <script src="<c:out value='${pageContext.request.contextPath}' />/biblioteca/funcoes/popup.js"></script>
        <script src="<c:out value='${pageContext.request.contextPath}' />/biblioteca/funcoes/ajax/aa.js"></script>
        <script src="<c:out value='${pageContext.request.contextPath}' />/biblioteca/funcoes/ajax/aaUtils.js"></script>
        <script src="<c:out value='${pageContext.request.contextPath}' />/biblioteca/funcoes/comum/PortableDrag.js"></script>

        <jsp:include page="/WEB-INF/paginas/Comum/aguarde/aguarde.jsp" />

		<script>
		    window.top.avisarProcessamento(false);
        </script>

        <!-- Início javascript da página -->
        <script type="text/javascript">
            <tiles:insert attribute="direcionadorAcao" />
            <tiles:insert attribute="javascriptPagina" />
        </script>
        <!-- Fim javascript da página -->

		<table id="layoutAplicacaoPopup" cellpadding="0" cellspacing="0">
			<tr id="layoutAplicacaoPopup" class="paginaAplicacao">
				<td id="layoutAplicacaoPopup"  class="paginaAplicacao">
					<tiles:insert attribute="mensagens"/>
					<tiles:insert attribute="corpo" />
				</td>
			</tr>
		</table>
		<script>
			aplicarCorrecoesVisuais();
		</script>
    </body>
</html>
