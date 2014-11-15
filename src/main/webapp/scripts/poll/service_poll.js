'use strict';

pollingApp.factory('Poll', function ($resource) {
    return $resource('app/rest/polls/:id', {}, {
        'query': { method: 'GET', isArray: true},
        'get': { method: 'GET'}
    });
}).factory('PollProducts', function ($resource) {
    return $resource('app/rest/polls/:id/products', {}, {
        'query': { method: 'GET', isArray: true},
        'get': { method: 'GET', isArray: true}
    });
}).factory('PollScores', function ($resource) {
    return $resource('app/rest/scores/', {}, {
        'query': { method: 'GET', isArray: true},
        'get': { method: 'GET', isArray: true}
    });
}).factory('PollFirstScore', function ($resource) {
    return $resource('app/rest/scores/first/', {}, {
        'query': { method: 'GET', isArray: true},
        'get': { method: 'GET'}
    });
});
