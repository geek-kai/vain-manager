/**系统配置列表**/
angular.module("config.controllers", ["config.services","common.menu.services","common.services"])
    .controller("ConfigListCtrl", ["appConstant","$scope","menuUtils","configHttpServices", "commonUtils", "msgModal",
        function (appConstant, $scope,menuUtils,configHttpServices, commonUtils, msgModal) {
            $scope.init = function () {
                var menuKey = commonUtils.getUrlParameter("menuKey");
                $scope.permissions = [];
                menuUtils.getUserPermissionsByMenuKey(menuKey, $scope.permissions);
                configHttpServices.getPagedList({}, function (data) {
                    if (data.code == 200) {
                        $scope.configs = data.dataList;
                    }
                });
            };

            /*修改配置弹窗*/
            $scope.modifyConfig = function (config) {
                $scope.config = config;
                commonUtils.showCover();
                $scope.coverShow = 1;
                $scope.modifyWindow = 1;
                commonUtils.centerElem($("#modifyDiv"));
            };

            /*修改*/
            $scope.modify = function () {
                configHttpServices.modify($scope.config, function (data) {
                    msgModal.alertMsg(commonUtils.convertResult(data.code));
                    if (data.code == 200)
                        $scope.close();
                })
            };

            /*关闭配置弹窗*/
            $scope.close = function () {
                commonUtils.hideCover();
                $scope.coverShow = 0;
                $scope.modifyWindow = 0;
            };


            $scope.configType = [{
                key: "String",
                value: "1"
            }, {
                key: "Int",
                value: "2"
            }, {
                key: "String[]",
                value: "3"
            }]
        }]);

