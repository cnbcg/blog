'use strict';
var blog = angular.module('blog', [])
    .config(function ($routeProvider) {

        $routeProvider
            .when('/', {
                htmlTitle: '文章列表',
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
                htmlTitle: '新建文章',
                templateUrl: 'views-blog-edit',
                controller: 'BlogEditController',
                authority: 'auth',
                resolve: {
                    blog: function ($route, $q, messageService, Blogs) {
                        return {author: {nickname: "test"}, createdDate: new Date(), content: "", commentCount: 0, viewCount: 0};
                    }
                }
            })
            .when('/blogs/edit/:id', {
                htmlTitle: '编辑文章',
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
                    blog: function ($rootScope, $route, $q, messageService, Blogs) {
                        var defer = $q.defer();
                        Blogs.get({id: $route.current.params.id}, function (data) {
                            $rootScope.title = data.title;
                            defer.resolve(data);

                        }, function (data) {
                            defer.reject(data);
                        });
                        return defer.promise;
                    }
                }
            });
    });