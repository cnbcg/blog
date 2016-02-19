'use strict';
user.controller('UserLoginController', function ($scope) {

    $scope.login = function (credentials) {
        $scope.authenticationService.authenticate(credentials);
    };
});