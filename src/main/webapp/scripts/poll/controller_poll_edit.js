'use strict';

pollingApp.controller('PollEditController', function ($scope, $routeParams, Poll, Product, PollProducts, Invitation, Access, Session) {
    var EMAIL_REGEXP = /^\s*[a-z0-9!#$%&'*+\/=?^_`{|}~.-]+@[a-z0-9]([a-z0-9-]*[a-z0-9])?(\.[a-z0-9]([a-z0-9-]*[a-z0-9])?)*\s*$/i;

        $scope.polls = [];
    $scope.blackList = ['bad@domain.com','verybad@domain.com'];
    $scope.notBlackListed = function(value) {
        return $scope.blackList.indexOf(value) === -1;
    };
    $scope.currentPoll = Poll.get({id: $routeParams.id});
    $scope.polls[0] = $scope.currentPoll;
    $scope.users = Access.get({pollId: $routeParams.id});
    $scope.invitations = Invitation.get({pollid: $routeParams.id});
    $scope.result = {};

    $scope.products = PollProducts.get({id: $routeParams.id});
    $scope.clearProduct = function () {
        $scope.product = {name: null, id: null, poll: $scope.currentPoll};
        console.log($scope.product);
    };

    $scope.isOwner = function (login) {
        console.log($scope.currentPoll);
        return login !== $scope.currentPoll.user.login;
    }

    $scope.createProduct = function () {
        Product.save($scope.product,
            function () {
                $scope.products = PollProducts.get({id: $routeParams.id});
                $('#saveProductModal').modal('hide');
                $scope.clear();
            });
    };
    $scope.update = function (id) {
        $scope.poll = Poll.get({id: id});
        $('#savePollModal').modal('show');
    };

    $scope.invite = function () {
        var invitationRequestDTO = {emails: csvToArray($scope.emails), poll: $scope.currentPoll};
        console.log(invitationRequestDTO);
        Invitation.save(invitationRequestDTO, function (result) {
            console.log(result);
            $('#inviteModal').modal('hide');
            $scope.clear();
            $scope.result = result;
            $scope.invitations = Invitation.get({pollid: $routeParams.id});
            $scope.currentPoll = Poll.get({id: $routeParams.id});
            $scope.users = Access.get({pollId: $routeParams.id});
            $scope.polls[0] = $scope.currentPoll;
        });
    };

    $scope.isRecipientListInvalid = function() {
        var input = $scope.emails;
        if (angular.isUndefined(input) || input=="") {
            return false;
        }
        var array = input.split(";") ;
        for (var i = 0; i < array.length; i++ ) {
            if (!array[i].match(EMAIL_REGEXP) || array[i].trim() === "") {
                return true;
            }
        }
        return false;
    };


    //TO remove?????
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


        $scope.delete = function (id) {
            Poll.delete({id: id},
                function () {
                    $scope.polls = Poll.query();
                });
        };

        $scope.clear = function () {
            $scope.poll = {name: null, maxScore: null, id: null, user: {}};
            $scope.poll.user.login = Session.login;
            $scope.emails = "";
        };

    var csvToArray = function(input) {
        if (angular.isUndefined(input) || input=="") {
            return undefined;
        }
        var result = [];

        var splitted = input.split(";");
        for (var i = 0; i < splitted.length; i++) {
            var trimmedObject = splitted[i].trim();
            if (trimmedObject !== "") {
                result.push(trimmedObject);
            }
        }
        return result;
    };
    });
