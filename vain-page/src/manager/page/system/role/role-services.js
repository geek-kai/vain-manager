angular.module("role.services", ["ngResource", "common.services"])
    .factory("roleHttpServices", ["$resource", "appConstant", function ($resource, appConstant) {
        return $resource("", {}, {
            getList: {
                method: "post",
                url: appConstant.BASE_URL + "/role/getList"
            },
            getMenusByRoleId: {
                method: "post",
                url: appConstant.BASE_URL + "/menu/getMenusByRoleId"
            }
        })
    }]);