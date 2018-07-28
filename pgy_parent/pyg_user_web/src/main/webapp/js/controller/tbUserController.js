app.controller("tbUserController",function ($scope,tbUserService) {

    $scope.userAdd = function () {
      if ($scope.entity.password != $scope.password2) {
          alert("密码不一致");
          return;
      }

      tbUserService.userSave($scope.entity,$scope.code).success(function (response) {
            if (response.success) {
                alert("注册成功，即将tiao转");
            } else {
                alert(response.message);
            }
      });

    };

    $scope.sendCode = function () {
        tbUserService.sendCode($scope.entity.phone).success(function (response) {
            alert("发送成功");
        });
    }

});