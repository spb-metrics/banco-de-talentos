function direcionarAcao(destino)
{
    var origem = obterPagina(document.URL);


    if (destino == '')
    {

        // Origem com somente uma acao no Pr�ximo Destino

        if (origem == 'detalheFiltroConsultaUsuarioEfetuarInclusao.do')
        {
            document.forms[0].action = 'detalheFiltroConsultaUsuarioEfetuarInclusao.do';
        }
        else
        if (origem == 'detalheFiltroConsultaUsuarioPrepararInclusao.do')
        {
            document.forms[0].action = 'detalheFiltroConsultaUsuarioEfetuarInclusao.do';
        }
        else
        if (origem == 'detalheFiltroConsultaUsuarioEfetuarInclusao.do')
        {
            document.forms[0].action = 'detalheFiltroConsultaUsuarioEfetuarInclusao.do';
        }

    }
    else
    {

        // Origem com mais de uma acao no Pr�ximo Destino

        document.forms[0].action = destino;

    }

}
