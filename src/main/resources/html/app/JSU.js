/**
 * JSU.js
 * jsearch utilities
 *  */

var JSU = JSU || {};

JSU.combine				= function() {
	var result		= "";
	var argSize		= arguments.length;
	var arg;
	for (var i=0; i < argSize; ++i) {
		arg = arguments[i];
		result += arg;
		if ((i+1) < argSize) {
			result += ",";
		}
	}
	return result;
}

