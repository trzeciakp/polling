'use strict';

pollingApp.factory('Invitation', function ($resource) {
        return $resource('app/rest/invitations/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET', isArray: true}
        });
    });
