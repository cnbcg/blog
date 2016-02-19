'use strict';
var app = angular.module('app', ['ngResource', 'ngRoute', 'blog', 'user'])
    .config(function ($locationProvider, $httpProvider) {

        $httpProvider.defaults.headers.common['DNR'] = '1';
        $locationProvider.html5Mode(true).hashPrefix('!');
    })
    .run(function ($rootScope, $location, authenticationService) {

        NProgress.configure({
            minimum: 0.2
            , ease: 'ease', speed: 500
            , trickleRate: 0.08, trickleSpeed: 800
        });

        $rootScope.$on('$routeChangeStart', function (scope, next, current) {
            var authority = next.$$route ? next.$$route.authority : null;

            if (authority && authority === 'user' && !authenticationService.isAuthenticated()) {
                authenticationService.setRememberedRequest(next.$$route.originalPath);
                $location.path('login');
                return;
            }

            NProgress.start();
        });

        $rootScope.$on('$routeChangeSuccess', function (scope, next, current) {
            next.$$route && ($rootScope.path = next.$$route.originalPath);
            NProgress.done();

            $("#mainWrapper")
              .css({ opacity: 0, x: -400, rotateY: '-80deg', scale: '1'})
              .transition({
                  opacity: 1, x: 0,  rotateY: '0deg', scale: '1',
                  duration: 600, easing: 'ease'});
            });

    });