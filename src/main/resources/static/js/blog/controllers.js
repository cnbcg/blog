'use strict';
blog.controller('BlogListController', function ($scope, messageService, Blogs, paginationBlog) {

    $scope.paginationBlog = paginationBlog;

    $scope.selectPage = function (pageNow) {
        $scope.paginationBlog = Blogs.paginationQuery({page: pageNow});
    };

}).controller('BlogDetailController', function ($scope, $location, messageService, Blogs, blog) {
    $scope.blog = blog;

    $scope.edit = function(blog) {
        $location.path('/blogs/edit/' + blog.id);
    };

    $scope.delete = function (blog) {
        Blogs.delete({id: blog.id}, function (dbBlog) {
            messageService.showSuccessMessage("删除成功");
            $location.path('blogs');
        });
    };

}).controller('BlogEditController', function ($scope, $location, messageService, Blogs, blog) {
    $scope.blog = blog;

    $scope.createBlog = function (blog) {
        Blogs.save(blog, function (dbBlog) {
            messageService.showSuccessMessage("保存成功");
            $location.path('blogs/' + dbBlog.id);
        });
    };

}).controller('BlogCommentController', function ($scope, $location, $anchorScroll, BlogComments, messageService) {

    $scope.selectPage = function (pageNow) {
        $scope.paginationBlogComments = BlogComments.paginationQuery({blogId: $scope.blog.id, page: pageNow});
    };

    $scope.reply = function (comment) {
        $scope.blogComment.parent = comment;
        $anchorScroll("commentParent");
    };

    $scope.delete = function (blog, comment) {
        BlogComments.delete({blogId: blog.id},{id: comment.id}, function (dbBlogComment) {
            blog.commentCount--;
            if ($scope.blogComment.parent && $scope.blogComment.parent.id === comment.id)  $scope.blogComment.parent = null;
            $scope.selectPage();
            messageService.showSuccessMessage("删除成功");
        });
    };

    $scope.blogComment = {content: ""};
    $scope.selectPage();

    $scope.createBlogComment = function (blog, blogComment) {
        BlogComments.save({blogId: $scope.blog.id}, blogComment, function (dbBlogComment) {
            blog.commentCount++;
            $scope.blogComment = {content: ""};
            $scope.selectPage();
            messageService.showSuccessMessage("保存成功");
        });
    };

});