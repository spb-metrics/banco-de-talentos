function direcionarAcao(destino)
{
    var origem = obterPagina(document.URL);


    if (destino == '')
    {

        // Origem com somente uma acao no Próximo Destino

        if (origem == 'filtroConsultaGrupoEfetuarAtualizacao.do')
        {
            document.forms[0].action = 'filtroConsultaGrupoEfetuarAtualizacao.do';
        }
        else
        if (origem == 'filtroConsultaGrupoPrepararVisualizacao.do')
        {
            document.forms[0].action = 'filtroConsultaGrupoEfetuarAtualizacao.do';
        }
        else
        if (origem == 'filtroConsultaGrupoEfetuarAtualizacao.do')
        {
            document.forms[0].action = 'filtroConsultaGrupoEfetuarAtualizacao.do';
        }

    }
    else
    {

        // Origem com mais de uma acao no Próximo Destino

        document.forms[0].action = destino;

    }

}
