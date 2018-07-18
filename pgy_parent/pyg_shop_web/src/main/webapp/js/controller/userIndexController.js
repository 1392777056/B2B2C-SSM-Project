app.controller("userIndexController",function ($scope,tbsellerService,userIndexService) {

    $scope.findUser = function () {
        userIndexService.findUser().success(function (response) {
           $scope.username = JSON.parse(response);
           /* 根据username名称去查询用户资料 */
            tbsellerService.findSellerAll($scope.username).success(function (response) {
                $scope.entity = response;
            });
        });
    };

    $scope.save = function() {
        userIndexService.updateSellerInfo($scope.entity,$scope.username).success(function (response) {
            if(response.success){
                alert(response.message);
            }else{
                alert(response.message);
            }
        })
    }

});