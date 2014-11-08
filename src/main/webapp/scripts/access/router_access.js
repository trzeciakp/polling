'use strict';

pollingApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/access', {
                    templateUrl: 'views/accesss.html',
                    controller: 'AccessController',
                    resolve:{
                        resolvedAccess: ['Access', function (Access) {
                            return Access.query().$promise;
                        }],
                        resolvedPoll: ['Poll', function (Poll) {
                            return Poll.query().$promise;
                        }],
                        resolvedUser: ['User', function (User) {
                            return User.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
