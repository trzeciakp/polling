'use strict';

pollingApp.controller('ScoreController', function ($scope, resolvedScore, Score, resolvedProduct, resolvedUser) {

        $scope.scores = resolvedScore;
        $scope.products = resolvedProduct;
        $scope.users = resolvedUser;

        $scope.create = function () {
            Score.save($scope.score,
                function () {
                    $scope.scores = Score.query();
                    $('#saveScoreModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.score = Score.get({id: id});
            $('#saveScoreModal').modal('show');
        };

        $scope.delete = function (id) {
            Score.delete({id: id},
                function () {
                    $scope.scores = Score.query();
                });
        };

        $scope.clear = function () {
            $scope.score = {value: null, id: null};
        };
    });
