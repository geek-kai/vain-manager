angular.module("main.services",["ngResource","common.services"])
    .factory("mainHttpServices",["$resource","appConstant",function ($resource,appConstant) {
        return $resource("",{},{
            getNews:{
                method:"get",
                url:appConstant.BASE_URL+ "/user/getNews/:type"
            }
        })
}
    ]);