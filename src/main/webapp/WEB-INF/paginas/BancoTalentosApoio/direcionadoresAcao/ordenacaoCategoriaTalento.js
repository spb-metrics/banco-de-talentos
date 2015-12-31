function direcionarAcao(destino)
{
    var origem = obterPagina(document.URL);


    if (destino == '')
    {

        // Origem com somente uma acao no Próximo Destino

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

        // Origem com mais de uma acao no Próximo Destino

        document.forms[0].action = destino;

    }

}
