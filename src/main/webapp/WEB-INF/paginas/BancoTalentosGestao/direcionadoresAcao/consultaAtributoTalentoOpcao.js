function direcionarAcao(destino)
{
    var origem = obterPagina(document.URL);


    if (destino == '')
    {

        // Origem com somente uma acao no Próximo Destino

        if (origem == 'consultaAtributoTalentoOpcaoPaginarListagem.do')
        {
            document.forms[0].action = 'consultaAtributoTalentoOpcaoEfetuarConsulta.do';
        }
        else
        if (origem == 'consultaAtributoTalentoOpcaoEfetuarConsulta.do')
        {
            document.forms[0].action = 'consultaAtributoTalentoOpcaoEfetuarConsulta.do';
        }
        else
        if (origem == 'consultaAtributoTalentoOpcaoPrepararVisualizacao.do')
        {
            document.forms[0].action = 'consultaAtributoTalentoOpcaoEfetuarConsulta.do';
        }

    }
    else
    {

        // Origem com mais de uma acao no Próximo Destino

        document.forms[0].action = destino;

    }

}
