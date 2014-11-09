'use strict';

pollingApp.controller('PollDetailController', function ($scope, $routeParams, Poll, Product, PollProducts, Access, Session) {

    $scope.currentPoll = Poll.get({id: $routeParams.id});
    $scope.products = PollProducts.get({id: $routeParams.id});

    $scope.clear = function () {
        $scope.product = {name: null, id: null, poll: {}};
        $scope.product.poll = $scope.currentPoll;
    };

    $scope.create = function () {
        Product.save($scope.product,
            function () {
                $scope.products = PollProducts.get({id: $routeParams.id});
                $('#saveProductModal').modal('hide');
                $scope.clear();
            });
    };
});

