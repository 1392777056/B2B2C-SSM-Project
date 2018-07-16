app.service("typeTemplateService",function ($http) {

    /* 查询所有 */
    this.findTypeTempalteList = function () {
        return $http.get("../typeTemplate/findAll");
    };

    /* 分页 */
    this.findPages = function(pageNum,pageSize) {
        return $http.get("../typeTemplate/typeTemplatePage/" + pageNum + "/" + pageSize);
    };

    /* 搜索框 */
    this.sreachTypeTemplate = function (pageNum,pageSize,searchEntity) {
        return $http.post("../typeTemplate/searchTypeTemplates/" + pageNum  + "/" + pageSize,searchEntity);
    };

    /* 添加品牌 */
    this.save = function (entity){
        return $http.post("../typeTemplate/typeTemplateSave",entity);
    };

    /* 修改品牌 */
    this.update = function (entity){
        return $http.post("../typeTemplate/typeTemplateUpdate",entity);
    };

    /* 修改单个对象 */
    this.initfindting = function (id) {
        return $http.post("../typeTemplate/typeTemplateInitFind/"+id);
    };

    /* 删除单个品牌 */
    this.dele = function (selectIds) {
        return $http.get("../typeTemplate/typeTemplateDel/"+selectIds);
    };

});