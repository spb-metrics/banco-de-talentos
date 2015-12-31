function verificaCategoriaAtributoTalentoComFilhos(item)
{
    if (strCategoriaAtributoTalentoComFilhos.indexOf(item.name.substring(24, item.name.length - 1) + ",") >=0 )
    {
        var strIdeCategoriaAtributo = item.name.substring(24, item.name.length - 1);
        var strIdeAtributoTalentoOpcao = item.value;
        document.forms[0].action = ('detalheTalentoBuscarOpcao.do?categoriaAtributoTalento=' + strIdeCategoriaAtributo +
                '&atributoTalentoOpcao=' + strIdeAtributoTalentoOpcao);
        avisarProcessamento();
        document.forms[0].submit();
    }
}

function direcionarAcaoExtendida()
{
    if (document.forms[0].identificador.value == '')
    {
        document.forms[0].action = 'encaminhamentoTalentoEfetuarInclusao.do';
    }
    else
    {
        document.forms[0].action = 'talentoEfetuarAlteracao.do';
    }
}

function capturarAtributoTalentoOpcao(retorno)
{    
    if (retorno != '')
    {
        avisarProcessamento();
        document.forms[0].action = 'detalheTalentoBuscarHierarquia.do?atributoTalentoOpcao=' + retorno;
        document.forms[0].submit();
        
    }
}
