angular.module("log.services", ["ngResource", "common.services"])
    .factory("logHttpServices", ["$resource", "appConstant", function ($resource, appConstant) {
        return $resource("", {}, {
            /*获取日志列表*/
            getPagedList: {
                method: "post",
                timeout: 2000,
                url: appConstant.BASE_URL + "/log/getPagedList"
            }
        })
    }]);