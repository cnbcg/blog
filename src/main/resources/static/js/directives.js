app.directive("pagination", function () {
    return {
        restrict: 'E',
        scope: {
            pageNow: '=',
            pageCount: '=',
            selectPage: '&'
        },
        templateUrl: 'directives-pagination',
        replace: true,
        link: function ($scope, element) {

            $scope.previous = function () {
                if (!$scope.isFirst()) {
                    $scope.pageNow = $scope.pageNow > 0 ? $scope.pageNow - 1 : 0;
                    $scope.selectPage({pageNow: $scope.pageNow});
                }
            };

            $scope.next = function () {
                if (!$scope.isLast()) {
                    $scope.pageNow = $scope.pageNow < $scope.pageCount - 1 ? $scope.pageNow + 1 : $scope.pageCount - 1;
                    $scope.selectPage({pageNow: $scope.pageNow});
                }
            };

            $scope.isFirst = function () {
                return $scope.pageNow == 0;
            };

            $scope.isLast = function () {
                return $scope.pageCount == 0 || $scope.pageNow == $scope.pageCount - 1;
            };

        }
    }
});