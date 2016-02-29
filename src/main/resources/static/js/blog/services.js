'use strict';
blog.factory('Blogs', function ($resource) {
    return $resource('/blogs/:id',
        {userId: '@userId', id: '@id'},
        {
            paginationQuery: {method: 'GET', params: {page: 0, size: 15, sort: 'createdDate,desc'}, isArray: false}
        });

}).factory('BlogComments', function ($resource) {
    return $resource('/blogs/comments/:id',
        {userId: '@userId', blogId: '@blogId', id: '@id'},
        {paginationQuery: {method: 'GET', params: {page: 0, size: 15, sort: 'createdDate,desc'}, isArray: false}});
});