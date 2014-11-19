'use strict';

pollingApp.controller('PollDetailController', function ($scope, $routeParams, $location, Poll, Product, PollScores, Account, PollProducts, Access, Session) {

    $scope.currentPoll = Poll.get({id: $routeParams.id});
    Account.get().$promise.then(function (account) {
        $scope.score = PollScores.get({pollid: $routeParams.id, user: account.login});
    });

    $scope.scoreIt = function() {
        $location.path("/score/" + $scope.currentPoll.id);
    };
});

