app.directive("pagination", function () {
    return {
        restrict: 'E',
        scope :{
            pageNow: '=',
            pageCount: '=',
            selectPage: '&'
        },
        templateUrl: 'directives-pagination',
        replace: true,
        link: function ($scope, element) {

            $scope.previous = function() {
                $scope.pageNow = $scope.pageNow > 1 ? $scope.pageNow - 1 : 1;
                $scope.selectPage({pageNow : $scope.pageNow});
            }

            $scope.next = function() {
                $scope.pageNow = $scope.pageNow < $scope.pageCount ? $scope.pageNow + 1 : $scope.pageCount;
               $scope.selectPage({pageNow : $scope.pageNow});
            }

        }
    }
});