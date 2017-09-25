/**
 * 后台单页面的模板
 *
 */
angular.module("home.controllers", [])
    .controller("HomeCtrl", ["$rootScope", "$location", "appConstant", "$cookieStore", "$cookies", "$scope", "homeHttpServices", "msgModal",
        function ($rootScope, $location, appConstant, $cookieStore, $cookies, $scope, homeHttpServices, msgModal) {
            $scope.init = function () {
                $scope.user = $cookieStore.get("vain-user");//获取cookie下的用户信息
                if (!$scope.user) {
                    window.location.href = "../login/login.html";
                }

                homeHttpServices.getMyMenus({}, function (data) {
                    if (data.code == 200) {
                        $scope.menus = data.dataList;
                    } else {
                        msgModal.alertMsg("暂无权限");
                    }
                });
            };


            //保存点击的菜单
            $scope.checkState = function (index) {
                $("a.tpl-left-nav-link-list").eq($scope.checkIndex).removeClass('active'); //先移除原来的选择状态
                $scope.checkIndex = index;
                $("#homePage").removeClass('active'); //移除首页的选择状态
                $("a.tpl-left-nav-link-list").eq(index).addClass('active');
            };

            //修改style 属性 展示子菜单
            $scope.showSubMenu = function (index) {
                if ($scope.checkIndex == index)
                    return {"display": "block"};
                return {};
            };

            //跳转对应的子菜单页面
            $scope.clickMenu = function (menu) {
                if (typeof menu.url != "undefined")
                    $scope.menuUrl = menu.url;
            };

            $scope.logout = function () {//注销登录
                $cookieStore.remove("vain-user");
                window.location.href = "../login/login.html";
            }
        }]);
