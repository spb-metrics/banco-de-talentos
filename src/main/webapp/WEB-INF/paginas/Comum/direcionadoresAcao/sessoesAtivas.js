function direcionarAcao(destino)
{
    var origem = obterPagina(document.URL);


    if (destino == '')
    {

        // Origem com somente uma acao no Pr�ximo Destino

        if (origem == 'sessoesAtivasEfetuarExclusao.do')
        {
            document.forms[0].action = 'exclusaoSessaoAtivaPrepararConfirmacao.do';
        }
        else
        if (origem == 'sessoesAtivasPrepararVisualizacao.do')
        {
            document.forms[0].action = 'exclusaoSessaoAtivaPrepararConfirmacao.do';
        }
        else
        if (origem == 'exclusaoSessaoAtivaPrepararConfirmacao.do')
        {
            document.forms[0].action = 'exclusaoSessaoAtivaPrepararConfirmacao.do';
        }

    }
    else
    {

        // Origem com mais de uma acao no Pr�ximo Destino

        document.forms[0].action = destino;

    }

}
