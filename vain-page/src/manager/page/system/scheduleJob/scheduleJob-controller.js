/**在线用户列表**/
angular.module("scheduleJob.controllers", ["scheduleJob.services", "common.menu.services", "common.services"])
    .controller("scheduleJobListCtrl", ["appConstant", "$scope", "menuUtils", "scheduleJobHttpServices", "commonUtils", "msgModal",
        function (appConstant, $scope, menuUtils, scheduleJobHttpServices, commonUtils, msgModal) {
            $scope.init = function () {
                var menuKey = commonUtils.getUrlParameter("menuKey");
                $scope.permissions = [];
                menuUtils.getUserPermissionsByMenuKey(menuKey, $scope.permissions);
                scheduleJobHttpServices.getList({}, function (data) {
                    if (data.code == 200) {
                        $scope.jobs = data.dataList;
                    }
                });

                /*暂停任务*/
                $scope.pause = function (job) {
                    scheduleJobHttpServices.pause(job, function (data) {
                        msgModal.alertMsg(commonUtils.convertResult(data));
                        $scope.init();
                    });
                };

                /*重新开启任务*/
                $scope.resume = function (job) {
                    scheduleJobHttpServices.resume(job, function (data) {
                        msgModal.alertMsg(commonUtils.convertResult(data));
                        $scope.init();
                    });
                };

                /*触发一次任务*/
                $scope.trigger = function (job) {
                    scheduleJobHttpServices.trigger(job, function (data) {
                        msgModal.alertMsg(commonUtils.convertResult(data));
                    });
                };

                /*删除任务*/
                $scope.delete = function (job) {
                    scheduleJobHttpServices.delete(job, function (data) {
                        msgModal.alertMsg(commonUtils.convertResult(data));
                        $scope.init();
                    });
                };

                /*修改任务弹窗*/
                $scope.modify = function (job) {
                    commonUtils.showCover();
                    $scope.coverShow = 1;
                    $scope.modifyWindow = 1;
                    commonUtils.centerElem($("#modifyDiv"));
                    $scope.job = job;
                };

                /*修改cron表达式*/
                $scope.modifyJob = function () {
                    if ($scope.cronExpression != $scope.job.cronExpression) { //cron表达式有变更
                        $scope.job.cronExpression = $scope.cronExpression;
                        scheduleJobHttpServices.modify($scope.job, function (data) {
                            msgModal.alertMsg(commonUtils.convertResult(data));
                            $scope.init();
                        });
                    }
                };

                $scope.close = function () {
                    commonUtils.hideCover();
                    $scope.coverShow = 0;
                    $scope.modifyWindow = 0;
                    $scope.job = null;
                };
            };
        }]);

