app.service("payService",function ($http) {

    this.unifiedorder = function () {
        return $http.get("./pay/unifiedorder");
    }

    this.findPayInfo = function (out_trade_no) {
        return $http.get("./pay/findPayInfos/" + out_trade_no);
    }

});