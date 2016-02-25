'use strict';
blog.controller('BlogListController', function ($scope, messageService, BLOG_USERNAME, Blogs, paginationBlog) {

    $scope.paginationBlog = paginationBlog;

    $scope.selectPage = function (pageNow) {
        Blogs.paginationQuery({username: BLOG_USERNAME, page: pageNow}, function (data) {
            $('#blogListContainer').transition({
                opacity: 0, x: -400, rotateY: '80deg', duration: 500, ease: 'ease'

            }, function () {
                $scope.paginationBlog = data;
                $scope.$digest();
                $('#blogListContainer').transition({
                    opacity: 1, x: 0, rotateY: '0deg', duration: 500, ease: 'ease'
                });
            })
        }, function (error) {
            messageService.showErrorMessage("加载出错");
        });
    };

}).controller('BlogEditController', function ($scope, $location, Blogs, messageService) {

    $scope.blog = {author: {nickname: "test"}, createdDate: new Date(), content: ""};

    $scope.createBlog = function (blog) {
        Blogs.save(blog, function (dbBlog) {
            messageService.showSuccessMessage("保存成功");
            $location.path('blogs');

        }, function (error) {
            console.log(error);
            messageService.showErrorMessage(error.data.message);
        });
    };

}).controller('BlogDetailController', function ($scope, blog) {
    $scope.blog = blog;
});