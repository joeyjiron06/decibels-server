/* results.js */




var TEST_RESULTS_EMPTY = {};
var TEST_RESULTS =
{
    "artists" : [
        {
            "artist": "Rooney",
            "image": "http://upload.wikimedia.org/wikipedia/en/6/64/Black_Sabbath_-_Paranoid.jpg",
            "availabilites": [
                {
                    "name": "Spotify",
                    "deep_link": "https://open.spotify.com/track/3LGUsgNrsptmc9SFqVNZyD",
                    "image": "https://farm9.staticflickr.com/8748/16896578316_ca5cbf8da6_t.jpg"
                }
            ]
        },

        {
            "artist": "Rooney",
            "image": "http://upload.wikimedia.org/wikipedia/en/6/64/Black_Sabbath_-_Paranoid.jpg",
            "availabilites": [
                {
                    "name": "Spotify",
                    "deep_link": "https://open.spotify.com/track/3LGUsgNrsptmc9SFqVNZyD",
                    "image": "https://farm9.staticflickr.com/8748/16896578316_ca5cbf8da6_t.jpg"
                }
            ]
        },

        {
            "artist": "Rooney",
            "image": "http://upload.wikimedia.org/wikipedia/en/6/64/Black_Sabbath_-_Paranoid.jpg",
            "availabilites": [
                {
                    "name": "Spotify",
                    "deep_link": "https://open.spotify.com/track/3LGUsgNrsptmc9SFqVNZyD",
                    "image": "https://farm9.staticflickr.com/8748/16896578316_ca5cbf8da6_t.jpg"
                }
            ]
        },

        {
            "artist": "Rooney",
            "image": "http://upload.wikimedia.org/wikipedia/en/6/64/Black_Sabbath_-_Paranoid.jpg",
            "availabilites": [
                {
                    "name": "Spotify",
                    "deep_link": "https://open.spotify.com/track/3LGUsgNrsptmc9SFqVNZyD",
                    "image": "https://farm9.staticflickr.com/8748/16896578316_ca5cbf8da6_t.jpg"
                }
            ]
        },

        {
            "artist": "Rooney",
            "image": "http://upload.wikimedia.org/wikipedia/en/6/64/Black_Sabbath_-_Paranoid.jpg",
            "availabilites": [
                {
                    "name": "Spotify",
                    "deep_link": "https://open.spotify.com/track/3LGUsgNrsptmc9SFqVNZyD",
                    "image": "https://farm9.staticflickr.com/8748/16896578316_ca5cbf8da6_t.jpg"
                }
            ]
        },

        {
            "artist": "Rooney",
            "image": "http://upload.wikimedia.org/wikipedia/en/6/64/Black_Sabbath_-_Paranoid.jpg",
            "availabilites": [
                {
                    "name": "Spotify",
                    "deep_link": "https://open.spotify.com/track/3LGUsgNrsptmc9SFqVNZyD",
                    "image": "https://farm9.staticflickr.com/8748/16896578316_ca5cbf8da6_t.jpg"
                }
            ]
        },

        {
            "artist": "Rooney",
            "image": "http://upload.wikimedia.org/wikipedia/en/6/64/Black_Sabbath_-_Paranoid.jpg",
            "availabilites": [
                {
                    "name": "Spotify",
                    "deep_link": "https://open.spotify.com/track/3LGUsgNrsptmc9SFqVNZyD",
                    "image": "https://farm9.staticflickr.com/8748/16896578316_ca5cbf8da6_t.jpg"
                }
            ]
        }

        ],
    "albums" : [
        {
            "album": "Iron Man Armored Adventures Theme",
            "artist": "Rooney",
            "image": "http://upload.wikimedia.org/wikipedia/en/6/64/Black_Sabbath_-_Paranoid.jpg",
            "availabilites": [
                {
                    "name": "Spotify",
                    "deep_link": "https://open.spotify.com/track/3LGUsgNrsptmc9SFqVNZyD",
                    "image": "https://farm9.staticflickr.com/8748/16896578316_ca5cbf8da6_t.jpg"
                }
            ]
        }
        ],
    "songs" : [
        {
            "name": "Iron Man Armored Adventures Theme",
            "album": "Iron Man Armored Adventures Theme",
            "artist": "Rooney",
            "image": "http://upload.wikimedia.org/wikipedia/en/6/64/Black_Sabbath_-_Paranoid.jpg",
            "time" : "4:54",
            "availabilites": [
                {
                    "name": "Spotify",
                    "deep_link": "https://open.spotify.com/track/3LGUsgNrsptmc9SFqVNZyD",
                    "image": "https://farm9.staticflickr.com/8748/16896578316_ca5cbf8da6_t.jpg"
                }
            ]
        }
        ]
};

var Arrays = {
    isEmptyOrNull : function(array) {
        return array == null || array.length == 0;
    }
};

function createResultView(result) {
   
    var view = $('<li></li>').addClass('result');
    
    if (result.image != null) {
        view.append(
            $('<img>')
               .attr('width', 120)
               .attr('height', 120)
               .attr('src', result.image)
        );
    }
     
    var titles = $('<div></div>');
            
    if (result.artist != null) {
        titles.append($('<h3></h3>').text(result.artist));
    }
    
    if (result.album != null) {
         titles.append($('<p></p>').text(result.album));
    }
    
    if (result.name != null) {
         titles.append($('<p></p>').text(result.name));
    }
    
    view.append(titles);

    view.click(function() {
        showAvailabilities(result);
    });

    
    return view;              
}

(function() {
    var app = angular.module('decibels', []);

    app.controller('ResultsController', function() {
        this.artists = TEST_RESULTS.artists;
        this.albums = TEST_RESULTS.albums;
        this.songs = TEST_RESULTS.songs;

        this.hasItems = function() {
            return this.hasArtists() || this.hasAlbums() || this.hasSongs();
        }

        this.hasArtists = function() {
            return !Arrays.isEmptyOrNull(this.artists);
        };

        this.hasAlbums = function() {
            return !Arrays.isEmptyOrNull(this.albums);
        };

        this.hasSongs = function() {
            return !Arrays.isEmptyOrNull(this.songs);
        };
    });
})();

