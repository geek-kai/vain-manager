angular.module("info.services", ["ngResource", "common.services"])
    .factory("infoHttpServices", ["$resource", "appConstant", function ($resource, appConstant) {
        return $resource("", {}, {
            getSystemInfo: {
                method: "post",
                cache: true,
                url: appConstant.BASE_URL + "/systemConfig/getSystemInfo"
            },
            getSystemCpuInfo: {
                method: "post",
                timeout: 8000,
                url: appConstant.BASE_URL + "/systemConfig/getSystemCpuInfo"
            }
        })
    }]);