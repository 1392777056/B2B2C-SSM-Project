app.controller("tbGoodsController",function ($scope,$controller,itemCatService,uploadService,tbGoodsService,typeTemplateService) {

    /* 继承 baseController */
    $controller('baseController',{$scope:$scope});

    $scope.status = ["未审核","审核通过","审核未通过","关闭"];

    $scope.category={};
    /* 查询分类 */
    $scope.selectCatoryAll = function () {
        itemCatService.findAlls().success(function (response) {
            for (var i = 0; i < response.length; i++) {
                $scope.category[response[i].id] = response[i].name;
            }
        });
    };

    /* 搜索框 */
    $scope.sreach = function (pageNum,pageSize) {
        goodsService.sreachGoods(pageNum,pageSize,$scope.searchEntity).success(function (response) {
            $scope.list = response.rows;
            $scope.paginationConf.totalItems = response.total;
        });
    };

    /* 通过状态去修改审核通过 */
    $scope.updateAuditStatus = function (auditStatus) {
        goodsService.updateAuditStatus($scope.selectIds,auditStatus).success(function (response) {
            if (response.success) {
                $scope.reloadList();
                $scope.selectIds = [];
            } else {
                alert(response.message);
            }

        });
    }


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

            $scope.entity.itemList = [{spec:{},price:0,num:9999,status:"1",isDefault:"0"}];
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
               $scope.entity.tbGoodsDesc.specificationItems.push({attributeName:specName,attributeValue:[optionName]});
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

        createItemList();
    };

    $scope.deleSepcs = function (index) {
        var index =  $scope.entity.itemList.indexOf(entity.itemList);
        $scope.entity.itemList.splice(index,1);
    };

    /* 追加规格项 */
    function createItemList() {
        /*
        * spec  {"机身内存":"16G","网络":"联通3G"}
        * $scope.entity.tbGoodsDesc.specificationItems
        * [{"attributeName":"网络制式","attributeValue":["移动3G","移动4G"]},{"attributeName":"屏幕尺寸","attributeValue":["6寸","5寸"]}]
        * */
        var specItems = $scope.entity.tbGoodsDesc.specificationItems;
        $scope.entity.itemList = [{spec:{},price:0,num:9999,status:"1",isDefault:"0"}];
        for (var i = 0; i < specItems.length; i++) {
            $scope.entity.itemList = addItemColumn($scope.entity.itemList,specItems[i].attributeName,specItems[i].attributeValue);
        }
    }
    /* 往列中取追加值 */
    function addItemColumn(itemList,attributeName,attributeValue) {
        var newList = [];
            for (var i = 0; i < itemList.length; i++) {
            for (var j = 0; j < attributeValue.length; j++) {
                var newItem= JSON.parse(JSON.stringify(itemList[i]));   //涉及到了深克隆和浅克隆的问题
                newItem.spec[attributeName] = attributeValue[j];
                newList.push(newItem);
            }
        }
        return newList;
    }

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