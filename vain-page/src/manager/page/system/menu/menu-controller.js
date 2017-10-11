angular.module("menu.controllers", [])
/**角色列表**/
    .controller("MenuListCtrl", ["appConstant", "$scope", "menuHttpServices", "commonUtils", "msgModal",
        function (appConstant, $scope, menuHttpServices, commonUtils, msgModal) {
            $scope.init = function () {
                menuHttpServices.getMenuList({}, function (data) {
                    if (data.code == 200) {
                        $scope.menus = data.dataList;
                    }
                });
            };

        }]);
