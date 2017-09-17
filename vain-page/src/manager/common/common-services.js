angular.module("common.services", ["ui.bootstrap", 'ngCookies', 'ngResource'])
    /*nginx 路径配置*/
    .constant("appConstant", {
        BASE_URL: "../../../vain/manager/api",
        COOKIE_ROOT_PATH: "/vain/manager/",
        AK_BAIDU_LOCATION: '4p6C0D43v2kXDYbFqg7F0MKP3FjTeTAZ',
        AK_JUHE_WEATHER: '873650bec677b0fd991d8994a3501161',
        QUERYPARAM_PAGE_SIZE: 10,
        QUERYPARAM_SPECIAL_PAGE_SIZE: 5,
        HINT_MSG_CONFIRM_DELETE: "确认删除该数据？",
        HINT_MSG_CONFIRM_PUBLISH: "确认发布该数据？",
        HINT_MSG_CONFIRM_SAVE_AND_PUBLISH: "确认保存并发布？",
        HINT_MSG_CONFIRM_DISABLE_PUBLISH: "有必填字段未填写，请修改后重新发布。",
        HINT_MSG_CONFIRM_UNPUBLISH: "确认撤回该数据？",
        HINT_MSG_CONFIRM_RESET: "确认重置该数据？",
        HINT_MSG_CONFIRM_SEND: "确认发送该数据？",
        HINT_MSG_CONFIRM_APPROVE_PASS: "确认审核通过？",
        HINT_MSG_CONFIRM_APPROVE_REJECT: "确认审核拒绝？",
        HINT_MSG_CONFIRM_LOCK_USER: "确认锁定该用户？",
        HINT_MSG_CONFIRM_UNLOCK_USER: "确认解锁该用户？",
        HINT_MSG_CONFIRM_SAVE: "数据有修改，是否保存？",
        HINT_MSG_ALERT_GET_FAIL: "获取数据失败",
        HINT_MSG_ALERT_SAVE_FAIL: "保存失败",
        HINT_MSG_ALERT_SAVE_AND_PUBLISH_FAIL: "保存并发布失败",
        HINT_MSG_ALERT_DELETE_FAIL: "删除失败",
        HINT_MSG_ALERT_SEND_FAIL: "发送失败",
        HINT_MSG_ALERT_RESET_FAIL: "重置失败",
        HINT_MSG_ALERT_PUBLISH_FAIL: "发布失败",
        HINT_MSG_ALERT_UNPUBLISH_FAIL: "撤回失败",
        HINT_MSG_ALERT_APPROVE_FAIL: "审核失败",
        HINT_MSG_ALERT_OPER_FAIL: "操作失败",
        HINT_MSG_ALERT_NETWORK_FAIL: "网络异常",
        HINT_MSG_ALERT_URL_NOT_EXIST: "访问地址不存在"
    })

    .factory("commonUtils", [function () {
        return {
            getUrlParameter: function (property, url) {
                var parseUrl = url;
                if (parseUrl == null) {
                    parseUrl = String(window.document.location.href);
                }
                var rs = new RegExp("[\?\&]{1}" + property + "=([^\&#]{1,})")
                    .exec(parseUrl);
                if (rs) {
                    return rs[1];
                }
                return "";
            },

            // 克隆json对象
            cloneJsonData: function (data) {
                var newobj = data.constructor === Array ? [] : {};
                if (typeof data !== 'object') {
                    return;
                } else if (window.JSON) {
                    newobj = JSON.parse(JSON.stringify(data));
                } else {
                    for (var i in data) {
                        newobj[i] = typeof data[i] === 'object' ? this.cloneJsonData(data[i]) : data[i];
                    }
                }
                return newobj;
            },

            // 比较两个json对象是否一致(需要考虑属性不一致的情况，一个有、一个没有)
            isJsonDataEqual: function (data, originData, exclude) {
                if (typeof exclude === "undefined")
                    exclude = [];
                if (this.isObj(data) && this.isObj(originData)) {
                    if (exclude.indexOf(data) < 0) {
                        for (var obj in data) {
                            if (exclude.indexOf(obj) < 0) {
                                if (originData.hasOwnProperty(obj)) {
                                    if (this.isJsonDataEqual(data[obj], originData[obj], exclude))
                                        return true;
                                } else {
                                    return true;
                                }
                            }
                        }
                    }
                } else if (this.isArray(data) && this.isArray(originData)) {
                    if (exclude.indexOf(data) < 0) {
                        if (data.length == originData.length) {
                            for (var i = 0; i < data.length; i++) {
                                if (this.isJsonDataEqual(data[i], originData[i], exclude))
                                    return true;
                            }
                        } else {
                            return true;
                        }
                    }
                } else if (typeof data === "string" && typeof originData === "string") {
                    if (exclude.indexOf(data) < 0) {
                        if (data !== originData)
                            return true;
                    }
                } else if (typeof data === "number" && typeof originData === "number") {
                    if (exclude.indexOf(data) < 0) {
                        if (data !== originData)
                            return true;
                    }
                } else if (typeof data === "boolean" && typeof originData === "boolean") {
                    if (exclude.indexOf(data) < 0) {
                        if (data !== originData)
                            return true;
                    }
                } else {
                    return false;
                }
                return false;
            },

            //判断是否是对象
            isObj: function (object) {
                return object && typeof (object) == 'object' && Object.prototype.toString.call(object).toLowerCase() == "[object object]";
            },

            //判断是否是数组
            isArray: function (object) {
                return object && typeof (object) == 'object' && object.constructor == Array;
            },

            // 判断json对象的所有属性都为空
            isJsonDataEmpty: function (data) {
                // 需要对象为空，或者属性都为空
                for (var key in data) {
                    if (key) {
                        if (data[key]){
                            if (this.isObj(data[key])){
                                this.isJsonDataEmpty(data[key]);
                            }else if (this.isArray(data[key])){
                                if (data[key].Length>0)
                                    return true;
                            } else {
                                return true;
                            }
                        }
                    }
                }
                return false;
            },

            // 初始化分页
            initPaginator: function (totalSize, curPage, pageSize) {
                var paginator = null;
                if (totalSize && totalSize > 0) {
                    paginator = {totalSize: totalSize};
                    paginator.curPage = curPage;
                    paginator.totalPage = parseInt((totalSize + pageSize - 1) / pageSize);

                    // 分页显示规则参考百度
                    // 如果不超过n页全部显示，如果超过n页，只显示n页，当前页居中
                    var showPageNum = 6;
                    if (paginator.totalPage <= showPageNum) {
                        paginator.showStartPage = 1;
                        paginator.showEndPage = paginator.totalPage;
                    } else {
                        if (paginator.curPage - showPageNum / 2 > 0) {
                            paginator.showStartPage = paginator.curPage - showPageNum / 2;
                        } else {
                            paginator.showStartPage = 1;
                            paginator.showEndPage = showPageNum;
                        }

                        // 如果paginator.showEndPage没有赋值，还需要进行赋值
                        if (!paginator.showEndPage) {
                            if (paginator.curPage + (showPageNum / 2 - 1) < paginator.totalPage) {
                                paginator.showEndPage = paginator.curPage + (showPageNum / 2 - 1);
                            } else {
                                paginator.showEndPage = paginator.totalPage;
                                paginator.showStartPage = paginator.totalPage - showPageNum + 1;
                            }
                        }
                    }

                    paginator.showPageArray = new Array(paginator.showEndPage - paginator.showStartPage + 1);
                    for (var i = paginator.showStartPage; i <= paginator.showEndPage; i++) {
                        paginator.showPageArray[i - paginator.showStartPage] = i;
                    }

                    // 数据循环展示后，需要重新计算页面高度
                    this.resizeIframe();

                }

                return paginator;
            },

            //初始化查询参数
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

            //存储查询参数
            storeQueryParam: function (queryParam) {
                window.location.href = "#" + encodeURI(JSON.stringify(queryParam));
            },

            // 入参：jquery对象
            resetCustomSelect: function (elem, delay) {

                var _delay = delay ? delay : 100;

                setTimeout(function () {
                    elem.next().remove();

                    elem.CustomSelect();

                    $("div.example_div").click(function () {
                        $(".b-custom-select__dropdown").css("width", $(this).width());
                        $(".b-custom-select__list,.b-custom-select__dropdown__inner").css({width: '100%'});
                    })
                }, _delay);
            },

            // 使元素屏幕居中，入参为jquery对象
            centerElem: function (coverAlert) {

                var winWidth = $(window.parent).width();
                var winHeight = $(window.parent).height();
                var toolbarHeight = 100;

                var alertWidth = coverAlert.width();
                var alertHeight = coverAlert.height();

                var left = (winWidth - alertWidth) / 2;
                var top = (winHeight - alertHeight) / 2;
                // 最小的底部高度
                var bottomLeast = top;

                if (window.parent !== window) {

                    var childWinWidth = $(window.document).width();
                    var childWinHeight = $(window.document).height();
                    var menuWidth;
                    if ($('.aside', window.parent.document).css("display") == "block") {
                        menuWidth = $('.aside', window.parent.document).outerWidth(true) + $('.asideArrow', window.parent.document).outerWidth(true);
                    } else {
                        menuWidth = $('.asideArrow', window.parent.document).outerWidth(true);
                    }

                    left = left - (winWidth - childWinWidth + menuWidth) / 2;

                    // 减去主页面顶部导航栏的高度
                    top = top - toolbarHeight;

                    // top还需要考虑滚动条的滚动的高度
                    top = top + $(window.parent.document).scrollTop();

                    // 底部间隔不能够太少
                    if ((childWinHeight + toolbarHeight) > winHeight && (top + alertHeight + bottomLeast > childWinHeight)) {
                        if (bottomLeast > 0) {
                            top = childWinHeight - bottomLeast - alertHeight;
                        } else {
                            top = childWinHeight - alertHeight;
                        }
                    }
                    left = left < 0 ? 0 : left;
                    top = top < 0 ? 0 : top;
                }
                coverAlert.css("left", left);
                coverAlert.css("top", top);

            },

            //弹出框延迟显示
            centerElemDelay: function (coverAlert, delay) {
                delay = delay ? delay : 100;
                var doCenterElem = this.centerElem;
                setTimeout(function () {
                    doCenterElem(coverAlert);
                }, delay);
            },

            // 重新度量iframe的高度
            // 入参：延时时间、自定义高度
            resizeIframe: function (delay, customHeight) {
                delay = delay ? delay : 100;
                setTimeout(function () {
                    var height = $(".cont").outerHeight(true);
                    // 如果有指定高度，并且指定高度大于获得的高度，采用指定的高度
                    if (customHeight && customHeight > height) {
                        height = customHeight;
                    }
                    $("#Main_iframe", window.parent.document).css("height", height);
                }, delay);
            },

            // 显示主页面的遮罩
            showMainCover: function () {
                $('.cover', window.parent.document).show();
            },

            // 隐藏主页面的遮罩
            hideMainCover: function () {
                $('.cover', window.parent.document).hide();
            }
        };
    }])
    .factory(
        "msgModal",
        [
            "$modal",
            function ($modal) {
                return {
                    alertMsg: function (msg) {
                        var t = $modal
                            .open({
                                template: '<div class="cover" style="display:block;"></div>' +
                                '<div id="coverAlert" class="cover_alert cover_SettingAd" style="display:block;" ng-init="init()">'
                                + '<div class="coverHead">'
                                + '<img src="../../../images/close.png" alt="" ng-click="close()"/>'
                                + '</div>'
                                + '<div class="coverCont addFavorite_setHome">'
                                + '<div class="cover_icon"></div>'
                                + '<h3>' + msg + '</h3>'
                                + '<div class="cover_hr"></div>'
                                + '<div class="cont_btn">'
                                + '<input type="button" class="btn btn_green" value="确定" ng-click="ok()">'
                                + '</div>'
                                + '</div>'
                                + '</div>',
                                controller: "ModalInstanceCtrl"
                            });
                        return t;
                    },
                    alertMsgDelay: function (msg, delay) {
                    	
                    	var msgModal = this;
                    	
                    	setTimeout(function(){
                    		msgModal.alertMsg(msg);
                    	}, delay ? delay : 300);
                    },
                    confirmMsg: function (msg, subMsg, okBtn, cancelBtn) {
                        var t = $modal
                            .open({
                                template: '<div class="cover" style="display:block;"></div>' +
                                '<div id="coverAlert" class="cover_alert cover_SettingAd1" style="display:block;" ng-init="init()">'
                                + '<div class="coverHead">'
                                + '<img src="../../../images/close.png" alt="" ng-click="close()"/>'
                                + '</div>'
                                + '<div class="coverCont addFavorite_setHome">'
                                + '<div class="cover_icon"></div>'
                                + '<h3>' + msg + '</h3>'
                                + '<p>' + (subMsg ? subMsg : '') + '</p>'
                                + '<div class="cover_hr"></div>'
                                + '<div class="cont_btn">'
                                + '<input type="button" class="btn btn_red" value="' + (okBtn ? okBtn : '确定') + '" ng-click="ok()"><input type="button" class="btn btn_green cancel" value="' + (cancelBtn ? cancelBtn : '取消') + '"  ng-click="cancel()">'
                                + '</div>'
                                + '</div>'
                                + '</div>',
                                controller: "ModalInstanceCtrl"
                            });
                        return t;
                    },
                    confirmSaveMsg: function (msg) {
                        return this.confirmMsg(msg, '', '是', '否');
                    },
                    //图片预览
                    previewPic: function (src) {
                        var t = $modal
                            .open({
                                template: '<div class="cover"  style="display:block;"></div>' +
                                '<div  id="coverAlert" class="cover_alert cover_alert5 cover_preview2" style="display:block;" ng-init="init()">' +
                                '<div class="coverHead">' +
                                '<img src="../../../images/close.png" alt="" ng-click="close()"/>' +
                                '</div>' +
                                '<div class="coverCont" id="imgUrlDiv">' +
                                '<img alt="" id="imgUrlPreview" style="display:none;" src="' + src + '"/>' +
                                '</div>' +
                                '</div>',
                                controller: "PreviewPicCtrl"
                            });
                        return t;
                    }
                };
            }])
    .controller("ModalInstanceCtrl", ["$scope", "$modalInstance", "commonUtils",
        function (e, t, commonUtils) {
            e.init = function () {
                e.beforeShow();
                commonUtils.centerElem($("#coverAlert"));
            }, e.getCover = function () {
                if (window.parent !== window) {
                    return $('.cover', window.parent.document);
                }
                return $("#Main_iframe").contents().find(".cover");
            }, e.beforeShow = function () {
                // 显示 主页面或子页面的遮罩（非当前页面）
                e.getCover().show();
                // 重置body样式，解决弹窗的问题
                var body = $(document.body);
                var oldWidth = body.innerWidth();
                body.css({position: "fixed", overflow: "hidden"});
                body.width(oldWidth);
            }, e.afterClose = function () {
                // 隐藏 主页面或子页面的遮罩（非当前页面）
                e.getCover().hide();
                // 重置body样式，解决弹窗的问题
                var body = $(document.body);
                body.css({position: "", overflow: ""});
                body.width("auto");
            }, e.ok = function () {
                t.close();
                e.afterClose();
            }, e.close = function () {
                t.dismiss('close');
                e.afterClose();
            }, e.cancel = function () {
                t.dismiss('cancel');
                e.afterClose();
            }, e.update = function () {
            }
        }])
    .controller("PreviewPicCtrl", ["$scope", "$modalInstance", "commonUtils",
        function (e, t, commonUtils) {
            e.init = function () {
                var maxWidth = 638;
                var maxHeight = 303;
                var obj = $('#imgUrlPreview');
                $("#imgUrlDiv").css({"text-align": "center"});
                // 创建对象
                var img = new Image();
                // 改变图片的src
                img.src = obj.attr("src");
                // 加载完成执行
                img.onload = function () {
                    var curWidth = img.width;
                    var curHeight = img.height;
                    if (curWidth > maxWidth && curHeight < maxHeight) {
                        obj.css({"width": maxWidth, "height": (curHeight * maxWidth) / curWidth});
                        obj.css("marginTop", (maxHeight - (curHeight * maxWidth) / curWidth) / 2);
                    } else if (curHeight > maxHeight && curWidth < maxWidth) {
                        obj.css({"width": (curWidth * maxHeight) / curHeight, "height": maxHeight});
                    } else if (curWidth < maxWidth && curHeight < maxHeight) {
                        obj.css({"width": curWidth, "height": curHeight});
                        obj.css("marginTop", (maxHeight - curHeight) / 2);
                    } else {
                        if (maxWidth / maxHeight >= curWidth / curHeight) {
                            obj.css({"width": (maxHeight * curWidth) / curHeight, "height": maxHeight});
                        } else {
                            obj.css({"width": maxWidth, "height": (maxWidth * curHeight) / curWidth});
                            obj.css("marginTop", (maxHeight - (maxWidth * curHeight) / curWidth) / 2);
                        }
                    }
                    obj.css({"display": "inline-block"});
                };

                e.beforeShow();
                commonUtils.centerElem($("#coverAlert"));
            }, e.getCover = function () {
                if (window.parent !== window) {
                    return $('.cover', window.parent.document);
                }
                return $("#Main_iframe").contents().find(".cover");
            }, e.beforeShow = function () {
                // 显示 主页面或子页面的遮罩（非当前页面）
                e.getCover().show();
                // 重置body样式，解决弹窗的问题
                var body = $(document.body);
                var oldWidth = body.innerWidth();
                body.css({position: "fixed", overflow: "hidden"});
                body.width(oldWidth);
            }, e.afterClose = function () {
                // 隐藏 主页面或子页面的遮罩（非当前页面）
                e.getCover().hide();
                // 重置body样式，解决弹窗的问题
                var body = $(document.body);
                body.css({position: "", overflow: ""});
                body.width("auto");
            }, e.ok = function () {
                t.close();
                e.afterClose();
            }, e.close = function () {
                t.dismiss('close');
                e.afterClose();
            }, e.cancel = function () {
                t.dismiss('cancel');
                e.afterClose();
            }, e.update = function () {
            }
        }])
    .filter('publishTimeFormat', function () {
        return function (input) {
            if (input == '' || input == null) return;
            input=input.substring(0,10);
            return input;
        }
    })
    .factory('userInterceptor', ["$cookieStore", "$cookies", "appConstant",
        function ($cookieStore, $cookies, appConstant) {
            var userInterceptor = {
                request: function (config) {
                    return config;
                },
                response: function (response) {
                    if (response.data.code == 401) {
                        $cookies.remove("lsc-user", {path: appConstant.COOKIE_ROOT_PATH});
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
                        $cookies.remove("lsc-user", {path: appConstant.COOKIE_ROOT_PATH});
                        var win = window;
                        if (window != window.parent) {
                            win = window.parent;
                        }
                        win.location = "../../lsc/login/login.html";
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
    }])



