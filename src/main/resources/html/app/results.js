/* results.js */



var Arrays = {
    isEmptyOrNull : function(array) {
        return array == null || array.length == 0;
    }
};

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