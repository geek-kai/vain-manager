/**
 * 系统管理用户中心的controller
 */
angular.module("user.controllers", []).controller(
    "UserListCtrl", ["$cookieStore", "appConstant", "$cookies", "$scope", "userHttpService", "commonUtils",
        function ($cookieStore, appConstant, $cookies, $scope, userHttpService, commonUtils) {
            $scope.init = function () {
                $scope.paginator = null;
                userHttpService.getPagedList({}, function (data) {
                    if (data.code == 200) {
                        $scope.users = data.dataList;
                    }
                });
                $scope.queryParam = commonUtils.initQueryParam();
                if (!$scope.queryParam) {
                    $scope.queryParam = {curPage: 1, pageSize: appConstant.QUERY_PARAM_PAGE_SIZE};
                }
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
                userHttpService.getPagedList(curQueryParam, function (data) {
                    if (data.code == 200) {
                        // $scope.noData = false;
                        commonUtils.storeQueryParam(curQueryParam);
                        $scope.users = data.dataList;
                        $scope.paginator = commonUtils.initPaginator(data.totalSize, curQueryParam.curPage, curQueryParam.pageSize);
                    } else if (data.code == 404) {
                        if (curQueryParam.curPage == 1) {
                            commonUtils.storeQueryParam(curQueryParam);
                            //   $scope.noData = true;
                            $scope.datas = null;
                            $scope.paginator = null;
                            return;
                        }
                        $scope.jumpPage(curQueryParam.curPage - 1);
                    } else {
                    }
                })
            };


        }]);