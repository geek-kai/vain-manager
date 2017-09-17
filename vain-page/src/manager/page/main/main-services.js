angular.module("main.services",["ngResource","common.services"])
    .factory("mainHttpService",["$resource","appConstant",function ($resource,appConstant) {
        return $resource("",{},{
            getNews:{
                method:"get",
                url:appConstant.BASE_URL+ "/user/getNews/:type"
            }
        })
}
    ]);