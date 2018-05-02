/* global Highcharts */
/* global app */

(function (Highcharts) {
    var each = Highcharts.each;

    Highcharts.seriesTypes.columnrange.prototype.drawDataLabels = Highcharts.seriesTypes.column.prototype.drawDataLabels;

    Highcharts.wrap(Highcharts.Legend.prototype, 'renderItem', function (proceed, item) {

        proceed.call(this, item);

        var series = this.chart.series, element = item.legendGroup.element;

        element.onmouseover = function () {
            each(series, function (seriesItem) {
                if (seriesItem !== item) {
                    each(['group', 'markerGroup'], function (group) {
                        seriesItem[group].attr('opacity', 0.25);
                    });
                }
            });
        };
        element.onmouseout = function () {
            each(series, function (seriesItem) {
                if (seriesItem !== item) {
                    each(['group', 'markerGroup'], function (group) {
                        seriesItem[group].attr('opacity', 1);
                    });
                }
            });
        };

    });
}(Highcharts));

app.service('KpService', ['$q', 'SWDService', 'HighChartsOptionsService', 'I18NService',
    function ($q, swdService, highChartsOptionsService, i18NService) {
        var parseDate = function (ano, mes, dia, hour, minuto) {
            var data = new Date(Date.UTC(ano, mes - 1, dia, hour, minuto, 0, 0));
            return data;
        };

        var isArrayEmpty = function (array) {
            var flag = true;
            angular.forEach(array, function (item) {
                if (item.length > 0) {
                    flag = false;
                }
            });
            return flag;
        };

        var color = function (value, type) {

            if (type === 0) {
                if (value < 5) {
                    return '#7F7F7F';
                }

                if (value >= 5 && value < 7) {
                    return '#7F7F7F';
                }

                if (value >= 7) {
                    return '#7F7F7F';
                }

            } else if (type === 1) {
                if (value < 5) {
                    return 'rgba(0, 245, 0, 0.3)';
                }

                if (value >= 5 && value < 7) {
                    return 'rgba(245, 245, 0, 0.3)';
                }

                if (value >= 7) {
                    return 'rgba(245, 0, 0, 0.3)';
                }

            }
        };

        var kpIndexName = function (value) {
            if (value < 1.0) {
                return '1-';
            }

            if (value === 1.0) {
                return '1o';
            }

            if (value > 1 && value < 1.5) {
                return '1+';
            }

            if (value >= 1.5 && value < 2.0) {
                return '2-';
            }

            if (value === 2.0) {
                return '2o';
            }

            if (value > 2.0 && value < 2.5) {
                return '2+';
            }

            if (value >= 2.5 && value < 3.0) {
                return '3-';
            }

            if (value === 3.0) {
                return '3o';
            }

            if (value > 3.0 && value < 3.5) {
                return '3+';
            }

            if (value >= 3.5 && value < 4.0) {
                return '4-';
            }

            if (value === 4.0) {
                return '4o';
            }

            if (value > 4.0 && value < 4.5) {
                return '4+';
            }

            if (value >= 4.5 && value < 5.0) {
                return '5-';
            }

            if (value === 5.0) {
                return '5o';
            }

            if (value > 5.0 && value < 5.5) {
                return '5+';
            }

            if (value >= 5.5 && value < 6.0) {
                return '6-';
            }

            if (value === 6.0) {
                return '6o';
            }

            if (value > 6.0) {
                return '6+';
            }

        };

        var service = {
            _constructSeries: function (swdSet, swdSetProcess) {
                var serieKp, serieKpF3, serieKpF6;
                var kp = [];
                var kpF3 = [];
                var kpF6 = [];
                var contKpF = 0;

                angular.forEach(swdSet.kpForecastEntries, function (item) {
                	var timeTag = parseDate(item.presentationTimeTag.year, item.presentationTimeTag.monthValue, item.presentationTimeTag.dayOfMonth, item.presentationTimeTag.hour, item.presentationTimeTag.minute);
                    if (item.forecast === false) {
                        kp.push({
                            x: timeTag,
                            y: item.value,
                            value: kpIndexName(item.value),
                            color: color(item.value, 0)
                        });
                    } else {
                        if (contKpF === 0) {
                            
                            kpF3.push({
                                x: timeTag,
                                low: 0,
                                high: 5,
                                name: '' + item.probability1,
                                inf: item.inferiorLimit1,
                                sup: item.upperLimit1
                            });

                            kpF3.push({
                                x: timeTag,
                                low: 5,
                                high: 7,
                                name: '' + item.probability2,
                                inf: item.inferiorLimit2,
                                sup: item.upperLimit2
                            });

                            kpF3.push({
                                x: timeTag,
                                low: 7,
                                high: 9,
                                name: '' + item.probability3,
                                inf: item.inferiorLimit3,
                                sup: item.upperLimit3
                            });
                            contKpF++;
                        } else {
                            kpF6.push({
                                x: timeTag,
                                low: 0,
                                high: 5,
                                name: '' + item.probability1,
                                inf: item.inferiorLimit1,
                                sup: item.upperLimit1
                            });

                            kpF6.push({
                                x: timeTag,
                                low: 5,
                                high: 7,
                                name: '' + item.probability2,
                                inf: item.inferiorLimit2,
                                sup: item.upperLimit2
                            });

                            kpF6.push({
                                x: timeTag,
                                low: 7,
                                high: 9,
                                name: '' + item.probability3,
                                inf: item.inferiorLimit3,
                                sup: item.upperLimit3
                            });
                        }


                    }
                });

                serieKp = {
                    type: 'column',
                    data: kp,
                    tooltip: {
                        headerFormat: '<span style="font-size: 11px">{point.x:%A %d %b, %Y} </span><br>',
                        pointFormat:
                                '<tr><td style="text-align: left"><b>' + i18NService.get('KP.TITULO') + ': {point.y:.1f} (<span style="color:{point.color}">{point.value}</span>)</b></td></tr>'
                    }
                };

                serieKpF3 = {
                    type: 'columnrange',
                    data: kpF3,
                    tooltip: {
                        headerFormat: '<span style="text-align: left; font-size: 11px; font-weight: bold">' + i18NService.get('KP.TOOLTIP_HEADER') + '<span style="color: red; font-size: 14px"> 3h</span></span><br>',
                        pointFormat:
                                '<span style="text-align: left">' + i18NService.get('KP.TOOLTIP_PREVISAO_1') + ' <span style="color: darkblue; font-size: 16px; font-weight: bold">{point.inf}</span> ' + i18NService.get('KP.TOOLTIP_PREVISAO_2') + ' <span style="color: darkblue; font-size: 16px; font-weight: bold">{point.sup}</span>' + i18NService.get('KP.TOOLTIP_PREVISAO_3') + '</span>'
                    }
                };

                serieKpF6 = {
                    type: 'columnrange',
                    data: kpF6,
                    tooltip: {
                        headerFormat: '<span style="text-align: left; font-size: 11px; font-weight: bold">' + i18NService.get('KP.TOOLTIP_HEADER') + '<span style="color: red; font-size: 14px"> 6h</span></span><br>',
                        pointFormat:
                                '<span style="text-align: left">' + i18NService.get('KP.TOOLTIP_PREVISAO_1') + ' <span style="color: darkblue; font-size: 16px; font-weight: bold">{point.inf}</span> ' + i18NService.get('KP.TOOLTIP_PREVISAO_2') + ' <span style="color: darkblue; font-size: 16px; font-weight: bold">{point.sup}</span>' + i18NService.get('KP.TOOLTIP_PREVISAO_3') + '</span>'
                    }
                };

                swdSetProcess.series = [serieKp, serieKpF3, serieKpF6];

            },

            _createKpChartOptions: function (swdSet, isDataEmpty) {
                swdSetProcess = {series: []};
                varDate = new Date();
                var currentDate = new Date(Date.UTC(varDate.getUTCFullYear(), varDate.getUTCMonth(), varDate.getUTCDate(), varDate.getUTCHours(), varDate.getUTCMinutes(), varDate.getUTCSeconds()));

                if (!isDataEmpty) {
                    service._constructSeries(swdSet, swdSetProcess);
                }

                var optionsKpConfig = {
                    noData: i18NService.get('APP.NO_DATA'),
                    realtime: i18NService.get('APP.REALTIME'),
                    all: i18NService.get('APP.ALL'),
                    title: i18NService.get('KP.TITULO'),
                    subtitle: i18NService.get('KP.SUBTITULO'),
                    yAxisTitle: i18NService.get('KP.TITULO'),
                    valueSuffix: i18NService.get('KP.TITULO'),
                    series: swdSetProcess.series,
                    xAxis: [{
                            type: 'datetime',
                            title: {
                                text: i18NService.get('APP.UTC')
                            },
                            crosshair: false,
                            lineColor: 'gray',
                            tickColor: 'gray',
                            gridLineColor: 'gray',
                            gridLineWidth: 1,
                            gridZIndex: 1,
                            gridLineDashStyle: 'dash',
                            minorTickWidth: 1,
                            minorTickLength: 5,
                            minorTickInterval: 'auto',
                            minorTickColor: 'black',
                            minorGridLineWidth: 0,
                            showEmpty: true,
                            labels: {
                                style: {
                                    fontSize: "14px"
                                },
                                x: -10
                            },
                            plotLines: [{
                                    color: '#0066FF',
                                    width: 1,
                                    value: currentDate,
                                    zIndex: 5,
                                    label: {
                                        text: i18NService.get('APP.REALTIME'),
                                        verticalAlign: 'top',
                                        style: {
                                            color: 'gray',
                                            fontSize: 9
                                        }
                                    }
                                }]
                        }],
                    yAxis: [{
                            title: {
                                text: i18NService.get('KP.Y_AXIS_LABEL')
                            },
                            min: 0,
                            max: 10,
                            tickInterval: 1,
                            endOnTick: true,
                            type: 'linear',
                            gridLineColor: 'gray',
                            gridLineWidth: 1,
                            gridLineDashStyle: 'dot',
                            gridZIndex: 1,
                            plotLines: [{
                                    color: '#006600',
                                    dashStyle: 'shortdash',
                                    width: 2,
                                    value: 5,
                                    zIndex: 4
                                }, {
                                    color: '#DAB000',
                                    dashStyle: 'shortdash',
                                    width: 2,
                                    value: 7,
                                    zIndex: 4
                                }, {
                                    color: '#A50021',
                                    dashStyle: 'shortdash',
                                    width: 2,
                                    value: 9,
                                    zIndex: 4
                                }]
                        }]
                };

                var options = highChartsOptionsService.getKpOptions(optionsKpConfig);
                return options;
            },

            getKpChartOptions: function () {
                var deferred = $q.defer();
                swdService.getKpForecastData()
                        .then(function (data) {
                            var isEmpty = isArrayEmpty(data);
                            if (isEmpty) {
                                data = {};
                            }
                            deferred.resolve(service._createKpChartOptions(data, isEmpty));
                        })
                        .catch(function (message) {
                            deferred.reject(message);
                        });

                return deferred.promise;
            },

            getKpRangeDate: function () {
                var deferred = $q.defer();
                swdService.getKpRangeDate()
                        .then(function (data) {
                            deferred.resolve(data);
                        })
                        .catch(function (message) {
                            deferred.reject(message);
                        });
                return deferred.promise;
            }

        };

        return service;
    }
]);
