angular.module("personalInfo.services", ["ngResource", "common.services"])
    .factory("personalInfoHttpServices", ["$resource", "appConstant", function ($resource, appConstant) {
        return $resource("", {}, {
            getUserInfo: {
                method: 'post',
                url: appConstant.BASE_URL + "/user/get",
                timeout: 500
            },
            modify: {
                method: 'post',
                url: appConstant.BASE_URL + "/user/modifyPersonInfo"
            }
        })
    }]);