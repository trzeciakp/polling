'use strict';

pollingApp.controller('PollDetailController', function ($scope, $routeParams, Poll, Access, Session) {

    $scope.currentPoll = Poll.get({id: $routeParams.id});
    });
