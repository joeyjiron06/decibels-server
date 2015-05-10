/* - availability.js */

function Availability() {

	var self			= this;

	self.name			= null;		//string
	self.image			= null;		//string url
	self.link			= null;		//string url

	return self;
}

Availability.parseArray		= function(availabilities) {
	if (availabilities) {
		var parsedAvailabilities= [];
		for (var i=0; i < availabilities.length; ++i) {
			// GET RAW DATA
			var availability	= availabilities[i];

			// PARSE
			var parsedAvailability = Availability.parse(availability);

			// ADD TO ARRAY
			parsedAvailabilities.push(parsedAvailability);
		}
		return parsedAvailabilities;
	}

	return null;
};

Availability.parse			= function(rawJson) {
	if (rawJson) {
		var availability	= new Availability();
		availability.name	= rawJson.name;
		availability.image	= rawJson.image;
		availability.link	= rawJson.deepLink;
		return availability;
	}

	return null;
};