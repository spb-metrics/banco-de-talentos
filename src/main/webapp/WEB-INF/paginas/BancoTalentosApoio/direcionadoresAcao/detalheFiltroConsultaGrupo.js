function direcionarAcao(destino)
{
    var origem = obterPagina(document.URL);


    if (destino == '')
    {

        // Origem com somente uma acao no Próximo Destino

        if (origem == 'detalheFiltroConsultaGrupoEfetuarInclusao.do')
        {
            document.forms[0].action = 'detalheFiltroConsultaGrupoEfetuarInclusao.do';
        }
        else
        if (origem == 'detalheFiltroConsultaGrupoPrepararAlteracao.do')
        {
            document.forms[0].action = 'filtroConsultaGrupoEfetuarAlteracao.do';
        }
        else
        if (origem == 'filtroConsultaGrupoEfetuarAlteracao.do')
        {
            document.forms[0].action = 'filtroConsultaGrupoEfetuarAlteracao.do';
        }
        else
        if (origem == 'detalheFiltroConsultaGrupoPrepararInclusao.do')
        {
            document.forms[0].action = 'detalheFiltroConsultaGrupoEfetuarInclusao.do';
        }
        else
        if (origem == 'detalheFiltroConsultaGrupoEfetuarInclusao.do')
        {
            document.forms[0].action = 'detalheFiltroConsultaGrupoEfetuarInclusao.do';
        }

    }
    else
    {

        // Origem com mais de uma acao no Próximo Destino

        document.forms[0].action = destino;

    }

}
