'use strict';
user.controller('UserLoginController', function ($scope) {
    $scope.login = function (credentials) {
        $scope.authenticationService.authenticate(credentials);
    };

}).controller('UserRegisterController', function ($scope, $location, Users, messageService) {
    $scope.register = function (user) {
        Users.save(user, function(dbUser) {
            messageService.showMessage("注册成功, 请查看邮件激活账号。");
            $location.path('login');

        }, function(response) {
            messageService.showMessage(response.data.message);
        });
    };

}).controller('UserActivateController', function ($scope, user) {
    console.log(user);
    $scope.activateUser = user;
});