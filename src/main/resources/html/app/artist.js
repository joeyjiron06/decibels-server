/* - artist.js */

function Artist() {

	var self 			= this;

	self.name			= null;		//string
	self.images			= null;		//[]
	self.availabilities	= null;		//[]

	return self;
}

Artist.parseArray		= function(artists) {
	if (artists) {
		var parsedArtists	= [];
		for (var i=0; i < artists.length; ++i) {
			parsedArtists.push(Artist.parse(artists[i]));
		}
		return parsedArtists;
	}

	return null;
};

Artist.parse			= function(rawJson) {
	if (rawJson) {
		var artist				= new Artist();
		artist.name				= rawJson.name;
		artist.images			= Image.parseArray(rawJson.images);
		artist.availabilities	= Availability.parseArray(rawJson.availabilities);
		return artist;
	}
};