app.factory('LanguageService', ['$location',
    function ($location) {
        var EN = 'en',
                PT = 'pt',
                EN_US = 'EN_US',
                PT_BR = 'PT_BR';

        var selected = 'EN_US';

        var service = {
            $stateful: true,
            get: function () {
                return selected;
            },
            getRoute: function () {
                var route;

                switch (selected) {
                    case EN_US:
                        route = EN;
                        break;
                    case PT_BR:
                        route = PT;
                        break;
                    default:
                        throw 'Idioma inválido.';
                }

                return route;
            },
            getLanguage: function (route) {
                var language;

                switch (route) {
                    case EN:
                        language = EN_US;
                        break;
                    case PT:
                        language = PT_BR;
                        break;
                    default:
                        throw 'Rota inválida.';
                }

                return language;
            },
            isSelected: function (language) {
                return selected === language;
            },
            select: function (language) {
                selected = language;
                $location.path("/" + service.getRoute() + "/" + service._getPageFromPath());
            },
            updateSelected: function () {
                service.select(service.getLanguage(service._getRouteFromPath()));
            },
            _getRouteFromPath: function () {
                var tokens = service._getTokens();
                var route = tokens[1];
                return route;
            },
            _getPageFromPath: function () {
                var tokens = service._getTokens();
                var page = tokens[tokens.length - 1];
                return page;
            },
            _getTokens: function () {
                var path = $location.path();
                var tokens = path.split('/');
                return tokens;
            }
        };

        return service;
    }
]);