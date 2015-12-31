function direcionarAcao(destino)
{
    var origem = obterPagina(document.URL);


    if (destino == '')
    {

        // Origem com somente uma acao no Próximo Destino

        if (origem == 'consultaUsuarioPaginarListagem.do')
        {
            document.forms[0].action = 'consultaUsuarioEfetuarConsulta.do';
        }
        else
        if (origem == 'consultaUsuarioEfetuarConsulta.do')
        {
            document.forms[0].action = 'consultaUsuarioEfetuarConsulta.do';
        }
        else
        if (origem == 'consultaUsuarioPrepararVisualizacao.do')
        {
            document.forms[0].action = 'consultaUsuarioEfetuarConsulta.do';
        }

    }
    else
    {

        // Origem com mais de uma acao no Próximo Destino

        document.forms[0].action = destino;

    }

}
