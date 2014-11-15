'use strict';

pollingApp.controller('PollController', function ($scope, $location, resolvedPoll, Poll, Session) {

    $scope.user = Session.login;

        $scope.polls = resolvedPoll;

        $scope.isOwner = function(poll) {
            console.log(Session.login);
            return Session.login === poll.user.login;
        };

        $scope.create = function () {
            $scope.poll.user.login = Session.login;
            //or $scope.poll.user.login = Account.login;
            Poll.save($scope.poll,
                function () {
                    $scope.polls = Poll.query();
                    $('#savePollModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $location.path( 'poll/' + id + '/edit');
        };

        $scope.delete = function (id) {
            Poll.delete({id: id},
                function () {
                    $scope.polls = Poll.query();
                });
        };

        $scope.clear = function () {
            $scope.poll = {name: null, maxScore: null, id: null, user: {}};
            $scope.poll.user.login = Session.login;
        };
    });
