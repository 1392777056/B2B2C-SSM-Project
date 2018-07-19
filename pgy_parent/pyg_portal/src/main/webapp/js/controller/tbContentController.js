app.controller("tbContentController",function ($scope,tbContentService) {

    $scope.findByCategoryId = function(categoryId) {
        tbContentService.findByCategoryId(categoryId).success(function (response) {
            $scope.bannerList = response;
        });
    }


});