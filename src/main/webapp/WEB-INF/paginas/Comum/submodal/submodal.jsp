<%@ taglib prefix="c" uri="/jstl/c" %>

<link rel="stylesheet" href="<c:out value='${pageContext.request.contextPath}' />/biblioteca/estilos/submodal.css" type="text/css" />

<script type="text/javascript" src="<c:out value='${pageContext.request.contextPath}' />/biblioteca/funcoes/comum/common.js" ></script>
<script type="text/javascript" src="<c:out value='${pageContext.request.contextPath}' />/biblioteca/funcoes/submodal/submodal.js" ></script>

<script type="text/javascript">
    function exibirSubModal (titulo, urlInicial, urlFecharSubModal, blnExibirBotaoFechar, blnConfirmarFechamento, mensagemConfirmacao, ponteiroRetorno, blnCloseOnError)
    {
        if (void(0)==blnConfirmarFechamento)
        {
            blnConfirmarFechamento = false;
        }
        initPopUp()
        setPopTitle(titulo);
        closeOnError(blnCloseOnError);
        setUrlOnClose(urlFecharSubModal);
        if (blnConfirmarFechamento==true)
        {
            if (mensagemConfirmacao==null || mensagemConfirmacao=="")
            {
                mensagemConfirmacao = "Deseja Fechar ?";
            }
        }
        //exibirBotaoFecharPopup (mensagemConfirmacao);
        showPopWin(urlInicial, null, null, ponteiroRetorno);
    }
</script>       