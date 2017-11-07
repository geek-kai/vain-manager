/**系统日志列表**/
angular.module("log.controllers", ["log.services", "common.menu.services", "common.services"])
    .controller("LogListCtrl", ["appConstant", "$scope", "menuUtils", "logHttpServices", "commonUtils",
        function (appConstant, $scope, menuUtils, logHttpServices, commonUtils) {
            $scope.init = function () {
                var menuKey = commonUtils.getUrlParameter("menuKey");
                $scope.permissions = [];
                menuUtils.getUserPermissionsByMenuKey(menuKey, $scope.permissions);
                $scope.queryParam = commonUtils.initQueryParam();
                if (!$scope.queryParam) {
                    $scope.queryParam = {curPage: 1, pageSize: appConstant.QUERY_PARAM_PAGE_SIZE};
                }
                $scope.queryParam = {curPage: 1, pageSize: appConstant.QUERY_PARAM_PAGE_SIZE};
                $scope.jumpPage();
            };

            $scope.jumpPage = function (page, query) {
                if (page !== undefined) {
                    $scope.queryParam.curPage = page;
                }
                var curQueryParam;
                // 如果是查询动作，取输入的条件
                if (query) {
                    curQueryParam = $scope.queryParam;
                } else {
                    // 如果是翻页动作，取上次查询的条件
                    curQueryParam = commonUtils.initQueryParam();
                    if (!curQueryParam) {
                        curQueryParam = {curPage: 1, pageSize: appConstant.QUERY_PARAM_PAGE_SIZE};
                    } else {
                        curQueryParam.curPage = $scope.queryParam.curPage;
                    }
                }
                logHttpServices.getPagedList(curQueryParam, function (data) {
                    if (data.code == 200) {
                        $scope.noData = false;
                        commonUtils.storeQueryParam(curQueryParam);
                        $scope.logs = data.dataList;
                        $scope.paginator = commonUtils.initPaginator(data.totalSize, curQueryParam.curPage, curQueryParam.pageSize);
                    } else if (data.code == 404) {
                        if (curQueryParam.curPage == 1) {
                            commonUtils.storeQueryParam(curQueryParam);
                            $scope.logs = null;
                            $scope.paginator = null;
                            $scope.noData = true;
                            return;
                        }
                       $scope.jumpPage(curQueryParam.curPage - 1);
                    } else {
                    }
                })
            };

            $scope.search = function () {
                $scope.jumpPage(1, true);
            };

            $scope.logType = [{
                key: "登录注销",
                value: [6, 7]
            }, {
                key: "敏感操作",
                value: [1, 2, 3]
            }, {
                key: "状态操作",
                value: [4, 5]
            }]
        }]);

