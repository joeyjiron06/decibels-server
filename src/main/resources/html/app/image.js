/* - image.js */

function Image() {

	var self			= this;

	self.width			= null;		//integer
	self.height			= null;		//integer
	self.url			= null;		//string

	return self;
}

Image.parseArray		= function(images) {
	if (images) {
		var parsedImages	= [];
		for (var i=0; i < images.length; ++i) {
			parsedImages.push(Image.parse(images[i]));
		}
		return parsedImages;
	}

	return null;
};


Image.parse				= function(rawJson) {
	if (rawJson) {
		var image		= new Image();
		image.width		= rawJson.width;
		image.height	= rawJson.height;
		image.url		= rawJson.url;
		return image;
	}

	return null;
};