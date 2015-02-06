function TextUtils() {};

TextUtils.isEmptyOrNull = function(text) {
    return text == null || text==="";
};


var App = {
    API_ENDPOINT : 'http://localhost:8080/search',
    
    getUrl : function(query, queryType) {
        return this.API_ENDPOINT + '?query=' + query + '&type=' + queryType;
    },
    
    search : function(query, queryType) {
        if (!TextUtils.isEmptyOrNull(query) && !TextUtils.isEmptyOrNull(queryType)) {
            var url = this.getUrl(query, queryType); 
            console.log('url : ' + url);
           
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
            alert('please put in a valid query!');
        }
    }
};


$(document).ready(function() {
    
    this.getUrl = function(query, queryType) {
        return this.API_ENDPOINT + '?query=' + query + '&type=' + queryType; 
    }

    $('#searchButton').click(function(data) {
        var query = $('#searchInput').val();
        var queryType = $('#searchOptions').val();
        App.search(query, queryType);
    });
});

