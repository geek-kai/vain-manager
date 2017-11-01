angular.module("scheduleJob.services", ["ngResource", "common.services"])
    .factory("scheduleJobHttpServices", ["$resource", "appConstant", function ($resource, appConstant) {
        return $resource("", {}, {
            /*获取任务*/
            getList: {
                method: "post",
                timeout: 2000,
                url: appConstant.BASE_URL + "/scheduleJob/getList"
            },
            /*重新开启任务*/
            resume: {
                method: "post",
                url: appConstant.BASE_URL + "/scheduleJob/resumeJob"
            },
            /*暂停任务*/
            pause: {
                method: "post",
                url: appConstant.BASE_URL + "/scheduleJob/pauseJob"
            },
            /*触发一次任务*/
            trigger: {
                method: "post",
                url: appConstant.BASE_URL + "/scheduleJob/triggerJob"
            },
            /*修改任务*/
            modify: {
                method: "post",
                url: appConstant.BASE_URL + "/scheduleJob/modify"
            },
            /*删除任务*/
            delete: {
                method: "post",
                url: appConstant.BASE_URL + "/scheduleJob/delete"

            }
        })
    }]);