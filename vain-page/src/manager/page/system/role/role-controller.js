angular.module("role.controllers", []).controller(
    "roleListCtrl", ["appConstant", "$scope", "roleHttpServices", "commonUtils", "msgModal",
        function (appConstant, $scope, roleHttpServices, commonUtils, msgModal) {
            $scope.init = function () {

                roleHttpServices.getList({id: 1}, function (data) {
                    console.log(data);
                });
            };
        }]);