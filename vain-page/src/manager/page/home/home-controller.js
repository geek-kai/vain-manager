/**
 * 后台单页面的模板
 *
 */
angular.module("home.controllers", [])
    .controller("HomeCtrl", ["$rootScope", "$location", "appConstant", "$cookieStore", "$cookies", "$scope","homeHttpServices", "msgModal",
        function ($rootScope, $location, appConstant, $cookieStore, $cookies, $scope,homeHttpServices, msgModal) {
            $scope.init = function () {
                $scope.user = $cookieStore.get("vain-user");//获取cookie下的用户信息
                if (!$scope.user) {
                    window.location.href = "../login/login.html";
                }
            };

            $scope.logout = function () {//注销登录
                $cookieStore.remove("vain-user");
                window.location.href="../login/login.html";
            }
        }])
