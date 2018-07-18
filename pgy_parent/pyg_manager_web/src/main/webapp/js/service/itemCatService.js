app.service("itemCatService",function ($http) {

    this.findAlls = function () {
      return $http.get("../itemCat/findAll");
    };

    this.findItemCatList = function () {
        return $http.get("../itemCat/findItemCatList");
    };

    /* 分页 */
    this.findPages = function(pageNum,pageSize) {
        return $http.get("../itemCat/itemCatPage/" + pageNum + "/" + pageSize);
    };

    /* 根据父id 去查询 */
    this.findParentId = function (pageNum,pageSize,parentId) {
      return $http.get("../itemCat/findParentId/"+ pageNum  + "/" + pageSize+"/"+parentId);
    };
    /*this.findParentId = function (parentId) {
        return $http.get("../itemCat/findParentId/"+parentId);
    };*/

    /* 添加品牌 */
    this.save = function (entity){
        return $http.post("../itemCat/itemCatSave",entity);
    };

    /* 修改品牌 */
    this.update = function (entity){
        return $http.post("../itemCat/itemCatUpdate",entity);
    };

    /* 修改单个对象 */
    this.initfindting = function (id) {
        return $http.post("../itemCat/itemCatInitFind/"+id);
    };

    /* 删除单个品牌 */
    this.dele = function (selectIds) {
        return $http.get("../itemCat/itemCatDel/"+selectIds);
    };

});