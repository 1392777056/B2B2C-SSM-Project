app.service("tbAddressService",function ($http) {

    this.findAddressByUser = function () {
        return $http.get("./address/findAddressByUser");
    }
});