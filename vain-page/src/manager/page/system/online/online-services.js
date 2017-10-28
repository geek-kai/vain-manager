angular.module("online.services", ["ngResource", "common.services"])
    .factory("onlineHttpServices", ["$resource", "appConstant", function ($resource, appConstant) {
        return $resource("", {}, {
            /*获取在线用户列表*/
            getOnLineUser: {
                method: "post",
                timeout: 2000,
                url: appConstant.BASE_URL + "/user/onLineUser"
            },
            /*在线用户下线*/
            forcedOffLine: {
                method: "post",
                url: appConstant.BASE_URL + "/user/forcedOffLine"
            }
        })
    }]);