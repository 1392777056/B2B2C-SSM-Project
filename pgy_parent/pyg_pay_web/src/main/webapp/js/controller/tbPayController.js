app.controller("payController",function ($scope,payService) {

    $scope.unifiedorder = function () {
        payService.unifiedorder().success(function (response) {

            $scope.resultMap = response;
            new QRious({
                element:document.getElementById('qrious'),
                size:250,
                value:response.code_url,
                level:'H'
            });

            $scope.findPayInfo(response.out_trade_no);

        });

    }

    $scope.findPayInfo = function (out_trade_no) {

        payService.findPayInfo(out_trade_no).success(function (response) {
            if (response.success) {
                location.href = "paysuccess.html#?totalFee="+;
            } else {
                if (response.message == "支付超时") {
                    $scope.unifiedorder();
                }

            }
        })
    }
    
    $scope.showMon = function () {
       $scope.totalFee =   location.search()['totalFee'];
        
    }

});