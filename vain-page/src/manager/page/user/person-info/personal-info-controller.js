angular.module("personalInfo.controllers", ["angularFileUpload"])
/**个人信息列表**/
    .controller("PersonalInfoCtrl", ["appConstant", "$scope", "FileUploader", "personalInfoHttpServices",
        function (appConstant, $scope, FileUploader, personalInfoHttpServices) {
            $scope.init = function () {
                personalInfoHttpServices.getUserInfo({}, function (data) {
                    $scope.user = data.data;
                    if (typeof $scope.user.birthday != "undefined") {
                        $scope.user.birthday = $scope.user.birthday.split(" ")[0];
                    }
                });
            };

            /*日期选择*/
            $('#birthday').datepicker().on('changeDate.datepicker.amui', function (event) {
                var date = event.date;
                $scope.user.birthday = date.getFullYear();
                Number(date.getMonth() < 9) ? $scope.user.birthday += "-0" + (Number(date.getMonth()) + 1) : $scope.user.birthday += "-" + (Number(date.getMonth()) + 1);
                Number(date.getDate() < 9) ? $scope.user.birthday += "-0" + String(date.getDate()) : $scope.user.birthday += "-" + date.getDate();
                $scope.$apply();
            });

            /*修改信息*/
            $scope.submit = function () {
                $scope.user.birthday = $scope.user.birthday + " 00:00:00";
                personalInfoHttpServices.modify($scope.user, function (data) {
                    if (data.code == 200)
                        $scope.init();
                });
            };


            //上传文件的选择项
            $scope.uploadMainImage = new FileUploader({
                url: appConstant.BASE_URL + "/upload/uploadPics",
                method: "post",
                autoUpload: true
            });


            //上传图片
            $scope.uploadMainImage.onSuccessItem = function (t, n) {
                $scope.imgType = false;
                $scope.imgSize = false;
                $scope.imgUpload = false;
                $scope.imgUrlRequire = true;
                $scope.user.head = n.data[0].url;

            };
            $scope.uploadMainImage.onProgressItem = function (t, n) {
                $scope.imgUpload = true;
            };
            $scope.uploadMainImage.onWhenAddingFileFailed = function () {

            };
        }]);

