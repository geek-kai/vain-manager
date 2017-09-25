/**
 * 后台单页面的模板的service
 */
angular.module("home.services", ["ngResource", "common.services"])
    .factory("homeHttpServices", ["$resource", "appConstant", function ($resource, appConstant) {
        return $resource(
            "", {}, {
                getMyMenus: {
                    method: 'post',
                    url: appConstant.BASE_URL + "/menu/getMyMenus"
                }
            })
    }]);