/**
 * 前端页面代码抽取的一些公用类
 */
angular.module("common.services", ["ui.bootstrap", 'ngCookies', 'ngResource'])
/*项目访问的具体路径*/
    .constant("appConstant", {
        BASE_URL: "http://127.0.0.1/vain/manager/api",
        COOKIE_ROOT_PATH: "/",
        QUERY_PARAM_PAGE_SIZE: 10,
        QUERY_PARAM_SPECIAL_PAGE_SIZE: 5
    })
    /*公用帮助类*/
    .factory("commonUtils", [function () {
        return {
            /*初始化分页参数*/
            initPaginator: function (totalSize, curPage, pageSize) {
                var paginator = null;
                if (totalSize && totalSize > 0) {
                    paginator = {totalSize: totalSize, curPage: curPage};
                    paginator.totalPage = parseInt((totalSize + pageSize - 1) / pageSize);

                    var pageNumShow = 6; //展示的页码数
                    if (paginator.totalPage <= pageNumShow) {
                        paginator.showStartPage = 1; //起始页码数
                        paginator.showEndPage = paginator.totalPage; //最后一页
                    } else {
                        //拿默认值为6来举例  3 4 5 6 7 8 curPage=6 startPage=3 endPage = 8
                        //赋值起始页
                        if (paginator.curPage - pageNumShow / 2 > 0) {
                            paginator.showStartPage = paginator.curPage - pageNumShow / 2;
                        } else {
                            paginator.showStartPage = 1;    //第一页
                            paginator.showStartPage = pageNumShow;
                        }

                        //赋值终止页
                        if (!paginator.showEndPage) {
                            if (paginator.curPage + (pageNumShow / 2 - 1) < paginator.totalPage) {
                                paginator.showEndPage = paginator.curPage + (pageNumShow / 2 - 1);
                            } else {
                                paginator.showEndPage = paginator.totalPage;
                                paginator.showStartPage = paginator.totalPage - pageNumShow; //最后一页
                            }
                        }
                    }

                    paginator.PageArray = new Array(paginator.showEndPage - paginator.showStartPage + 1);//初始化
                    for (var i = paginator.showStartPage; i <= paginator.showEndPage; i++) {
                        paginator.PageArray[i - paginator.showStartPage] = i; //赋值
                    }
                }
                return paginator;
            },
            /*初始化查询参数*/
            initQueryParam: function () {
                var queryParamString = String(window.document.location.href).split("#")[1];
                if (queryParamString) {
                    queryParamString = decodeURI(queryParamString);
                    if (queryParamString.indexOf('/') == 0) {
                        queryParamString = queryParamString.substring(1);
                    }
                    return JSON.parse(queryParamString);
                }
                return null;
            },
            /*存储查询参数*/
            storeQueryParam: function (queryParam) {
                window.document.location.href = "#" + encodeURI(JSON.stringify(queryParam));
            },
            /*传入元素在屏幕居中 接收参数为jQuery对象*/
            centerElem: function (elem) {
                var widowWidth = $(window.parent).width();
                var windowHeight = $(window.parent).height();
                var toolbarHeight = 100;
                var elemWidth = elem.width();
                var elemHeight = elem.height();
                var left = (widowWidth - elemWidth) / 2;
                var top = (windowHeight - elemHeight) / 2;
                //距离底部最低
                var bottomLest = top;
                if(window.parent!==window){
                    var childWinWidth = $(window.document).width();
                    var childWinHeight = $(window.document).height();
                    var menuWidth;//菜单栏的高度

                }
            }

        };
    }])
    .factory("msgModal", ['$modal', function ($modal) {
        return {
            /*提示语弹窗*/
            alertMsg: function (msg) {
                var t = $modal.open({
                    template: '<div class="cover" style="display:block;"></div>' +
                    '<div id="coverAlert" class="am-modal am-modal-alert" tabindex="-1" style="display:block;">'
                    + '<div class="am-modal-dialog">'
                    + '<div class="am-modal-hd">提示：</div>'
                    + '<div class="am-modal-bd">'
                    + '<h3>' + msg + '</h3>'
                    + '</div>'
                    + '<div class="am-modal-footer">'
                    + '<span  class="am-modal-btn">确定</span>'
                    + '</div>'
                    + '</div>'
                    + '</div>',
                    controller: "ModalInstanceCtrl"
                });
            }
        };

    }])
    /*弹窗msgModal的依赖*/
    .controller("ModalInstanceCtrl", ["$scope", "$modalInstance", "commonUtils",
        function (e, t, commonUtils) {
        }])
    .controller("PreviewPicCtrl", ["$scope", "$modalInstance", "commonUtils",
        function (e, t, commonUtils) {
        }])

    /*拦截用户状态*/
    .factory('userInterceptor', ["$cookieStore", "$cookies", "appConstant",
        function ($cookieStore, $cookies, appConstant) {
            var userInterceptor = {
                request: function (config) {
                    return config;
                },
                response: function (response) {
                    if (response.data.code == 401) {
                        $cookies.remove("vain-user", {path: appConstant.COOKIE_ROOT_PATH});
                        var win = window;
                        if (window != window.parent) {
                            win = window.parent;
                        }
                        win.location = "index.html";
                        return;
                    } else if (response.data.code == 403) {
                        alert('无权操作');
                        return;
                    }
                    return response;
                },
                responseError: function (err) {
                    if (401 === err.status) {
                        $cookies.remove("vain-user", {path: appConstant.COOKIE_ROOT_PATH});
                        var win = window;
                        if (window != window.parent) {
                            win = window.parent;
                        }
                        win.location = "http://127.0.0.1/manager/page/login/login.html";
                    } else if (403 === err.status) {
                        alert('无权操作');
                    } else if (err.status !== -1) {
                        // -1代表请求取消
                        alert("网络异常！");
                    }
                    return;
                }
            };
            return userInterceptor;
        }])
    .config(["$httpProvider", function ($httpProvider) {
        $httpProvider.interceptors.push("userInterceptor");
    }]);



