'use strict';
var admin = angular.module('admin', [])
    .config(function ($routeProvider) {

        $routeProvider
            .when("/:username/management", {
                templateUrl: 'views-admin-management',
                controller: 'ManagementController',
                resolve: {
                    paginationBlog: function ($q, authenticationService, Blogs) {
                        var defer = $q.defer();
                        Blogs.paginationQuery({username: authenticationService.authUser().username}, function (data) {
                            defer.resolve(data);

                        }, function (data) {
                            defer.reject(data);
                        });
                        return defer.promise;
                    },
                    paginationBlogComments: function ($q, authenticationService, BlogComments) {
                        var defer = $q.defer();
                        BlogComments.paginationQuery(function (data) {
                            defer.resolve(data);

                        }, function (data) {
                            defer.reject(data);
                        });
                        return defer.promise;
                    }
                }
            });
    });