app.service("tbUserService",function ($http) {

    this.showUserNameInit = function () {
        return $http.get("../user/showUserName");
    };
    this.sendCode = function (phone) {
        return $http.post("/user/getCode/"+phone);
    }

    this.userSave = function (user,code) {
        return $http.post("/user/saveUser/"+code,user);
    }

});