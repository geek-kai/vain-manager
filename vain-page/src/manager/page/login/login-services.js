/**
 * 登录
 */
angular.module("login.services",["ngResource","common.services"])
    .factory("loginHttpServices",["$resource","appConstant",function ($resource,appConstant) {
        return $resource("",{},{
            login:{
                method:"post",
                url:appConstant.BASE_URL+ "/user/login"
            }
        })
}
    ]);