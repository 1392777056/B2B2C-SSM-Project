app.controller("itemCatController",function ($scope,typeTemplateService,itemCatService) {

    $scope.findTypeTempalteList = function () {
        typeTemplateService.findTypeTempalteList().success(function (response) {
            $scope.typeTemList =  response;
        });
    };

    /* 分页控件配置 */
    $scope.paginationConf = {
        currentPage: 1,
        itemsPerPage: 10,
        perPageOptions: [10, 20, 30, 40, 50],
        onChange: function(){
            $scope.reloadList();//重新加载
        }
    };

    $scope.parentId=0;
    $scope.reloadList = function () {
        $scope.findParentId($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
    };

    /* 根据父 id 去查询  */
    $scope.findParentId = function(pageNum,pageSize) {
        itemCatService.findParentId(pageNum,pageSize,$scope.parentId).success(function (response) {
            $scope.list = response.rows;
            $scope.paginationConf.totalItems = response.total;
        });
    };

    /*/!* 根据父 id 去查询  *!/
    $scope.findParentId = function(parentId) {
        itemCatService.findParentId(parentId).success(function (response) {
            $scope.list = response;
            /!*$scope.paginationConf.totalItems = response.total;*!/
        });
    };*/

    $scope.findBypId = function (id) {
        $scope.parentId  = id;
        $scope.findParentId($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage,$scope.parentId);
    };
    /* 面包屑 */
    $scope.gola = 1;
    $scope.entity1 = null;
    $scope.entity2 = null;

    $scope.mianbao = function (gola,pojo) {
        $scope.gola = gola;
        $scope.parentId = pojo.id;

        if ($scope.gola == 1) {
            $scope.entity1 = null;
            $scope.entity2 = null;
        }
        if ($scope.gola == 2) {
            $scope.entity1 = pojo;
            $scope.entity2 = null;
        }
        if ($scope.gola == 3) {
            $scope.entity2 = pojo;
        }
    };


    /* 保存添加或者修改品牌 */
    $scope.save = function (){
        /* 判断是否有id 如果存在走保存方法 否则update */
        var resultObject  = null;
        $scope.entity['parentId'] = $scope.parentId;
        if ($scope.entity.id != null){
            resultObject = itemCatService.update($scope.entity);
        } else {
            resultObject = itemCatService.save($scope.entity);
        }

        resultObject.success(function (response) {
            if(response.success){
                $scope.reloadList();
            } else {
                alert(response.message);
            }
        });
    };

    /* 修改单个对象 */
    $scope.initfindting = function (id) {
        itemCatService.initfindting(id).success(function (response) {
            $scope.entity = response;
        });
    };

    /* 初始化新的数组 */
    $scope.selectIds = [];
    /* 用$event获取选中项 */
    $scope.selections = function ($event,id) {
        if($event.target.checked){
            $scope.selectIds.push(id);
        } else {
            var index = $scope.selectIds.indexOf(id);
            $scope.selectIds.splice(index,1);
        }
    };
    /* 删除单个品牌 */
    $scope.dele = function () {
        if ($scope.selectIds.length > 0 ){
            if(window.confirm("确认要删除吗？")) {
                itemCatService.dele($scope.selectIds).success(function (response) {
                    if(response.success){
                        $scope.reloadList();
                    } else {
                        alert(response.message);
                    }
                });
            }
        }
    };
});