'use strict';
blog.controller('BlogListController', function ($scope, paginationBlog) {

    console.log(paginationBlog);

    $scope.blogs = paginationBlog.content;

    $scope.selectPage = function (pageNow) {
        alert(pageNow);
    };

}).controller('BlogEditController', function ($scope, $location, Blogs, messageService) {

    $scope.blog = {author : {nickname : "test"}, createdDate: new Date(),  content : ""};

    $scope.createBlog = function(blog) {
        Blogs.save(blog, function(dbBlog) {
            messageService.showSuccessMessage("保存成功");
            $location.path('blogs');

        }, function(error) {
            console.log(error);
            messageService.showErrorMessage(error.data.message);
        });
    };

});