/**
AJAX
----

A idéia do AJAX (Asynchronous JavaScript and XML) é utilizar Javascript para transformar suas páginas 
em aplicações, de modo que não precise recarregar a tela cada vez que o usuário clicar em alguma coisa. 
Você pode recarregar apenas a área que precisa ser alterada pela ação realizada. Com AJAX não precisamos
utilizar iframes ocultos para que ações possam ser executadas.

Para saber mais sobre AJAX:
http://developer.mozilla.org/en/docs/AJAX
http://pt.wikipedia.org/wiki/AJAX_%28Web%29
http://www.tableless.com.br/artigos/ajaxdemo/
http://www.tableless.com.br/artigos/ajaxdemo2/

**/

/* namespacing object */
var net=new Object();

net.READY_STATE_UNINITIALIZED=0;
net.READY_STATE_LOADING=1;
net.READY_STATE_LOADED=2;
net.READY_STATE_INTERACTIVE=3;
net.READY_STATE_COMPLETE=4;


/*--- content loader object for cross-browser requests ---*/
net.ContentLoader=function(url,onload,method,onerror,params,contentType){
	this.req=null;
	this.onload=(onload) ? onload : null;
	this.onerror=(onerror) ? onerror : this.defaultError;
	params=(params==void(0)) ? null : params;
	this.loadXMLDoc(url,method,params,contentType);
}

net.ContentLoader.prototype.loadXMLDoc=function(url,method,params,contentType){
	if (!method){
		method="GET";
	}
	if (!contentType && method=="POST"){
		contentType='application/x-www-form-urlencoded';
	}
	if (window.XMLHttpRequest){        // Mozilla, Safari, ...
		try {
			this.req=new XMLHttpRequest();
			if (this.req.overrideMimeType) {
		        this.req.overrideMimeType('text/xml');
	        }
	    } catch (e) {}
	} else if (window.ActiveXObject){  // IE
		try {
	    	this.req = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
		    try {
		    	this.req = new ActiveXObject("Microsoft.XMLHTTP");
		    } catch (e) {}
		}
	}

	if (this.req){
		try{
			var loader=this;
			if (this.onload!=null)
			{
				this.req.onreadystatechange=function(){
					net.ContentLoader.onReadyState.call(loader);
				}
			}
			this.req.open(method,urlComRequestUID(url),true);
			if (contentType){
				this.req.setRequestHeader('Content-Type', contentType);
			}
			this.req.send(params);
		}catch (err){
			this.onerror();
		}
	}
	else {
	    alert('Seu navegador da internet não é suportado: não foi possível criar uma instância XMLHTTP.');
    }
}


net.ContentLoader.onReadyState=function(){
	var req = this.req;
	var ready = req.readyState;
	if (ready==net.READY_STATE_COMPLETE){
		try {
			var httpStatus=req.status;
			if (httpStatus==200 || httpStatus==0){
				if (void(0)!=this.onload)
				{
					this.onload();
				}
			}else {
				// 204 -> Nenhuma alteração no conteúdo...
				this.onerror();
			}
		}catch (e){
			alert (e);
		}
	}
}

net.ContentLoader.prototype.defaultError=function(){
	alert("error fetching data!"
		+"\n\nreadyState:"+this.req.readyState
		+"\nstatus: "+this.req.status
		+"\nheaders: "+this.req.getAllResponseHeaders()
		+"\nstatus: "+this.req.responseText);
	//alert("Ocorreu um erro na recuperação dos dados. Por favor, tente novamente em alguns instantes...");
}

/**
	@param url é a url a ser chamada internamente (ocultamente)
	@param onload é a função javascript que será chamada após o retorno dos dados da url passada
	@param method Método de submissão: GET ou POST
	@param onerror Método javascript que será executado no caso de erro na chamada da url
	@param params Parâmetros para a url
	@param contentType Content-type
**/
function AJAXLoad(url,onload,method,onerror,params,contentType) {
	var AJAXLoader = new net.ContentLoader(url,onload,method,onerror,params,contentType);
}