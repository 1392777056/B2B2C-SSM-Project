app.controller("goodsController",function ($scope,$controller,goodsService) {

    /* 继承 baseController */
    $controller('baseController',{$scope:$scope});

    /* 搜索框 */
    $scope.sreach = function (pageNum,pageSize) {
        goodsService.sreachGoods(pageNum,pageSize,$scope.searchEntity).success(function (response) {
            $scope.list = response.rows;
            $scope.paginationConf.totalItems = response.total;
        });
    };


});