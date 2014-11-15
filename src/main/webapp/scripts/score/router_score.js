'use strict';

pollingApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/score/:id', {
                    templateUrl: 'views/scores.html',
                    controller: 'ScoreController',
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
