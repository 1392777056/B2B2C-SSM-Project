app.service("tbUserService",function ($http) {

    this.sendCode = function (phone) {
        return $http.post("/user/getCode/"+phone);
    }

    this.userSave = function (user,code) {
        return $http.post("/user/saveUser/"+code,user);
    }

});