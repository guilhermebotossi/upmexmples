app.controller('AppController', ['$scope', 'LanguageService', 'I18NService', '$mdDateLocale',
    function ($scope, languageService, i18NService, $mdDateLocale) {
        var self = this;
        var months, shortMonths, weekdays, decimal, thousands;

        self.isLanguageSelected = languageService.isSelected;
        self.selectLanguage = languageService.select;


        $scope.$on('$routeChangeSuccess', function () {
            languageService.updateSelected();

            self.language = languageService.getRoute();

            if (languageService.getRoute() === 'en') {
                months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
                shortMonths = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
                weekdays = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thrusday", "Friday", "Saturday"];
                shortWeekdays = ["Sun", "Mon", "Tue", "Wed", "Thr", "Fri", "Sat"];
                decimal = '.';
                thousands = ',';
            } else {
                months = ["Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"];
                shortMonths = ["Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"];
                weekdays = ["Domingo", "Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado"];
                shortWeekdays = ["Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb"];
                decimal = ',';
                thousands = '.';
            }

            $mdDateLocale.formatDate = function (date) {
                return moment(date).format(i18NService.get('APP.ANGULAR_FORMATDATE'));
            };

            $mdDateLocale.months = months;
            $mdDateLocale.shortMonths = shortMonths;
            $mdDateLocale.days = weekdays;
            $mdDateLocale.shortDays = shortWeekdays;

            Highcharts.setOptions({
                lang: {
                    months: months,
                    shortMonths: shortMonths,
                    weekdays: weekdays,
                    decimalPoint: decimal,
                    thousandsSep: thousands
                }
            });

        });
    }
]);