/* - album.js */

function Album() {

	var self 			= this;

	self.name			= null;		//string
	self.artist			= null;		//string
	self.images			= null;		//[]
	self.availabilities	= null;		//[]

	return self;
}

Album.parseArray		= function(albums) {
	if (albums) {
		var parsedAlbums	= [];
		for (var i=0; i < albums.length; ++i) {
			parsedAlbums.push(Album.parse(albums[i]));
		}
		return parsedAlbums;
	}

	return null;
};

Album.parse			= function(rawJson) {
	if (rawJson) {
		var album				= new Album();
		album.name				= rawJson.name;
		album.artist			= rawJson.artist;
		album.images			= Image.parseArray(rawJson.images);
		album.availabilities	= Availability.parseArray(rawJson.availabilities);
		return album;
	}

	return null;
};