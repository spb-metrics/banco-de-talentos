function direcionarAcao(destino)
{
    var origem = obterPagina(document.URL);


    if (destino == '')
    {

        // Origem com somente uma acao no Próximo Destino

        if (origem == 'detalheAtributoTalentoEfetuarInclusao.do')
        {
            document.forms[0].action = 'detalheAtributoTalentoEfetuarInclusao.do';
        }
        else
        if (origem == 'detalheAtributoTalentoPrepararAlteracao.do')
        {
            document.forms[0].action = 'atributoTalentoEfetuarAlteracao.do';
        }
        else
        if (origem == 'atributoTalentoEfetuarAlteracao.do')
        {
            document.forms[0].action = 'atributoTalentoEfetuarAlteracao.do';
        }
        else
        if (origem == 'detalheAtributoTalentoPrepararInclusao.do')
        {
            document.forms[0].action = 'detalheAtributoTalentoEfetuarInclusao.do';
        }
        else
        if (origem == 'detalheAtributoTalentoEfetuarInclusao.do')
        {
            document.forms[0].action = 'detalheAtributoTalentoEfetuarInclusao.do';
        }

    }
    else
    {

        // Origem com mais de uma acao no Próximo Destino

        document.forms[0].action = destino;

    }

}
