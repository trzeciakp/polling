'use strict';

pollingApp.factory('Poll', function ($resource) {
        return $resource('app/rest/polls/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
