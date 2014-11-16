'use strict';

pollingApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/invitation', {
                    templateUrl: 'views/invitations.html',
                    controller: 'InvitationController',
                    resolve:{
                        resolvedInvitation: ['Invitation', function (Invitation) {
                            return Invitation.query().$promise;
                        }],
                        resolvedPoll: ['Poll', function (Poll) {
                            return Poll.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
