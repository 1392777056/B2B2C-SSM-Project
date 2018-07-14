app.service("specificationService",function ($http) {

    this.findSpecificationList = function () {
        return  $http.get("../specification/findSpecificationList");
    };

    /* 分页 */
    this.findPages = function(pageNum,pageSize) {
        return $http.get("../specification/specificationPage/" + pageNum + "/" + pageSize);
    };

    /* 搜索框 */
    this.sreachSpecification = function (pageNum,pageSize,searchEntity) {
        return $http.post("../specification/searchSpecifications/" + pageNum  + "/" + pageSize,searchEntity);
    };

    /* 添加品牌 */
    this.save = function (entity){
        return $http.post("../specification/specificationSave",entity);
    };

    /* 修改品牌 */
    this.update = function (entity){
        return $http.post("../specification/specificationUpdate",entity);
    };

    /* 修改单个对象 */
    this.initfindting = function (id) {
        return $http.post("../specification/specificationInitFind/"+id);
    };

    /* 删除单个品牌 */
    this.dele = function (selectIds) {
        return $http.get("../specification/specificationDel/"+selectIds);
    };

});