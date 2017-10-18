angular.module("menu.controllers", [])
/**角色列表**/
    .controller("MenuListCtrl", ["appConstant", "$scope", "menuHttpServices", "commonUtils", "msgModal",
        function (appConstant, $scope, menuHttpServices, commonUtils, msgModal) {
            $scope.init = function () {
                menuHttpServices.getMenuList({}, function (data) {
                    if (data.code == 200) {
                        if (commonUtils.isArray(data.dataList)) {
                            $scope.menus = [];
                            $scope.converterMenus(data.dataList);
                        }
                    }
                });
            };

            /*将菜单数据转换(最末级菜单不显示)*/
            $scope.converterMenus = function (dataList) {
                for (var i = 0; i < dataList.length; i++) {
                    dataList[i].isShow = true;
                    $scope.menus.push(dataList[i]);
                    if (dataList[i].type != 3 || dataList[i].hasChildren) { //如果只判断是否有子类 会出现2级菜单下没有加入按钮权限 但是不显示的情况
                        dataList[i].arrowState = false;
                        dataList[i].isShowChildren = true; //如果子集合为空 就默认为false  即最下级菜单 无子集 默认不展示
                        if (commonUtils.isArray(dataList[i].children))
                            $scope.converterMenus(dataList[i].children);
                    }
                }
            };

            /*转换菜单栏状态*/
            $scope.toggleChildren = function (menu) {
                menu.arrowState = !menu.arrowState; //箭头状态取反
                if (commonUtils.isArray(menu.children)) {
                    for (var i = 0; i < menu.children.length; i++) {
                        if (menu.children[i].type != 3 || commonUtils.isArray(menu.children[i].children)) {  //1 2菜单
                            menu.children[i].arrowState = !menu.children[i].arrowState;
                            menu.children[i].isShowChildren = !menu.children[i].isShowChildren;
                        } else {  //最下级菜单  去上级菜单的状态  是否展示取反
                            menu.children[i].isShowChildren = !menu.children[i].isShowChildren;
                            menu.children[i].isShow = menu.isShow;
                        }
                        $scope.fillMenuChildren(menu.children[i], menu.isShow);
                    }
                }
            };

            $scope.fillMenuChildren = function (children, isShow) {
                var grandChildren = children.children;
                if (grandChildren) {
                    for (var b = 0; b < grandChildren.length; b++) {
                        grandChildren[b].isShow = !isShow;
                        $scope.fillMenuChildren(grandChildren[b]);
                    }
                }
            };

            /*修改菜单弹窗*/
            $scope.modifyMenu = function (menu) {
                $scope.menu = menu;
                commonUtils.showCover();
                $scope.coverShow = 1;
                $scope.modifyWindow = 1;
                commonUtils.centerElem($("#modifyDiv"));
            };

            /*修改*/
            $scope.modify = function () {
                menuHttpServices.modify($scope.menu, function (data) {
                    msgModal.alertMsg(commonUtils.convertResult(data.code));
                    if (data.code == 200)
                        $scope.close();
                })
            };

            /*关闭菜单弹窗*/
            $scope.close = function () {
                commonUtils.hideCover();
                $scope.coverShow = 0;
                $scope.modifyWindow = 0;
            };


            $scope.menuType = [{
                key: "目录",
                value: "1"
            }, {
                key: "菜单",
                value: "2"
            }, {
                key: "按钮",
                value: "3"
            }]

        }]);
