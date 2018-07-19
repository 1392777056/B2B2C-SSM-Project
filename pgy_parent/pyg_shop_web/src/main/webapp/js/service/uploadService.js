app.service("uploadService",function ($http) {

    /* 图片上传 */
    this.uploadGoodImage = function () {
        var formData=new FormData();  // H5对象
        formData.append("file",file.files[0]);
        return $http({
            method:'post',
            url:'../upload/uploadFile',
            data:formData,
            headers:{'Content-Type':undefined},
            transformRequest:angular.identity      //anjularjs transformRequest function 将序列化我们的 formdata object
        });
    };

});