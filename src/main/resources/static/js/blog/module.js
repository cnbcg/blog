'use strict';
var blog = angular.module('blog', [])
    .config(function ($routeProvider) {

        $routeProvider
            .when('/blogs', {
                templateUrl: 'views-blog-list',
                controller: 'BlogListController',
                resolve: {
                    paginationBlog: function ($q, Blogs, BLOG_USERNAME) {
                        var defer = $q.defer();
                        Blogs.paginationQuery({username: BLOG_USERNAME}, function(data) {
                            defer.resolve(data);

                        }, function(data) {
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
                        Blogs.get({id: $route.current.params.id}, function(data) {
                            defer.resolve(data);

                        }, function(data) {
                            defer.reject(data);
                        });
                        return defer.promise;
                    }
                }
            })
            .when('/newblog', {
                templateUrl: 'views-blog-edit',
                controller: 'BlogEditController',
                authority: 'user'
            });
    });