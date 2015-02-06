'use strict';

angular.module('ticketApp', [ 'ngResource', 'ngRoute', 'ui.bootstrap' ])
    .config(function ($routeProvider) {
        $routeProvider.when('/', {
            controller: 'SeatCtrl'
        }).otherwise({
            redirectTo: '/'
        });
    });
