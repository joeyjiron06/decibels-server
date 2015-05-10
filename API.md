#JSearch REST API documentation

##Search

Search for artists, albums, tracks. Filter by types or query for all that match a keyword string.

**Endpoint**

```GET http://api.jsearch.com/search```

**Query Parameters**

| key     | value   | required   |
|:-------:|:-------:|:----------:|
| query | The search query's keyword (url encoded). | yes|
| type  | Type is an array of any of the following types: *artist, album, song* (without any spaces). If no type is specified, then the default is to search all types. If you specify an invalid type then you will receive back the appropriate message. | no |

You will receive an object back that contains the *type* you search for (remember if no type is specified than the default is all types). So if you specify ```type=artist,album,song``` you will get back an object that looks like this:

```json
{
	"artists" : [...],
	"albums" : [...],
	"songs" : [...]
}
```




**Example**

*Request*

```GET http://api.jsearch.com/search?query=rolling%20stones&type=artist```

*Response*

```json
{
  "artists": [
    {
      "name": "Karaoke Soundtrack - Originally Performed By The Rolling Stones",
      "image": "http://path/to/image/url",
      "availabilites": [
        {
          "name": "Spotify",
          "deep_link": "http://open.spotify.com/artist/76NXjNzf3ylBgADP53uelZ",
          "image": "https://flic.kr/p/rK6o4N"
        }
      ]
    },
    {
      "name": "Peter Green, Ian Stewart, Charlie Hart, Charlie Watts (of The Rolling Stones), Brian Knight",
      "image": null,
      "availabilites": [
        {
          "name": "Spotify",
          "deep_link": "http://open.spotify.com/artist/1THNGPjAeQi7V5o2kOSoG2",
          "image": "https://flic.kr/p/rK6o4N"
        }
      ]
    }
    ...
  ]
}

```

##Artist

An artist object has the following fields:

| key   | value type |  description |
|:-----|:------------|:-------------|
|name|	string|The name of the artist|
|imges|array of [image](#image) objects| The images associated with this artist|
|availabilites|array of [availability](#availability) objects| The availabilities of an artist, meaning it's available on *Spotify, Rhapsody, Rdio, etc.*|


##Album

An album object has the following fields:

| key   | value type |  description |
|:------|:-----------|:-------------|
|name|string|The name of the album|
|artist|string|The name of the artist|
|images|array of [image](#image) objects|An array of images for this album|
|availabilites|array of [availability](#availability) objects| The availabilities of an album, meaning it's available on *Spotify, Rhapsody, Rdio, etc.*|

##Song

A song object has the following fields:

| key   | value type |  description |
|:------|:-----------|:-------------|
|name|string|The name of the song|
|album|string|The name of the album|
|artist|string|The name of the artist|
|images|array of [image](#image) objects|An array of images for this album|



##<a name="image"></a> Image

An image object has the following fields:

| key   | value type |  description |
|:------|:-----------|:-------------|
|width|integer|The width of the image|
|height|integer|The height of the image|
|url|string|The image url|

##<a name="availability"></a> Availability

The service that the respective object is available on. For instance, "The Black Keys" are available on Spotify. An availability contains the following fields:

| key   | value type |  description |
|:------|:-----------|:-------------|
|name|string|The name of the availability *(Spotify, Rhapsody, Youtube, etc)*.|
|image|string|The url of the image for the availability|
|deep_link|string|The url pointing to the service that the item is available on.|

