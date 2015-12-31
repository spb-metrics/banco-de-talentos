function direcionarAcao(destino)
{
    var origem = obterPagina(document.URL);


    if (destino == '')
    {

        // Origem com somente uma acao no Próximo Destino

        if (origem == 'consultaEstatisticaCadastroPrepararVisualizacao.do')
        {
            document.forms[0].action = 'resultadoConsultaEstatisticaCadastroEfetuarConsulta.do';
        }
        else
        if (origem == 'resultadoConsultaEstatisticaCadastroEfetuarConsulta.do')
        {
            document.forms[0].action = 'resultadoConsultaEstatisticaCadastroEfetuarConsulta.do';
        }

    }
    else
    {

        // Origem com mais de uma acao no Próximo Destino

        document.forms[0].action = destino;

    }

}
