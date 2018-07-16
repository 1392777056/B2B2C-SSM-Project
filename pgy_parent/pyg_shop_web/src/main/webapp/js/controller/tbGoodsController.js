app.controller("tbGoodsController",function ($scope,itemCatService,uploadService,tbGoodsService,typeTemplateService) {

    /* 对保存的图片格式进行初始化 */
    $scope.entity = {tbGoodsDesc:{itemImages:[]}};

    /* 添加保存 */
    $scope.addGoodsToImages = function () {
        $scope.entity.tbGoodsDesc.itemImages.push($scope.images);
    };

    /* 图片上传 */
    $scope.uploadGoodImages = function () {
        uploadService.uploadGoodImage().success(function (response) {
            if (response.success){
                $scope.images.url=response.message;
            }else {
                alert(response.message);
            }
        })
    };

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

    $scope.$watch("entity.tbGoods.category3Id",function (newvalue) {
        itemCatService.findOne(newvalue).success(function (response) {
            $scope.entity.tbGoods.typeTemplateId = response.typeId;
        });
    });

    /* 显示模板的Id */
    $scope.$watch("entity.tbGoods.typeTemplateId",function (newvalue) {
        typeTemplateService.initfindting(newvalue).success(function (response) {
            $scope.brandList = JSON.parse(response.brandIds);
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