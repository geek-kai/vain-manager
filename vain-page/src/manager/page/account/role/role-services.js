angular.module("role.services", ["ngResource", "common.services"])
    .factory("roleHttpServices", ["$resource", "appConstant", function ($resource, appConstant) {
        return $resource("", {}, {
            /*获取角色列表*/
            getList: {
                method: "post",
                url: appConstant.BASE_URL + "/role/getList"
            },
            /*通过角色id获取角色菜单权限（分层次 带hasPermission）*/
            getMenusByRoleId: {
                method: "post",
                url: appConstant.BASE_URL + "/menu/getMenusByRoleId"
            },
            /*分配角色权限*/
            assignRoleMenu: {
                method: "post",
                url: appConstant.BASE_URL + "/role/assignRoleMenu"
            },
            delete: {
                method: "post",
                url: appConstant.BASE_URL + "/role/delete"
            }
        })
    }]);