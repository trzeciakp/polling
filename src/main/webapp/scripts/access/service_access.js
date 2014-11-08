'use strict';

pollingApp.factory('Access', function ($resource) {
        return $resource('app/rest/accesss/poll/:pollId', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET', isArray: true}
        });
    });
