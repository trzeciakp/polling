'use strict';

pollingApp.controller('AccessController', function ($scope, resolvedAccess, Access, resolvedPoll, resolvedUser) {

        $scope.accesss = resolvedAccess;
        $scope.polls = resolvedPoll;
        $scope.users = resolvedUser;

        $scope.create = function () {
            Access.save($scope.access,
                function () {
                    $scope.accesss = Access.query();
                    $('#saveAccessModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.access = Access.get({id: id});
            $('#saveAccessModal').modal('show');
        };

        $scope.delete = function (id) {
            Access.delete({id: id},
                function () {
                    $scope.accesss = Access.query();
                });
        };

        $scope.clear = function () {
            $scope.access = {id: null};
        };
    });
