function direcionarAcao(destino)
{
    var origem = obterPagina(document.URL);


    if (destino == '')
    {

        // Origem com somente uma acao no Próximo Destino

        if (origem == 'detalheTalentoBuscarHierarquia.do')
        {
            document.forms[0].action = 'detalheTalentoBuscarHierarquia.do';
        }
        else
        if (origem == 'detalheTalentoBuscarOpcao.do')
        {
            document.forms[0].action = 'detalheTalentoBuscarOpcao.do';
        }
        else
        if (origem == 'detalheTalentoEncaminharInclusao.do')
        {
            document.forms[0].action = 'encaminhamentoTalentoEfetuarInclusao.do';
        }
        else
        if (origem == 'detalheTalentoPrepararAlteracao.do')
        {
            document.forms[0].action = 'talentoEfetuarAlteracao.do';
        }
        else
        if (origem == 'talentoEfetuarAlteracao.do')
        {
            document.forms[0].action = 'talentoEfetuarAlteracao.do';
        }
        else
        if (origem == 'detalheTalentoPrepararInclusao.do')
        {
            document.forms[0].action = 'encaminhamentoTalentoEfetuarInclusao.do';
        }
        else
        if (origem == 'encaminhamentoTalentoEfetuarInclusao.do')
        {
            document.forms[0].action = 'encaminhamentoTalentoEfetuarInclusao.do';
        }

    }
    else
    {

        // Origem com mais de uma acao no Próximo Destino

        document.forms[0].action = destino;

    }

}
