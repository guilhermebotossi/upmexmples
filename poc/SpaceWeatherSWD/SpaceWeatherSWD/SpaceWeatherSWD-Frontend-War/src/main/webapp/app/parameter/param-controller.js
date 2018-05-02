/* global moment */

app.controller('ParamController', ['ParamService', 'I18NService', '$timeout',
    function (paramService, i18NService, $timeout) {
        var self = this;

        var maxCalendar;
        var minMoment;
        var maxMoment;
        var carregando = false;

        paramService.getParametersRangeDate()
                .then(function (data) {
                    if (data.maxDate !== null) {
                        self.isDisable = false;
                        self.mensagem = i18NService.get('PARAM.RESUMO_MENSAGEM');
                        self.minDate = new Date(data.minDate.year, data.minDate.monthValue - 1, data.minDate.dayOfMonth);
                        self.maxDate = new Date(data.maxDate.year, data.maxDate.monthValue - 1, data.maxDate.dayOfMonth);
                        self.calendarMinDate = new Date(2017, 0, 1);
                        self.calendarMaxDate = self.maxDate;
                        self.carregando = true;
                        
                        paramService.getParametersChartOptions(self.minDate, self.maxDate)
                                .then(function (data) {
                                	$timeout(function(){
                                		self.carregando = false;
                                	}, 1000);
                                    self.parametersOptions = data;
                                })
                                .catch(function (message) {
                                    console.log(message);
                                });
                    } else {
                        self.isDisable = true;
                        self.mensagem = i18NService.get('PARAM.BD_VAZIO_MENSAGEM');
                        self.calendarMinDate = new Date(2017, 0, 1);
                        self.calendarMaxDate = new Date();
                        self.minDate = new Date();
                        self.maxDate = self.minDate;

                        paramService.getParametersChartOptions(null, null)
                                .then(function (data) {
                                    self.parametersOptions = data;
                                })
                                .catch(function (message) {
                                    console.log(message);
                                });
                    }

                    self.oldMinDate = self.minDate;
                    self.oldMaxDate = self.maxDate;

                })
                .catch(function (message) {
                    console.log(message);
                });

        self.dateChanged = function () {

            var DAYS = 30;
            minMoment = moment(self.minDate);
            maxMoment = moment(self.maxDate);

            if (moment(maxMoment).isAfter(maxCalendar)) {
                self.maxDate = moment(maxCalendar).toDate();
            }

            if ((moment(maxMoment).diff(minMoment, 'days') > DAYS) && moment(maxMoment).isSame(self.oldMaxDate)) {
                self.minDate = moment(minMoment).toDate();
                self.maxDate = moment(minMoment).add(DAYS, 'days').toDate();
            }

            if ((moment(maxMoment).diff(minMoment, 'days') > DAYS) && moment(minMoment).isSame(self.oldMinDate)) {
                self.minDate = moment(maxMoment).subtract(DAYS, 'days').toDate();
                self.maxDate = moment(maxMoment).toDate();
            }

            if (moment(maxMoment).isBefore(minMoment) && moment(minMoment).isSame(self.oldMinDate)) {
                self.minDate = moment(maxMoment).toDate();
                self.maxDate = moment(maxMoment).toDate();
            }

            if (moment(minMoment).isAfter(maxMoment) && moment(maxMoment).isSame(self.oldMaxDate)) {
                self.minDate = moment(minMoment).toDate();
                self.maxDate = moment(minMoment).toDate();
            }

            minMoment = moment(self.minDate);
            maxMoment = moment(self.maxDate);
            self.oldMinDate = moment(minMoment).toDate();
            self.oldMaxDate = moment(maxMoment).toDate();
            self.carregando = true;
            
            paramService.getParametersChartOptions(self.minDate, self.maxDate)
                    .then(function (data) {
                    	$timeout(function(){
                    		self.carregando = false;
                    	}, 1000);
                        self.parametersOptions = data;
                    })
                    .catch(function (message) {
                        console.log(message);
                    });

        };

    }
]);