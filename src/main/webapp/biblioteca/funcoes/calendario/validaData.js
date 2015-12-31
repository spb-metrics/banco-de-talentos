var err = 0; // Set the error code to a default of zero
var datePatterns = new Array(4);
datePatterns[0] = 'MM/dd/yyyy';
datePatterns[1] = 'yyyy/MM/dd';
datePatterns[2] = 'dd/MM/yyyy';


function DateFormat(obj, evt, dateCheck,vDateType) {
	if (vDateType == void(0))
		vDateType = 3;

	var LEFT=37, RIGHT=39, DEL=46, BCK=8, ETR=13, TAB=9;
	var key, tecla;

	if(evt.which) tecla = evt.which;
	else tecla = evt.keyCode;

	key = String.fromCharCode(tecla);

	if(tecla == ETR) 
		return false;
	if(tecla==BCK || tecla==TAB || tecla==LEFT || tecla==RIGHT)
		return true;

	var vDateValue = obj.value;
	while (vDateValue.indexOf('\/') != -1)
	 	vDateValue = vDateValue.replace('\/', '');

	if (vDateType == 1) {
		if (vDateValue.length > 2) {
			vDateValue = vDateValue.substring(0,2) + '/' + vDateValue.substring(2);
			obj.value = vDateValue;
		}
		if (vDateValue.length > 5) {
			vDateValue = vDateValue.substring(0,5) + '/' + vDateValue.substring(5,9);
			obj.value = vDateValue;
		}
	} else if (vDateType == 2) {
		if (vDateValue.length > 4) {
			vDateValue = vDateValue.substring(0,4) + '/' + vDateValue.substring(4);
			obj.value = vDateValue;
		}
		if (vDateValue.length > 7) {
			vDateValue = vDateValue.substring(0,7) + '/' + vDateValue.substring(7,9);
			obj.value = vDateValue;
		}
	} else if (vDateType == 3) {	
		if (vDateValue.length > 2) {
			vDateValue = vDateValue.substring(0,2) + '/' + vDateValue.substring(2);
			obj.value = vDateValue;
		}
		if (vDateValue.length > 5) {
			vDateValue = vDateValue.substring(0,5) + '/' + vDateValue.substring(5,9);
			obj.value = vDateValue;
		}
	} else if (vDateType == 4) {	
		if (vDateValue.length > 1) {
			vDateValue = vDateValue.substring(0,2) + '/' + vDateValue.substring(2);
			obj.value = vDateValue;
		}
	}
	
	if (vDateValue.length == 10) {
		if (!dateCheck) {
			return false;
		} else if (!validateDate(obj, "'" + datePatterns[vDateType - 1] + "'")) {
			alert('Data invalida!');
			obj.value = '';
			obj.focus();
			return false;
		} else {
			return true;
		}
	}
	
	if (dateCheck && !validateDate(obj, "'" + datePatterns[vDateType - 1] + "'")) {
		alert('Data invalida!');
		obj.value = '';
		obj.focus();
		return false;
	}
}


//
// adaptada do validator
//
function validateDate(field, datePattern) {
	var bValid = true;
	var value;
	if ((field.type == 'hidden' || field.type == 'text' || field.type == 'textarea') && field.disabled == false) {
	    value = field.value;
	} else if (field.type == undefined) {
		value = field;
	} else {
		return false;
	}
    
	// try loose pattern
    if (datePattern == null)
		datePattern = "dd/MM/yyyy"

	if (value.length > 0 && datePattern.length > 0) {
		var MONTH = "MM";
        var DAY = "dd";
        var YEAR = "yyyy";
        var orderMonth = datePattern.indexOf(MONTH);
        var orderDay = datePattern.indexOf(DAY);
        var orderYear = datePattern.indexOf(YEAR);
        
		if ((orderDay < orderYear && orderDay > orderMonth)) {
			var iDelim1 = orderMonth + MONTH.length;
            var iDelim2 = orderDay + DAY.length;
            var delim1 = datePattern.substring(iDelim1, iDelim1 + 1);
            var delim2 = datePattern.substring(iDelim2, iDelim2 + 1);
            if (iDelim1 == orderDay && iDelim2 == orderYear) {
				dateRegexp = new RegExp("^(\\d{2})(\\d{2})(\\d{4})$");
            } else if (iDelim1 == orderDay) {
				dateRegexp = new RegExp("^(\\d{2})(\\d{2})[" + delim2 + "](\\d{4})$");
            } else if (iDelim2 == orderYear) {
				dateRegexp = new RegExp("^(\\d{2})[" + delim1 + "](\\d{2})(\\d{4})$");
            } else {
				dateRegexp = new RegExp("^(\\d{2})[" + delim1 + "](\\d{2})[" + delim2 + "](\\d{4})$");
			}

			var matched = dateRegexp.exec(value);
            if(matched != null) {
				if (!isValidDate(matched[2], matched[1], matched[3])) {
                    bValid =  false;
                }
            } else {
				bValid =  false;
            }
		} else if ((orderMonth < orderYear && orderMonth > orderDay)) {
			var iDelim1 = orderDay + DAY.length;
			var iDelim2 = orderMonth + MONTH.length;
			var delim1 = datePattern.substring(iDelim1, iDelim1 + 1);
			var delim2 = datePattern.substring(iDelim2, iDelim2 + 1);
			if (iDelim1 == orderMonth && iDelim2 == orderYear) {
				dateRegexp = new RegExp("^(\\d{2})(\\d{2})(\\d{4})$");
			} else if (iDelim1 == orderMonth) {
				dateRegexp = new RegExp("^(\\d{2})(\\d{2})[" + delim2 + "](\\d{4})$");
			} else if (iDelim2 == orderYear) {
				dateRegexp = new RegExp("^(\\d{2})[" + delim1 + "](\\d{2})(\\d{4})$");
			} else {
				dateRegexp = new RegExp("^(\\d{2})[" + delim1 + "](\\d{2})[" + delim2 + "](\\d{4})$");
			}
			
			var matched = dateRegexp.exec(value);
			if(matched != null) {
				if (!isValidDate(matched[1], matched[2], matched[3])) {
					bValid =  false;
                }
            } else {
				bValid =  false;
            }
		} else if ((orderMonth > orderYear && orderMonth < orderDay)) {
			var iDelim1 = orderYear + YEAR.length;
			var iDelim2 = orderMonth + MONTH.length;
			var delim1 = datePattern.substring(iDelim1, iDelim1 + 1);
			var delim2 = datePattern.substring(iDelim2, iDelim2 + 1);
			if (iDelim1 == orderMonth && iDelim2 == orderDay) {
				dateRegexp = new RegExp("^(\\d{4})(\\d{2})(\\d{2})$");
			} else if (iDelim1 == orderMonth) {
				dateRegexp = new RegExp("^(\\d{4})(\\d{2})[" + delim2 + "](\\d{2})$");
			} else if (iDelim2 == orderDay) {
				dateRegexp = new RegExp("^(\\d{4})[" + delim1 + "](\\d{2})(\\d{2})$");
			} else {
				dateRegexp = new RegExp("^(\\d{4})[" + delim1 + "](\\d{2})[" + delim2 + "](\\d{2})$");
			}
			var matched = dateRegexp.exec(value);
			if(matched != null) {
				if (!isValidDate(matched[3], matched[2], matched[1])) {
					bValid =  false;
				}
			} else {
				bValid =  false;
			}
		}
	}
	return bValid;
}

function isValidDate(day, month, year) {
    if (month < 1 || month > 12) {
        return false;
    }
    if (day < 1 || day > 31) {
        return false;
    }
    if ((month == 4 || month == 6 || month == 9 || month == 11) &&
        (day == 31)) {
        return false;
    }
    if (month == 2) {
        var leap = (year % 4 == 0 &&
           (year % 100 != 0 || year % 400 == 0));
        if (day>29 || (day == 29 && !leap)) {
            return false;
        }
    }
    return true;
}