app.controller("sellerController",function ($scope,$controller,sellerService) {

    /* 继承 baseController */
    $controller('baseController',{$scope:$scope});

    /* 分页 */
    $scope.findPages = function(pageNum,pageSize) {
        sellerService.findPages(pageNum,pageSize).success(function (response) {
            $scope.list = response.rows;
            $scope.paginationConf.totalItems = response.total;
        })
    };

    /* 搜索框 */
    $scope.search = function (pageNum,pageSize) {
        sellerService.sreachSeller(pageNum,pageSize,$scope.searchEntity).success(function (response) {
            $scope.list = response.rows;
            $scope.paginationConf.totalItems = response.total;
        })
    };

    
    /* 修改单个对象 */
    $scope.initfindting = function (id) {
        sellerService.initfindting(id).success(function (response) {
            $scope.entity = response;
        });
    };

    $scope.update = function (status) {
        sellerService.updateStatus(status,$scope.entity.sellerId).success(function (response) {
            if(response.success) {
                $scope.reloadList();
            } else {
                alert(response.message);
            }
        });
    }
    
    $scope.exportXls = function () {
        window.open("../seller/exportXls");
    }

    $scope.savePoiFile = function () {
        sellerService.savePoiFile().success(function (response) {
            alert(response);
        });
    }
});