app.controller("tbCartController",function ($scope,tbAddressService,tbCartService,tbOrderService) {

    $scope.entity = {paymentType:'1'};

    $scope.saveOrder = function () {

        $scope.entity['receiverAreaName'] = $scope.selectedAddress.address;
        $scope.entity['receiverMobile'] = $scope.selectedAddress.mobile;
        $scope.entity['receiver'] = $scope.selectedAddress.contact;

        tbOrderService.save($scope.entity).success(function (response) {
            if (response.success) {
                alert("跳转到付款页面");
            } else {
                alert(response.message);
            }
        })


    };

    $scope.selectedAddress = null;

    $scope.findAddressByUser = function () {
        tbAddressService.findAddressByUser().success(function (response) {
            $scope.addressList = response;
            for (var i = 0; i < response.length; i++) {
                if(response[i].isDefault == '1'){
                    $scope.selectedAddress = response[i];
                    break;
                }
            }
            if (response!=null && $scope.selectedAddress ==null){
                $scope.selectedAddress = response[0];
            }
        })
    };

    $scope.isSelectedAddress = function (pojo) {

        if ($scope.selectedAddress == pojo ) {
            return true;
        }

        return false;
    };

    $scope.selectAddre = function (pojo) {
        $scope.selectedAddress = pojo;
    };




    $scope.addCartLists = function (itemId,num) {
      tbCartService.addCartLists(itemId,num).success(function (response) {
          if (response.success) {
              $scope.findCartItemList();
          } else {
              alert(response.message)
          }
      })
    };

    $scope.findCartItemList = function () {
        tbCartService.findCartItemList().success(function (response) {
            $scope.cartItemList = response;

            $scope.totalNum = 0;
            $scope.totalMoney = 0.00;
            for (var i = 0; i < response.length; i++) {
                var tbOrderItemList = response[i].tbOrderItemList;
                for (var j = 0; j < tbOrderItemList.length; j++) {
                    $scope.totalNum += tbOrderItemList[j].num;
                    $scope.totalMoney += tbOrderItemList[j].totalFee;
                }
            }
        });
    }

});