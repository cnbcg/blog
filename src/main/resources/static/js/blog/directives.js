blog.directive("blog", function () {
    return {
        restrict: 'E',
        templateUrl: 'directives-blog-detail',
        replace: true,
        link: function ($scope, element) {
        }
    }
});