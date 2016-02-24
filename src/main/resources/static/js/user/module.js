'use strict';
var user = angular.module('user', [])
    .config(function ($routeProvider) {

        $routeProvider
            .when('/login', {
                templateUrl: 'views-user-login',
                controller: 'UserLoginController'
            })
            .when('/register', {
                templateUrl: 'views-user-register',
                controller: 'UserRegisterController'
            })
            .when('/activate/:activateCode', {
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
            .when('/404', {
                templateUrl: 'views-errors-404'
            });
    });