blog.directive("blog", function () {
    return {
        restrict: 'E',
        templateUrl: 'directives-blog-detail',
        scope: {
            blog: '=',
            edit: '&',
            delete: '&'
        },
        replace: true,
        link: function ($scope, element) {
        }
    }

}).directive("comment", function ($compile) {
    return {
        restrict: 'E',
        templateUrl: 'directives-blog-comment',
        scope: {
            comment: '=',
            reply: '&',
            delete: '&'
        },
        replace: true,
        link: function ($scope, element) {
            if ($scope.comment && $scope.comment.parent) {
                element.find(".comment-content").prepend($compile('<comment comment="comment.parent" reply="reply(comment)"></comment>')($scope));
            }
        }
    }
});