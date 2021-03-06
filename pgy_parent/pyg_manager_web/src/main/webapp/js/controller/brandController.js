app.controller("brandController",function ($scope,$controller,brandService) {

    /* 继承 baseController */
    $controller('baseController',{$scope:$scope});

    /* 分页 */
    $scope.findPages = function(pageNum,pageSize) {
        brandService.findPages(pageNum,pageSize).success(function (response) {
            $scope.list = response.rows;
            $scope.paginationConf.totalItems = response.total;
        })
    };

    /* 搜索框 */
    $scope.sreach = function (pageNum,pageSize) {
        brandService.sreachBrand(pageNum,pageSize,$scope.searchEntity).success(function (response) {
            $scope.list = response.rows;
            $scope.paginationConf.totalItems = response.total;
        })
    };

    /* 保存添加或者修改品牌 */
    $scope.save = function (){
        /* 判断是否有id 如果存在走保存方法 否则update */
        var resultObject  = null;
        if ($scope.entity.id != null){
            resultObject = brandService.update($scope.entity);
        } else {
            resultObject = brandService.save($scope.entity);
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
        brandService.initfindting(id).success(function (response) {
            $scope.entity = response;
        });
    };

    /* 删除单个品牌 */
    $scope.dele = function () {
        if ($scope.selectIds.length > 0 ){
            if(window.confirm("确认要删除吗？")) {
                brandService.dele($scope.selectIds).success(function (response) {
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