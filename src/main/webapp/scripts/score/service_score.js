'use strict';

pollingApp.factory('Score', function ($resource) {
        return $resource('app/rest/scores/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
