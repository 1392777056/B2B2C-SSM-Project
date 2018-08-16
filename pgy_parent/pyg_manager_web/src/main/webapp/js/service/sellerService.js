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


    /* 图片上传 */
    this.savePoiFile = function () {
        var formData=new FormData();  // H5对象
        formData.append("file",file.files[0]);
        return $http({
            method:'post',
            url:'../seller/uploadFile',
            data:formData,
            headers:{'Content-Type':undefined},
            transformRequest:angular.identity      //anjularjs transformRequest function 将序列化我们的 formdata object
        });
    };




});