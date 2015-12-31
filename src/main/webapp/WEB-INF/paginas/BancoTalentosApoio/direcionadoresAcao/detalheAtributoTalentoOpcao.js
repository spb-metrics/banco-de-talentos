function direcionarAcao(destino)
{
    var origem = obterPagina(document.URL);


    if (destino == '')
    {

        // Origem com somente uma acao no Próximo Destino

        if (origem == 'detalheAtributoTalentoOpcaoEfetuarInclusao.do')
        {
            document.forms[0].action = 'detalheAtributoTalentoOpcaoEfetuarInclusao.do';
        }
        else
        if (origem == 'detalheAtributoTalentoOpcaoPrepararAlteracao.do')
        {
            document.forms[0].action = 'atributoTalentoOpcaoEfetuarAlteracao.do';
        }
        else
        if (origem == 'atributoTalentoOpcaoEfetuarAlteracao.do')
        {
            document.forms[0].action = 'atributoTalentoOpcaoEfetuarAlteracao.do';
        }
        else
        if (origem == 'detalheAtributoTalentoOpcaoPrepararInclusao.do')
        {
            document.forms[0].action = 'detalheAtributoTalentoOpcaoEfetuarInclusao.do';
        }
        else
        if (origem == 'detalheAtributoTalentoOpcaoEfetuarInclusao.do')
        {
            document.forms[0].action = 'detalheAtributoTalentoOpcaoEfetuarInclusao.do';
        }

    }
    else
    {

        // Origem com mais de uma acao no Próximo Destino

        document.forms[0].action = destino;

    }

}
