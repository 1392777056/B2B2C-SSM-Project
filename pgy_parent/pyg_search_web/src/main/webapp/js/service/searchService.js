app.service("searchService",function ($http) {

    this.searchKeywordsInit = function (paramsMap) {
       return $http.post("./search/searchKeywordsInit",paramsMap);
    }
});