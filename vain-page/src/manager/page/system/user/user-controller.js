/**
 * 系统管理用户中心的controller
 */
angular.module("user.controllers", []).controller(
    "UserListCtrl", ["$cookieStore", "appConstant", "$cookies", "$scope", "userHttpService", "commonUtils", "msgModal",
        function ($cookieStore, appConstant, $cookies, $scope, userHttpService, commonUtils, msgModal) {
            $scope.init = function () {
                $scope.paginator = null;
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
                userHttpService.getPagedList(curQueryParam, function (data) {
                    if (data.code == 200) {
                        commonUtils.storeQueryParam(curQueryParam);
                        $scope.users = data.dataList;
                        $scope.paginator = commonUtils.initPaginator(data.totalSize, curQueryParam.curPage, curQueryParam.pageSize);
                    } else if (data.code == 404) {
                        if (curQueryParam.curPage == 1) {
                            commonUtils.storeQueryParam(curQueryParam);
                            $scope.datas = null;
                            $scope.paginator = null;
                            return;
                        }
                        $scope.jumpPage(curQueryParam.curPage - 1);
                    } else {
                    }
                })
            };

            /*锁定用户*/
            $scope.lock = function (user) {
                user.state = user.state == 0 ? 1 : 0;
                userHttpService.lock(user, function (data) {
                    msgModal.alertMsg(commonUtils.convertResult(data.code));
                    $scope.init();
                })
            };

            /*重置密码的div*/
            $scope.showResetPwdDiv = function (user) {
                commonUtils.showCover();
                $scope.coverShow = 1;
                $scope.resetPwdWindow = 1;
                commonUtils.centerElem($("#pwdDiv"));
                $scope.resetPwdUser = user;
            };

            /*重置密码*/
            $scope.resetPwd = function () {
                if (new RegExp("^[0-9]*(([a-zA-Z]+[0-9]+)|([0-9]+[a-zA-Z]+))+[a-zA-Z]*$").test($scope.resetPassword)) {
                    $scope.resetPwdUser.newpasswd = $scope.resetPassword;
                    userHttpService.resetPwd($scope.resetPwdUser, function (data) {
                        msgModal.alertMsg(commonUtils.convertResult(data.code));
                        $scope.close();
                    })
                }
            };

            /*关闭重置密码的弹窗*/
            $scope.close = function (type) {
                commonUtils.hideCover();
                if (type == 1)
                    $scope.resetPwdWindow = 0;
                if (type == 2)
                    $scope.modifyWindow = 0;
                $scope.coverShow = 0;
            };

            /*显示添加账号div*/
            $scope.addAccount = function () {
                commonUtils.showCover();
                $scope.coverShow = 1;
                $scope.modifyWindow = 1;
                commonUtils.centerElem($("#modifyDiv"));
            };

            /*添加账号*/
            $scope.add = function () {
                userHttpService.add($scope.addUser, function (data) {
                    msgModal.alertMsg(commonUtils.convertResult(data.code));
                    $scope.close();
                })
            };

            /*系统用户类型 可扩展*/
            $scope.userType = [{
                key: '管理员',
                value: 1
            }, {
                key: '普通用户',
                value: 2
            }]

        }]);