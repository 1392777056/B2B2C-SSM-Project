app.service("tbOrderService",function ($http) {

    this.save = function (entity) {
        return $http.post("./order/add",entity);
    }
});