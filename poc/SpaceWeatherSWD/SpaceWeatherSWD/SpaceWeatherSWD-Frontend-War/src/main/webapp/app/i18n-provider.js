app.provider('I18NService', function () {

    var merged = {};

    this.add = function (keys) {
        angular.merge(merged, keys);
    };

    this.$get = ['LanguageService', function (languageService) {
            return {
                get: function (input) {
                    var language = languageService.get();
                    var keys = input.split('.');
                    var page = keys[0];
                    var key = keys[1];

                    return merged[language][page][key];
                }
            };
        }];
});
