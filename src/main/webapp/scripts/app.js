'use strict';

var killrChat = angular.module('KillrChat', ['ngRoute','ngResource','ngCookies', 'ui.bootstrap','angularSpinner'])
    .config(function ($routeProvider, $httpProvider) {
        $httpProvider.defaults.headers.common = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'X-Requested-With': 'XMLHttpRequest'};

        $routeProvider
            .when('/', {
                templateUrl: 'views/login.html'
            })
            .when('/chat', {
            	templateUrl: 'views/chat.html'
            })
            .otherwise({
                redirectTo: '/'
            });

});

killrChat.run(["$rootScope", "$templateCache", "RememberMeService", function ($rootScope,$templateCache, RememberMeService) {
    $templateCache.put("template/popover/popover.html",
        "<div class=\"popover {{placement}}\" ng-class=\"{ in: isOpen(), fade: animation() }\">\n" +
        "  <div class=\"arrow\"></div>\n" +
        "\n" +
        "  <div class=\"popover-inner\">\n" +
        "      <h3 class=\"popover-title\" ng-bind-html=\"title | unsafe\" ng-show=\"title\"></h3>\n" +
        "      <div class=\"popover-content text-center\"ng-bind-html=\"content | unsafe\"></div>\n" +
        "  </div>\n" +
        "</div>\n" +
        "");
    $rootScope.generalError = null;
    $rootScope.$on("$routeChangeStart", function(event, next) {
        delete $rootScope.generalError;
        RememberMeService.fetchAuthenticatedUser(next);
    });

    $rootScope.$on("$routeChangeError",function (event, current, previous, rejection) {
        GeneralErrorService.displayGeneralError(rejection);
    });
}]);