app.controller("userIndexController",function ($scope,tbsellerService,userIndexService) {

    $scope.findUser = function () {
        userIndexService.findUser().success(function (response) {
           $scope.username = JSON.parse(response);
        });
    };

    /* 查询商家用户 */
    $scope.findSellerAll = function () {
        tbsellerService.findSellerAll($scope.username).success(function (response) {
            $scope.entity = response;
        });
    };

});