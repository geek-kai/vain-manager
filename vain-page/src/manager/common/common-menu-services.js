/**
 * Created by vain on 2017/10/22.
 */
/**
 *将权限赋值给数组 实现控制到按钮
 *
 */
angular.module("common.menu.services", ["ngResource", "common.services"])
    .factory("commonMenuHttpServices", ["$Resource", "appConstant", function ($resource, appConstant) {
        return $resource(
            "", {}, {
                getUserMenusByMenuKey: {
                    method: "post",
                    url: appConstant.BASE_URL + "/menu/getMyMenus"
                }
            });

    }])
    .factory("menuUtils", ["commonMenuHttpServices", function (commonMenuHttpServices) {
        return {
            getUserPermissionsByMenuKey: function (menuKey, permissions) {
                if (menuKey) {
                    commonMenuHttpServices.getUserMenusByMenuKey({menuKey: menuKey}, function (data) {
                        if (data.code == 200 || data.code == 404) {//无数据也要更新
                            var menus = data.dataList;
                            if (menus && menus.length > 0) {
                                for (var x = 0; x < menus.length; x++) {
                                    permissions[menus[x].menuKey] = true;
                                }
                            }
                        }
                    })
                }
            }
        };
    }]);
