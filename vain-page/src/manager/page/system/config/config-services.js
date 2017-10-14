angular.module("config.services", ["ngResource", "common.services"])
    .factory("configHttpServices", ["$resource", "appConstant", function ($resource, appConstant) {
        return $resource("", {}, {
            /*获取配置列表*/
            getPagedList: {
                method: "post",
                url: appConstant.BASE_URL + "/systemConfig/getPagedList"
            }
        })
    }]);