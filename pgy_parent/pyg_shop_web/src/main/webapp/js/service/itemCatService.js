app.service("itemCatService",function ($http) {

    this.findParentId = function (parentId) {
        return $http.get("../itemCat/findParentId1/"+parentId);
    };

    this.findOne = function (id) {
        return $http.get("../itemCat/findOne/" + id);
    }

});