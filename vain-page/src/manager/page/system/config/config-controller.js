angular.module("config.controllers", [])
/**系统配置列表**/
    .controller("ConfigListCtrl", ["appConstant", "$scope", "configHttpServices", "commonUtils", "msgModal",
        function (appConstant, $scope, configHttpServices, commonUtils, msgModal) {
            $scope.init = function () {
                configHttpServices.getPagedList({}, function (data) {
                    if (data.code == 200) {
                        $scope.configs = data.dataList;
                    }
                });
            };

        }]);

