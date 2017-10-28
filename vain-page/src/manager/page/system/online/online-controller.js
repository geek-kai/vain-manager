/**在线用户列表**/
angular.module("online.controllers", ["online.services", "common.menu.services", "common.services"])
    .controller("OnLineListCtrl", ["appConstant", "$scope", "menuUtils", "onlineHttpServices", "commonUtils", "msgModal",
        function (appConstant, $scope, menuUtils, onlineHttpServices, commonUtils, msgModal) {
            $scope.init = function () {
                var menuKey = commonUtils.getUrlParameter("menuKey");
                $scope.permissions = [];
                menuUtils.getUserPermissionsByMenuKey(menuKey, $scope.permissions);
                onlineHttpServices.getOnLineUser({}, function (data) {
                    if (data.code == 200) {
                        $scope.onlineUsers = data.dataList;
                    }
                });
            };

            /*强制下线*/
            $scope.forcedOffLine = function (user) {
                onlineHttpServices.forcedOffLine(user, function (data) {
                    msgModal.alertMsg(commonUtils.convertResult(data.code));
                    if (data.code == 200)
                        $scope.init();
                });
            };
        }]);

