/**
 * 后台单页面的模板
 *
 */
angular.module("home.controllers", [])
    .controller("HomeCtrl", ["$rootScope", "$location", "appConstant", "$cookieStore", "$cookies", "$scope","homeHttpServices", "msgModal",
        function ($rootScope, $location, appConstant, $cookieStore, $cookies, $scope,homeHttpServices, msgModal) {
            $scope.init = function () {

                $scope.user = $cookieStore.get("vain-user");
                console.log($scope.user);
                // if (!$scope.user) {
                //     window.location.href = "../login/login.html";
                // }
            };
        }])
