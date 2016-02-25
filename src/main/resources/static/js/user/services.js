'use strict';
user.factory('authenticationService', function ($http, $window, $location, messageService) {

    var authUser, LOGIN_DEFAULT_PATH = "/", rememberedRequest = LOGIN_DEFAULT_PATH;

    if ($window.sessionStorage["authUser"]) {
        authUser = JSON.parse($window.sessionStorage["authUser"]);
    }

    return {
        authenticate: function (credentials, successCallback, errorCallback) {
            $http.post('/login?' + $.param(credentials)).success(function (user) {
                (authUser = user) && ($window.sessionStorage["authUser"] = JSON.stringify(user));
                successCallback && successCallback(authUser);
                $location.path(rememberedRequest) && (rememberedRequest = LOGIN_DEFAULT_PATH);

            }).error(function (error) {
                errorCallback && errorCallback(error);
            });
        },
        logout: function () {
            $http.post('/logout');
            $window.sessionStorage.removeItem("authUser");
            authUser = null;
            $window.location.reload();
        },
        isAuthenticated: function () {
            return !!authUser;
        },
        authUser: function () {
            return authUser;
        },
        setRememberedRequest: function (rq) {
            rememberedRequest = rq;
        }
    };

}).factory("Users", function ($resource) {
    return $resource("/users/:id", {id: "@id"});
});