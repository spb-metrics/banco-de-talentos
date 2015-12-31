<script type="text/javascript" src="<%=request.getContextPath()%>/biblioteca/funcoes/comum/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/biblioteca/funcoes/aguarde/aguarde.js"></script>

<script type="text/javascript">
	var trailimage = new Object(); //image path, plus width and height //image x,y offsets from cursor position in pixels. Enter 0,0 for no offset
	trailimage.src = "<%=request.getContextPath()%>/biblioteca/imagens/aguarde/aguarde.gif";
	trailimage.width = 62;
	trailimage.height = 19;
	trailimage.xOffsetFromMouse = 5;
	trailimage.yOffsetFromMouse = 5;

    document.write('<div id="trailimageid" style="position:absolute;visibility:hidden;left:0px;top:0px;width:1px;height:1px;"><img src="' + trailimage.src + '" border="0" width="' + trailimage.width + 'px" height="' + trailimage.height + 'px"></div>')

	addEvent(document, 'mousemove', followmouse);
	addEvent(document, 'load', hidetrail);
</script>