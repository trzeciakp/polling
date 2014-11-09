'use strict';

pollingApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/product', {
                    templateUrl: 'views/products.html',
                    controller: 'ProductController',
                    resolve:{
                        resolvedProduct: ['Product', function (Product) {
                            return Product.query().$promise;
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
