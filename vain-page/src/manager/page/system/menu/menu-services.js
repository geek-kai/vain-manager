angular.module("menu.services", ["ngResource", "common.services"])
    .factory("menuHttpServices", ["$resource", "appConstant", function ($resource, appConstant) {
        return $resource("", {}, {
            getMenuList: {
                method: 'post',
                url: appConstant.BASE_URL + "/menu/getMenuList"
            }
        })
    }]);