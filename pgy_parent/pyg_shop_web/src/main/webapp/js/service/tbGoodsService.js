app.service("tbGoodsService",function ($http) {

    /* 添加商品 */
    this.add = function (entity) {
        return $http.post("../goods/add",entity);
    }

    /* 搜索框 */
    this.sreachGoods = function (pageNum,pageSize,searchEntity) {
        return $http.post("../goods/sreachGoods/" + pageNum  + "/" + pageSize,searchEntity);
    };

    this.updateMarketable = function (selectIds,isMarketable) {
        return $http.get("../goods/updateMarketable/"+selectIds+"/"+isMarketable);
    }

    this.deleIsDele = function (selectIds,IsDeleid) {
        return $http.get("../goods/deleIsDele/"+selectIds+"/"+IsDeleid)

    }
});