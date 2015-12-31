function direcionarAcao(destino)
{
    var origem = obterPagina(document.URL);


    if (destino == '')
    {

        // Origem com somente uma acao no Próximo Destino

        if (origem == 'detalheTalentoPrepararAlteracao.do')
        {
            document.forms[0].action = 'detalheTalentoPrepararAlteracao.do';
        }
        else
        if (origem == 'detalheTalentoPrepararInclusao.do')
        {
            document.forms[0].action = 'detalheTalentoPrepararInclusao.do';
        }
        else
        if (origem == 'exclusaoTalentoPrepararExclusao.do')
        {
            document.forms[0].action = 'exclusaoTalentoPrepararExclusao.do';
        }

    }
    else
    {

        // Origem com mais de uma acao no Próximo Destino

        document.forms[0].action = destino;

    }

}
