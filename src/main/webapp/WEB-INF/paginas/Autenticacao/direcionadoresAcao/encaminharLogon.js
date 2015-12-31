function direcionarAcao(destino)
{
    var origem = obterPagina(document.URL);


    if (destino == '')
    {

        // Origem com somente uma acao no Próximo Destino

        if (origem == 'encaminharLogon.do')
        {
            document.forms[0].action = 'validarLogonEfetuarLogon.do';
        }
        else
        if (origem == 'validarLogonEfetuarLogon.do')
        {
            document.forms[0].action = 'validarLogonEfetuarLogon.do';
        }
        else
        if (origem == 'validarAutenticacaoExterna.do')
        {
            document.forms[0].action = 'validarLogonEfetuarLogon.do';
        }

    }
    else
    {

        // Origem com mais de uma acao no Próximo Destino

        document.forms[0].action = destino;

    }

}
