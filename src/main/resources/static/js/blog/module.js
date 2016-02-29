'use strict';
var blog = angular.module('blog', [])
    .config(function ($routeProvider) {

        $routeProvider
            .when('/blogs', {
                templateUrl: 'views-blog-list',
                controller: 'BlogListController',
                resolve: {
                    paginationBlog: function ($q, Blogs) {
                        var defer = $q.defer();
                        Blogs.paginationQuery(function (data) {
                            defer.resolve(data);

                        }, function (data) {
                            defer.reject(data);
                        });
                        return defer.promise;
                    }
                }
            })
            .when('/blogs/new', {
                templateUrl: 'views-blog-edit',
                controller: 'BlogNewController',
                authority: 'auth'
            })
            .when('/blogs/edit/:id', {
                templateUrl: 'views-blog-edit',
                controller: 'BlogEditController',
                authority: 'auth',
                resolve: {
                    blog: function ($route, $q, messageService, Blogs) {
                        var defer = $q.defer();
                        Blogs.get({id: $route.current.params.id}, function (data) {
                            defer.resolve(data);

                        }, function (data) {
                            defer.reject(data);
                        });
                        return defer.promise;
                    }
                }
            })
            .when('/blogs/:id', {
                templateUrl: 'views-blog-detail',
                controller: 'BlogDetailController',
                resolve: {
                    blog: function ($route, $q, messageService, Blogs) {
                        var defer = $q.defer();
                        Blogs.get({id: $route.current.params.id}, function (data) {
                            defer.resolve(data);

                        }, function (data) {
                            defer.reject(data);
                        });
                        return defer.promise;
                    }
                }
            });
    });