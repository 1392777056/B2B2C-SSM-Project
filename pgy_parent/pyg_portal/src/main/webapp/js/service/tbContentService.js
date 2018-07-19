app.service("tbContentService",function ($http) {

    this.findByCategoryId = function (categoryId) {
        return $http.get("../content/findByCategoryId/"+categoryId);
    }

});