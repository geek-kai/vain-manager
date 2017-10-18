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

            /*删除角色*/
            $scope.delete = function (id) {
                var msg = msgModal.confirmMsg(appConstant.ROLE_DELETE_WARNING);
                msg.result.then(function () {
                    roleHttpServices.delete({id: id}, function (data) {
                        $scope.init();
                    })
                })
            };

            /*关闭添加角色div*/
            $scope.close = function () {
                commonUtils.hideCover();
                $scope.coverShow = 0;
                $scope.addWindow = 0;
            };

            /*分配权限*/
            $scope.grantMenu = function (id) {
                window.location.href = "role-menu.html?id=" + id;
            };
        }])
    /**角色权限列表**/
    .controller("RoleGrantCtrl", ["appConstant", "$scope", "roleHttpServices", "commonUtils", "msgModal",
        function (appConstant, $scope, roleHttpServices, commonUtils, msgModal) {
            $scope.init = function () {
                roleHttpServices.getMenusByRoleId({id: commonUtils.getUrlParameter("id")}, function (data) {
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
                    roleHttpServices.assignRoleMenu({id: id, menus: $scope.menus}, function (data) {
                        if (data.code == 200) {
                            history.go(-1);
                        } else {
                            msgModal.alertMsg(commonUtils.convertResult(data.code));
                        }
                    });
                }
            };

            /*返回*/
            $scope.cancel = function () {
                history.go(-1);
            };
        }]);