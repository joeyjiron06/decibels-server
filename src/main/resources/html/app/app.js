function TextUtils() {};

TextUtils.isEmptyOrNull = function(text) {
    return text == null || text==="";
};



$(document).ready(function() {
    //constants
    var API_ENDPOINT = 'http://localhost:8080/search';
    

    //functions
    var getUrl = function(query, queryType) {
        return API_ENDPOINT + '?query=' + query + '&type=' + queryType; 
    }
});

