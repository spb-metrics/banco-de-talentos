window.calendar = null;

function showCalendar(nome, format, showsTime, showsOtherMonths) {
	if (showsTime==void(0) || showsTime==null)
	{
		showsTime = false;
	}
	if (format==void(0) || format==null)
	{
		format = '%d/%m/%Y';
		if (showsTime)
		{
			format = format + ' %H:%M:%S';
		}
	}
    
	// Procura o campo com o nome especificado
	var el = null;
	var i = 0;
	while (el == null && i < 3)
	{
		el = document.forms[i++].elements[nome];
	}
	if (el == null) {
		alert('Ocorreu um erro na configuração do calendário: o campo "' + nome + '" não foi encontrado.');
		return null;
	}
	if (el.readOnly == true)
	{
	   alert ('Campo do tipo somente leitura');
	   return null;
	}
	
	var dataExistente = Date.parseDate(el.value || el.innerHTML, format);
	
	onClose = function (cal) 
	{
		cal.hide(); 
	};
	
	onSelect = function (cal, dateValue) 
	{
		if (cal.dateClicked) 
		{
			el.value = dateValue;
			if (typeof el.onchange == "function") {
				el.onchange();
			}
			cal.callCloseHandler();
		}
	};

	if (window.calendar != null) {
		// we already have some calendar created
		window.calendar.hide();                 // so we hide it first.
	} else {
		// first-time call, create the calendar.
		var cal = new Calendar(0);
		
		window.calendar = cal;           // remember it in the global var
		cal.setRange(1900, 2070);        // min/max year allowed.
		cal.create();
	}

	if (showsTime) {
		window.calendar.showsTime = true;
		window.calendar.time24 = true;
	}
	window.calendar.onSelected = onSelect;
	window.calendar.onClose = onClose;
	window.calendar.date = dataExistente;
	window.calendar.showsOtherMonths = showsOtherMonths;
	
	window.calendar.setDateFormat(format);    // set the specified date format

	window.calendar.showAtElement(el, "BR");        // show the calendar
	return false;
}
