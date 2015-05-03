angular.module('decibels')
.service('DeibelsService', function($http) {
    var my = this;
    
    
    var init = function() {
        
    };
    
    var constants = function() {
        this.BASE_ENDPOINT = 'http://localhost:8080';
        this.SEARCH_ENDPOINT = internals.createEndpoint('/search');
    };
    
    /**
    * Public
    */
    
    my.search = function(query) {
        return $http.get()
    };
    
    var internals = {
        createEndpoint : function(endpoint) {
            return my.constants.BASE_ENDPOINT + endpoint;
        }
    };
    
    
    /**
    *   Init
    */
    init();
})