/* global moment */

app.controller('KpController', ['KpService', 'I18NService',
    function (kpService) {
        var self = this;

        kpService.getKpChartOptions()
                .then(function (data) {
                    self.kpIndexOptions = data;
                })
                .catch(function (message) {
                    console.log(message);
                });

    }
]);