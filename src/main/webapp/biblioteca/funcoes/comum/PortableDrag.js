/**
 * PortableDrag.js:
 * beginDrag() is designed to be called from an onmousedown event handler.
 * elementToDrag may be the element that received the mousedown event, or it
 * may be some containing element.  event must be the Event object for the
 * mousedown event.  This implementation works with both the DOM Level 2
 * event model and the IE Event model.
 **/
function beginDrag(elementToDrag, event) {
	var objPortableDrag = getCustomObject ('portableDrag');

	// Já está sendo tratado o evento de dragging ?
	if (objPortableDrag.isDragging)
	{
		return;
	}

	// indicar que estamos arrastando...
	objPortableDrag.isDragging = true;

    // Compute the distance between the upper-left corner of the element
    // and the mouse click. The moveHandler function below needs these values.
    var deltaX = event.clientX - parseInt(elementToDrag.style.left);
    var deltaY = event.clientY - parseInt(elementToDrag.style.top);

    // Register the event handlers that will respond to the mousemove events
    // and the mouseup event that follow this mousedown event.
    addEvent(document, "mousemove", moveHandler, true);
    addEvent(document, "mouseup", upHandler, true);

	// We've handled this event.  Don't let anybody else see it.
	_stopPropagation(event);
	// Now prevent any default action.
	_preventDefault(event);

    /**
     * This is the handler that captures mousemove events when an element
     * is being dragged.  It is responsible for moving the element.
     **/
    function moveHandler(e) {
		e = getEvent(e);

        // Move the element to the current mouse position, adjusted as
  		// necessary by the offset of the initial mouse click.
		elementToDrag.style.left = (e.clientX - deltaX) + "px";
  		elementToDrag.style.top = (e.clientY - deltaY) + "px";

		// And don't let anyone else see this event.
		_stopPropagation(event);
    }

    /**
     * This is the handler that captures the final mouseup event that
     * occurs at the end of a drag.
     **/
    function upHandler(e) {
		e = getEvent(e);

  		// Unregister the capturing event handlers.
	    removeEvent(document, "mousemove", moveHandler, true);
	    removeEvent(document, "mouseup", upHandler, true);

		// indicar que NÃO estamos arrastando...
		var objPortableDrag = getCustomObject ('portableDrag');
		objPortableDrag.isDragging = false;

  		// And don't let the event propagate any further.
		_stopPropagation(event);
    }
}

function getEvent(ev) {
	return ev || (ev = window.event);
}

function _stopPropagation(ev) {
	var event = getEvent(ev);
	if (_browserId.ie) {
		event.cancelBubble = true;
	} else {
		event.stopPropagation();
	}
	return false;
}

function _preventDefault(ev) {
	var event = getEvent(ev);
	if (_browserId.ie) {
		event.returnValue = false;
	} else {
		event.preventDefault();
	}
	return false;
}
