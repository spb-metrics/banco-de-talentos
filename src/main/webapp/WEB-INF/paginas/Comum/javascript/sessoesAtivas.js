function checkUnckeAll()
{
    // � necess�rio habilitar o primeiro campo no caso de n�o haver mais de um registro/checkbox, ou seja
    // havendo um checkbox n�o ser� feito um array de ideFuncionalidadeSistema
    document.forms[0].ideSessao.checked = document.forms[0].checkUncked.checked;
    for (i = 0; i < document.forms[0].ideSessao.length; i++) {
        document.forms[0].ideSessao[i].checked = document.forms[0].checkUncked.checked;
    }
}

function submeterForm()
{
    avisarProcessamento(true);
    direcionarAcao('exclusaoSessaoAtivaPrepararConfirmacao.do');
    document.forms[0].submit();
}
