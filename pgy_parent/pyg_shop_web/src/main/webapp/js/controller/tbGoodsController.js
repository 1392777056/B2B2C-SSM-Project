app.controller("tbGoodsController",function ($scope,itemCatService,uploadService,tbGoodsService,typeTemplateService) {

    /* 对保存的图片格式进行初始化 */
    $scope.entity = {tbGoodsDesc:{itemImages:[],specificationItems:[]},tbGoods:{isEnableSpec:'1'}};

    /* 添加保存 */
    $scope.addGoodsToImages = function () {
        $scope.entity.tbGoodsDesc.itemImages.push($scope.images);
    };

    $scope.delGoodImage = function (index) {
        $scope.entity.tbGoodsDesc.itemImages.splice(index,1);
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
            $scope.brandList = JSON.parse(response.brandIds);  // 显示某模板下的品牌
            $scope.entity.tbGoodsDesc.customAttributeItems = JSON.parse(response.customAttributeItems); // 显示某模板下的扩展属性

            typeTemplateService.findSpecLists(newvalue).success(function (response) {
                $scope.specList = response;
            });
        });
    });

    /* 点击规格详细表，进行添加或修改 */
    $scope.updateSpecification = function ($event,specName,optionName) {
        if ($event.target.checked) {
           var specObj =  selectObjFromList($scope.entity.tbGoodsDesc.specificationItems,specName);
           /* 如果有对象  再去追加属性中的值 */
           if (specObj != null) {
               specObj.attributeValue.push(optionName);
           } else {
               $scope.entity.tbGoodsDesc.specificationItems.push({attributeName:specName,attributeValue:[specName]});
           }
        } else {
            var specObj =  selectObjFromList($scope.entity.tbGoodsDesc.specificationItems,specName);
            var index= specObj.attributeValue.indexOf(optionName);
            specObj.attributeValue.splice(index,1);
            if(specObj.attributeValue.length == 0) {
                var index = $scope.entity.tbGoodsDesc.specificationItems.indexOf(specObj);
                $scope.entity.tbGoodsDesc.specificationItems.splice(index,1);
            }
        }
    };

    function selectObjFromList(specificationItems,specName) {
        for (var i = 0; i < specificationItems.length; i++) {
            if(specificationItems[i].attributeName == specName){
                return specificationItems[i];
            }
        }
        return null;
    }


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