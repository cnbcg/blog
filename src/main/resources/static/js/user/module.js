'use strict';
var user = angular.module('user', [])
    .config(function ($routeProvider) {

        $routeProvider
            .when('/login', {
                templateUrl: 'views-user-login',
                controller: 'UserLoginController'
            })
            .otherwise({
                redirectTo: '/'
            });
    });