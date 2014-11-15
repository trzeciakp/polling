'use strict';

pollingApp.controller('PollDetailController', function ($scope, $routeParams, $location, Poll, Product, PollScores, Account, PollProducts, Access, Session) {

    $scope.currentPoll = Poll.get({id: $routeParams.id});
    $scope.products = PollProducts.get({id: $routeParams.id});
    console.log(Session);
    Account.get().$promise.then(function (account) {
        $scope.score = PollScores.get({pollid: $routeParams.id, user: account.login});
        console.log($scope.score);
    });
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

    $scope.scoreIt = function() {
        $location.path("/score/" + $scope.currentPoll.id);
    };
});

