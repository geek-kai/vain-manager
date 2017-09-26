angular.module("common.directive", [])
    .directive('onRepeatFinishedRender', function ($timeout) {
        return {
            restrict: 'A', link: function (scope, element, attr) {
                if (scope.$last === true) {
                    $timeout(function () {
                        scope.$emit('ngRepeatFinished', element);
                    });
                }
            }
        };
    })
    .directive("pwdCheck", ["$window", "$rootScope", function (w, t) {
        return {
            require: "ngModel",
            link: function (scope, elem, attrs, ctrl) {
                var oldPasswd = '#' + attrs.pwdCheck;
                elem.add(oldPasswd).on('keyup', function () {
                    scope.$apply(function () {
                        var v = elem.val() !== $(oldPasswd).val();
                        ctrl.$setValidity("pwdWatch", v);
                    })
                })
            }
        }
    }])

    .directive("repwdCheck", ["$window", "$rootScope", function (w, t) {
        return {
            require: "ngModel",
            link: function (scope, elem, attrs, ctrl) {
                var newPasswd = '#' + attrs.repwdCheck;
                elem.add(newPasswd).on('keyup', function () {
                    scope.$apply(function () {
                        var s = elem.val() === $(newPasswd).val();
                        ctrl.$setValidity("repwdWatch", s);
                    })
                })
            }
        }
    }])
    .directive('sdate', function () {
        return {
            restrict: 'A',
            scope: false,
            require: 'ngModel',
            link: function (scope, element, attr, ngModel) {
                element.val(ngModel.$viewValue);
                function onpicked(dp) {
                    var date = dp.cal.getNewDateStr();
                    scope.$apply(function () {
                        ngModel.$setViewValue(date);
                    });
                }

                element.bind('click', function () {
                    window.WdatePicker({
                        onpicked: onpicked,
                        oncleared: function () {
                            scope.$apply(function () {
                                ngModel.$setViewValue("");
                            });
                        },
                        dateFmt: 'yyyy-MM-dd'
                    })
                });
            }
        };
    });