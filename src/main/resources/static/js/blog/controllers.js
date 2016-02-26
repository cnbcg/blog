'use strict';
blog.controller('BlogListController', function ($scope, messageService, BLOG_USERNAME, Blogs, paginationBlog) {

    $scope.paginationBlog = paginationBlog;

    $scope.selectPage = function (pageNow) {
        $scope.paginationBlog = Blogs.paginationQuery({username: BLOG_USERNAME, page: pageNow});
    };

}).controller('BlogEditController', function ($scope, $location, Blogs, messageService) {

    $scope.blog = {author: {nickname: "test"}, createdDate: new Date(), content: "", commentCount: 0, viewCount: 0};

    $scope.createBlog = function (blog) {
        Blogs.save(blog, function (dbBlog) {
            messageService.showSuccessMessage("保存成功");
            $location.path('blogs');
        });
    };

}).controller('BlogDetailController', function ($scope, blog) {
    $scope.blog = blog;

}).controller('BlogCommentController', function ($scope, $location, $anchorScroll, BlogComments, messageService) {

    $scope.selectPage = function (pageNow) {
        $scope.paginationBlogComments = BlogComments.paginationQuery({blogId: $scope.blog.id, page: pageNow});
    };

    $scope.reply = function (comment) {
        $scope.blogComment.parent = comment;
        $anchorScroll("commentParent");
    };

    $scope.delete = function (comment) {
        BlogComments.delete({blogId: $scope.blog.id},{id: comment.id}, function (dbBlogComment) {
            $scope.selectPage();
            messageService.showSuccessMessage("删除成功");
        });
    };

    $scope.blogComment = {content: ""};
    $scope.selectPage();

    $scope.createBlogComment = function (blog, blogComment) {
        BlogComments.save({blogId: $scope.blog.id}, blogComment, function (dbBlogComment) {
            $scope.blogComment = {content: ""};
            $scope.selectPage();
            messageService.showSuccessMessage("保存成功");
        });
    };

});