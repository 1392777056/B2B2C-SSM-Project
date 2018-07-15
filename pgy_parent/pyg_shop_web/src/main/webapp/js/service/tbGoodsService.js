app.service("tbGoodsService",function ($http) {

    /* 添加商品 */
    this.add = function (entity) {
        return $http.post("../goods/add",entity);
    }

});