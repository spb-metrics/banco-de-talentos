function direcionarAcao(destino)
{
    var origem = obterPagina(document.URL);


    if (destino == '')
    {

        // Origem com somente uma acao no Pr�ximo Destino

        if (origem == 'ordenacaoCategoriaTalentoPrepararVisualizacao.do')
        {
            document.forms[0].action = 'categoriaTalentoEfetuarOrdenacao.do';
        }
        else
        if (origem == 'categoriaTalentoEfetuarOrdenacao.do')
        {
            document.forms[0].action = 'categoriaTalentoEfetuarOrdenacao.do';
        }

    }
    else
    {

        // Origem com mais de uma acao no Pr�ximo Destino

        document.forms[0].action = destino;

    }

}
