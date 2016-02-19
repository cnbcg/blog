app.controller("IndexController", function($scope, $http, $window, authenticationService){

    $scope.authenticationService = authenticationService;

    $scope.cancel = function() {
        $window.history.back();
    };

    $scope.changeUI = function(param, value) {
        $http.get("?" + param  + "=" + value).success(function(data, status, headers, config) {
            $window.location.reload();
        });
    };

});