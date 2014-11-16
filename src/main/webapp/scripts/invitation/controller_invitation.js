'use strict';

pollingApp.controller('InvitationController', function ($scope, resolvedInvitation, Invitation, resolvedPoll) {

        $scope.invitations = resolvedInvitation;
        $scope.polls = resolvedPoll;

        $scope.create = function () {
            Invitation.save($scope.invitation,
                function () {
                    $scope.invitations = Invitation.query();
                    $('#saveInvitationModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.invitation = Invitation.get({id: id});
            $('#saveInvitationModal').modal('show');
        };

        $scope.delete = function (id) {
            Invitation.delete({id: id},
                function () {
                    $scope.invitations = Invitation.query();
                });
        };

        $scope.clear = function () {
            $scope.invitation = {email: null, id: null};
        };
    });
