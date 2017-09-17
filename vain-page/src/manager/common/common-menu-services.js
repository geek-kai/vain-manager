/**
 * Created by xuxiang on 2016/5/12.
 */
angular.module("common.menu.services", ["ngResource", "common.services"])
    .factory("commonMenuHttpServices", ["$resource", "appConstant", function ($resource, appConstant) {
        return $resource(
            "", {}, {
                getUserPermissionsByMenu: {
                    method: 'post',
                    url: appConstant.BASE_URL + "/lsc/menu/getMyMenus"
                }
            }
        )
    }])
    .factory("menuUtils", ["commonMenuHttpServices", "msgModal", function (commonMenuHttpServices, msgModal) {
        return {
            getUserPermissionsByMenu: function (menuKey, permissions) {
                if(menuKey){
                    commonMenuHttpServices.getUserPermissionsByMenu({
                        menuKey: menuKey
                    }, function (data) {
                        if (data.code == 200 || data.code == 404) {
                            var menus = data.dataList;
                            if (menus && menus.length > 0) {
                                for (var c = 0; c < menus.length; c++) {
                                    permissions[menus[c].menuKey] = true;
                                }
                            }
                        }
                    })
                }
            }
        };
    }])
