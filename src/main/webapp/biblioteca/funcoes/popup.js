function openPopUp(popURL, ponteiroRetorno, optWidth, optHeight, optTop, optLeft, closeOnError, urlFecharPopup, tituloPopup)
{
    // Valida par?metros obrigat?rios

    if (popURL == void(0) || popURL == "")
    {
    	return;
    }
    
    if (tituloPopup == void(0) || tituloPopup == "" || tituloPopup == null)
    {
    	tituloPopup = "Popup";
    }

    // Verifica valor dos par?metros opcionais

    if (optWidth == void(0) || optWidth == "")
    {
    	optWidth = 400;
    }
    if (optHeight == void(0) || optHeight == "")
    {
    	optHeight = 338;
    }
    if (optTop == void(0) || optTop == "")
    {
    	optTop = 40;
    }
    if (optLeft == void(0) || optLeft == "")
    {
    	optLeft = screen.width - optWidth - 40;
    }

	if (closeOnError == void(0))
	{
		closeOnError = true;
	}
	
    // Se tem um retorno...

    if (ponteiroRetorno != void(0) && ponteiroRetorno != "")
    {

        // ...define o ponteiro para o retorno

        window.ponteiroRetornoPopUp = ponteiroRetorno;

        // ...seta o tipo de retorno = "campo" ou "funcao"

        if (typeof ponteiroRetorno == "function")
        {
            window.tipoRetornoPopup = "funcao";
        }
        else if (typeof ponteiroRetorno == "string")
        {
            window.tipoRetornoPopup = "campo";
        }
    }
    else
    {
        window.ponteiroRetornoPopUp = void(0);
    }

    // Abre PopUp

    // winpops = window.open(popURL,'PopupWindow','width=' + optWidth + ',left=' + optLeft + ',top=' + optTop + ',height=' + optHeight + ', resizable, status, scrollbars, ');
    
	exibirSubModal (tituloPopup, popURL, urlFecharPopup, true, false, '', null, closeOnError);

	return;
}

function openPopUpURLExterna(popURL, optWidth, optHeight, optTop, optLeft)
{
    // Valida par?metros obrigat?rios

    if (popURL == void(0) || popURL == "")
    {
    	return;
    }

    // Verifica valor dos par?metros opcionais

    if (optWidth == void(0) || optWidth == "")
    {
    	optWidth = 630;
    }
    if (optHeight == void(0) || optHeight == "")
    {
    	optHeight = 440;
    }
    if (optTop == void(0) || optTop == "")
    {
    	optTop = 20;
    }
    if (optLeft == void(0) || optLeft == "")
    {
    	optLeft = screen.width - optWidth - 40;
    	
    	if (optLeft < 0)
    	{
    		optLeft = 0;
    	}
    }

    // Abre PopUp
    winpops = window.open(popURL,'PopupWindow','width=' + optWidth + ',left=' + optLeft + ',top=' + optTop + ',height=' + optHeight + ', resizable, status, scrollbars, ');

	avisarProcessamento(false);

	return;
}

function returnConsultaPopup(valorRetorno)
{
    // Verificar se o formul?rio pai ainda existe

    if (window.top.ponteiroRetornoPopUp != void(0))
    {

        // Verifica o tipo de retorno e passa para a fun??o

        if (window.top.tipoRetornoPopup == "funcao")
        {
        	// Passa o valor de retorno para a funcao

            arrayInicial = valorRetorno.split(":|:");

            arrayDescricao = arrayInicial[0].split(";");

            valorRetorno = "'" + arrayInicial[1] + "', '" + arrayDescricao[0] + "', '" + arrayInicial[2] + "', '" + arrayDescricao[0] + ";" + arrayInicial[0].replace("'", "\\u0027") + "'";

        	eval("window.top.ponteiroRetornoPopUp (" + valorRetorno + ")");
        }
        else if (window.top.tipoRetornoPopup == "campo")
        {
            // Se o campo informado existe...

            if (window.top.document.forms[0].elements[window.top.ponteiroRetornoPopUp])
            {
                // ...atribuir o valor de retorno ao campo no formul?rio principal

                window.top.document.forms[0].elements[window.top.ponteiroRetornoPopUp].value = valorRetorno;
            }
        }
    }

	if (window.top.ponteiroFuncaoExtra != void(0))
       	eval("window.top.ponteiroFuncaoExtra (" + valorRetorno + ")");

    // Fechar a janela pop-up
	window.top.hidePopWin(false);
	
	return;
}

function returnPopUp(valorRetorno)
{
    if (window.top.ponteiroRetornoPopUp != void(0))
    {

        // Verifica o tipo de retorno e passa para a fun??o

        if (window.top.tipoRetornoPopup == "funcao")
        {
        	// Passa o valor de retorno para a funcao

            arrayInicial = valorRetorno.split(":|:");
            
            arrayDescricao = arrayInicial[0].split(";");

            if (arrayInicial[1] != void(0) && arrayInicial[2] != void(0))
            {
                valorRetorno = "'" + arrayInicial[1] + "', '" + arrayDescricao[0] + "', '" + arrayInicial[2] + "', '" + arrayInicial[0] + "'";
            }
            else
            {
                valorRetorno = "'" + valorRetorno + "'";
            }

        	eval("window.top.ponteiroRetornoPopUp (" + valorRetorno + ")");
        }
        else if (window.top.tipoRetornoPopup == "campo")
        {
            // Se o campo informado existe...

            if (window.top.document.forms[0].elements[window.top.ponteiroRetornoPopUp])
            {
                // ...atribuir o valor de retorno ao campo no formul?rio principal

                window.top.document.forms[0].elements[window.top.ponteiroRetornoPopUp].value = valorRetorno;
            }
        }
    }

    // Fechar a janela pop-up
	window.top.hidePopWin(false);

	return;
}
