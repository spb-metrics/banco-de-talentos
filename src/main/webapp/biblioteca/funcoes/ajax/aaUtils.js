var AA_PREFIXO_ZONA_AJAX = 'aazone.'; 

/* Public - altera os links de paginação e ordenação de uma displaytag para 
 * utilizar o ajaxAnywhere na execução dessas solicitações
 * Parâmetro obrigatório: aaDisplayTagAAZone - Nome da zona ajax que contém a displayTag
 * Parâmetro opcional: aaAAZonesAtualizar - Nome(s) da(s) zona(s) ajax que deseja atualizar,
             se for diferente do parâmetro aaDisplayTagAAZone, nas operações da display (paginação, ordenação, ...) */
function configurarAADisplayTag(aaDisplayTagAAZone, aaAAZonesAtualizar) {
    // Validação
    if (aaDisplayTagAAZone==void(0) || aaDisplayTagAAZone==null || aaDisplayTagAAZone=="")
        return;
        
    if (aaAAZonesAtualizar==void(0) || aaAAZonesAtualizar==null || aaAAZonesAtualizar=="") {
        aaAAZonesAtualizar = aaDisplayTagAAZone;
    }

    // Delimita a zona de trabalho
    var aaZoneDT = $(AA_PREFIXO_ZONA_AJAX + aaDisplayTagAAZone);

    if (aaZoneDT != null) {
        // Recupera a displayTag em questão
        var aadt = getElementsByClassName('displaytag', 'table', aaZoneDT);
        if (aadt != null && aadt!=void(0) && aadt!="") {
            if (aadt.length == 0) { aadt = null; }
            else { aadt = aadt[0]; }

            if (aadt.configAADisplayTag != 1) {
		        // replace sortlinks <thead> (titles)
		        elmthead = aadt.getElementsByTagName('thead')[0];
		        var sortLinks = null;
		        if (elmthead != null) { sortLinks = elmthead.getElementsByTagName('a'); }
		        if (sortLinks != null) { ajaxifyLinks(sortLinks, aaAAZonesAtualizar); }
		
		        // replace pagelinks
		        var pagelinks = getElementsByClassName('pagelinks', 'span', aaZoneDT);
		        if (pagelinks != null && pagelinks.length > 0) {
		            pagelinks = pagelinks[0].getElementsByTagName('a');
		            ajaxifyLinks(pagelinks, aaAAZonesAtualizar);
		        }
		        
		        // remove ajax request from export
	            var exportlinks = getElementsByClassName('exportlinks', 'div', aaZoneDT);
	            if (exportlinks != null && exportlinks.length > 0) {
	                exportlinks = exportlinks[0].getElementsByTagName('a');
	                removeAAParams(exportlinks);
	            }
	            
	            aadt.configAADisplayTag = 1;
            }
        }
    }
}

/* Public - Atualiza o conteúdo de uma displayTag */
function atualizarAjaxAnywhere(aaAAZone, url, stringChamadaFuncaoExecutarDepoisProcessamento, tirarAvisoProcessamento)
{
	tirarAvisoProcessamento = (tirarAvisoProcessamento==void(0)||tirarAvisoProcessamento==null||tirarAvisoProcessamento);
    var displayTagAjaxAnywhere = getAjaxAnywhere (aaAAZone);
    displayTagAjaxAnywhere.getZonesToReload = function () { return aaAAZone; };
    if (void(0)==stringChamadaFuncaoExecutarDepoisProcessamento || stringChamadaFuncaoExecutarDepoisProcessamento==null || stringChamadaFuncaoExecutarDepoisProcessamento=="")
    {
		var funcaoExecutarDepoisProcessamento = function () { 
			aplicarCorrecoesVisuais();
			if (tirarAvisoProcessamento)
			{
				hidetrail(); 
			}
		};
    }
    else
    {
		var funcaoExecutarDepoisProcessamento = function () {
			eval(stringChamadaFuncaoExecutarDepoisProcessamento);
			aplicarCorrecoesVisuais();
			if (tirarAvisoProcessamento)
			{
				hidetrail(); 
			}
		};
	}
    displayTagAjaxAnywhere.onAfterResponseProcessing = funcaoExecutarDepoisProcessamento;
    displayTagAjaxAnywhere.showLoadingMessage = function () { showtrail(); };
    displayTagAjaxAnywhere.getAJAX(url);
}

/* Public - Atualiza o conteúdo de uma displayTag */
function submeterAjaxAnywhere(aaAAZone, url, formulario, stringChamadaFuncaoExecutarDepoisProcessamento, tirarAvisoProcessamento)
{
	tirarAvisoProcessamento = (tirarAvisoProcessamento==void(0)||tirarAvisoProcessamento==null||tirarAvisoProcessamento);
    var actionOriginalFormulario = formulario.action;
    var displayTagAjaxAnywhere = getAjaxAnywhere (aaAAZone);
    displayTagAjaxAnywhere.formName = formulario.name;
    formulario.action = url;
    displayTagAjaxAnywhere.getZonesToReload = function () { return aaAAZone; };
    if (void(0)==stringChamadaFuncaoExecutarDepoisProcessamento || stringChamadaFuncaoExecutarDepoisProcessamento==null || stringChamadaFuncaoExecutarDepoisProcessamento=="")
    {
		var funcaoExecutarDepoisProcessamento = function () { 
			formulario.action = actionOriginalFormulario;
			aplicarCorrecoesVisuais();
			if (tirarAvisoProcessamento)
			{
				hidetrail(); 
			}
		};
    }
    else
    {
		var funcaoExecutarDepoisProcessamento = function () {
			eval(stringChamadaFuncaoExecutarDepoisProcessamento);
			formulario.action = actionOriginalFormulario;
			aplicarCorrecoesVisuais();
			if (tirarAvisoProcessamento)
			{
				hidetrail(); 
			}
		};
	}
    displayTagAjaxAnywhere.onAfterResponseProcessing = funcaoExecutarDepoisProcessamento;
    displayTagAjaxAnywhere.showLoadingMessage = function () { showtrail(); };
    displayTagAjaxAnywhere.submitAJAX();
}

/* Private - uso interno */
function ajaxifyLinks(links, aaDisplayTagAAZone) 
{
    var displayTagAjaxAnywhere = getAjaxAnywhere (aaDisplayTagAAZone);
    for (i=0; i < links.length; i++) 
    {
        links[i].onclick = function () 
        {
            displayTagAjaxAnywhere.getZonesToReload = function () { return aaDisplayTagAAZone; };
            displayTagAjaxAnywhere.onAfterResponseProcessing = function () { configurarAADisplayTag(aaDisplayTagAAZone); hidetrail(); };
            displayTagAjaxAnywhere.showLoadingMessage = function () { showtrail(); };
            displayTagAjaxAnywhere.getAJAX(this.href);
            return false;
        }
    }
}

/* Private - uso interno */
function removeAAParams(links) 
{
    for (i=0; i < links.length; i++) 
    {
        urlOriginalSeparada = links[i].href.split('?');
        if (urlOriginalSeparada.lenth == 1)
        {
            // Sem parâmetros (não deveria acontecer)
            urlTratada = urlOriginalSeparada[0];
        }
        else
        {
            // Existem parâmetros
            urlTratada = urlOriginalSeparada[0] + '?';
            parametros = urlOriginalSeparada[1].split('&');
            for (j=0; j < parametros.length; j++)
            {
                if (parametros[j].indexOf('aaxmlrequest')==-1)
                {
                    urlTratada = urlTratada + ((j==0)?'':'&') + parametros[j]; 
                }
            } 
        }
        links[i].href = urlTratada;
    }
}

/* Global - uso interno */
function getAjaxAnywhere (aaAAZone)
{
	var aaId = aaAAZone.replace(/[ ,]/g, "");
    var aaInstance = getCustomObject (aaId, 'ajaxAnywhere', 'AjaxAnywhere', 'document');
	aaInstance.id = aaId;
	aaInstance.bindById();
	return aaInstance;
}

function getCustomObject (nomeObjetoTratado, prefixo, tipo, container)
{
	if (prefixo==void(0) || prefixo==null) {
		prefixo = "";
	}
	if (container==void(0) || container==null) {
		container = "document";
	}
	if (tipo==void(0) || tipo==null) {
		tipo = "Object";
	}
	var nomeCompletoObjeto = container + '.' + prefixo + nomeObjetoTratado;
    var thisCustomObject = eval(nomeCompletoObjeto);
    if (thisCustomObject==void(0) || thisCustomObject==null)
    {
        eval (nomeCompletoObjeto + ' = new ' + tipo + '();');
        thisCustomObject = eval(nomeCompletoObjeto);
    }
    return thisCustomObject;
}

/* Deprecated, visto que pode ser utilizado por outras rotinas, mas para manter a compatibilidade */
function getDisplayTagAjaxAnywhere (aaDisplayTagAAZone)
{
	return getAjaxAnywhere (aaDisplayTagAAZone);
}

function submeterAADisplayTag(aaDisplayTagAAZone, url, formulario)
{
	submeterAjaxAnywhere(aaDisplayTagAAZone, url, formulario, 'configurarAADisplayTag("' + aaDisplayTagAAZone + '")');
}

function atualizarAADisplayTag(aaDisplayTagAAZone, url)
{
	atualizarAjaxAnywhere(aaDisplayTagAAZone, url, 'configurarAADisplayTag("' + aaDisplayTagAAZone + '")');
}
