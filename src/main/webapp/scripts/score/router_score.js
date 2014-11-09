'use strict';

pollingApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/score', {
                    templateUrl: 'views/scores.html',
                    controller: 'ScoreController',
                    resolve:{
                        resolvedScore: ['Score', function (Score) {
                            return Score.query().$promise;
                        }],
                        resolvedProduct: ['Product', function (Product) {
                            return Product.query().$promise;
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
