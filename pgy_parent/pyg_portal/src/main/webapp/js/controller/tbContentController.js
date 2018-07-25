app.controller("tbContentController",function ($scope,tbContentService) {

    $scope.findByCategoryId = function(categoryId) {
        tbContentService.findByCategoryId(categoryId).success(function (response) {
            $scope.bannerList = response;
        });
    };

    $scope.keywords = "苹果";

    $scope.search = function () {
        if ($scope.keywords == "") {
            $scope.keywords = "苹果";
        }
        location.href = "http://search.pinyougou.com/search.html#?keywords="+$scope.keywords;
    }

});