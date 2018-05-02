/* global Highcharts */
/* global app */

(function (Highcharts) {
    var each = Highcharts.each;

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

app.service('InicioService', ['$q', 'SWDService', 'HighChartsOptionsService', 'I18NService',
    function ($q, swdService, highChartsOptionsService, i18NService) {
        var parseDate = function (ano, mes, dia, hour) {
            var data = new Date(Date.UTC(ano, mes - 1, dia, hour, 0, 0, 0));
            return data;
        };

        var formatSubtitle = function (value) {
            return Highcharts.dateFormat('%e %b, %Y', value);
        };

        var color = function (value) {
            if (value < 1) {
                return '#f2f2f2';
            }
            ;
            if (value >= 1 && value < 2) {
                return '#1E90FF';
            }
            ;
            if (value >= 2 && value < 3) {
                return '#32CD32';
            }
            ;
            if (value >= 3 && value < 4) {
                return 'gold';
            }
            ;
            if (value >= 4 && value < 5) {
                return 'orange';
            }
            ;
            if (value >= 5) {
                return '#DC143C';
            }
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

        var service = {

            _fillIndexesChartData: function (swdSet, swdSetProcess, tickInterval) {
                var dataC = [], dataB = [], dataZ = [], dataV = [];
                var previouscolor_b = 0, previouscolor_z = 0, previouscolor_v = 0, previouscolor_c = 0;

                angular.forEach(swdSet.bEntries, function (item) {
                    dataB.push({x: parseDate(item.timeTag.year, item.timeTag.monthValue, item.timeTag.dayOfMonth, item.timeTag.hour), y: item.postValue, marker: {states: {hover: {fillColor: color(item.postValue)}}}, value: item.preValue});
                });
                angular.forEach(swdSet.cEntries, function (item) {
                    dataC.push({x: parseDate(item.timeTag.year, item.timeTag.monthValue, item.timeTag.dayOfMonth, item.timeTag.hour), y: item.postValue, marker: {states: {hover: {fillColor: color(item.postValue)}}}, value: item.preValue});
                });
                angular.forEach(swdSet.zEntries, function (item) {
                    dataZ.push({x: parseDate(item.timeTag.year, item.timeTag.monthValue, item.timeTag.dayOfMonth, item.timeTag.hour), y: item.postValue, marker: {states: {hover: {fillColor: color(item.postValue)}}}, value: item.preValue});
                });
                angular.forEach(swdSet.vEntries, function (item) {
                    dataV.push({x: parseDate(item.timeTag.year, item.timeTag.monthValue, item.timeTag.dayOfMonth, item.timeTag.hour), y: item.postValue, marker: {states: {hover: {fillColor: color(item.postValue)}}}, value: item.preValue});
                });

                swdSetProcess.data = [{
                        name: 'I.C',
                        color: 'rgb(102,0,204)',
                        data: dataC,
                        zIndex: 1,
                        cursor: 'pointer',
                        pointInterval: tickInterval,
                        events: {
                            click: function () {
                                if (previouscolor_c === 0) {
                                    this.update({
                                        color: 'gray',
                                        zIndex: 0,
                                        marker: {lineColor: 'gray'}
                                    });
                                    previouscolor_c = 1;
                                } else {
                                    this.update({
                                        color: 'black',
                                        zIndex: 1,
                                        marker: {lineColor: 'black'}
                                    });
                                    previouscolor_c = 0;
                                }
                            }
                        },
                        marker: {symbol: 'triangle', radius: 4, lineColor: 'black',
                            states: {
                                hover: {
                                    lineWidth: 2,
                                    radiusPlus: 2
                                }
                            }
                        },
                        states: {
                            hover: {
                                enabled: true,
                                lineWidth: 5,
                                halo: 0
                            }
                        },
                        tooltip: {
                            pointFormat:
                                    '<tr><td style="text-align: left">[{point.x:' + i18NService.get('APP.TOOLTIP_FORMATDATE') + '}] <b>{series.name}: {point.y:.1f} </b></td>' +
                                    '<td style="text-align: right">({point.value:.2f} Re)</td>'
                        }
                    }, {
                        name: 'I.B',
                        color: 'black',
                        data: dataB,
                        zIndex: 1,
                        cursor: 'pointer',
                        pointInterval: tickInterval,
                        events: {
                            click: function () {
                                if (previouscolor_b === 0) {
                                    this.update({
                                        color: 'gray',
                                        zIndex: 0,
                                        marker: {lineColor: 'gray'}
                                    });
                                    previouscolor_b = 1;
                                } else {
                                    this.update({
                                        color: 'black',
                                        zIndex: 1,
                                        marker: {lineColor: 'black'}
                                    });
                                    previouscolor_b = 0;
                                }
                            }
                        },
                        marker: {symbol: 'square', radius: 4, lineColor: 'black',
                            states: {
                                hover: {
                                    lineWidth: 2,
                                    radiusPlus: 2
                                }
                            }
                        },
                        states: {
                            hover: {
                                enabled: true,
                                lineWidth: 5,
                                halo: 0
                            }
                        },
                        tooltip: {
                            pointFormat:
                                    '<tr><td style="text-align: left">[{point.x:' + i18NService.get('APP.TOOLTIP_FORMATDATE') + '}] <b>{series.name}: {point.y:.1f} </b></td>' +
                                    '<td style="text-align: right">({point.value:.2f} nT)</td>'
                        }
                    }, {
                        name: 'I.Z',
                        color: 'rgb(255,102,0)',
                        data: dataZ,
                        zIndex: 1,
                        cursor: 'pointer',
                        pointInterval: tickInterval,
                        events: {
                            click: function () {
                                if (previouscolor_z === 0) {
                                    this.update({
                                        color: 'gray',
                                        zIndex: 0,
                                        marker: {lineColor: 'gray'}
                                    });
                                    previouscolor_z = 1;
                                } else {
                                    this.update({
                                        color: 'black', zIndex: 1,
                                        marker: {lineColor: 'black'}
                                    });
                                    previouscolor_z = 0;
                                }
                            }
                        },
                        marker: {symbol: 'circle', radius: 4, lineColor: 'black',
                            states: {
                                hover: {
                                    lineWidth: 2,
                                    radiusPlus: 2
                                }
                            }
                        },
                        states: {
                            hover: {
                                enabled: true,
                                lineWidth: 5,
                                halo: 0
                            }
                        },
                        tooltip: {
                            pointFormat:
                                    '<tr><td style="text-align: left">[{point.x:' + i18NService.get('APP.TOOLTIP_FORMATDATE') + '}] <b>{series.name}: {point.y:.1f} </b></td>' +
                                    '<td style="text-align: right">({point.value:.2f} nT)</td>'
                        }

                    }, {
                        name: 'I.V',
                        color: 'rgb(204,0,0)',
                        data: dataV,
                        zIndex: 1,
                        cursor: 'pointer',
                        pointInterval: tickInterval,
                        events: {
                            click: function () {
                                if (previouscolor_v === 0) {
                                    this.update({
                                        color: 'gray',
                                        zIndex: 0,
                                        marker: {lineColor: 'gray'}
                                    });
                                    previouscolor_v = 1;
                                } else {
                                    this.update({
                                        color: 'black', zIndex: 1,
                                        marker: {lineColor: 'black'}
                                    });
                                    previouscolor_v = 0;
                                }
                            }
                        },
                        marker: {symbol: 'diamond', radius: 6, lineColor: 'black',
                            states: {
                                hover: {
                                    lineWidth: 2,
                                    radiusPlus: 2
                                }
                            }
                        },
                        states: {
                            hover: {
                                enabled: true,
                                lineWidth: 5,
                                halo: 0
                            }
                        },
                        tooltip: {
                            pointFormat:
                                    '<tr><td style="text-align: left">[{point.x:' + i18NService.get('APP.TOOLTIP_FORMATDATE') + '}] <b>{series.name}: {point.y:.1f} </b></td>' +
                                    '<td style="text-align: right">({point.value:.2f} km/s)</td>'
                        }
                    }];

            },

            _createIndexesChartOptions: function (swdSet, minDate, maxDate, type, isAnEmptyData) {
                swdSetProcess = {data: []};
                var tickInterval;
                var subtitle;

                if (minDate !== null && !isAnEmptyData) {
                    if (type === 0) {
                        tickInterval = 3600 * 1000 * 24;
                        subtitle = i18NService.get('INICIO.SUBTITULO_RESUMO_DAY') + ' - ' + i18NService.get('INICIO.SUBTITULO_RESUMO_DAY_COMPLEMENT') + formatSubtitle(minDate) + i18NService.get('APP.E') + formatSubtitle(maxDate);
                    } else if (type === 1) {
                        subtitle = i18NService.get('INICIO.SUBTITULO_RESUMO_HOUR') + ' - ' + i18NService.get('INICIO.SUBTITULO_RESUMO_DAY_COMPLEMENT') + formatSubtitle(minDate) + i18NService.get('APP.E') + formatSubtitle(maxDate);
                        tickInterval = 3600 * 1000;
                    }
                    service._fillIndexesChartData(swdSet, swdSetProcess, tickInterval);
                } else {
                    subtitle = i18NService.get('INICIO.SUBTITULO_RESUMO');
                }

                var optionsIndexConfig = {
                    creditsText: i18NService.get('APP.DIREITOS'),
                    noData: i18NService.get('APP.NO_DATA'),
                    realtime: i18NService.get('APP.REALTIME'),
                    format: i18NService.get('APP.FORMATDATE'),
                    all: i18NService.get('APP.ALL'),
                    title: i18NService.get('INICIO.TITULO_RESUMO'),
                    subtitle: subtitle,
                    yAxisTitle: i18NService.get('INICIO.VALORES'),
                    series: swdSetProcess.data,
                    xAxis: {
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
                        gridLineDashStyle: 'dot',
                        tickInterval: tickInterval,
                        labels: {
                            style: {
                                fontSize: "14px"
                            }
                        }
                    },
                    yAxis: {
                        title: {
                            text: i18NService.get('INICIO.VALORES')
                        },
                        min: 0,
                        max: 6,
                        showLastLabel: false,
                        crosshair: false,
                        tickInterval: 1,
                        gridLineColor: 'gray',
                        gridLineWidth: 1,
                        gridZIndex: 1,
                        gridLineDashStyle: 'dot',
                        labels: {
                            style: {
                                fontSize: "14px"
                            },
                            x: -10
                        },
                        plotLines: [{
                                color: 'rgb(156,156,156)',
                                dashStyle: 'shortdash',
                                width: 2,
                                value: 5,
                                zIndex: 3
                            }, {
                                color: 'rgb(156,156,156)',
                                dashStyle: 'shortdash',
                                width: 2,
                                value: 4,
                                zIndex: 3
                            }, {
                                color: 'rgb(156,156,156)',
                                dashStyle: 'shortdash',
                                width: 2,
                                value: 3,
                                zIndex: 3
                            }, {
                                color: 'rgb(156,156,156)',
                                dashStyle: 'shortdash',
                                width: 2,
                                value: 2,
                                zIndex: 3
                            }, {
                                color: 'rgb(156,156,156)',
                                dashStyle: 'shortdash',
                                width: 2,
                                value: 1,
                                zIndex: 3
                            }]
                    }
                };

                var options = highChartsOptionsService.getSummaryIndexOptions(optionsIndexConfig);
                return options;
            },

            getIndexesChartOptions: function (minDate, maxDate, type) {
                var deferred = $q.defer();

                if (minDate === null || maxDate === null) {
                    var data = {};
                    deferred.resolve(service._createIndexesChartOptions(data, minDate, maxDate, type, true));
                } else {
                    swdService.getMaxSummaryData(minDate, maxDate)
                            .then(function (data) {
                                var isEmpty = isArrayEmpty(data);
                                if (isEmpty) {
                                    data = {};
                                }
                                deferred.resolve(service._createIndexesChartOptions(data, minDate, maxDate, type, isEmpty));
                            })
                            .catch(function (message) {
                                deferred.reject(message);
                            });
                }

                return deferred.promise;
            },

            getIndexesRangeDate: function () {
                var deferred = $q.defer();
                swdService.getIndexesRangeDate()
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
