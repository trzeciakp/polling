'use strict';

pollingApp.controller('ScoreController', function ($scope, $routeParams, $location, $document,  PollFirstScore, Account, Score) {

    $scope.isFetched = false;
    $scope.maxScore = null;

    $scope.reload = function () {
        $scope.isFetched = false;
        $scope.maxScore = null;

        Account.get().$promise.then(function (account) {
            $scope.score = PollFirstScore.get({pollid: $routeParams.id, user: account.login});
            $scope.score.$promise.then(function (score) {

                $scope.isFetched = ($scope.score != null);
                $scope.score.value = 0;
                console.log($scope.score.productA.name + ' - ' + $scope.score.productB.name);
                $scope.maxScore = $scope.score.productA.poll.maxScore;
                $scope.isScoreChanged = false;
            });

        });
    };

    $document.bind('keypress', function (event) {
        if (!$scope.isFetched) {
            return;
        }
        if (event.keyCode === 37 && $scope.score.value > -$scope.maxScore) {
            $scope.$apply($scope.score.value = $scope.score.value - 1);
            $scope.isScoreChanged = true;
        } else if (event.keyCode === 39 && $scope.score.value < $scope.maxScore) {
            $scope.$apply($scope.score.value = $scope.score.value + 1);
            $scope.isScoreChanged = true;
        } else if (event.charCode === 32) {
            if ($scope.isFinished())  {
                $location.path('/poll/' + $routeParams.id);
            }
            var shouldChange = true;
            if (!$scope.isScoreChanged) {
                console.log("show confirmation dialog if not changed and go next")
            }
            if (shouldChange) {
                Score.save($scope.score, function () {
                    $scope.reload();
                });
            }

        }
    });

    $scope.isFinished = function () {
        return $scope.score.id == null;
    };

    $scope.reload();
});
