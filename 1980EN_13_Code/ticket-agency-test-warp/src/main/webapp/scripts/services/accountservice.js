'use strict';

angular.module('ticketApp').service('AccountService',
    function AccountService($resource) {
        return $resource('rest/account', {}, {
            query: {
                method: 'GET',
                isArray: false
            },
            reset: {
                method: 'POST'
            }
        });
    });
