/**
 * 登录的controller
 */
angular.module("login.controllers", []).controller(
    "LoginCtrl",
    ["$cookieStore", "appConstant", "$cookies", "$scope", "loginHttpService",
        function ($cookieStore, appConstant, $cookies, $scope, loginHttpService) {
            $scope.init = function () {
                $scope.loginError = 0;
                if ($cookieStore.get("vain-user")) {
                    window.location.href = '../home/home.html';
                    return;
                }
                $scope.user = {};
            }


            $scope.enter = function (event) {
                if (event.keyCode !== 13)
                    return;
                $scope.login();
            };

            $scope.login = function () {
                if ($scope.form.$invalid) {
                    $scope.formSubmitInvalid = true;
                    return;
                }
                $scope.formSubmitting = true;
                loginHttpService.login($scope.user, function (data) {
                    $scope.formSubmitting = false;
                    if (data.code == 200) {
                        $cookies.putObject("vain-user", data.data, {
                            path: appConstant.COOKIE_ROOT_PATH
                        });
                        window.location.href = "../home/home.html";
                    } else {
                        $scope.loginError = 1;
                        $scope.loginErrorMsg = data.msg;
                        $scope.loginErrorShow = 1;
                    }
                })
            };

            $scope.enterPwd = function () {
                $scope.loginError = 0;
            }
        }])