'use strict';
blog.factory('Blogs', function($resource){
        return $resource('/blogs/:id',
            {username: '@username', id: '@id'},
            {paginationQuery: {method: 'GET', isArray: false}});
    });