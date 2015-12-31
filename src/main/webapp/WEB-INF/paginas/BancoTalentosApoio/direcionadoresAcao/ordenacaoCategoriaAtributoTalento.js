function direcionarAcao(destino)
{
    var origem = obterPagina(document.URL);


    if (destino == '')
    {

        // Origem com somente uma acao no Próximo Destino

        if (origem == 'categoriaAtributoTalentoEfetuarOrdenacao.do')
        {
            document.forms[0].action = 'categoriaAtributoTalentoEfetuarOrdenacao.do';
        }
        else
        if (origem == 'ordenacaoCategoriaAtributoTalentoPrepararVisualizacao.do')
        {
            document.forms[0].action = 'categoriaAtributoTalentoEfetuarOrdenacao.do';
        }

    }
    else
    {

        // Origem com mais de uma acao no Próximo Destino

        document.forms[0].action = destino;

    }

}
