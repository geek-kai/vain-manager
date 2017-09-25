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
                        console.log($scope.menus);
                    } else {
                        msgModal.alertMsg("暂无权限");
                    }
                });
            };


            $scope.testA = function (index) {
                console.log(index);
                $("ul.tpl-left-nav-sub-menu:lt(1)").css("display", "block");
            }


            $scope.logout = function () {//注销登录
                $cookieStore.remove("vain-user");
                window.location.href = "../login/login.html";
            }
        }])
