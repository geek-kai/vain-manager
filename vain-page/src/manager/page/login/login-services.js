/**
 * Created by xuxiang on 2016/3/28.
 */
angular.module("login.services",["ngResource","common.services"])
    .factory("loginHttpService",["$resource","appConstant",function ($resource,appConstant) {
        return $resource("",{},{
            login:{
                method:"post",
                url:appConstant.BASE_URL+ "/user/login"
            }
        })
}
    ]);