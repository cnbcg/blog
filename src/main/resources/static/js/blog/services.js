'use strict';
blog.factory('Blogs', function($resource){
        return $resource('/blogs/:id',
            {username: '@username', id: '@id'},
            {paginationQuery: {method: 'GET', params: {page: 0, size : 15, sort: 'createdDate,desc'},isArray: false}});
    });