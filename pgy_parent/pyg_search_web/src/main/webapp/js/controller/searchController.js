app.controller("searchController",function ($scope,searchService) {

    $scope.paramsMap = {keywords:"苹果"};
    $scope.searchKeywordsInit = function () {
        searchService.searchKeywordsInit($scope.paramsMap).success(function (response) {
            $scope.itemList = response.itemList;
        });
    }

});