function submeterForm()
{
    avisarProcessamento(true);
    if (document.forms[0].action.indexOf('avisarJavaScript') > -1)
    {
        direcionarAcao('');
    }
    return true;
}

function visualizarMensagemGlobal()
{
    var mensagemGlobal = document.forms[0].elements['mensagemGlobal'].value;
    var spanMensagemGlobal = $('messagemGlobalTeste');
    var spanAutorMensagemGlobal = $('autorMessagemGlobalTeste');
    spanMensagemGlobal.innerHTML = mensagemGlobal;

    if (mensagemGlobal == '')
    {
        spanAutorMensagemGlobal.innerHTML = '';
        spanMensagemGlobal.className = "";
        spanAutorMensagemGlobal.className = "";
    }
    else
    {
        spanAutorMensagemGlobal.innerHTML = 'P_XXXXXX - DD/MM/AAAA HH:MM:SS';
        spanMensagemGlobal.className = "messagemGlobal";
        spanAutorMensagemGlobal.className = "autorMessagemGlobal";
    }
}
