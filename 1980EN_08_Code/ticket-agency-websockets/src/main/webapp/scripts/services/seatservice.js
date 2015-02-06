'use strict';

angular.module('ticketApp').service('SeatService',
    function SeatService($resource) {
        return $resource('rest/seat/:seatId', {
            seatId: '@id'
        }, {
            query: {
                method: 'GET',
                isArray: true
            },
            book: {
                method: 'POST'
            }
        });
    });
