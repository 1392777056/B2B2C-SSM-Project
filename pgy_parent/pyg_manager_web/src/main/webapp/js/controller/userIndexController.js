app.controller("userIndexController",function ($scope,userIndexService) {

    $scope.findUser = function () {
        userIndexService.findUser().success(function (response) {
           $scope.username = JSON.parse(response);
        });
    };

});