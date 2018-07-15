app.service("itemCatService",function ($http) {

    this.findParentId = function (parentId) {
        return $http.get("../itemCat/findParentId1/"+parentId);
    };

});