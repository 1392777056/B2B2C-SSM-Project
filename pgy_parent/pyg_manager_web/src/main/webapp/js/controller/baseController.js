app.controller("baseController",function ($scope) {

    /* 分页控件配置 */
    $scope.paginationConf = {
        currentPage: 1,
        itemsPerPage: 10,
        perPageOptions: [10, 20, 30, 40, 50],
        onChange: function(){
            $scope.reloadList();//重新加载
        }
    };

    $scope.searchEntity = {};
    /* 查询分页集的数据，总条数以及当前对象 */
    $scope.reloadList = function () {
        $scope.sreach($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
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

});