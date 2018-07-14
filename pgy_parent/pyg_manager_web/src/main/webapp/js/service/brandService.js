app.service("brandService",function ($http) {

    this.findBrandList = function () {
        return $http.get("../brand/findBrandList");
    };

    /* 分页 */
    this.findPages = function(pageNum,pageSize) {
        return $http.get("../brand/brandPage/" + pageNum + "/" + pageSize);
    };

    /* 搜索框 */
    this.sreachBrand = function (pageNum,pageSize,searchEntity) {
        return $http.post("../brand/searchBrands/" + pageNum  + "/" + pageSize,searchEntity);
    };

    /* 添加品牌 */
    this.save = function (entity){
        return $http.post("../brand/brandSave",entity);
    };

    /* 修改品牌 */
    this.update = function (entity){
        return $http.post("../brand/brandUpdate",entity);
    };

    /* 修改单个对象 */
    this.initfindting = function (id) {
        return $http.post("../brand/brandInitFind/"+id);
    };

    /* 删除单个品牌 */
    this.dele = function (selectIds) {
        return $http.get("../brand/brandDel/"+selectIds);
    };

});