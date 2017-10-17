angular.module("info.services", ["ngResource", "common.services"])
    .factory("infoHttpServices", ["$resource", "appConstant", function ($resource, appConstant) {
        return $resource("", {}, {
        })
    }]);