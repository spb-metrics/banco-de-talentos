function direcionarAcao(destino)
{
    var origem = obterPagina(document.URL);


    if (destino == '')
    {

        // Origem com somente uma acao no Próximo Destino

        if (origem == 'mensagemGlobalEfetuarAlteracao.do')
        {
            document.forms[0].action = 'mensagemGlobalEfetuarAlteracao.do';
        }
        else
        if (origem == 'mensagemGlobalPrepararVisualizacao.do')
        {
            document.forms[0].action = 'mensagemGlobalEfetuarAlteracao.do';
        }
        else
        if (origem == 'mensagemGlobalEfetuarAlteracao.do')
        {
            document.forms[0].action = 'mensagemGlobalEfetuarAlteracao.do';
        }

    }
    else
    {

        // Origem com mais de uma acao no Próximo Destino

        document.forms[0].action = destino;

    }

}
