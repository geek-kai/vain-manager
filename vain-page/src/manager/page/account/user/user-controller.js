/**
 * 系统管理用户中心的controller
 */
angular.module("user.controllers", ["user.services", "common.services", "common.menu.services"])
    .controller("UserListCtrl", ["$cookieStore", "appConstant", "$cookies", "$scope", "userHttpServices", "commonUtils", "msgModal", "menuUtils",
        function ($cookieStore, appConstant, $cookies, $scope, userHttpServices, commonUtils, msgModal, menuUtils) {
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
                userHttpServices.getPagedList(curQueryParam, function (data) {
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
                userHttpServices.lock(user, function (data) {
                    msgModal.alertMsg(commonUtils.convertResult(data));
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
                    userHttpServices.resetPwd($scope.resetPwdUser, function (data) {
                        msgModal.alertMsg(commonUtils.convertResult(data));
                        $scope.close();
                    })
                }
            };

            /*关闭重置密码的弹窗*/
            $scope.close = function (type) {
                commonUtils.hideCover();
                switch (type) {
                    case 1:
                        $scope.resetPwdWindow = 0;
                        break;
                    case 2:
                        $scope.addWindow = 0;
                        break;
                    case 3:
                        $scope.modifyWindow = 0;
                        break;
                    case 4:
                        $scope.roleWindow = 0;
                        break;
                }
                $scope.coverShow = 0;
            };

            /*显示添加账号div*/
            $scope.addAccount = function () {
                commonUtils.showCover();
                $scope.coverShow = 1;
                $scope.addWindow = 1;
                commonUtils.centerElem($("#addDiv"));
            };

            /*修改信息*/
            $scope.showModify = function (user) {
                commonUtils.showCover();
                $scope.converShow = 1;
                $scope.modifyWindow = 1;
                commonUtils.centerElem($("#modifyDiv"));
                $scope.modifyUser = user;
            };

            /*添加账号*/
            $scope.add = function () {
                userHttpServices.add($scope.addUser, function (data) {
                    msgModal.alertMsg(commonUtils.convertResult(data));
                    $scope.close();
                    $scope.init();
                })
            };

            /*删除账号*/
            $scope.delete = function (id) {
                userHttpServices.delete({id: id}, function (data) {
                    msgModal.alertMsg(commonUtils.convertResult(data));
                    $scope.init();
                })
            };

            /*修改用户信息*/
            $scope.modify = function () {
                userHttpServices.modify($scope.modifyUser, function (data) {
                    msgModal.alertMsg(commonUtils.convertResult(data));
                    $scope.init();
                })
            };

            /*checkbox点击添加到选择数组中*/
            $scope.batchId = function (data) {
                if (typeof $scope.ids == "undefined")
                    $scope.ids = [];
                if (typeof data.checkState == "undefined") {
                    data.checkState = true;
                    $scope.ids.push(data.id);
                    return;
                } else {
                    data.checkState = !data.checkState; //状态取反
                }
                if (!isNaN(data.id)) {
                    if ($scope.ids.indexOf(data.id) == -1) {
                        $scope.ids.push(data.id);
                    } else {
                        commonUtils.removeItemFromArray($scope.ids, data.id);
                    }
                }
            };

            /*批量删除账号*/
            $scope.deleteBatch = function () {
                if (commonUtils.isArray($scope.ids)) {
                    userHttpServices.delete({ids: $scope.ids}, function (data) {
                        msgModal.alertMsg(commonUtils.convertResult(data));
                        if (data.code == 200)
                            $scope.ids = [];
                        $scope.init();
                    })
                }
            };

            /*批量删锁定账号*/
            $scope.lockBatch = function () {
                if (commonUtils.isArray($scope.ids)) {
                    userHttpServices.lock({ids: $scope.ids, state: 1}, function (data) {
                        msgModal.alertMsg(commonUtils.convertResult(data));
                        if (data.code == 200)
                            $scope.ids = [];
                        $scope.init();
                    })
                }
            };

            /*给用户赋值权限*/
            $scope.grant = function (user) {
                window.location.href = "user-menu.html?id=" + user.id + "&menuKey=" + commonUtils.getUrlParameter("menuKey");
            };

            /*给用户分配角色弹窗div*/
            $scope.grantRole = function (user) {
                $scope.role = {userId: user.id};
                commonUtils.showCover();
                $scope.converShow = 1;
                $scope.roleWindow = 1;
                commonUtils.centerElem($("#roleDiv"));
                userHttpServices.getRoleList({}, function (data) {
                    if (data.code == 200)
                        $scope.roles = data.dataList;
                });
            };

            /*给用户分配角色*/
            $scope.modifyRole = function () {
                userHttpServices.grantUserRole($scope.role, function (data) {
                    msgModal.alertMsg(commonUtils.convertResult(data));
                    if (data.code == 200)
                        $scope.init();
                });
            };

            /*查询*/
            $scope.search = function () {
                $scope.jumpPage(1, true);
            };

            /*系统用户类型 可扩展*/
            $scope.userType = [{
                key: '管理组',
                value: 2
            }, {
                key: '普通用户',
                value: 3
            }]

        }])
    /**用户权限列表**/
    .controller("UserGrantCtrl", ["appConstant", "$scope", "userHttpServices", "commonUtils", "msgModal", "menuUtils",
        function (appConstant, $scope, userHttpServices, commonUtils, msgModal, menuUtils) {
            $scope.init = function () {
                var menuKey = commonUtils.getUrlParameter("menuKey");
                $scope.permissions = [];
                menuUtils.getUserPermissionsByMenuKey(menuKey, $scope.permissions);
                userHttpServices.getMenusByUserId({id: commonUtils.getUrlParameter("id")}, function (data) {
                    if (data.code == 200) {
                        $scope.menus = data.dataList;
                    }
                });
            };

            /*赋予或取消权限*/
            $scope.grantMenus = function (menu) {
                //如果选择的是一级菜单 那么遍历二级菜单将二级菜单状态一致
                if (commonUtils.isArray(menu.children)) {
                    for (var i = 0; i < menu.children.length; i++) {
                        menu.children[i].hasPermission = menu.hasPermission;
                        if (menu.children[i].children) {
                            //在遍历三级菜单 将状态一致（直接选一二级菜单时候）
                            for (var j = 0; j < menu.children[i].children.length; j++) {
                                menu.children[i].children[j].hasPermission = menu.children[i].hasPermission;
                            }
                        }
                    }
                }

                //如果选中的是三级菜单 那么三级菜单所属一二级菜单全部需要设置为选中（一有都有）
                if (menu.hasPermission) {
                    for (var i = 0; i < $scope.menus.length; i++) {
                        var child = $scope.menus[i].children;
                        if ($scope.menus[i].id == menu.parentId) {
                            $scope.menus[i].hasPermission = menu.hasPermission;
                        } else {
                            if (child && child.length > 0) {
                                for (var j = 0; j < child.length; j++) {
                                    if (child[j].id == menu.parentId) {
                                        child[j].hasPermission = menu.hasPermission;
                                        $scope.menus[i].hasPermission = child[j].hasPermission
                                    }
                                }
                            }
                        }
                    }
                }
            };

            /*更新权限菜单*/
            $scope.save = function () {
                var id = commonUtils.getUrlParameter("id");
                if (id) {
                    userHttpServices.assignUserMenu({id: id, menus: $scope.menus}, function (data) {
                        if (data.code == 200) {
                            history.go(-1);
                        } else {
                            msgModal.alertMsg(commonUtils.convertResult(data));
                        }
                    });
                }
            };

            /*返回*/
            $scope.cancel = function () {
                history.go(-1);
            };
        }]);