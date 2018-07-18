app.service("goodsService",function ($http) {

    /* 搜索框 */
    this.sreachGoods = function (pageNum,pageSize,searchEntity) {
        return $http.post("../goodsController/sreachGoods/" + pageNum  + "/" + pageSize,searchEntity);
    };
});