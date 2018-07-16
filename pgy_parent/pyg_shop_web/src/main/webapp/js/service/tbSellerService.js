app.service("tbsellerService",function($http){

    //增加
    this.add=function(entity){
        return  $http.post('userIndex/registerUser',entity);
    };

    this.findSellerAll = function(sellerId){
        return $http.get("../userIndex/findSellerAll/"+ sellerId);
    }

});