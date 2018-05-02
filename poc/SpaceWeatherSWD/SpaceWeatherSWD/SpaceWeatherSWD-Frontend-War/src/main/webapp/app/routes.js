app.config(function ($locationProvider, $routeProvider) {
    $locationProvider.html5Mode(true);
    $routeProvider
            .when('/:language/inicio', {
                controller: 'InicioController',
                controllerAs: 'inicioCtrl',
                templateUrl: 'app/inicio/inicio.html'
            })
            .when('/:language/sobre', {
                controller: 'SobreController',
                controllerAs: 'sobreCtrl',
                templateUrl: 'app/sobre/sobre.html'
            })
            .when('/:language/hello', {
                controller: 'HelloController',
                controllerAs: 'helloCtrl',
                templateUrl: 'app/hello/hello.html'
            })
            .when('/:language/param', {
                controller: 'ParamController',
                controllerAs: 'paramCtrl',
                templateUrl: 'app/parameter/param.html'
            })
            .when('/:language/forecast', {
                controller: 'KpController',
                controllerAs: 'kpCtrl',
                templateUrl: 'app/kp/kp.html'
            })
            .otherwise({redirectTo: '/en/inicio'});
});