function direcionarAcao(destino)
{
    var origem = obterPagina(document.URL);


    if (destino == '')
    {

        // Origem com somente uma acao no Pr�ximo Destino

        if (origem == 'detalheFiltroConsultaEfetuarInclusao.do')
        {
            document.forms[0].action = 'detalheFiltroConsultaEfetuarInclusao.do';
        }
        else
        if (origem == 'detalheFiltroConsultaPrepararAlteracao.do')
        {
            document.forms[0].action = 'filtroConsultaEfetuarAlteracao.do';
        }
        else
        if (origem == 'filtroConsultaEfetuarAlteracao.do')
        {
            document.forms[0].action = 'filtroConsultaEfetuarAlteracao.do';
        }
        else
        if (origem == 'detalheFiltroConsultaPrepararInclusao.do')
        {
            document.forms[0].action = 'detalheFiltroConsultaEfetuarInclusao.do';
        }
        else
        if (origem == 'detalheFiltroConsultaEfetuarInclusao.do')
        {
            document.forms[0].action = 'detalheFiltroConsultaEfetuarInclusao.do';
        }

    }
    else
    {

        // Origem com mais de uma acao no Pr�ximo Destino

        document.forms[0].action = destino;

    }

}
