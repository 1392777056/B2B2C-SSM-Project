app.controller('registerController', function($scope,tbsellerService) {

    $scope.registerUser=function () {
        tbsellerService.add($scope.entity).success(function (response) {
            if(response.success){
                alert("请等待审核，两个工作日内完成");
            }else{
                alert(response.message);
            }
        })
    }
});