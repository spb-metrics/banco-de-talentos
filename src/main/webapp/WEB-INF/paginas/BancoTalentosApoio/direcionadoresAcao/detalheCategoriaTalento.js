function direcionarAcao(destino)
{
    var origem = obterPagina(document.URL);


    if (destino == '')
    {

        // Origem com somente uma acao no Próximo Destino

        if (origem == 'detalheCategoriaTalentoEfetuarInclusao.do')
        {
            document.forms[0].action = 'detalheCategoriaTalentoEfetuarInclusao.do';
        }
        else
        if (origem == 'detalheCategoriaTalentoPrepararAlteracao.do')
        {
            document.forms[0].action = 'categoriaTalentoEfetuarAlteracao.do';
        }
        else
        if (origem == 'categoriaTalentoEfetuarAlteracao.do')
        {
            document.forms[0].action = 'categoriaTalentoEfetuarAlteracao.do';
        }
        else
        if (origem == 'detalheCategoriaTalentoPrepararInclusao.do')
        {
            document.forms[0].action = 'detalheCategoriaTalentoEfetuarInclusao.do';
        }
        else
        if (origem == 'detalheCategoriaTalentoEfetuarInclusao.do')
        {
            document.forms[0].action = 'detalheCategoriaTalentoEfetuarInclusao.do';
        }

    }
    else
    {

        // Origem com mais de uma acao no Próximo Destino

        document.forms[0].action = destino;

    }

}
