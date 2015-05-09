#JSearch API documentation

##Search

Search for artists, albums, tracks. Filter by types or query for all that match a keyword string.

**Endpoint**

```GET http://api.jsearch.com/search```

**Query Parameters**

| key     | value   | required   |
|:-------:|:-------:|:----------:|
| query | The search query's keyword url encoded | yes|
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
