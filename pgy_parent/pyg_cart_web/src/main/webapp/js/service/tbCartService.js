app.service("tbCartService",function ($http) {

    this.findCartItemList = function () {
        return $http.post("/cart/findCartList");
    };

    this.addCartLists = function (itemId,num) {
        return $http.post("/cart/addCartList/" + itemId+ "/" + num);
    }

});