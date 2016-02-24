'use strict';
var blog = angular.module('blog', [])
    .config(function ($routeProvider) {

        $routeProvider
            .when('/blogs', {
                templateUrl: 'views-blog-list',
                controller: 'BlogListController',
                resolve: {
                    paginationBlog: function ($route, authenticationService, Blogs, BLOG_USERNAME) {
                        return Blogs.paginationQuery({username: BLOG_USERNAME});
                    }
                }
            })
            .when('/newblog', {
                templateUrl: 'views-blog-edit',
                controller: 'BlogEditController',
                authority: 'user'
            });
    });