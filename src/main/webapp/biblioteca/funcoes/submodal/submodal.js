/**
 * SUBMODAL v1.5
 * Used for displaying DHTML only popups instead of using buggy modal windows.
 *
 * By Seth Banks
 * http://www.subimage.com/
 *
 * Contributions by:
 * 	Eric Angel - tab index code
 * 	Scott - hiding/showing selects for IE users
 *	Todd Huss - inserting modal dynamically and anchor classes
 *
 * Up to date code can be found at http://www.subimage.com/dhtml/subModal
 * 
 *
 * This code is free for you to use anywhere, just keep this comment block.
 */

function PopupSubmodal(id) {
	// Popup code
	this.id = id || '';
	this.gPopupMask = null;
	this.gPopupContainer = null;
	this.gPopFrame = null;
	this.gReturnFunc;
	this.gPopupIsShown = false;
	this.gDefaultPage = obterContextPath(true) + "/biblioteca/paginas/aguarde.html"; // "/paginaEmBrancoPrepararVisualizacao.do";
	this.gHideSelects = false;
	this.gReturnVal = null;
	this.gUrl = '';
	
	this.gPopupIsShown = false;
	this.urlOnClose = null;
	
	this.gTabIndexes = new Array();
	// Pre-defined list of tags we want to disable/enable tabbing into
	this.gTabbableTags = new Array("A","BUTTON","TEXTAREA","INPUT","IFRAME");
	
	// Controles padrões Sigesp
	this.initialized = false;
	this.popupControls = '';
	this.tamanhoPadraoPercent = 0.90;
	this.onErrorClose = false;
	this.gPopupTitleBar = null;
}

function closeOnError(blnAtribuicao) {
	var objPopup = _getDocumentPopupObject ();
	if (blnAtribuicao!=null && blnAtribuicao!=void(0))
	{
		if (blnAtribuicao==true || blnAtribuicao==false)
		{
			objPopup.onErrorClose = blnAtribuicao;
		}
	}
	return objPopup.onErrorClose;
}

function _getDocumentPopupObject () {
	return getCustomObject ('popup', null, 'PopupSubmodal');
}


/**
 * Initializes popup code on load.	
 */
function initPopUp() {
	var objPopup = _getDocumentPopupObject();

	// Add the HTML to the body
	theBody = document.getElementsByTagName('BODY')[0];

	// Área cinza (desabilitar o que está embaixo)
	popmask = $('popupMask');
	if (popmask==null || popmask==void(0))
	{
		popmask = document.createElement('div');
		popmask.id = 'popupMask';
		theBody.appendChild(popmask);
	}

	// Popup propriamente dito
	popcont = $('popupContainer');
	if (popcont==null || popcont==void(0))
	{
		popcont = document.createElement('div');
		popcont.id = 'popupContainer';
		popcont.innerHTML = '' +
			'<div id="popupInner">' +
				'<div id="popupTitleBar">' +
					'<div id="popupTitle"></div>' +
					'<div id="popupControls">' + objPopup.popupControls + '<img src="../biblioteca/imagens/submodal/close.gif" onclick="hidePopWin(false);" id="popCloseBox" /></div>' +
				'</div>' +
				'<iframe src="'+ objPopup.gDefaultPage +'" style="width:100%;height:100%;background-color:transparent;" scrolling="auto" frameborder="0" allowtransparency="true" id="popupFrame" name="popupFrame" width="100%" height="100%"></iframe>' +
			'</div>';
		theBody.appendChild(popcont);
	}

	objPopup.gPopupMask = $("popupMask");
	objPopup.gPopupContainer = $("popupContainer");
	objPopup.gPopFrame = $("popupFrame");
	objPopup.gPopupTitleBar = $("popupTitleBar");
	
	// check to see if this is IE version 6 or lower. hide select boxes if so
	// maybe they'll fix this in version 7?
	if (_browserId.ie && _browserId.vernum <= 6) {
		objPopup.gHideSelects = true;
	}

	// set the url
	objPopup.gPopFrame.src = objPopup.gDefaultPage;
	
	objPopup.initialized = true;
}

 /**
	* @argument width - int in pixels
	* @argument height - int in pixels
	* @argument url - url to display
	* @argument returnFunc - function to call when returning true from the window.
	* @argument showCloseBox - show the close box - default true
	*/

function showPopWin(url, width, height, returnFunc, showCloseBox) {
	var objPopup = _getDocumentPopupObject();
	if (!objPopup.initialized) 
	{
		alert ('O popup ainda não foi incializado nesta página');
		return false;
	}

	// set the url
	objPopup.gUrl = url;

	var size = getViewportSize();
	if (width == null || isNaN(width) || width==void(0)) {
		width = parseInt(size.x * objPopup.tamanhoPadraoPercent);
	}
	if (height == null || isNaN(height) || height==void(0)) {
		height = parseInt(size.y * objPopup.tamanhoPadraoPercent);
	}
	
	// show or hide the window close widget
	if (showCloseBox == null || showCloseBox == true) {
		$("popCloseBox").style.display = "block";
	} else {
		$("popCloseBox").style.display = "none";
	}

	// If using Mozilla or Firefox, use Tab-key trap.
	if (!_browserId.ie) {
		addEvent (document, 'keypress', _keyDownHandler);
	}

	objPopup.gPopupIsShown = true;
	_disableTabIndexes(objPopup);
	objPopup.gPopupMask.style.display = "block";
	objPopup.gPopupContainer.style.display = "block";
	// calculate where to place the window on screen
	_centerPopWin(width, height);
	
	var titleBarHeight = parseInt($("popupTitleBar").offsetHeight, 10);

	objPopup.gPopupContainer.style.width = width + "px";
	objPopup.gPopupContainer.style.height = (height+titleBarHeight) + "px";
	
	_setMaskSize();

	// need to set the width of the iframe to the title bar width because of the dropshadow
	// some oddness was occuring and causing the frame to poke outside the border in IE6
	objPopup.gPopFrame.style.width = parseInt($("popupTitleBar").offsetWidth, 10) + "px";
	objPopup.gPopFrame.style.height = (height) + "px";
	
	objPopup.gReturnFunc = returnFunc;
	// for IE
	if (objPopup.gHideSelects == true) {
		_hideSelectBoxes();
	}

	addEvent(window, "resize", _centerPopWin);
	// addEvent(window, "scroll", _centerPopWin);
	
	addEvent(document, "mousedown", _mouseDown);
	// window.onscroll = _centerPopWin;

	window.setTimeout ('_abrirUrl();', 200);
}

function _abrirUrl() {
	var objPopup = _getDocumentPopupObject();
	objPopup.gPopFrame.src = objPopup.gUrl;
}

function _mouseDown (ev) {
	var objPopup = _getDocumentPopupObject();
	ev = getEvent(ev);
	beginDrag (objPopup.gPopupContainer, ev);
}

function _centerPopWin(width, height) {
	var objPopup = _getDocumentPopupObject();
	if (objPopup.gPopupIsShown) {
		if (width == null || isNaN(width)) {
			width = objPopup.gPopupContainer.offsetWidth;
		}
		if (height == null) {
			height = objPopup.gPopupContainer.offsetHeight;
		}
		
		//var theBody = document.documentElement;
		var theBody = document.getElementsByTagName("BODY")[0];
		//theBody.style.overflow = "hidden";
		var scTop = parseInt(getScrollTop(),10);
		var scLeft = parseInt(theBody.scrollLeft,10);
	
		_setMaskSize();
		
		var titleBarHeight = parseInt($("popupTitleBar").offsetHeight, 10);
		
		var fullHeight = getViewportHeight();
		var fullWidth = getViewportWidth();
		
		objPopup.gPopupContainer.style.top = (scTop + ((fullHeight - (height+titleBarHeight)) / 2)) + "px";
		objPopup.gPopupContainer.style.left =  (scLeft + ((fullWidth - width) / 2)) + "px";
		//alert(fullWidth + " " + width + " " + gPopupContainer.style.left);
	}
}

/**
 * @argument callReturnFunc - bool - determines if we call the return function specified
 * @argument returnVal - anything - return value 
 */
function hidePopWin(callReturnFunc, urlFechar) {
	urlFechar = (urlFechar==void(0)||urlFechar=='')?null:urlFechar;
	var objPopup = _getDocumentPopupObject();
	objPopup.gPopupIsShown = false;
	
	removeEvent(window, "resize", _centerPopWin);
	// removeEvent(window, "scroll", _centerPopWin);
	
	removeEvent(document, "mousedown", _mouseDown);
	//window.onscroll = _centerPopWin;
	
	// If using Mozilla or Firefox, use Tab-key trap.
	if (!_browserId.ie) {
		removeEvent (document, 'keypress', _keyDownHandler);
	}
	
	var theBody = document.getElementsByTagName("BODY")[0];
	theBody.style.overflow = "";
	_restoreTabIndexes(objPopup);
	if (objPopup.gPopupMask == null) {
		return;
	}
	objPopup.gPopupMask.style.display = "none";
	objPopup.gPopupContainer.style.display = "none";
	if (callReturnFunc == true && objPopup.gReturnFunc != null) {
		// Set the return code to run in a timeout.
		// Was having issues using with an Ajax.Request();
		gReturnVal = window.frames["popupFrame"].returnVal;
		window.setTimeout('gReturnFunc(gReturnVal);', 1);
	}
	
	objPopup.gPopFrame.src = objPopup.gDefaultPage;
	
	// display all select boxes
	if (objPopup.gHideSelects == true) {
		_displaySelectBoxes();
	}
	
	if (urlFechar == null)
	{
		if (objPopup.urlOnClose != null)
		{
		    document.location.href = objPopup.urlOnClose;
	    }
	    else
	    {
            avisarProcessamento(false);
	    }
	}
	else
	{
		document.location.href = urlFechar;
	}
}

/**
 * Sets the size of the popup mask.
 *
 */
function _setMaskSize() {
	var objPopup = _getDocumentPopupObject();
	var theBody = document.getElementsByTagName("BODY")[0];
			
	var fullHeight = getViewportHeight();
	var fullWidth = getViewportWidth();
	
	// Determine what's bigger, scrollHeight or fullHeight / width
	if (fullHeight > theBody.scrollHeight) {
		popHeight = fullHeight;
	} else {
		popHeight = theBody.scrollHeight;
	}
	
	if (fullWidth > theBody.scrollWidth) {
		popWidth = fullWidth;
	} else {
		popWidth = theBody.scrollWidth;
	}
	
	objPopup.gPopupMask.style.height = popHeight + "px";
	objPopup.gPopupMask.style.width = popWidth + "px";
}

/**
 * Sets the popup title based on the title of the html document it contains.
 * Uses a timeout to keep checking until the title is valid.
 */
function setPopTitle(titulo) {
	var containerTitulo = $("popupTitle");
	if (containerTitulo!=null && containerTitulo!=void(0)) {
		containerTitulo.innerHTML = titulo;
	}
}

function setUrlOnClose(urlFechar) {
	urlFechar = (urlFechar==void(0) || urlFechar=='')?null:urlFechar;
	if (urlFechar != null)
	{
		var objPopup = _getDocumentPopupObject();
		objPopup.urlOnClose = urlFechar;
	}
}

// Tab key trap. iff popup is shown and key was [TAB], suppress it.
// @argument e - event - keyboard event that caused this function to be called.
function _keyDownHandler(e) {
    if (e.keyCode == 9)  return false;
}

// For IE.  Go through predefined tags and disable tabbing into them.
function _disableTabIndexes(objPopup) {
	if (_browserId.ie) {
		var i = 0;
		for (var j = 0; j < objPopup.gTabbableTags.length; j++) {
			var tagElements = document.getElementsByTagName(objPopup.gTabbableTags[j]);
			for (var k = 0 ; k < tagElements.length; k++) {
				objPopup.gTabIndexes[i] = tagElements[k].tabIndex;
				tagElements[k].tabIndex="-1";
				i++;
			}
		}
	}
}

// For IE. Restore tab-indexes.
function _restoreTabIndexes(objPopup) {
	if (_browserId.ie) {
		var i = 0;
		for (var j = 0; j < objPopup.gTabbableTags.length; j++) {
			var tagElements = document.getElementsByTagName(objPopup.gTabbableTags[j]);
			for (var k = 0 ; k < tagElements.length; k++) {
				tagElements[k].tabIndex = objPopup.gTabIndexes[i];
				tagElements[k].tabEnabled = true;
				i++;
			}
		}
	}
}


/**
* Hides all drop down form select boxes on the screen so they do not appear above the mask layer.
* IE has a problem with wanted select form tags to always be the topmost z-index or layer
*
* Thanks for the code Scott!
*/
function _hideSelectBoxes() {
	for(var i = 0; i < document.forms.length; i++) {
		for(var e = 0; e < document.forms[i].length; e++){
			if(document.forms[i].elements[e].tagName == "SELECT") {
				document.forms[i].elements[e].style.visibility="hidden";
			}
		}
	}
}

/**
* Makes all drop down form select boxes on the screen visible so they do not reappear after the dialog is closed.
* IE has a problem with wanted select form tags to always be the topmost z-index or layer
*/
function _displaySelectBoxes() {
	for(var i = 0; i < document.forms.length; i++) {
		for(var e = 0; e < document.forms[i].length; e++){
			if(document.forms[i].elements[e].tagName == "SELECT") {
			document.forms[i].elements[e].style.visibility="visible";
			}
		}
	}
}
