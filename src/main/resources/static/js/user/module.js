'use strict';
var user = angular.module('user', [])
    .config(function ($routeProvider) {

        $routeProvider
            .when('/login', {
                htmlTitle: '用户登录',
                templateUrl: 'views-user-login',
                controller: 'UserLoginController'
            })
            .when('/register', {
                htmlTitle: '用户注册',
                templateUrl: 'views-user-register',
                controller: 'UserRegisterController'
            })
            .when('/activate/:activateCode', {
                htmlTitle: '用户激活',
                templateUrl: 'views-user-activate',
                controller: 'UserActivateController',
                resolve: {
                    user : function($route, $http, $q) {
                        var defer = $q.defer();
                        $http.put('users/activate/' + $route.current.params.activateCode).success(function(data){
                            defer.resolve(data);

                        }).error(function(data){
                            defer.resolve(null);
                        });

                        return defer.promise;
                    }
                }
            })
            .when('/profile', {
                htmlTitle: '用户资料',
                templateUrl: 'views-user-profile',
                controller: 'UserProfileController',
                authority: 'auth',
            })
            .when('/404', {
                templateUrl: 'views-errors-404'
            });
    });