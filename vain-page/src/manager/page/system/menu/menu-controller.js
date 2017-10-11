angular.module("menu.controllers", [])
/**角色列表**/
    .controller("MenuListCtrl", ["appConstant", "$scope", "menuHttpServices", "commonUtils", "msgModal",
        function (appConstant, $scope, menuHttpServices, commonUtils, msgModal) {
            $scope.init = function () {
                menuHttpServices.getMenuList({}, function (data) {
                    if (data.code == 200) {
                        if (commonUtils.isArray(data.dataList)) {
                            $scope.menus = new Array();
                            $scope.converterMenus(data.dataList);
                        }
                    }
                });
            };

            /*将菜单数据转换*/
            $scope.converterMenus = function (dataList) {
                for (var i = 0; i < dataList.length; i++) {
                    dataList[i].isShow = true;
                    $scope.menus.push(dataList[i]);
                    if (commonUtils.isArray(dataList[i].children)) {
                        dataList[i].isShowChildren = false;//默认不展开子类
                        dataList[i].hasChildren = true;
                        $scope.converterMenus(dataList[i].children);
                        /*for (var j = 0; j < dataList[i].children.length; j++) {
                         }*/
                    }
                }

            };

        }]);
