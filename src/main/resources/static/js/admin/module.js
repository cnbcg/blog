'use strict';
var admin = angular.module('admin', [])
    .config(function ($routeProvider) {

        $routeProvider
            .when("/:username/management", {
                htmlTitle: '后台管理',
                templateUrl: 'views-admin-management',
                controller: 'ManagementController',
                authority: 'auth',
                resolve: {
                    paginationBlog: function ($q, authenticationService, Blogs) {
                        var defer = $q.defer();
                        Blogs.paginationQuery({userId: authenticationService.authUser().id}, function (data) {
                            defer.resolve(data);

                        }, function (data) {
                            defer.reject(data);
                        });
                        return defer.promise;
                    },
                    paginationBlogComments: function ($q, authenticationService, BlogComments) {
                        var defer = $q.defer();
                        BlogComments.paginationQuery({userId: authenticationService.authUser().id}, function (data) {
                            defer.resolve(data);

                        }, function (data) {
                            defer.reject(data);
                        });
                        return defer.promise;
                    }
                }
            });
    });