angular.module("role.controllers", []).controller(
    "RoleListCtrl", ["appConstant", "$scope", "roleHttpServices", "commonUtils", "msgModal",
        function (appConstant, $scope, roleHttpServices, commonUtils, msgModal) {
            $scope.init = function () {

                roleHttpServices.getList({id: 1}, function (data) {
                   if(data.code==200){
                      $scope.roles =  data.dataList;
                   }
                });
            };
        }]);