app.controller("specificationController",function ($scope,$controller,specificationService) {

    /* 继承 baseController */
    $controller('baseController',{$scope:$scope});

    /* 分页 */
    $scope.findPages = function(pageNum,pageSize) {
        specificationService.findPages(pageNum,pageSize).success(function (response) {
            $scope.list = response.rows;
            $scope.paginationConf.totalItems = response.total;
        })
    };

    /* 搜索框 */
    $scope.sreach = function (pageNum,pageSize) {
        specificationService.sreachSpecification(pageNum,pageSize,$scope.searchEntity).success(function (response) {
            $scope.list = response.rows;
            $scope.paginationConf.totalItems = response.total;
        })
    };

    /* 初始化规格详细对象 */
    $scope.entity={tbSpecificationOptions:[]};

    /* 添加规格详细表 */
    $scope.saveSpecificationOptions = function () {
        $scope.entity.tbSpecificationOptions.push({});
    };

    /* 删除规格详细表 */
    $scope.delSpecificationOptions = function (index) {
        $scope.entity.tbSpecificationOptions.splice(index,1);
    };

    /* 保存添加或者修改品牌 */
    $scope.save = function (){
        /* 判断是否有id 如果存在走保存方法 否则update */
        var resultObject  = null;
        if ($scope.entity.tbSpecification.id != null){
            resultObject = specificationService.update($scope.entity);
        } else {
            resultObject = specificationService.save($scope.entity);
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
        specificationService.initfindting(id).success(function (response) {
            $scope.entity = response;
        });
    };

    /* 删除单个品牌 */
    $scope.dele = function () {
        if ($scope.selectIds.length > 0 ){
            if(window.confirm("确认要删除吗？")) {
                specificationService.dele($scope.selectIds).success(function (response) {
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