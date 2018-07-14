app.service("sellerService",function ($http) {

    /* 分页 */
    this.findPages = function(pageNum,pageSize) {
        return $http.get("../seller/sellerPage/" + pageNum + "/" + pageSize);
    };

    /* 搜索框 */
    this.sreachSeller = function (pageNum,pageSize,searchEntity) {
        return $http.post("../seller/searchSellers/" + pageNum  + "/" + pageSize,searchEntity);
    };

    /* 添加品牌 */
    this.save = function (entity){
        return $http.post("../seller/sellerSave",entity);
    };

    /* 修改品牌 */
    this.update = function (entity){
        return $http.post("../seller/sellerUpdate",entity);
    };

    /* 修改单个对象 */
    this.initfindting = function (id) {
        return $http.post("../seller/sellerInitFind/"+id);
    };

    /* 删除单个品牌 */
    this.dele = function (selectIds) {
        return $http.get("../seller/sellerDel/"+selectIds);
    };

});