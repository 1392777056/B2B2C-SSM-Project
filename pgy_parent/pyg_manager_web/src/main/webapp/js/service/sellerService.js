app.service("sellerService",function ($http) {

    /* 分页 */
    this.findPages = function(pageNum,pageSize) {
        return $http.get("../seller/sellerPage/" + pageNum + "/" + pageSize);
    };

    /* 搜索框 */
    this.sreachSeller = function (pageNum,pageSize,searchEntity) {
        return $http.post("../seller/searchSellers/" + pageNum  + "/" + pageSize,searchEntity);
    };

    /* 修改品牌 */
    this.updateStatus = function (status,sellerId){
        return $http.post("../seller/sellerUpdate/"+status + "/" +sellerId);
    };

    /* 修改单个对象 */
    this.initfindting = function (id) {
        return $http.post("../seller/sellerInitFind/"+id);
    };



});