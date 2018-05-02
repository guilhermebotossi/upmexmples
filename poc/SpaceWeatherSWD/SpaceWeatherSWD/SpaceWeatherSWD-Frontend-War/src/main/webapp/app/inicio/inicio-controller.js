/* global moment */

app.controller('InicioController', ['InicioService', 'I18NService', '$timeout',
    function (inicioService, i18NService, $timeout) {
        var self = this;
        
        var maxCalendar;
        var minMoment;
        var maxMoment;
        var carregando = false;
        
        inicioService.getIndexesRangeDate()
                .then(function (data) {
                    if (data.maxDate !== null) {
                        self.isDisable = false;
                        self.mensagem = i18NService.get('INICIO.RESUMO_MENSAGEM');
                        self.minDate = new Date(data.minDate.year, data.minDate.monthValue - 1, data.minDate.dayOfMonth);
                        self.maxDate = new Date(data.maxDate.year, data.maxDate.monthValue - 1, data.maxDate.dayOfMonth);
                        self.calendarMinDate = new Date(2017, 0, 1);
                        self.calendarMaxDate = self.maxDate;
                        
                        self.carregando = true;
                        inicioService.getIndexesChartOptions(self.minDate, self.maxDate, 0)
                                .then(function (data) {
                                	$timeout(function(){
                                		self.carregando = false;
                                	}, 1000);
                                    self.chartOptions = data;
                                })
                                .catch(function (message) {
                                    console.log(message);
                                });
                    } else {
                        self.isDisable = true;
                        self.mensagem = i18NService.get('APP.BD_VAZIO_MENSAGEM');
                        self.calendarMinDate = new Date(2017, 0, 1);
                        self.calendarMaxDate = new Date();
                        self.minDate = self.calendarMinDate;
                        self.maxDate = self.calendarMaxDate;

                        inicioService.getIndexesChartOptions(null, null, 0)
                                .then(function (data) {
                                    self.chartOptions = data;
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
            var DAYS_LIMIT_HOURS = 2;
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

            if ((moment(maxMoment).diff(minMoment, 'days') <= DAYS_LIMIT_HOURS)) {
            	self.carregando = true;
                inicioService.getIndexesChartOptions(self.minDate, self.maxDate, 1)
                        .then(function (data) {
                        	$timeout(function(){
                        		self.carregando = false;	
                        	}, 1000);
                            self.chartOptions = data;
                        })
                        .catch(function (message) {
                            console.log(message);
                        });
            } else {
            	self.carregando = true;
                inicioService.getIndexesChartOptions(self.minDate, self.maxDate, 0)
                        .then(function (data) {
                        	$timeout(function(){
                        		self.carregando = false;	
                        	}, 1000);
                            self.chartOptions = data;
                        })
                        .catch(function (message) {
                            console.log(message);
                        });
            }

        };

    }
]);

