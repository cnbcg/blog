blog.directive("blog", function () {
    return {
        restrict: 'E',
        templateUrl: 'directives-blog-detail',
        scope: {
            blog: '='
        },
        replace: true,
        link: function ($scope, element) {
        }
    }
});