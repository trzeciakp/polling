'use strict';

pollingApp.controller('PollController', function ($scope, resolvedPoll, Poll, Session) {

        $scope.polls = resolvedPoll;
    console.log(resolvedPoll);

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
            $scope.poll = Poll.get({id: id});
            $('#savePollModal').modal('show');
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
