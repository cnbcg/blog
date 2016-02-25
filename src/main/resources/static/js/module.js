'use strict';
var app = angular.module('app', ['ngResource', 'ngRoute', 'ng-showdown', 'blog', 'user'])
    .value('BLOG_USERNAME', 'cnbcg')
    .config(function ($locationProvider, $httpProvider) {
        $httpProvider.defaults.headers.common['DNR'] = '1';
        $locationProvider.html5Mode(true).hashPrefix('!');
        $httpProvider.interceptors.push('progressInterceptor');

    }).config(function ($routeProvider) {
        $routeProvider.otherwise({
            redirectTo: '/blogs'
        });
    })
    .run(function ($rootScope, $location, authenticationService, messageService) {

        NProgress.configure({
            minimum: 0.2
            , ease: 'ease', speed: 500
            , trickleRate: 0.05, trickleSpeed: 800
        });

        $('body').on("mouseenter", ".btn", function () {
            $(this).find(".glyphicon").transition({
                rotate: '360deg'
            }, 200);

        }).on("mouseleave", ".btn", function () {
            $(this).find(".glyphicon").transition({
                rotate: '0deg'
            }, 200);

        }).on("mouseenter", ".showTips", function () {
            $(this).tooltip({
                placement: 'bottom'
            });
            $(this).tooltip('show');

        });

        $rootScope.$on('$routeChangeStart', function (event, next, current) {
            var authority = next.$$route ? next.$$route.authority : null;

            if (authority && authority === 'user' && !authenticationService.isAuthenticated()) {
                messageService.showErrorMessage("权限不足，请登录");
                authenticationService.setRememberedRequest(next.$$route.originalPath);
                $location.path('login');
                return;
            }

        });

        $rootScope.$on('$routeChangeSuccess', function (event, next, current) {
            next.$$route && ($rootScope.path = next.$$route.originalPath);

            $("#mainWrapper").stop(false, true)
                .css({opacity: 0, x: -400, rotateY: '-70deg', scale: '1'})
                .transition({
                    opacity: 1, x: 0, rotateY: '0deg', scale: '1'
                }, 500, 'ease', function () {
                    $("#mainWrapper").removeAttr("style");
                });
        });

        $rootScope.$on('$routeChangeError', function (event, next, current) {
            messageService.showErrorMessage("无法加载页面");
        });

    }).factory('progressInterceptor', function ($q, messageService) {
        return {
            request: function (config) {
                NProgress.start();
                return config;
            },
            response: function (response) {
                NProgress.done();
                return response;
            },
            responseError: function (response) {
                NProgress.done();
                messageService.showErrorMessage(response.data.message);
                return $q.reject(response);
            }
        };
    });
;