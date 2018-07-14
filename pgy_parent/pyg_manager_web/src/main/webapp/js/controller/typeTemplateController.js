app.controller("typeTemplateController",function ($scope,$controller,brandService,specificationService,typeTemplateService) {

    /* 继承 baseController */
    $controller('baseController',{$scope:$scope});

    /* 分页 */
    $scope.findPages = function(pageNum,pageSize) {
        typeTemplateService.findPages(pageNum,pageSize).success(function (response) {
            $scope.list = response.rows;
            $scope.paginationConf.totalItems = response.total;
        })
    };

    /* 搜索框 */
    $scope.sreach = function (pageNum,pageSize) {
        typeTemplateService.sreachTypeTemplate(pageNum,pageSize,$scope.searchEntity).success(function (response) {
            $scope.list = response.rows;
            $scope.paginationConf.totalItems = response.total;
        })
    };

    /* 模板 --- 查询品牌所有数据 */
    $scope.findBrandList = function () {
      brandService.findBrandList().success(function (response) {
          $scope.brandList = {data:response};
      });
    };

    /* 模板 --- 查询规格所有数据 */
    $scope.findSpecificationList = function () {
        specificationService.findSpecificationList().success(function (response) {
            $scope.specificationList = {data:response};
        });
    };

    //$scope.brandList = {data:[{id:1,text:"联想"},{id:2,text:"小米"}]};

    /* 初始化 扩展属性对象 */
    $scope.entity={customAttributeItems:[]};

    /* 添加扩展属性 */
    $scope.saveTypeTemplateOptions = function () {
        $scope.entity.customAttributeItems.push({});
    };

    /* 删除扩展属性 */
    $scope.delTypeTemplateOptions = function (index) {
        $scope.entity.customAttributeItems.splice(index,1);
    };

    /* 以text 名称显示在页面上 */
    $scope.arrayListToStr = function (arrayLists) {
        arrayLists =  JSON.parse(arrayLists);
        var str = "";
        for(var i =0; i < arrayLists.length;i++) {
            if (i == arrayLists.length -1) {
                str += arrayLists[i].text;
            } else {
                str += arrayLists[i].text+",";
            }
        }
        return str;
    };


    /* 保存添加或者修改品牌 */
    $scope.save = function (){
        /* 判断是否有id 如果存在走保存方法 否则update */
        var resultObject;

        if ($scope.entity.id != null){
            resultObject = typeTemplateService.update($scope.entity);
        } else {
            resultObject = typeTemplateService.save($scope.entity);
        }

        resultObject.success(function (response) {
            if(response.success){
                $scope.reloadList();
            } else {
                alert(response.message);
            }
        });
    };

    /* 修改单个对象 */
    $scope.initfindting = function (id) {
        typeTemplateService.initfindting(id).success(function (response) {
            response.brandIds = JSON.parse(response.brandIds);
            response.specIds = JSON.parse(response.specIds);
            response.customAttributeItems = JSON.parse(response.customAttributeItems);
            $scope.entity = response;
        });
    };

    /* 删除单个品牌 */
    $scope.dele = function () {
        if ($scope.selectIds.length > 0 ){
            if(window.confirm("确认要删除吗？")) {
                typeTemplateService.dele($scope.selectIds).success(function (response) {
                    if(response.success){
                        $scope.reloadList();
                    } else {
                        alert(response.message);
                    }
                });
            }
        }
    };
});