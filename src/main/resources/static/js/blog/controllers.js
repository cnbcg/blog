'use strict';
blog.controller('BlogListController', function ($scope, blogs) {
    $scope.blogs = blogs;

}).controller('BlogEditController', function ($scope, $location, Blogs, messageService) {

    $scope.createBlog = function(blog) {
        Blogs.save(blog, function(data) {
            messageService.showMessage("保存成功");
            $location.path('blogs');
        });
    }

});