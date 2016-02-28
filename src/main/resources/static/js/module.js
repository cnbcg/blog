'use strict';
var app = angular.module('app', ['ngResource', 'ngRoute', 'ng-showdown', 'blog', 'user', 'admin'])
    .value('BLOG_USERNAME', 'admin')
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

        (function breathingLights() {
            $(".navbar-brand").transition({
                color: '#777',
                textShadow: '0px 0px 0px #34302d'

            }, 2000).transition({
                color: '#0CC',
                textShadow: '0px 0px 15px #0FF'
            }, 2000);

            setTimeout(breathingLights, 4000);
        }());

        NProgress.configure({
            minimum: 0.2
            , ease: 'ease', speed: 500
            , trickleRate: 0.05, trickleSpeed: 800
        });

        $('body').on("mouseenter", ".btn:not([disabled])", function () {
            $(this).find(".glyphicon").transition({
                rotate: '360deg'
            }, 200);

        }).on("mouseleave", ".btn:not([disabled])", function () {
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
                authenticationService.setRememberedRequest($location.url());
                $location.path('login');
                return;
            }

        });

        $rootScope.$on('$routeChangeSuccess', function (event, next, current) {
            next.$$route && ($rootScope.path = next.$$route.originalPath);

            $("#mainWrapper").stop(false, true)
                .css({opacity: 0, x: -400, rotateY: '-75deg'})
                .transition({opacity: 1, x: 0, rotateY: '0deg'}, function () {
                    $("#mainWrapper").removeAttr("style");
                });
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