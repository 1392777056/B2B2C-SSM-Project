app.controller("userIndexController",function ($scope,tbsellerService,userIndexService) {

    $scope.findUser = function () {
        userIndexService.findUser().success(function (response) {
           $scope.username = JSON.parse(response);

            tbsellerService.findSellerAll($scope.username).success(function (response) {
                $scope.entity = response;
            });
        });
    };

});