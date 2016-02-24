'use strict';
var app = angular.module('app', ['ngResource', 'ngRoute', 'ng-showdown', 'blog', 'user'])
    .value('BLOG_USERNAME', 'cnbcg')
    .config(function ($locationProvider, $httpProvider) {
        $httpProvider.defaults.headers.common['DNR'] = '1';
        $locationProvider.html5Mode(true).hashPrefix('!');

    }).config(function ($routeProvider) {
        $routeProvider
            .otherwise({
                redirectTo: '/blogs'
            });
    })
    .run(function ($rootScope, $location, authenticationService, messageService) {

        NProgress.configure({
            minimum: 0.2
            , ease: 'ease', speed: 500
            , trickleRate: 0.08, trickleSpeed: 800
        });

        $rootScope.$on('$routeChangeStart', function (scope, next, current) {
            var authority = next.$$route ? next.$$route.authority : null;

            if (authority && authority === 'user' && !authenticationService.isAuthenticated()) {
                messageService.showErrorMessage("权限不足，请登录");
                authenticationService.setRememberedRequest(next.$$route.originalPath);
                $location.path('login');
                return;
            }

            $("body").css('overflow', 'hidden');

            NProgress.start();
        });

        $rootScope.$on('$routeChangeSuccess', function (scope, next, current) {
            next.$$route && ($rootScope.path = next.$$route.originalPath);
            NProgress.done();

            $("#mainWrapper").stop(false, true)
                .css({opacity: 0, x: -400, rotateY: '-70deg', scale: '1'})
                .transition({
                    opacity: 1, x: 0, rotateY: '0deg', scale: '1'
                }, 500, 'ease', function () {
                    $("body").css('overflow', 'auto');
                    $("#mainWrapper").removeAttr("style");
                });
        });

    });