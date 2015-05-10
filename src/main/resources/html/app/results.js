/**
 * results.js
 * */

//TODO try requre.js

(function(){
	angular.module('decibels', [])
	.directive('ngEnter', function () {
		return function (scope, element, attrs) {
			element.bind("keydown keypress", function (event) {
				if(event.which === 13) {
					scope.$apply(function (){
						scope.$eval(attrs.ngEnter);
					});

					event.preventDefault();
				}
			});
		};
	})
	.controller('ResultsController', ['$scope','DecibelsService',function($scope, DecibelsService){
		var self = this;

		var init = function() {
			internals.setData(null, null, null);
		};

		/**
		 * Public
		 * */

		self.hasItems = function() {
			return self.hasArtists() || self.hasAlbums() || self.hasSongs();
		};

		self.hasArtists = function() {
			return !JSU.isNullOrEmpty(self.artists);
		};

		self.hasAlbums = function() {
			return !JSU.isNullOrEmpty(self.albums);
		};

		self.hasSongs = function() {
			return !JSU.isNullOrEmpty(self.songs);
		};

		$scope.search = function() {
			// GET QUERY FROM UI
			var query = $scope.query;

			if (!JSU.isNullOrEmpty(query)) {

				console.log('searching ' + $scope.query);

				// GET TYPES TO SEARCH FOR
				var searchTypes		= internals.getSearchTypes();

				// QUERY THE SERVICE
				DecibelsService.search(query, searchTypes, {
					success : function(data) {
						console.log('success!', data);
						// SET THE DATA
						internals.setData(data.artists, data.albums, data.songs);
					},
					error : function(data) {
						//TODO figure out proper error
						console.log('error :(', data);
						internals.setData(null, null, null);
					}
				});
			} else {
				//TODO show ui?
				console.log('null or empty query');
			}
		};

		/**
		 * Private
		 * */

		var internals = {
			setData			: function(artists, albums, songs) {
				$scope.$applyAsync(function() {
					self.artists = artists;
					self.albums = albums;
					self.songs = songs;
				});
			},
			getSearchTypes	: function() {
				return JSU.combine(DecibelsService.TYPES.ARTIST, DecibelsService.TYPES.ALBUM, DecibelsService.TYPES.SONG);
			}
		};

		init();

		return self;
	}])
	.service('DecibelsService', ['$http', function($http) {
			var self			= this;

			var baseUrl			= 'http://localhost:8080';//TODO change to proper url

			var constants		= {
				SEARCH_ENDPOINT		: (baseUrl + '/search?query={0}')
			};

			//TODO move to it's own class singleto
			self.TYPES			= {
				ARTIST				: "artist",
				ALBUM				: "album",
				SONG				: "song"
			};

			var callbacks		= {
				success 			: function(data, callback) {
					if (callback && callback.success) {
						callback.success(data);
					}
				},
				error				: function(data, callback) {
					if (callback && callback.error) {
						callback.error(data);
					}
				}
			};

			/**
			 * Public
			 */

			self.search 		= function(query, type, callback) {
				var url			= internals.getSearchUrl(query, type);

				console.log(url + ' searchign url');
				$http.get(url)
					.success(function(data) {
						//TODO set parser
						var parsedData = internals.parseServerResponse(data);
						console.log('raw json', data);
						callbacks.success(parsedData, callback);
					}).error(function(data) {
						//TODO convert data
						callbacks.error(data, callback);
					});
			};

			var internals		= {
				getSearchUrl		: function(query, type) {
					var url			= JSU.format(constants.SEARCH_ENDPOINT, query);
					if (JSU.isNullOrEmpty(type)) {
						url			+= '&type=' + type;
					}
					return url;
				},
				//TODO clean up and move this to a parser class.
				parseServerResponse	: function(data) {
					if (data) {
						var result		= { };
						result.artists	= Artist.parseArray(data.artists);
						result.albums	= Album.parseArray(data.albums);
						result.songs	= Song.parseArray(data.songs);
						return result;
					}

					return null;
				}
			};

			return self;
		}]);
})();
