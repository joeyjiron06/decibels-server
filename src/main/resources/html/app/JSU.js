/**
 * JSU.js
 * jsearch utilities
 *  */

var JSU = JSU || {};

JSU.isNullOrEmpty		= function(item) {
	return (!item)
		|| (item === null)
		|| (item === undefined)
		|| (item.length && item.length === 0);
};

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
};

JSU.format			= function(string) {
	var result		= string;
	if (string) {
		var argSize		= arguments.length;
		for (var i=1; i < argSize; ++i) {
			var arg		= arguments[i];
			var key		= "{"+(i-1)+"}";	// (i-1) because args are 0 based
			result		= result.replace(key, arg);
		}
	}
	return result;
};