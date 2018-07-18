app.service("userIndexService",function ($http) {
    
    this.findUser = function () {
        return $http.get("../tbUser/findAll");
    };

    this.updateSellerInfo = function (entity,username) {
        return $http.post("../tbUser/updateSellerInfo/"+username,entity);
    }
});