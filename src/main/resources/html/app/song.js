/* - song.js */

function Song() {

	var self 				= this;

	self.name				= null;		//string
	self.album				= null;		//string
	self.artist				= null;		//string
	self.images				= null;		//[]
	self.availabilities		= null;		//[]
	self.durationMs			= null;		//integer

	/**
	 * Public
	 * */
	self.getFormattedTime	= function() {
		var t;
		var secs;
		var mins;
		var hrs;

		//secs
		t			= self.durationMs / 1000;
		secs		= Math.floor(t % 60);
		if (secs && secs < 10) {
			secs	= "0" + secs;
		}


		//mins
		t			= t / 60;
		mins		= Math.floor(t % 60);
		if (mins && mins < 10) {
			mins	= "0" + mins;
		}

		//hours
		t			= t / 60;
		hrs			= Math.floor(t % 60);
		if (hrs && hrs < 10) {
			hrs		= "0" + hrs;
		}

		var formattedTime;
		formattedTime	= hrs ? (hrs+":") : "";
		formattedTime	+= mins ? (mins+":") : "00";
		formattedTime	+= secs ? (secs) : "00";
		return formattedTime;
	};

	return self;
}

Song.parseArray			= function(songs) {
	if (songs) {
		var parsedSongs		= [];
		for (var i=0; i < songs.length; ++i) {
			parsedSongs.push(Song.parse(songs[i]));
		}
		return parsedSongs;
	}

	return null;
};

Song.parse				= function(rawJson) {
	if (rawJson) {
		var song			= new Song();
		song.name			= rawJson.name;
		song.album			= rawJson.album;
		song.artist			= rawJson.artist;
		song.durationMs		= rawJson.durationMs;
		song.images			= Image.parseArray(rawJson.images);
		song.availabilities = Availability.parseArray(rawJson.availabilities);
		return song;
	}

	return null;
};