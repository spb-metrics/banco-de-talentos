var acaoEscolhida;
var nomeFuncaoValidacaoEscolhida;

/* Exemplo de utilização: 
**    if (_browserId.ie && _browserId.ver < 7) .... 
**    // -> Se for o Internet Explorer e a versão for anterior à 7 ....
*/
function lib_bwcheck(){ //Browsercheck new for ie7 (needed)
	this.ver=navigator.appVersion;
	this.agent=navigator.userAgent;
	this.dom=document.getElementById?1:0
	this.ie5=(this.ver.indexOf("MSIE 5")>-1 && this.dom)?1:0;
	this.ie6=(this.ver.indexOf("MSIE 6")>-1 && this.dom)?1:0;
	this.ie7=(this.ver.indexOf("MSIE 7")>-1 && this.dom)?1:0;
    this.ie8=(this.ver.indexOf("MSIE 8")>-1 && this.dom)?1:0;
    this.ieX=(this.ver.indexOf("MSIE ")>-1 && this.dom)?1:0; // Será que resolve o problema do IE9?
	this.ie4=(document.all && !this.dom)?1:0;
	this.ie=this.ie4||this.ie5||this.ie6||this.ie7||this.ie8||this.ieX;
	this.mac=this.agent.indexOf("Mac")>-1;
	this.opera5=this.agent.indexOf("Opera 5")>-1;
	this.ns6=(this.dom && parseInt(this.ver) >= 5) ?1:0;
	this.ns7=(this.dom && parseInt(this.ver) >= 5) ?1:0;
	this.ns4=(document.layers && !this.dom)?1:0;
	this.bw=(this.ieX || this.ie8 || this.ie7 || this.ie6 || this.ie5 || this.ie4 || this.ns4 || this.ns6 || this.opera5 || this.dom);
	this.vernum = (this.ie)?parseInt(this.ver.charAt(this.ver.indexOf("MSIE ") + 5), 10):0;
	return this;
} 
var _browserId = new lib_bwcheck();

function pegaNome(nome)
{
    for (var j = 0; j < document.forms.length; j++)
    {
        for (var i = 0; i < document.forms[j].length; i++)
        {
            if(document.forms[j].elements[i].name == nome)
            {
                return document.forms[j].elements[i];
            }
        }
    }
}

function obterPagina(ref)
{

    ref = unescape(ref)
	
    if (ref.indexOf('?')!=-1)
    {
    	ref = ref.substring(1,ref.indexOf('?') + 1);
        ref = ref.substring(ref.lastIndexOf('/') + 1, ref.indexOf('?'));
    }
    else
    {
        ref = ref.substring(ref.lastIndexOf('/') + 1);
    }

    if (ref.indexOf(';')!=-1)
    {
    	ref = ref.substring(0, ref.indexOf(';'));
    }

    return ref;
}

//Valida se a data inicial ? maior ou nao est?
function validaIntervaloData(campo1, campo2){
    data1 = campo1.value;
    data2 = campo2.value;
    if(data1!= null && data1 != ""){
	    data1 = trim(data1);
    } else {
	    data1 = "";
    }
    if(data1!= null && data1 != ""){
	    data2 = trim(data2);
	} else {
		data2 = "";
	}
    if(data2 != "" && data1 == ""){
        campo1.focus();
        return false;
    } else {
        if(trim(data2) == "" )
        {
            //N?o h? a segunda data,entao tudo bem...
            return true;
        }        
        
    }
    
    var dataInicial = data1.split("/");
    
    diaInicial = dataInicial[0];
    mesInicial = dataInicial[1];
    anoInicial = dataInicial[2];
    
    inicio = new Date();
    fim = new Date();

    inicio.setTime(0);        
    fim.setTime(0);

    inicio.setDate(parseInt(diaInicial,10));
    inicio.setMonth(parseInt(mesInicial,10)-1);
    inicio.setYear(parseInt(anoInicial,10));

   
    dataFim = data2.split("/");    

    diaFim = dataFim[0];
    mesFim = dataFim[1];
    anoFim = dataFim[2];

    fim.setDate(parseInt(diaFim,10));
    fim.setMonth(parseInt(mesFim,10)-1);
    fim.setYear(parseInt(anoFim,10));
    
    if(inicio.getTime() > fim.getTime() ) {
        return false;
    } else {
        return true;
    }
    
}

function obterPaginaParametro(ref)
{
    ref = ref.substring(ref.lastIndexOf('/') + 1);

    return ref;
}

function moveIt(moveFrom,moveTo)
{
    var cntA = 0;
    for (var i=0; i < document.forms[0][moveFrom].options.length; i++) if (document.forms[0][moveFrom].options[i].selected) cntA = cntA + 1;
    for (var i=0; i < document.forms[0][moveFrom].options.length; i++)
    {
        if (document.forms[0][moveFrom].options[i].selected)
        {
            p = document.forms[0][moveFrom].options[i];
            x = new Option(p.text,p.value);
            document.forms[0][moveTo].options[document.forms[0][moveTo].options.length] = x;
            document.forms[0][moveFrom].options[i] = null;
            i = i - 1;
        }
    }
}

function incluiItemLista(lista, valor)
{
	valor = trim(valor);
	if (! isNumerico(valor)) 
	{
		alert('Neste campo s? ? permitido incluir num?ricos');
		return false;		
	}
    for (var i=0; i < document.forms[0][lista].options.length; i++)
    {
    	if (valor == "") 
    		return false; 
        if (document.forms[0][lista].options[i].value == valor)
        	return false;
    }
    x = new Option(valor,valor);
    document.forms[0][lista].options[document.forms[0][lista].options.length] = x;
}

function removeItemLista(lista)
{
    for (var i=0; i < document.forms[0][lista].options.length; i++)
    {
        if (document.forms[0][lista].options[i].selected)
        {
            document.forms[0][lista].options[i] = null;
            i = i - 1;
        }
    }
}

function ordenar(down, col)
{
    sl = document.forms[0][col].selectedIndex;
    if (sl != -1 && document.forms[0][col].options[sl].value > "")
    {
        oText = document.forms[0][col].options[sl].text;
        oValue = document.forms[0][col].options[sl].value;
        if (document.forms[0][col].options[sl].value > "" && sl > 0 && down == 0)
        {
            document.forms[0][col].options[sl].text = document.forms[0][col].options[sl-1].text;
            document.forms[0][col].options[sl].value = document.forms[0][col].options[sl-1].value;
            document.forms[0][col].options[sl-1].text = oText;
            document.forms[0][col].options[sl-1].value = oValue;
            document.forms[0][col].selectedIndex--;
        }
        else if (sl < document.forms[0][col].length-1 && document.forms[0][col].options[sl+1].value > "" && down == 1)
        {
            document.forms[0][col].options[sl].text = document.forms[0][col].options[sl+1].text;
            document.forms[0][col].options[sl].value = document.forms[0][col].options[sl+1].value;
            document.forms[0][col].options[sl+1].text = oText;
            document.forms[0][col].options[sl+1].value = oValue;
            document.forms[0][col].selectedIndex++;
        }
    }
    else
    {
        alert("Selecione um item");
    }
}

function selectTeam(texto, lista)
{
    var mt;
   
    mt = document.forms[0][lista];
    if (texto != null && mt.options.length == 0)
    {
       alert(texto);
       return false;
    }
    else
    {
       for (var i=0; i < mt.options.length; i++)
       {
	       mt.options[i].selected = true
	   }
	   return true;
    }
}

function contarOpcoesSelectMultiple(mt)
{
	var contador = 0;

	for (var i=0; i < mt.options.length; i++)
	{
	   if (mt.options[i].selected)
	   {
	   		contador++;
	   }
	}

	return contador;
}

function selectEmptyTeam()
{
    var mt;
	
    for (var i = 0; i < document.forms[0].length; i++)
    {
        if(document.forms[0].elements[i].type == 'select-multiple')
        {
		    mt = document.forms[0].elements[i];
		    if (contarOpcoesSelectMultiple(mt) == 0)
		    {
				mt.options[0].selected = true
		    }
        }
    }
}

function obterContextPath(comProtocoloServidor)
{
	var retorno = "";
    ref = document.URL.split('/');
    if (comProtocoloServidor==true)
    {
    	retorno = ref[0] + ref[1] + '//' + ref[2] + '/' + ref[3];
    }
    else
    {
    	retorno = ref[3];
    }
    return retorno;
}

function avisarProcessamento(exibir)
{
    if (exibir == void(0))
    {
    	exibir = true;
    }

    if (exibir)
    {
        showtrail();
    }
    else
    {
        hidetrail();
    }
}

function trim(s)
{
    while (s.substring(0,1) == ' ')
    {
        s = s.substring(1,s.length);
    }
    while (s.substring(s.length-1,s.length) == ' ')
    {
        s = s.substring(0,s.length-1);
    }
    return s;
}

function isNumerico(num)
{
    for(i=0; i < num.length; i++)
    {
        if (isNaN(parseInt(num.substr(i,1))))
        {
            return false;
        }
    }
    return true;
}

function desabilitarEnter(event)
{
	//uso: onkeypress="return desabilitarEnter(event);"
	var retorno = true;
	var keyCode = obterCodigoTeclaPressionada(event);
	if (keyCode == 13)
	{
		retorno = false;
	}
	return retorno;
}

function desabilitarEnterLimitacao(event, field, limit)
{
	//uso: onkeypress="return desabilitarEnterLimitacao(event, this, 10);"
	var retorno = true;
	retorno = desabilitarEnter(event);
	if (retorno == true)
	{
		retorno = verificarLimitacao(field, limit);
	}
	return retorno;
}

function verificarLimitacao(field, limit)
{
	//uso: onkeypress="return verificarLimitacao(this, 10);"
	var retorno = true;
	if (field.value.length >= limit)
	{
		retorno = false;
	}
	return retorno;
}

function focarProximoCampo(field)
{
	var i;
	for (i = 0; i < field.form.elements.length; i++)
	{
		if (field == field.form.elements[i])
		{
			break;
		}
	}
	i = (i + 1) % field.form.elements.length;
	field.form.elements[i].focus();
}

function exibeEsconde(strDivID)
{
	objDivID = $(strDivID);

    if (objDivID.style.visibility == 'visible')
    {
        objDivID.style.visibility = 'hidden';
        objDivID.style.position = 'absolute';
    }
    else
    {
        objDivID.style.visibility = 'visible';
        objDivID.style.position = 'relative';
    }
}

// submete formul?rio para a acao indicada
function submeter(acao, nomeFuncaoValidacao)
{
	nomeFuncaoValidacao = nomeFuncaoValidacao || false;
	acao = acao || false;
	var podeSubmeter = true;

    avisarProcessamento();

    if (nomeFuncaoValidacao)
    {
		podeSubmeter = (eval(nomeFuncaoValidacao)(document.forms[0]));
    }

    if (podeSubmeter)
    {
		if (acao)
		{
	        document.forms[0].action = acao;
		}
		else
	    {
	        direcionarAcao('');
	    }
	    document.forms[0].submit();
    }
}

// verifica se usu?rio teclou enter e submete formul?rio
function keyDown(DnEvents) 
{
	// ve quando e o netscape ou IE
	k = (_browserId.ie) ? window.event.keyCode : DnEvents.which;
	if (k == 13) { 
		submeter(acaoEscolhida, nomeFuncaoValidacaoEscolhida);		
	}
}

function verificarEnter(acao, nomeFuncaoValidacao)
{
    acaoEscolhida = acao;
    nomeFuncaoValidacaoEscolhida = nomeFuncaoValidacao;

	document.onkeydown = keyDown; 
	if (!_browserId.ie) document.captureEvents(Event.KEYDOWN|Event.KEYUP);
}

function mascararCEP (keypress, objeto)
{
	campo = eval (objeto);
	caracteres = '01234567890';
	separacoes = 1;
	separacao1 = '-';
	conjuntos = 2;
	conjunto1 = 5;
	conjunto2 = 3;
	if ((caracteres.search(String.fromCharCode (keypress))!=-1) && campo.value.length < (conjunto1 + conjunto2 + 1))
	{
		if (campo.value.length == conjunto1)
		campo.value = campo.value + separacao1;
	}
	else
	{
		event.returnValue = false;
	}
}

function mascararCPF(objeto,tammax,keypress) {
	var tecla = keypress;
	vr = objeto.value;
	vr = vr.replace( "/", "" );
	vr = vr.replace( "/", "" );
	vr = vr.replace( ",", "" );
	vr = vr.replace( ".", "" );
	vr = vr.replace( ".", "" );
	vr = vr.replace( ".", "" );
	vr = vr.replace( ".", "" );
	vr = vr.replace( "-", "" );
	vr = vr.replace( "-", "" );
	vr = vr.replace( "-", "" );
	vr = vr.replace( "-", "" );
	vr = vr.replace( "-", "" );
	tam = vr.length;
	
	if (tam < tammax && tecla != 8)
	{ 
		tam = vr.length + 1 ; 
	}
	
	if (tecla == 8 )
	{	
		tam = tam - 1 ; 
	}
	
	if ( tecla == 8 || tecla >= 48 && tecla <= 57 || tecla >= 96 && tecla <= 105 )
	{
		if ( tam <= 2 )
		{ 
	 		objeto.value = vr ; 
	 	}
	 	
	 	if ( (tam > 2) && (tam <= 5) )
	 	{
	 		objeto.value = vr.substr( 0, tam - 2 ) + '-' + vr.substr( tam - 2, tam ) ; 
	 	}
	 	
	 	if ( (tam >= 6) && (tam <= 8) )
	 	{
	 		objeto.value = vr.substr( 0, tam - 5 ) + '.' + vr.substr( tam - 5, 3 ) + '-' + vr.substr( tam - 2, tam ) ; 
	 	}
	 	
	 	if ( (tam >= 9) && (tam <= 11) )
	 	{
	 		objeto.value = vr.substr( 0, tam - 8 ) + '.' + vr.substr( tam - 8, 3 ) + '.' + vr.substr( tam - 5, 3 ) + '-' + vr.substr( tam - 2, tam ) ; 
	 	}
	 	
	 	if ( (tam >= 12) && (tam <= 14) )
	 	{
	 		objeto.value = vr.substr( 0, tam - 11 ) + '.' + vr.substr( tam - 11, 3 ) + '.' + vr.substr( tam - 8, 3 ) + '.' + vr.substr( tam - 5, 3 ) + '-' + vr.substr( tam - 2, tam ) ; 
	 	}
	 	
	 	if ( (tam >= 15) && (tam <= 17) )
	 	{
	 		objeto.value = vr.substr( 0, tam - 14 ) + '.' + vr.substr( tam - 14, 3 ) + '.' + vr.substr( tam - 11, 3 ) + '.' + vr.substr( tam - 8, 3 ) + '.' + vr.substr( tam - 5, 3 ) + '-' + vr.substr( tam - 2, tam );
	 	}
	}		
}

function mascararCGC(objeto,tammax,keypress){
	var tecla = keypress;
	vr = objeto.value;
	vr = vr.replace( "/", "" );
	vr = vr.replace( "/", "" );
	vr = vr.replace( "/", "" );
	vr = vr.replace( ",", "" );
	vr = vr.replace( ".", "" );
	vr = vr.replace( ".", "" );
	vr = vr.replace( ".", "" );
	vr = vr.replace( ".", "" );
	vr = vr.replace( ".", "" );
	vr = vr.replace( ".", "" );
	vr = vr.replace( ".", "" );
	vr = vr.replace( "-", "" );
	vr = vr.replace( "-", "" );
	vr = vr.replace( "-", "" );
	vr = vr.replace( "-", "" );
	vr = vr.replace( "-", "" );
	tam = vr.length;

	if (tam < tammax && tecla != 8)
	{
		tam = vr.length + 1 ; 
	}
	
	if (tecla == 8 )
	{
		tam = tam - 1 ; 
	}
		
	if ( tecla == 8 || tecla >= 48 && tecla <= 57 || tecla >= 96 && tecla <= 105 )
	{
		if ( tam <= 2 )
		{ 
	 		objeto.value = vr ; 
	 	}
	 	
	 	if ( (tam > 2) && (tam <= 6) )
	 	{
	 		objeto.value = vr.substr( 0, tam - 2 ) + '-' + vr.substr( tam - 2, tam ) ; 
	 	}
	 	
	 	if ( (tam >= 7) && (tam <= 9) )
	 	{
	 		objeto.value = vr.substr( 0, tam - 6 ) + '/' + vr.substr( tam - 6, 4 ) + '-' + vr.substr( tam - 2, tam ) ; 
	 	}
	 	
	 	if ( (tam >= 10) && (tam <= 12) )
	 	{
	 		objeto.value = vr.substr( 0, tam - 9 ) + '.' + vr.substr( tam - 9, 3 ) + '/' + vr.substr( tam - 6, 4 ) + '-' + vr.substr( tam - 2, tam ) ; 
	 	}
	 	
	 	if ( (tam >= 13) && (tam <= 14) )
	 	{
	 		objeto.value = vr.substr( 0, tam - 12 ) + '.' + vr.substr( tam - 12, 3 ) + '.' + vr.substr( tam - 9, 3 ) + '/' + vr.substr( tam - 6, 4 ) + '-' + vr.substr( tam - 2, tam ) ; 
	 	}
	 	
	 	if ( (tam >= 15) && (tam <= 17) )
	 	{
	 		objeto.value = vr.substr( 0, tam - 14 ) + '.' + vr.substr( tam - 14, 3 ) + '.' + vr.substr( tam - 11, 3 ) + '.' + vr.substr( tam - 8, 3 ) + '.' + vr.substr( tam - 5, 3 ) + '-' + vr.substr( tam - 2, tam ) ;
	 	}
	}
}

function mascararValorMonetario(campo, tammax, teclapres) {
	var tecla = teclapres.keyCode;
	vr = campo.value;
	vr = vr.replace( "/", "" );
	vr = vr.replace( "/", "" );
	vr = vr.replace( ",", "" );
	vr = vr.replace( ".", "" );
	vr = vr.replace( ".", "" );
	vr = vr.replace( ".", "" );
	vr = vr.replace( ".", "" );
	tam = vr.length;

	if (tam < tammax && tecla != 8){ tam = vr.length + 1 ; }

	if (tecla == 8 ){	tam = tam - 1 ; }

	if ( tecla == 8 || tecla >= 48 && tecla <= 57 || tecla >= 96 && tecla <= 105 ){
		if ( tam <= 2 ){ 
	 		campo.value = vr ; }
	 	if ( (tam > 2) && (tam <= 5) ){
	 		campo.value = vr.substr( 0, tam - 2 ) + ',' + vr.substr( tam - 2, tam ) ; }
	 	if ( (tam >= 6) && (tam <= 8) ){
	 		campo.value = vr.substr( 0, tam - 5 ) + '.' + vr.substr( tam - 5, 3 ) + ',' + vr.substr( tam - 2, tam ) ; }
	 	if ( (tam >= 9) && (tam <= 11) ){
	 		campo.value = vr.substr( 0, tam - 8 ) + '.' + vr.substr( tam - 8, 3 ) + '.' + vr.substr( tam - 5, 3 ) + ',' + vr.substr( tam - 2, tam ) ; }
	 	if ( (tam >= 12) && (tam <= 14) ){
	 		campo.value = vr.substr( 0, tam - 11 ) + '.' + vr.substr( tam - 11, 3 ) + '.' + vr.substr( tam - 8, 3 ) + '.' + vr.substr( tam - 5, 3 ) + ',' + vr.substr( tam - 2, tam ) ; }
	 	if ( (tam >= 15) && (tam <= 17) ){
	 		campo.value = vr.substr( 0, tam - 14 ) + '.' + vr.substr( tam - 14, 3 ) + '.' + vr.substr( tam - 11, 3 ) + '.' + vr.substr( tam - 8, 3 ) + '.' + vr.substr( tam - 5, 3 ) + ',' + vr.substr( tam - 2, tam ) ;}
	}
}

function calcularDias(dataInicial, dataFinal, destino)
{		
	//Transforma a data em um array
	var vetorDataInicial = dataInicial.split("/");
	var vetorDataFinal = dataFinal.split("/");

	//Calcula a diferen?a de dias
	var quantidadeDias = parseInt(vetorDataFinal[0] - vetorDataInicial[0]);

	if (quantidadeDias > 0){
    	quantidadeDias = quantidadeDias + 1;
	}
	

	//Calcula a diferen?a de meses
	var quantidadeMeses = parseInt(vetorDataFinal[1]) - parseInt(vetorDataInicial[1]);

	//Calcula a diferen?a de anos
	var quantidadeAnos = parseInt(vetorDataFinal[2] - vetorDataInicial[2]);

	//Transforma os meses em dias
	var iMesCorrente = parseInt(vetorDataInicial[1]);	

	//alert('Imes corrente ' + iMesCorrente);
	//alert('vetor ' + parseInt(vetorDataFinal[1]));
	//alert('vetor sem parse int' + vetorDataFinal[1]);

	while (iMesCorrente != parseInt(vetorDataFinal[1]))
	{
		var sinalMultiplicacao = 1;
		if (quantidadeMeses < 0) 
		{
			sinalMultiplicacao = -1;
		}

		if (iMesCorrente == 1 
			|| iMesCorrente == 3 
			|| iMesCorrente == 5 
			|| iMesCorrente == 7 
			|| iMesCorrente == 8
			|| iMesCorrente == 10
			|| iMesCorrente == 12)
		{
			quantidadeDias = quantidadeDias + (31 * sinalMultiplicacao);
		}
		else if (iMesCorrente == 2)
		{
			quantidadeDias = quantidadeDias + (28 * sinalMultiplicacao);
		}
		else
		{
			quantidadeDias = quantidadeDias + (30 * sinalMultiplicacao);
		}

		if (quantidadeMeses < 0)
		{
			iMesCorrente = iMesCorrente - 1;
		}
		else
		{
			iMesCorrente = iMesCorrente + 1;
		}
	}

	//Transforma os anos em dias
	var iAnoCorrente = parseInt(vetorDataInicial[2]);

	while (iAnoCorrente < parseInt(vetorDataFinal[2]))
	{
		//Verifica se o ano ? bissexto
		if ((iAnoCorrente % 4) == 0)
		{
			quantidadeDias = quantidadeDias + 366;
		}
		else
		{
			if (( (iAnoCorrente + 1) % 4) == 0 && parseInt(vetorDataFinal[1]) > 2 )
			{			
				quantidadeDias = quantidadeDias + 365 + 1;
			}
			else
			{
				quantidadeDias = quantidadeDias + 365;
			}
		}

		iAnoCorrente = iAnoCorrente + 1;
	}
	
	//Seta a data no campo de destino
	destino.value = quantidadeDias;
}

function calcularIdade(dataNascimento)
{
	dataAtual = new Date();			

	var dataInicial = dataNascimento.value;
	var dataFinal = dataAtual.getDate() + '/' + (dataAtual.getMonth() + 1) + '/' + dataAtual.getYear();
	
	//Transforma a data em um array
	var vetorDataInicial = dataInicial.split("/");
	var vetorDataFinal = dataFinal.split("/");

	//Calcula a diferen?a de anos
	var quantidadeAnos = parseInt(vetorDataFinal[2] - vetorDataInicial[2]);
	
	//Verifica se j? fez anivers?rio este ano
	if(parseInt(vetorDataFinal[1]) <  parseInt(vetorDataInicial[1]))
	{
		quantidadeAnos = quantidadeAnos - 1; 
	}
	else if (parseInt(vetorDataFinal[1]) ==  parseInt(vetorDataInicial[1]))
	{		
		if (parseInt(vetorDataFinal[0]) < parseInt(vetorDataInicial[0])){
			quantidadeAnos = quantidadeAnos - 1; 
		}		
	}
	
	return quantidadeAnos;
}

function verificarRadioSelecionado(radioButton, msgSelecaoObrigatoria)
{
	// set var radio_choice to false
	var radio_choice = false;

	if (obterRadioSelecionado(radioButton) == void(0))
	{
		// If there were no selections made display an alert box 
		alert(msgSelecaoObrigatoria);
		return (false);
	}
	else
	{
		return (true);
	}
}

function obterRadioSelecionado(radioButton)
{
	// set var radio_choice to false
	var radio_choice = void(0);
	
	// Loop from zero to the one minus the number of radio button selections
	for (counter = 0; counter < radioButton.length; counter++)
	{
		// If a radio button has been selected it will return true
		// (If not it will return false)
		if (radioButton[counter].checked)
			radio_choice = radioButton[counter].value;
	}
	
	return (radio_choice);
}

function selecionarRadio(radioButton, valor)
{
	// Loop from zero to the one minus the number of radio button selections
	for (counter = 0; counter < radioButton.length; counter++)
	{
		// If a radio button has been selected it will return true
		// (If not it will return false)
		if (radioButton[counter].value == valor)
			radioButton[counter].checked = true;
	}
}

function direcionarURL( url , stringParametros )
// Significado dos par?metros:
// url = endere?o da p?gina que se deseja carregar. Pode ser uma action.
// stringParametros = string com parâmetros adicionais (mais utilizado passando a queryString)
{
    if (stringParametros!=void(0))
    {
        if (url.indexOf('?') == -1)
        {
            document.location = url + "?" + stringParametros;
        }
        else
        {
            document.location = url + "&" + stringParametros;
        }
    }
    else
    {
        document.location = url;
    }
}

function direcionarPagina( pagina )
// Significado dos par?metros:
// url = endere?o da p?gina que se deseja carregar. Pode ser uma action.
// blnConcatenarQueryString: Se true, a fun??o pega concatena a url com a querystring que exista no request.
//                           Se false, apenas a url informada ser? utilizada para mostrar a pr?xima p?gina.
{
  	ref = document.URL;
    ref = ref.substring(0, ref.lastIndexOf('/') + 1);
    ref = ref + pagina;
    
    document.location = ref;
}

function permitirSomenteInteiroPositivo(campo)
{
	campo.value = filtrarConteudo(campo.value, '0123456789', false, true);
	return true;
}

function permitirInteiroPositivoNegativo(campo)
{
    campo.value = filtrarConteudo(campo.value, '0123456789', true, true);
    return true;
}

// Fun??o que permite somente numeros decimais positivos
function permitirSomenteDecimalPositivo(campo)
{
    campo.value = filtrarConteudo(campo.value, '0123456789', false, true, true);
    return true;
}

// Fun??o que remove toda a formata??o de um campo (tira os '.', ',' e '/')
function filtrarCampo(campo)
{
	campo.value = filtrarConteudo(campo.value);
}

// Fun??o que remove toda a formata??o de um campo (s? deixa o sinal, caso exista, e os n?meros)
function filtrarConteudo(conteudoAtual, caracteresPermitidos, tratarNegativo, truncar, tratarDecimal)
{
	var CARACTERES_PERMITIDOS = caracteresPermitidos || '0123456789';
	var tratarNegativo = tratarNegativo;
	if (tratarNegativo == void(0))
	{
	   tratarNegativo = true;
    }
    var tratarDecimal = tratarDecimal || false;
	var truncar = truncar || false;

	var novoConteudo = "";
	var cp = "";
	var sinal = "-";
	var caracterDecimal = ".";

	var tam = conteudoAtual.length;
    var posicaoInicialAnalise = 0;
	
	if (tratarNegativo)
	{
		negativo = false;
		if (conteudoAtual.substring(0,1)==sinal)
		{
			negativo = true;
			posicaoInicialAnalise = 1;
		}
	}

    var decimal = false;
	for (i = posicaoInicialAnalise; i < tam ; i++) 
	{  
	    var caracterAnalisado = conteudoAtual.substring(i, i + 1);
		if (CARACTERES_PERMITIDOS.indexOf(caracterAnalisado) == -1)
		{
		    if (tratarDecimal && caracterAnalisado == caracterDecimal)
		    {
	            if (decimal)
	            {
	                caracterAnalisado = "";
	            }
	            else
	            {
	                decimal = true;
	            }
		    }
            else
            {
                caracterAnalisado = "";
            }
		}
		
		if (caracterAnalisado == "")
		{
	        if (truncar)
	        {
	            break;
	        }
		}
		else
		{
		    novoConteudo = novoConteudo + caracterAnalisado;
		}
	}
	
	if (tratarNegativo)
	{
		if (negativo)
		{
			novoConteudo = "-" + novoConteudo;
		}
	}
	
	return novoConteudo;
}

// Fun??o que remove toda a formata??o de um campo (tira os '.', ',' e '/')
function removerZerosNaoSignificativos(valor)
{
	var s = "";
	var cp = "";

	vr = valor;
	tam = vr.length;

	for (i = 0; i < tam ; i++) 
	{  
		if ("-".indexOf(vr.substring(i,i + 1)) > -1)
		{
		 	s = s + vr.substring(i,i + 1);
		}
		else if ("0".indexOf(vr.substring(i,i + 1)) > -1)
		{
			// Zero n?o significativo -> remover
		}
		else
		{
			s = s + vr.substring(i);
			break;
		}
	}
	return s;
}

// Fun??o que formata um campo num?rico (colocando os '.' e ',')
function formatarValor(campo)
{
	campo.value = formatarConteudoValor(campo.value);
}

// Fun??o que formata um campo num?rico (colocando os '.' e ',')
function formatarConteudoValor(valor)
{
	vr = filtrarConteudo(valor);

	var sinal = "-";
	negativo = false;

	if (vr.substring(0,1)==sinal)
	{
		negativo = true;
		vr = vr.substring(1);
	}

	vr = removerZerosNaoSignificativos(vr);
	tam = vr.length;


	if ( tam == 0 )
	{
 		novoConteudo = "0,00" ; 
 	}
 	else if ( tam == 1)
 	{
 		novoConteudo = "0,0" + vr; 
 	}
 	else if ( tam == 2)
 	{
 		novoConteudo = "0," + vr; 
 	}
 	else if ( (tam > 2) && (tam <= 5) ) 
 	{
 		novoConteudo = vr.substr( 0, tam - 2 ) + ',' + vr.substr( tam - 2, tam ) ; 
	}
 	else if ( (tam >= 6) && (tam <= 8) ) 
 	{
 		novoConteudo = vr.substr( 0, tam - 5 ) + '.' + vr.substr( tam - 5, 3 ) + ',' + vr.substr( tam - 2, tam ) ; 
	}
 	else if ( (tam >= 9) && (tam <= 11) ) 
 	{
 		novoConteudo = vr.substr( 0, tam - 8 ) + '.' + vr.substr( tam - 8, 3 ) + '.' + vr.substr( tam - 5, 3 ) + ',' + vr.substr( tam - 2, tam ) ; 
	}
 	else if ( (tam >= 12) && (tam <= 14) ) 
 	{
 		novoConteudo = vr.substr( 0, tam - 11 ) + '.' + vr.substr( tam - 11, 3 ) + '.' + vr.substr( tam - 8, 3 ) + '.' + vr.substr( tam - 5, 3 ) + ',' + vr.substr( tam - 2, tam ) ; 
	}
 	else if ( (tam >= 15) && (tam <= 17) )
 	{
 		novoConteudo = vr.substr( 0, tam - 14 ) + '.' + vr.substr( tam - 14, 3 ) + '.' + vr.substr( tam - 11, 3 ) + '.' + vr.substr( tam - 8, 3 ) + '.' + vr.substr( tam - 5, 3 ) + ',' + vr.substr( tam - 2, tam ) ;
	}
	
	if (negativo)
	{
		novoConteudo = "-" + novoConteudo;
	}
 		
 	return novoConteudo;
}

// Fun??o que altera visibilidade de um controle
function habilitaDesabilita(objObjeto)
{
	if (objObjeto.style.display == "") 
	{
		objObjeto.style.display = "none";
	}
	else 
	{
		objObjeto.style.display = "";
	}
}

function desabilitarObjeto(objObjeto, blnValor) 
{
    objObjeto.disabled = blnValor;
}

function desabilitarElemento(elemento) 
{
    elemento.disabled = true;
    elemento.classNameOriginal = elemento.className; 
    elemento.className = "disabled";
    elemento.backgroundOriginal = elemento.style['background-color'];
    
    aplicarCorrecoesVisuais();
}

function habilitarElemento(elemento) 
{
    elemento.disabled = false;
     
    if (elemento.classNameOriginal != void(0))
    {
        if (elemento.classNameOriginal=="disabled")
        {
            elemento.className = '';
        }
        else
        {
            elemento.className = elemento.classNameOriginal;
        }
        elemento.classNameOriginal = void(0);
    }
    else
    {
        elemento.className = '';
    }

    if (elemento.backgroundOriginal != void(0))
    {
        elemento.style['background-color'] = elemento.backgroundOriginal;
        elemento.backgroundOriginal = void(0);
    }
    else
    {
        elemento.style['background-color'] = "#FFFFFF";
    }
    
    aplicarCorrecoesVisuais();
}

/** @deprecated: Utilizar _browserId.ie */
function isIE()
{
	return _browserId.ie;
}

function executarAoCarregar(funcao)
{
	var theBody;
	if (_browserId.ie) {
        theBody = document.getElementsByTagName("BODY")[0];
        theBody.onload = funcao;
	} else {
        theBody = window;
    	addEvent(theBody, 'load', funcao);
	}
}

function urlComRequestUID(url)
{
	var reqUID = ((url.indexOf("?")+1) ? "&" : "?") + "reqUID=" + new Date().getTime(); //Kill the Cache problem in IE.
	return url + reqUID;
}

/*
	Examples of how to call the function:

	To get all a elements in the document with a "info-links" class:
    getElementsByClassName("info-links", "a", document);

	To get all div elements within the element named "container", with a "col" and a "left" class:
    getElementsByClassName("col", "div", document.getElementById("container"));
*/
function getElementsByClassName(className, tag, elm){
	var testClass = new RegExp("(^|\\s)" + className + "(\\s|$)");
	var tag = tag || "*";
	var elm = elm || document;
	var elements = (tag == "*" && elm.all)? elm.all : elm.getElementsByTagName(tag);
	var returnElements = [];
	var current;
	var length = elements.length;
	for(var i=0; i<length; i++)
	{
		current = elements[i];
		if(testClass.test(current.className))
		{
			returnElements.push(current);
		}	
	}
	return returnElements;
}

function getElementsReadOnly(tag, elm){
    var tag = tag || "*";
    var elm = elm || document;
    var elements = (tag == "*" && elm.all)? elm.all : elm.getElementsByTagName(tag);
    var returnElements = [];
    var current;
    var length = elements.length;
    for(var i=0; i<length; i++)
    {
        current = elements[i];
        if(current.readOnly==true)
        {
            returnElements.push(current);
        }   
    }
    return returnElements;
}

// use $('myId') instead of document.getElementById('myId')
function $(strId)
{
	var i, arrReturn, arrStrId;

	if(strId instanceof Array)
	{
		arrStrId = strId;
	} 
	else if(arguments.length > 1)
	{
		arrStrId = new Array();
		for(i=0; i<arguments.length; i++)
			arrStrId.push(arguments[i]);
	}

	if(arrStrId instanceof Array)
	{
		arrReturn = new Array();
		for(i=0; i<arrStrId.length; i++)
			arrReturn[i] = document.getElementById(arrStrId[i]);
	}
	else
	{
		arrReturn = document.getElementById(strId);
	}

	return arrReturn;
}

/**
 * X-browser event handler attachment and detachment
 * TH: Switched first true to false per http://www.onlinetools.org/articles/unobtrusivejavascript/chapter4.html
 *
 * @argument obj - the object to attach event to
 * @argument evType - name of the event - DONT ADD "on", pass only "mouseover", etc
 * @argument fn - function to call
 */
function addEvent(obj, evType, fn, useCapture) {
    useCapture = (useCapture==void(0) || useCapture==null) ? false : useCapture; 
	if (obj.addEventListener){
		obj.addEventListener(evType, fn, useCapture);
		return true;
	} else if (obj.attachEvent){
		var r = obj.attachEvent("on"+evType, fn);
		return r;
	} else {
		el["on" + evType] = fn;
		return false;
	}
}

function removeEvent(obj, evType, fn, useCapture) {
	if (obj.removeEventListener){
		obj.removeEventListener(evType, fn, useCapture);
		return true;
	} else if (obj.detachEvent){
		var r = obj.detachEvent("on"+evType, fn);
		return r;
	} else {
		el["on" + evType] = null;
		return false;
	}
}

function aplicarAlteracoesIE() {
	// Se for IE, alterar a cor dos campos que são disabled
	var elementosDesabilitados = getElementsByClassName ("disabled");
	for (var i = 0; i < elementosDesabilitados.length; i++) {
		elementosDesabilitados [i].style['background-color'] = "#FFFFE6";
	}
	// Se for IE, alterar a cor dos campos que são readonly
	var elementosSomenteLeitura = getElementsByClassName ("readonly");
	for (var i = 0; i < elementosSomenteLeitura.length; i++) {
		elementosSomenteLeitura [i].style['background-color'] = "#eeffee";
	}
    var elementosReadOnly = getElementsReadOnly ();
    for (var i = 0; i < elementosReadOnly.length; i++) {
        elementosReadOnly[i].style['background-color'] = "#eeffee";
    }
}

function aplicarAlteracoesFirefox() {
	// Se for Firefox, alterar o estilo dos textarea de rolagem
	var elementosRolagem = getElementsByClassName ("rolagem");
	for (var i = 0; i < elementosRolagem.length; i++) {
		if (elementosRolagem[i].type == 'textarea' && elementosRolagem[i].getAttribute('rows') == '1') {
			elementosRolagem[i].style.height = '19px';
		}
	}
}

function aplicarCorrecoesVisuais() {
	if (_browserId.ie) {
		aplicarAlteracoesIE();
	} else {
		aplicarAlteracoesFirefox();
	}
}

// addJavascript('newExternal.js');
function addJavascript(jsname) {
	var th = document.getElementsByTagName('head')[0];
	var s = document.createElement('script');
	s.setAttribute('type','text/javascript');
	s.setAttribute('src',jsname);
	th.appendChild(s);
}