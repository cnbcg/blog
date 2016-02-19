'use strict';
var blog = angular.module('blog', [])
    .config(function ($routeProvider) {

        $routeProvider
            .when('/blogs', {
                templateUrl: 'views-blog-list',
                controller: 'BlogListController',
                authority: 'user',
                resolve: {
                    blogs: function ($route, authenticationService, Blogs) {
                        return Blogs.query({username: authenticationService.authUser().username});
                    }
                }
            })
            .when('/newblog', {
                templateUrl: 'views-blog-edit',
                controller: 'BlogEditController',
                authority: 'user'
            })
            .otherwise({
                redirectTo: '/'
            });
    });