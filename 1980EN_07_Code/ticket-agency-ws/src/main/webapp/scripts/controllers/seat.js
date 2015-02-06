'use strict';

angular.module('ticketApp').controller(
    'SeatCtrl',
    function ($scope, SeatService, AccountService) {
        $scope.seats = SeatService.query();
        $scope.account = AccountService.query();

        $scope.alerts = [];

        $scope.bookTicket = function (seat) {
            seat.$book({}, function success() {
                $scope.account.$query();
            }, function err(httpResponse) {

                $scope.alerts.push({

                    type: 'danger',
                    msg: 'Error booking ticket for seat '
                        + httpResponse.config.data.id + ': '
                        + httpResponse.data.entity
                });
            });
        };

        $scope.closeAlert = function (index) {
            $scope.alerts.splice(index, 1);
        };

        $scope.clearWarnings = function () {
            $scope.alerts.length = 0;
        };

        $scope.resetAccount = function () {
            $scope.account.$reset();
        };
    });