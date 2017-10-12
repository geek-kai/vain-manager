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
                            console.log($scope.menus);
                        }
                    }
                });
            };

            /*将菜单数据转换*/
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
                        $scope.fillMenuChildren(menu.children[i], menu.isShowChildren);
                    }
                }
            };

            $scope.fillMenuChildren = function (children, isShowChildren) {
                var grandChildren = children.children;
                if (grandChildren) {
                    for (var b = 0; b < grandChildren.length; b++) {
                        grandChildren[b].isShow = !isShowChildren;
                        $scope.fillMenuChildren(grandChildren[b]);
                    }
                }
            }

        }]);
