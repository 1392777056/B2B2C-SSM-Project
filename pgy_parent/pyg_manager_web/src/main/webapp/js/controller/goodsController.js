app.controller("goodsController",function ($scope,$controller,itemCatService,goodsService) {

    /* 继承 baseController */
    $controller('baseController',{$scope:$scope});

    $scope.status = ["未审核","审核通过","审核未通过","关闭"];

    $scope.category={};
    /* 查询分类 */
    $scope.selectCatoryAll = function () {
        itemCatService.findAlls().success(function (response) {
            for (var i = 0; i < response.length; i++) {
                $scope.category[response[i].id] = response[i].name;
            }
        });
    };

    /* 搜索框 */
    $scope.sreach = function (pageNum,pageSize) {
        goodsService.sreachGoods(pageNum,pageSize,$scope.searchEntity).success(function (response) {
            $scope.list = response.rows;
            $scope.paginationConf.totalItems = response.total;
        });
    };

    /* 通过状态去修改审核通过 */
    $scope.updateAuditStatus = function (auditStatus) {
        goodsService.updateAuditStatus($scope.selectIds,auditStatus).success(function (response) {
            if (response.success) {
                $scope.reloadList();
                $scope.selectIds = [];
            } else {
                alert(response.message);
            }

        });
    }


});