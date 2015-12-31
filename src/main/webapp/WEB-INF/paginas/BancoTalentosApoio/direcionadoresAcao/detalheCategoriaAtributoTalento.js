function direcionarAcao(destino)
{
    var origem = obterPagina(document.URL);


    if (destino == '')
    {

        // Origem com somente uma acao no Próximo Destino

        if (origem == 'detalheCategoriaAtributoTalentoEfetuarInclusao.do')
        {
            document.forms[0].action = 'detalheCategoriaAtributoTalentoEfetuarInclusao.do';
        }
        else
        if (origem == 'detalheCategoriaAtributoTalentoPrepararAlteracao.do')
        {
            document.forms[0].action = 'categoriaAtributoTalentoEfetuarAlteracao.do';
        }
        else
        if (origem == 'categoriaAtributoTalentoEfetuarAlteracao.do')
        {
            document.forms[0].action = 'categoriaAtributoTalentoEfetuarAlteracao.do';
        }
        else
        if (origem == 'detalheCategoriaAtributoTalentoPrepararInclusao.do')
        {
            document.forms[0].action = 'detalheCategoriaAtributoTalentoEfetuarInclusao.do';
        }
        else
        if (origem == 'detalheCategoriaAtributoTalentoEfetuarInclusao.do')
        {
            document.forms[0].action = 'detalheCategoriaAtributoTalentoEfetuarInclusao.do';
        }

    }
    else
    {

        // Origem com mais de uma acao no Próximo Destino

        document.forms[0].action = destino;

    }

}
