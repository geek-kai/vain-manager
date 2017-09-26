angular.module("user.services",["ngResource","common.services"])
    .factory("userHttpService",["$resource","appConstant",function ($resource,appConstant) {
        return $resource("",{},{
            getPagedList:{
                method:"post",
                url:appConstant.BASE_URL+ "/user/getPagedList"
            }
        })
}
    ]);