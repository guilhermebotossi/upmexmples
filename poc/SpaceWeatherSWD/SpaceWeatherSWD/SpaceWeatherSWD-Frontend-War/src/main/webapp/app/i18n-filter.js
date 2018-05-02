app.filter('i18n', ['I18NService',
    function (i18NService) {
        var filter = function (input) {
            return i18NService.get(input);
        };

        filter.$stateful = true;

        return filter;
    }
]);