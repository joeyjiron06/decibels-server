/**
 * results.js
 * */

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
			return true;//self.hasArtists() || self.hasAlbums() || self.hasSongs();
		};

		self.hasArtists = function() {
			return !internals.isEmptyOrNull(self.artists);
		};

		self.hasAlbums = function() {
			return !internals.isEmptyOrNull(self.albums);
		};

		self.hasSongs = function() {
			return !internals.isEmptyOrNull(self.songs);
		};

		$scope.search = function() {
			var query = $scope.query;
			if (!internals.isEmptyOrNull(query)) {
				console.log('searching ' + $scope.query);

				DecibelsService.search(query, 'artist', {
					success : function(data) {
						console.log('success!');
						console.log(data.results);
						internals.setData(data.results, null, null);
					},
					error : function(data) {
						console.log(data);
					}
				});
			} else {
				console.log('null or empty query');
			}
		};

		/**
		 * Private
		 * */

		var internals = {
			setData : function(artists, albums, songs) {
				$scope.$applyAsync(function() {
					self.artists = artists;
					self.albums = albums;
					self.songs = songs;
				});
			},
			isEmptyOrNull : function(array) {
				return array === null || array === undefined || array.length == 0;
			}
		};

		init();

		return self;
	}])
	.service('DecibelsService', ['$http', function($http) {
			var self = this;

			var baseUrl = 'http://localhost:8080';//TODO change to proper url

			var constants = {
				SEARCH_ENDPOINT : (baseUrl + '/search')
			};

			var callbacks = {
				success : function(data, callback) {
					if (callback && callback.success) {
						callback.success(data);
					}
				},
				error : function(data, callback) {
					if (callback && callback.error) {
						callback.error(data);
					}
				}
			};

			/**
			 * Public
			 */

			self.search = function(query, type, callback) {
				var url = constants.SEARCH_ENDPOINT + '?type=' + type + '&query=' + query;
				$http.get(url)
					.success(function(data) {
						//TODO convert data
						callbacks.success(data, callback);
					}).error(function(data) {
						//TODO convert data
						callbacks.error(data, callback);
					});
			};

			return self;
		}]);
})();
