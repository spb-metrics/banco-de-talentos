/**
 * COMMON DHTML FUNCTIONS
 * These are handy functions I use all the time.
 *
 * By Seth Banks (webmaster at subimage dot com)
 * http://www.subimage.com/
 *
 * Up to date code can be found at http://www.subimage.com/dhtml/
 *
 * This code is free for you to use anywhere, just keep this comment block.
 */

/**
 * Code below taken from - http://www.evolt.org/article/document_body_doctype_switching_and_more/17/30655/
 *
 * Modified 4/22/04 to work with Opera/Moz (by webmaster at subimage dot com)
 *
 * Gets the full width/height because it's different for most browsers.
 */
function getViewportHeight() {
	if (window.innerHeight!=window.undefined) return window.innerHeight;
	if (document.compatMode=='CSS1Compat') return document.documentElement.clientHeight;
	if (document.body) return document.body.clientHeight; 
	return window.undefined; 
}
function getViewportWidth() {
	if (window.innerWidth!=window.undefined) return window.innerWidth; 
	if (document.compatMode=='CSS1Compat') return document.documentElement.clientWidth; 
	if (document.body) return document.body.clientWidth; 
	return window.undefined; 
}

function getViewportSize() {
	var xy = new Object();
	xy.x=null;
	xy.y=null;
	
	if (self.innerHeight) // all except Explorer
	{
		xy.x = self.innerWidth;
		xy.y = self.innerHeight;
	}
	else
	{
		var trueBody = getTrueBody();
		xy.x = trueBody.clientWidth;
		xy.y = trueBody.clientHeight;
	}
	return xy;
}

function getScrollOffset() {
	var xy = new Object();
	xy.x=null;
	xy.y=null;

	if (self.pageYOffset) // all except Explorer
	{
		xy.x = self.pageXOffset;
		xy.y = self.pageYOffset;
	}
	else
	{
		var trueBody = getTrueBody();
		xy.x = trueBody.scrollLeft;
		xy.y = trueBody.scrollTop;
	}
	return xy;
}

function getPageSize() {
	var xy = new Object();
	xy.x=null;
	xy.y=null;

	var test1 = document.body.scrollHeight;
	var test2 = document.body.offsetHeight
	if (test1 > test2) // all but Explorer Mac
	{
		xy.x = document.body.scrollWidth;
		xy.y = document.body.scrollHeight;
	}
	else // Explorer Mac;
	     //would also work in Explorer 6 Strict, Mozilla and Safari
	{
		xy.x = document.body.offsetWidth;
		xy.y = document.body.offsetHeight;
	}
	return xy;
}

function getTrueBody()
{
	var trueBody;

	if (document.compatMode && document.compatMode != "BackCompat")
	{
		trueBody = document.documentElement;
	}
	else if (document.body)
	{
		trueBody = document.body;
	}
	
	return trueBody;
}

/**
 * Gets the real scroll top
 */
function getScrollTop() {
	if (self.pageYOffset) // all except Explorer
	{
		return self.pageYOffset;
	}

	return getTrueBody().scrollTop;
}

function getScrollLeft() {
	if (self.pageXOffset) // all except Explorer
	{
		return self.pageXOffset;
	}

	return getTrueBody().scrollLeft;
}

function getEventPosition(ev) {
	var xy = new Object();
	xy.x=null;
	xy.y=null;

	if (_browserId.ie) 
	{
		xy.y = window.event.clientY + document.body.scrollTop;
		xy.x = window.event.clientX + document.body.scrollLeft;
	} 
	else 
	{
		xy.x = ev.pageX;
		xy.y = ev.pageY;
	}
	
	return xy;
}

function centralizar(elemento) {
	var width = parseInt(elemento.style.width,10);
	var height= parseInt(elemento.style.height,10);
		
	var theBody = document.getElementsByTagName("BODY")[0];

	var xyScrollOffset = getScrollOffset();
	var scLeft = parseInt(xyScrollOffset.x,10);
	var scTop = parseInt(xyScrollOffset.y,10);
	
	var xyFullSize = getViewportSize();
	var fullWidth = xyFullSize.x;
	var fullHeight = xyFullSize.y;
	
	elemento.style.position = "absolute";
	elemento.style.top = (scTop + ((parseInt(fullHeight,10) - height) / 2)) + "px";
	elemento.style.left =  (scLeft + ((parseInt(fullWidth,10) - width) / 2)) + "px";
}
