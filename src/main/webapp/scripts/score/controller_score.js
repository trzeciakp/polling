'use strict';

pollingApp.controller('ScoreController', function ($scope,$filter, $routeParams, $location, $document, $modal, PollFirstScore, Account, Score) {

    $scope.isFetched = false;
    $scope.maxScore = null;
    $scope.isModalOpened = false;

    $scope.reload = function () {
        $scope.isFetched = false;
        $scope.maxScore = null;

        Account.get().$promise.then(function (account) {
            $scope.score = PollFirstScore.get({pollid: $routeParams.id, user: account.login});
            $scope.score.$promise.then(function (score) {

                $scope.isFetched = ($scope.score != null);
                if ($scope.isFetched) {
                    $scope.score.value = 0;
                    $scope.maxScore = $scope.score.productA.poll.maxScore;
                    $scope.isScoreChanged = false;
                }
            });

        });
    };

    $scope.completeScore = function () {
        Score.save($scope.score, function () {
            $scope.reload();
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
            if (!$scope.isScoreChanged && $scope.score.value == 0) {

                if (!$scope.isModalOpened) {
                    $scope.isModalOpened = true;
                    $modal.open({
                        templateUrl: 'myModalContent.html'
                    }).result.then(function () {
                            $scope.completeScore();
                            $scope.isModalOpened = false;
                        }, function() {
                            $scope.isModalOpened = false;
                        });
                }
            } else {
                $scope.completeScore();
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

    var scoreValueToScorePrintValue = function(value) {
        if (value > 0) {
            return value;
        } else if (value < 0) {
            return $filter('number')(-1/value,2);
        }
        return 1;
    };

    $scope.getProductAScore = function() {
        return scoreValueToScorePrintValue($scope.score.value);
    };

    $scope.getProductBScore = function() {
        return scoreValueToScorePrintValue(-$scope.score.value);
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
