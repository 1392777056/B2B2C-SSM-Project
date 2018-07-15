app.service("userIndexService",function ($http) {

    this.findUser = function () {
        return $http.get("../userIndex/findAll");
    }
});