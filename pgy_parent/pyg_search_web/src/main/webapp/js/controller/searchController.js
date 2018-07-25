app.controller("searchController",function ($scope,$location,searchService) {

    $scope.addDataMap = function (key,value) {
        $scope.paramsMap[key]=value;
        $scope.searchByParamsMaps();
    };

    $scope.deleDataMap = function (key) {
        $scope.paramsMap[key]='';
        $scope.searchByParamsMaps();
    };
    $scope.addDataMapSpec = function (key,value) {
        $scope.paramsMap.spec[key]=value;
        $scope.searchByParamsMaps();
    };

    $scope.deleDataMapSpec = function (key) {
       delete $scope.paramsMap.spec[key];
       $scope.searchByParamsMaps();
    };

    $scope.searchKeywordsInit = function () {
       /* alert(JSON.stringify($location.search()['keywords']));*/
        $scope.paramsMap = {keywords:$location.search()['keywords'],category:'',brand:'',spec:{},price:'',sort:'ASC',page:1};
        $scope.searchByParamsMaps();
    };
    
    $scope.searchKeywords = function () {
        $scope.paramsMap = {keywords:$scope.paramsMap.keywords,category:'',brand:'',spec:{},price:'',sort:'ASC',page:1};
        $scope.searchByParamsMaps();
    };
    
    $scope.searchByParamsMaps = function () {
        searchService.searchKeywordsInit($scope.paramsMap).success(function (response) {
            $scope.itemParamMap = response;
            buildPageLabel();
        });
    };

    /* 分页方法 */
    function buildPageLabel() {
        $scope.totalPages = [];//新增分页栏属性
        var maxPageNo = $scope.itemParamMap.totalPage;//得到最后页码
        var firstPage = 1;//开始页码
        var lastPage = maxPageNo;//截止页码
        $scope.firstDot = true;//前面有点
        $scope.lastDot = true;//后边有点
        if ($scope.itemParamMap.totalPage > 5) { //如果总页数大于 5 页,显示部分页码
            if ($scope.paramsMap.page <= 3) {//如果当前页小于等于 3
                lastPage = 5; //前 5 页
                $scope.firstDot = false;//前面没点
            } else if ($scope.paramsMap.page >= lastPage - 2) {//如果当前页大于等于最大页码-2
                firstPage = maxPageNo - 4;  //后 5 页
                $scope.lastDot = false;//后边没点
            } else { //显示当前页为中心的 5 页
                firstPage = $scope.paramsMap.page - 2;
                lastPage = $scope.paramsMap.page + 2;
            }
        } else {
            $scope.firstDot = false;//前面无点
            $scope.lastDot = false;//后边无点
        }
        //循环产生页码标签
        for (var i = firstPage; i <= lastPage; i++) {
            $scope.totalPages.push(i);
        }
    }
});