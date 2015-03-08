function TextUtils() {};

TextUtils.isEmptyOrNull = function(text) {
    return text == null || text==="";
};

function UIUtils() {};

UIUtils.createElement = function(tag, innerText, attributes) {
    var attributesStr = '%attributes%';
    var result = '<'+tag+' '+attributesStr+'>'+innerText+'</'+tag+'>';
    var tags = '';
    for (var key in attributes) {
        if (attributes.hasOwnProperty(key)) {
            var val = attributes[key];
            tags = tags.concat(' '+key+'="'+val+'"')
        }
    }
    result = result.replace(attributesStr, tags);
    return result;
}



var TEST_RESULTS = [
    {
        "name": "Iron Man Armored Adventures Theme",
        "album": "Iron Man Armored Adventures Theme",
        "artist": "Rooney",
        "image": null,
        "availabilites": [
            {
                "name": "Spotify",
                "deep_link": "https://open.spotify.com/track/3LGUsgNrsptmc9SFqVNZyD",
                "image": null
            }
        ]
    },
    {
        "name": "Iron Man Mode",
        "album": "Generation Gaming II",
        "artist": "Dan Bull",
        "image": null,
        "availabilites": [
            {
                "name": "Spotify",
                "deep_link": "https://open.spotify.com/track/7q5WBozXYN1s9sPil7uMCT",
                "image": null
            }
        ]
    },
    {
        "name": "Iron Man - Live",
        "album": "Past Lives",
        "artist": "Black Sabbath",
        "image": null,
        "availabilites": [
            {
                "name": "Spotify",
                "deep_link": "https://open.spotify.com/track/1XtZ3GROvmy4JrT6uMvsD8",
                "image": null
            }
        ]
    },
    {
        "name": "Iron Man 3",
        "album": "Iron Man 3",
        "artist": "Brian Tyler",
        "image": null,
        "availabilites": [
            {
                "name": "Spotify",
                "deep_link": "https://open.spotify.com/track/7E5x1HdgFRyCnlRoaAMY38",
                "image": null
            }
        ]
    },
    {
        "name": "The Wicker Man - Live '01",
        "album": "Rock In Rio [Live]",
        "artist": "Iron Maiden",
        "image": null,
        "availabilites": [
            {
                "name": "Spotify",
                "deep_link": "https://open.spotify.com/track/1tXr6xy4msvYAQfXLUavxa",
                "image": null
            }
        ]
    },
    {
        "name": "Man On The Edge - Live",
        "album": "From Fear To Eternity The Best Of 1990-2010",
        "artist": "Iron Maiden",
        "image": null,
        "availabilites": [
            {
                "name": "Spotify",
                "deep_link": "https://open.spotify.com/track/5dxJ8JWyfYwTTHUKaX9gmL",
                "image": null
            }
        ]
    },
    {
        "name": "Can You Dig It (Iron Man 3 Main Titles)",
        "album": "Iron Man 3",
        "artist": "Brian Tyler",
        "image": null,
        "availabilites": [
            {
                "name": "Spotify",
                "deep_link": "https://open.spotify.com/track/6qlYJOg3XSur2V6VgqNIRb",
                "image": null
            }
        ]
    },
    {
        "name": "The Wicker Man",
        "album": "Brave New World",
        "artist": "Iron Maiden",
        "image": null,
        "availabilites": [
            {
                "name": "Spotify",
                "deep_link": "https://open.spotify.com/track/7gzOdsRPD7z9blkrvlLKbX",
                "image": null
            }
        ]
    },
    {
        "name": "The Man Who Would Be King",
        "album": "The Final Frontier",
        "artist": "Iron Maiden",
        "image": null,
        "availabilites": [
            {
                "name": "Spotify",
                "deep_link": "https://open.spotify.com/track/3w6OPkGDRIw5HVfKif4RZ3",
                "image": null
            }
        ]
    },
    {
        "name": "Iron Man",
        "album": "Paranoid (Remastered)",
        "artist": "Black Sabbath",
        "image": null,
        "availabilites": [
            {
                "name": "Spotify",
                "deep_link": "https://open.spotify.com/track/4HzdhXWJqczW6gOIXT6QRH",
                "image": null
            }
        ]
    },
    {
        "name": "Concerto in C Major for Piano and Orchestra (1773): II. Larghetto (from Iron Man)",
        "album": "Classical Music from the Cinema",
        "artist": "Antonio Salieri",
        "image": null,
        "availabilites": [
            {
                "name": "Spotify",
                "deep_link": "https://open.spotify.com/track/0UPY6Mielnmqyjyq1B7GqH",
                "image": null
            }
        ]
    },
    {
        "name": "Driving With The Top Down",
        "album": "Iron Man",
        "artist": "Ramin Djawadi",
        "image": null,
        "availabilites": [
            {
                "name": "Spotify",
                "deep_link": "https://open.spotify.com/track/7Mn2PWhGneW1kJYWWqwQYF",
                "image": null
            }
        ]
    },
    {
        "name": "Man On The Edge",
        "album": "The X Factor",
        "artist": "Iron Maiden",
        "image": null,
        "availabilites": [
            {
                "name": "Spotify",
                "deep_link": "https://open.spotify.com/track/5DtA7q19cLvJ7A9L13NmsH",
                "image": null
            }
        ]
    }
];




$(document).ready(function() {
    'use strict';
    
    //constants
    var API_ENDPOINT = 'http://localhost:8080/search';
    
    //views
    var resultsView = $('#searchResults');

    //functions
    var getUrl = function(query, queryType) {
        return API_ENDPOINT + '?query=' + query + '&type=' + queryType; 
    }
    
    var search = function() {
        var query = $('#searchInput').val();
        var queryType = $('#searchOptions').val();
        
        if (!TextUtils.isEmptyOrNull(query) && !TextUtils.isEmptyOrNull(queryType)) {
            var url = getUrl(query, queryType); 

            $.getJSON(url, function(data) {
                $('#resultsTitle').text('success!');
                console.log('success!');

                var resultsView = $('#searchResults');
                $.each( data.results, function( index, val ) {
                    console.log(index);
                    console.log(val);
                    resultsView.append("<li id='" + index + "'>" + val.name + "</li>" );
                });

            }).fail(function(data) {
                $('#resultsTitle').text('failed :( ' + data);
                console.log(data);
            })
        } else {
            
        }
    }
    
    var updateResultsWithSongs = function(songs) {
        var resultsView = $('#searchResults');
        resultsView.empty();
        
        if (songs != null && songs.length > 0) {
            $.each(songs, function(index, song) {
                var hyperlink = UIUtils.createElement('a', song.name + ' - ' + song.artist, {href:song.availabilites[0].deep_link, target : '_blank'});
                var cell = UIUtils.createElement('li', hyperlink, {id : index,
                                                                   class : 'searchCell'});
                console.log(cell);
                resultsView.append(cell);
            })
        } else {
            resultsView.append('no results!')    ;
        }
    }

//    //TODO remove when done with UI
//    $('#searchButton').click(function(data) {
//        search();
//    });
    
    updateResultsWithSongs(TEST_RESULTS);
});

