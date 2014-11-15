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
                $scope.maxScore = $scope.score.productA.poll.maxScore;
                $scope.isScoreChanged = false;
            });

        });
    };

    $document.bind('keypress', function (event) {
        if (!$scope.isFetched) {
            return;
        }
        if (event.keyCode === 37 ) {
            $scope.productBUp();
        } else if (event.keyCode === 39) {
            $scope.productAUp();
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

    $scope.productAUp = function() {
        if ($scope.score.value < $scope.maxScore) {
            $scope.$apply($scope.score.value = $scope.score.value + 1);
            $scope.isScoreChanged = true;
        }
    };
    $scope.productBUp = function() {
        if ($scope.score.value > -$scope.maxScore) {
            $scope.$apply($scope.score.value = $scope.score.value - 1);
            $scope.isScoreChanged = true;
        }
    };

    $scope.getProductAPercentValue = function() {
        return ($scope.maxScore + $scope.score.value)*100/(2*$scope.maxScore);
    };

    $scope.getProductBPercentValue = function() {
        return ($scope.maxScore - $scope.score.value)*100/(2*$scope.maxScore);
    };

    $scope.isFinished = function () {
        return $scope.score.id == null;
    };


    $scope.onProgressBarClicked = function (event) {
        var progressWidth = event.currentTarget.clientWidth;
        var clickedX = event.originalEvent.layerX;
        var computedValue = Math.round($scope.maxScore * (clickedX*2/progressWidth - 1));
        $scope.score.value = computedValue;
    };
    $scope.reload();
});
