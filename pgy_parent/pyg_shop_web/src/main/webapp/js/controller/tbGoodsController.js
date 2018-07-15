app.controller("tbGoodsController",function ($scope,itemCatService,tbGoodsService) {

    $scope.parentId = 0;
    $scope.findItemCategoty1List = function () {
        itemCatService.findParentId($scope.parentId).success(function (response) {
            $scope.itemCategoty1List = response;
        });
    };

    $scope.$watch("entity.tbGoods.category1Id",function (newvalue) {
        itemCatService.findParentId(newvalue).success(function (response) {
            $scope.itemCategoty2List = response;
            $scope.itemCategoty3List = [];
        });
    });

    $scope.$watch("entity.tbGoods.category2Id",function (newvalue) {
        itemCatService.findParentId(newvalue).success(function (response) {
            $scope.itemCategoty3List = response;
        });
    });

    /* 添加商品信息 */
    $scope.save = function () {

        $scope.entity.tbGoodsDesc.introduction = editor.html();

        tbGoodsService.add($scope.entity).success(function (response) {
            if (response.success) {
                location.href = "goods.html";
            } else {
                alert(response.message);
            }
        });
    }



});