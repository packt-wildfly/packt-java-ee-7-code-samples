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
                        + httpResponse.config.data.id + " "
                        + ((typeof httpResponse.data.entity != 'undefined' ) ? httpResponse.data.entity : "")
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

        var ws = new WebSocket("ws://localhost:8080/ticket-agency-websockets/tickets");
        ws.onmessage = function (message) {
            var receivedData = message.data;
            var bookedSeat = JSON.parse(receivedData);

            $scope.$apply(function () {
                for (var i = 0; i < $scope.seats.length; i++) {
                    if ($scope.seats[i].id === bookedSeat.id) {
                        $scope.seats[i].booked = bookedSeat.booked;
                        break;
                    }
                }
            });
        };
        ws.onopen = function (event) {
            $scope.$apply(function () {
                $scope.alerts.push({
                    type: 'info',
                    msg: 'Push connection from server is working'
                });
            });
        };
        ws.onclose = function (event) {
            $scope.$apply(function () {
                $scope.alerts.push({
                    type: 'warning',
                    msg: 'Error on push connection from server '
                });
            });
        };
    });