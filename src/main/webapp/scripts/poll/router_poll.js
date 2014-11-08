'use strict';

pollingApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/poll', {
                    templateUrl: 'views/polls.html',
                    controller: 'PollController',
                    resolve:{
                        resolvedPoll: ['Poll', function (Poll) {
                            return Poll.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
