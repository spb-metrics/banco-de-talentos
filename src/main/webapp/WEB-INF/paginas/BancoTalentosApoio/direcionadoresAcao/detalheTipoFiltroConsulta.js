function direcionarAcao(destino)
{
    var origem = obterPagina(document.URL);


    if (destino == '')
    {

        // Origem com somente uma acao no Pr�ximo Destino

        if (origem == 'detalheTipoFiltroConsultaEfetuarInclusao.do')
        {
            document.forms[0].action = 'detalheTipoFiltroConsultaEfetuarInclusao.do';
        }
        else
        if (origem == 'detalheTipoFiltroConsultaPrepararAlteracao.do')
        {
            document.forms[0].action = 'tipoFiltroConsultaEfetuarAlteracao.do';
        }
        else
        if (origem == 'tipoFiltroConsultaEfetuarAlteracao.do')
        {
            document.forms[0].action = 'tipoFiltroConsultaEfetuarAlteracao.do';
        }
        else
        if (origem == 'detalheTipoFiltroConsultaPrepararInclusao.do')
        {
            document.forms[0].action = 'detalheTipoFiltroConsultaEfetuarInclusao.do';
        }
        else
        if (origem == 'detalheTipoFiltroConsultaEfetuarInclusao.do')
        {
            document.forms[0].action = 'detalheTipoFiltroConsultaEfetuarInclusao.do';
        }

    }
    else
    {

        // Origem com mais de uma acao no Pr�ximo Destino

        document.forms[0].action = destino;

    }

}
