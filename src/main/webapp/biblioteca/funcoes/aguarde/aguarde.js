function gettrailobj()
{
    var objAguarde = $("trailimageid");
    return objAguarde.style;
}

function hidetrail()
{
    gettrailobj().visibility="hidden"
}

function showtrail()
{
    gettrailobj().visibility="visible";
}

function followmouse(e)
{
	var objAguarde = gettrailobj();
	if (objAguarde != "undefined")
	{
	
	    var xcoord=trailimage.xOffsetFromMouse
	    var ycoord=trailimage.yOffsetFromMouse
	
		var xyEvent = getEventPosition(e);
		xcoord += xyEvent.x;
		ycoord += xyEvent.y;
	
	   	var xyDocSize = getViewportSize();
	    var docwidth = xyDocSize.x;
	    var docheight = xyDocSize.y;

	    if (xcoord<14 || ycoord<14)
	    {
	        objAguarde.display="none";
	    }
	    else if (xcoord+trailimage.width+3>docwidth || ycoord+trailimage.height> docheight)
	    {
	        objAguarde.display="none";
	    }
	    else
	    {
	        objAguarde.display="";
        }
	
	    objAguarde.left = xcoord+"px"
	    objAguarde.top = ycoord+"px"
    }
}
