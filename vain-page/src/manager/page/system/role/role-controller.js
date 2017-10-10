angular.module("role.controllers", [])
/**角色列表**/
    .controller("RoleListCtrl", ["appConstant", "$scope", "roleHttpServices", "commonUtils", "msgModal",
        function (appConstant, $scope, roleHttpServices, commonUtils, msgModal) {
            $scope.init = function () {
                roleHttpServices.getList({}, function (data) {
                    if (data.code == 200) {
                        $scope.roles = data.dataList;
                    }
                });
            };

            /*打开添加角色div*/
            $scope.addRole = function () {
                commonUtils.showCover();
                $scope.coverShow = 1;
                $scope.addWindow = 1;
                commonUtils.centerElem($("#modifyDiv"));
            };

            /*关闭添加角色div*/
            $scope.close = function () {
                commonUtils.hideCover();
                $scope.coverShow = 0;
                $scope.addWindow = 0;
            };

            /*分配权限*/
            $scope.grantMenu = function () {
                window.location.href = "role-detail.html";
            };
        }])
    /**角色权限列表**/
    .controller("RoleGrantCtrl", ["appConstant", "$scope", "roleHttpServices", "commonUtils",
        function (appConstant, $scope, roleHttpServices, commonUtils) {
            $scope.init = function () {
                roleHttpServices.getMenusByRoleId({id:1},function (data) {
                    console.log(data);
                });
            };
        }]);