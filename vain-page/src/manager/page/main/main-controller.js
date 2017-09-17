/**
 * 首页的controller
 */
angular.module("main.controllers", []).controller(
    "MainCtrl", ["$cookieStore", "appConstant", "$cookies", "$scope", "mainHttpService", "msgModal",
        function ($cookieStore, appConstant, $cookies, $scope, mainHttpService, msgModal) {
            $scope.init = function () {
                //抓取百度新闻实时热点
                $scope.getNew(1);
            }
            $scope.getNew = function (type) {
                //抓取百度新闻今日热点
                mainHttpService.getNews({type: Number(type)}, function (data) {
                    if (data.code == 200) {
                        $scope.news = (data.data.length > 10 ? data.data.slice(0, 9) : data.data);

                    }
                })
            }
        }])