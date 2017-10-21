angular.module("user.services", ["ngResource", "common.services"])
    .factory("userHttpServices", ["$resource", "appConstant", function ($resource, appConstant) {
        return $resource("", {}, {
            getPagedList: {
                method: "post",
                url: appConstant.BASE_URL + "/user/getPagedList"
            },
            lock: {
                method: "post",
                url: appConstant.BASE_URL + "/user/lock"
            },
            resetPwd: {
                method: "post",
                url: appConstant.BASE_URL + "/user/resetPwd"
            },
            add: {
                method: "post",
                url: appConstant.BASE_URL + "/user/add"
            },
            delete: {
                method: 'post',
                url: appConstant.BASE_URL + "/user/delete"
            },
            modify: {
                method: 'post',
                url: appConstant.BASE_URL + "/user/modify"
            },
            /*通过角色id获取角色菜单权限（分层次 带hasPermission）*/
            getMenusByUserId: {
                method: "post",
                url: appConstant.BASE_URL + "/menu/getMenusByUserId"
            },
            /*赋予用户权限*/
            assignUserMenu: {
                method: "post",
                url: appConstant.BASE_URL + "/user/assignUserMenu"
            },
            /*获取角色列表*/
            getRoleList: {
                method: "post",
                url: appConstant.BASE_URL + "/role/getList"
            },
            /*赋予账号角色*/
            grantUserRole: {
                method: "post",
                url: appConstant.BASE_URL + "/role/grantUserRole"
            }
        })
    }
    ]);