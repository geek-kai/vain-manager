angular.module("info.controllers", [])
/**系统信息列表**/
    .controller("InfoCtrl", ["appConstant", "$scope", "infoHttpServices",
        function (appConstant, $scope, infoHttpServices) {
            $scope.init = function () {
                infoHttpServices.getSystemInfo({}, function (data) {
                    if (data.code == 200)
                        $scope.infos = data.data;
                });
                $scope.cpu = 0 + "%";
                $scope.cpuMonitoring();

            };

            /*递归监控cpu使用百分比*/
            $scope.cpuMonitoring = function () {
                infoHttpServices.getSystemCpuInfo({}, function (data) {
                    if (data.code == 200 && typeof (data.data)) {
                        $scope.cpu = Number(data.data) + "%";
                        $scope.cpuMonitoring();
                    }
                });

            };
        }]);

