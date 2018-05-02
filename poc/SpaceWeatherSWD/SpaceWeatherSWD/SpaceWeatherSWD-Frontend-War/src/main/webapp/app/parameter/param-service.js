/* global Highcharts */
/* global app */

$(function () {

    window.syncExtremes = function (e) {
        var thisChart = this.chart;

        if (e.trigger !== 'syncExtremes') {
            Highcharts.each(Highcharts.charts, function (chart) {
                if (chart !== thisChart) {
                    if (chart.xAxis[0].setExtremes) {
                        chart.xAxis[0].setExtremes(e.min, e.max, undefined, false, {trigger: 'syncExtremes'});
                    }
                }
            });
        }
    };
});

app.service('ParamService', ['$q', 'SWDService', 'HighChartsOptionsService', 'I18NService',
    function ($q, swdService, highChartsOptionsService, i18NService) {
        var parseDate = function (ano, mes, dia, hour, minute) {
            var data = new Date(Date.UTC(ano, mes - 1, dia, hour, minute, 0, 0));
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

        var service = {
            _parametersSettings: function (settings) {
                settings.datasets = [{
                        name: "\u2329B<sub>t</sub>\u232A (nT)",
                        unit: "nT",
                        valueDecimals: 3,
                        color: 'rgba(128, 128, 128, 1.0)',
                        colorMean: 'rgba(0, 0, 0, 1.0)',
                        type: 'linear',
                        tickInterval: 'auto',
                        mainAxis: 8
                    },
                    {
                        name: "\u2329GSM B<sub>x</sub> B<sub>y</sub>\u232A (nT)",
                        unit: "nT",
                        valueDecimals: 2,
                        type: 'linear',
                        tickInterval: 'auto',
                        mainAxis: 0
                    },
                    {
                        name: "\u2329GSM B<sub>z</sub>\u232A (nT)",
                        unit: "nT",
                        valueDecimals: 2,
                        color: 'rgba(255, 204, 153, 1.0)',
                        colorMean: 'rgba(255, 102, 0, 1.0)',
                        type: 'linear',
                        tickInterval: 'auto',
                        mainAxis: 0
                    },
                    {
                        name: "\u2329GSM E<sub>y</sub>\u232A (mV/m)",
                        unit: "mV/m",
                        valueDecimals: 3,
                        color: 'rgba(113, 255, 113, 1.0)',
                        colorMean: 'rgba(0, 128, 0, 1.0)',
                        type: 'linear',
                        tickInterval: 'auto',
                        mainAxis: 0
                    },
                    {
                        name: "\u2329" + i18NService.get('PARAM.DENSIDADE') + "\u232A (p/cm<sup>3</sup>)",
                        unit: "p/cm<sup>3</sup>",
                        valueDecimals: 3,
                        color: 'rgba(255, 204, 153, 1.0)',
                        colorMean: 'rgba(153, 51, 0, 1.0)',
                        type: 'linear',
                        tickInterval: 'auto',
                        mainAxis: 4
                    },
                    {
                        name: "\u2329" + i18NService.get('PARAM.VELOCIDADE') + "\u232A (km/s)",
                        unit: "km/s",
                        valueDecimals: 3,
                        color: 'rgba(255, 153, 153, 1.0)',
                        colorMean: 'rgba(204, 0, 0, 1.0)',
                        type: 'linear',
                        tickInterval: 'auto',
                        mainAxis: 400
                    },
                    {
                        name: "\u2329" + i18NService.get('PARAM.TEMPERATURA') + "\u232A (\u00D7 10<sup>4</sup> K)",
                        unit: "\u00D7 10<sup>4</sup> K",
                        valueDecimals: 3,
                        color: 'rgba(171, 223, 255, 1.0)',
                        colorMean: 'rgba(0, 204, 255, 1.0)',
                        type: 'logarithmic',
                        tickInterval: '0.1',
                        mainAxis: 1
                    },
                    {
                        name: "\u2329" + i18NService.get('PARAM.DAYSIDE') + "\u232A (Re)",
                        legend: "\u2329" + i18NService.get('PARAM.DAYSIDE_SUB') + "\u232A (Re)",
                        unit: "Re",
                        valueDecimals: 3,
                        color: 'rgba(204, 153, 255, 1.0)',
                        colorMean: 'rgba(102, 0, 204, 1.0)',
                        type: 'linear',
                        tickInterval: 'auto',
                        mainAxis: 10
                    }
                ];
            },

            _constructTitles: function (parametersSettings, swdSetProcess) {
                title = [];

                angular.forEach(parametersSettings.datasets, function (item) {
                    title.push(item.name);
                });
                swdSetProcess.title = title;
            },

            _constructTooltips: function (parametersSettings, swdSetProcess) {
                tooltip = [];

                angular.forEach(parametersSettings.datasets, function (item) {
                    tooltip.push({
                        useHTML: true,
                        shared: true,
                        shadow: false,
                        borderWidth: 0,
                        backgroundColor: 'none',
                        headerFormat: '',
                        formatter: function () {
                            var s = '[' + Highcharts.dateFormat(i18NService.get('APP.TOOLTIP_FORMATDATE'), this.x) + '] ';

                            $.each(this.points, function () {
                                s += '<b><span style="color: ' + this.series.color + '">' + Highcharts.numberFormat(this.y, item.valueDecimals) + ' ' + item.unit + '</span></b> ';
                            });

                            return s;
                        },
                        style: {
                            fontSize: '18px'
                        },
                        positioner: function () {
                            return {
                                x: this.chart.chartWidth - this.label.width,
                                y: 5
                            };
                        }
                    });
                });
                swdSetProcess.tooltip = tooltip;
            },

            _constructSeries: function (swdSet, parametersSettings, swdSetProcess) {
                MAX_MIN_PERCENTAGE = 1.15;
                TEMPERATURE_SIMPLIFY = 10000;
                btMean = [], bxMean = [], byMean = [], bzMean = [], eyMean = [], densityMean = [], speedMean = [], tempMean = [], daysideMean = [];
                btDot = [], bxDot = [], byDot = [], bzDot = [], eyDot = [], densityDot = [], speedDot = [], tempDot = [], daysideDot = [];
                series = [], yAxis = [];
                cont = 0;

                angular.forEach(swdSet.btlist, function (item) {
                    btDot.push({x: parseDate(item.timeTag.year, item.timeTag.monthValue, item.timeTag.dayOfMonth, item.timeTag.hour, item.timeTag.minute), y: item.value});
                });
                angular.forEach(swdSet.bxlist, function (item) {
                    bxDot.push({x: parseDate(item.timeTag.year, item.timeTag.monthValue, item.timeTag.dayOfMonth, item.timeTag.hour, item.timeTag.minute), y: item.value});
                });
                angular.forEach(swdSet.bylist, function (item) {
                    byDot.push({x: parseDate(item.timeTag.year, item.timeTag.monthValue, item.timeTag.dayOfMonth, item.timeTag.hour, item.timeTag.minute), y: item.value});
                });
                angular.forEach(swdSet.bzlist, function (item) {
                    bzDot.push({x: parseDate(item.timeTag.year, item.timeTag.monthValue, item.timeTag.dayOfMonth, item.timeTag.hour, item.timeTag.minute), y: item.value});
                });
                angular.forEach(swdSet.eylist, function (item) {
                    eyDot.push({x: parseDate(item.timeTag.year, item.timeTag.monthValue, item.timeTag.dayOfMonth, item.timeTag.hour, item.timeTag.minute), y: item.value});
                });
                angular.forEach(swdSet.densityList, function (item) {
                    densityDot.push({x: parseDate(item.timeTag.year, item.timeTag.monthValue, item.timeTag.dayOfMonth, item.timeTag.hour, item.timeTag.minute), y: item.value});
                });
                angular.forEach(swdSet.speedList, function (item) {
                    speedDot.push({x: parseDate(item.timeTag.year, item.timeTag.monthValue, item.timeTag.dayOfMonth, item.timeTag.hour, item.timeTag.minute), y: item.value});
                });
                angular.forEach(swdSet.temperatureList, function (item) {
                    var value = null;

                    if (item.value !== null) {
                        value = item.value / TEMPERATURE_SIMPLIFY;
                    }

                    tempDot.push({x: parseDate(item.timeTag.year, item.timeTag.monthValue, item.timeTag.dayOfMonth, item.timeTag.hour, item.timeTag.minute), y: value});
                });
                angular.forEach(swdSet.rmplist, function (item) {
                    daysideDot.push({x: parseDate(item.timeTag.year, item.timeTag.monthValue, item.timeTag.dayOfMonth, item.timeTag.hour, item.timeTag.minute), y: item.value});
                });
                angular.forEach(swdSet.averageBTList, function (item) {
                    btMean.push({x: parseDate(item.timeTag.year, item.timeTag.monthValue, item.timeTag.dayOfMonth, item.timeTag.hour, item.timeTag.minute), y: item.value});
                });
                angular.forEach(swdSet.averageBXList, function (item) {
                    bxMean.push({x: parseDate(item.timeTag.year, item.timeTag.monthValue, item.timeTag.dayOfMonth, item.timeTag.hour, item.timeTag.minute), y: item.value});
                });
                angular.forEach(swdSet.averageBYList, function (item) {
                    byMean.push({x: parseDate(item.timeTag.year, item.timeTag.monthValue, item.timeTag.dayOfMonth, item.timeTag.hour, item.timeTag.minute), y: item.value});
                });
                angular.forEach(swdSet.averageBZList, function (item) {
                    bzMean.push({x: parseDate(item.timeTag.year, item.timeTag.monthValue, item.timeTag.dayOfMonth, item.timeTag.hour, item.timeTag.minute), y: item.value});
                });
                angular.forEach(swdSet.averageEYList, function (item) {
                    eyMean.push({x: parseDate(item.timeTag.year, item.timeTag.monthValue, item.timeTag.dayOfMonth, item.timeTag.hour, item.timeTag.minute), y: item.value});
                });
                angular.forEach(swdSet.averageDensityList, function (item) {
                    densityMean.push({x: parseDate(item.timeTag.year, item.timeTag.monthValue, item.timeTag.dayOfMonth, item.timeTag.hour, item.timeTag.minute), y: item.value});
                });
                angular.forEach(swdSet.averageSpeedList, function (item) {
                    speedMean.push({x: parseDate(item.timeTag.year, item.timeTag.monthValue, item.timeTag.dayOfMonth, item.timeTag.hour, item.timeTag.minute), y: item.value});
                });
                angular.forEach(swdSet.averageTemperatureList, function (item) {
                    var value = null;

                    if (item.value !== null) {
                        value = item.value / TEMPERATURE_SIMPLIFY;
                    }

                    tempMean.push({x: parseDate(item.timeTag.year, item.timeTag.monthValue, item.timeTag.dayOfMonth, item.timeTag.hour, item.timeTag.minute), y: value});
                });
                angular.forEach(swdSet.averageRMPList, function (item) {
                    daysideMean.push({x: parseDate(item.timeTag.year, item.timeTag.monthValue, item.timeTag.dayOfMonth, item.timeTag.hour, item.timeTag.minute), y: item.value});
                });

                var d = [btDot, btMean, bxDot, bxMean, byDot, byMean, bzDot, bzMean, eyDot, eyMean, densityDot, densityMean, speedDot, speedMean, tempDot, tempMean, daysideDot, daysideMean];
                var counter = 0;
                var mean, values, meanX, valuesX, meanY, valuesY;

                angular.forEach(parametersSettings.datasets, function (item) {

                    if (cont === 1) {
                        valuesX = {data: d[counter++], name: '\u2329GSM B<sub>x</sub>\u232A', turboThreshold: 8000, pointInterval: 3600 * 1000, color: 'rgba(255, 186, 255, 1.0)'};
                        meanX = {data: d[counter++], name: i18NService.get('PARAM.MEDIA') + ' ' + '\u2329GSM B<sub>x</sub>\u232A', pointInterval: 3600 * 1000, color: 'rgba(214, 0, 147, 1.0)', marker: {radius: 3, symbol: 'circle', color: 'black', lineColor: 'black'}};
                        valuesY = {data: d[counter++], name: '\u2329GSM B<sub>y</sub>\u232A', turboThreshold: 8000, pointInterval: 3600 * 1000, color: 'rgba(124, 124, 255, 1.0)'};
                        meanY = {data: d[counter++], name: i18NService.get('PARAM.MEDIA') + ' ' + '\u2329GSM B<sub>y</sub>\u232A', pointInterval: 3600 * 1000, color: 'rgba(0, 51, 204, 1.0)', marker: {radius: 3, symbol: 'circle', color: 'black', lineColor: 'black'}};
                        pair = [valuesX, valuesY, meanX, meanY];

                        yAxis.push({
                            title: {text: null},
                            type: item.type,
                            lineWidth: 1,
                            tickWidth: 1,
                            lineColor: 'gray',
                            tickColor: 'gray',
                            minorTickWidth: 1,
                            minorTickLength: 5,
                            minorTickInterval: item.tickInterval,
                            minorTickColor: 'black',
                            minorGridLineWidth: 0,
                            gridLineWidth: 1,
                            gridLineDashStyle: 'dot',
                            gridLineColor: 'gray',
                            gridZIndex: 1,
                            endOnTick: false,
                            labels: {
                                style: {
                                    fontSize: "12px"
                                }
                            },
                            plotLines: [{
                                color: 'gray',
                                dashStyle: 'shortdash',
                                width: 2,
                                value: item.mainAxis
                            }]
                        });
                    } else {
                        values = {data: d[counter++], name: item.name, turboThreshold: 8000, pointInterval: 3600 * 1000, color: item.color};
                        mean = {data: d[counter++], name: i18NService.get('PARAM.MEDIA'), pointInterval: 3600 * 1000, color: item.colorMean, marker: {radius: 3, symbol: 'circle', color: 'black', lineColor: 'black'}};
                        pair = [values, mean];

                        yAxis.push({
                            title: {text: null},
                            type: item.type,
                            lineWidth: 1,
                            tickWidth: 1,
                            lineColor: 'gray',
                            tickColor: 'gray',
                            minorTickWidth: 1,
                            minorTickLength: 5,
                            minorTickInterval: item.tickInterval,
                            minorTickColor: 'black',
                            minorGridLineWidth: 0,
                            gridLineWidth: 1,
                            gridLineDashStyle: 'dot',
                            gridLineColor: 'gray',
                            gridZIndex: 1,
                            endOnTick: false,
                            labels: {
                                style: {
                                    fontSize: "12px"
                                }
                            },
                            plotLines: [{
                                color: 'gray',
                                dashStyle: 'shortdash',
                                width: 2,
                                value: item.mainAxis
                            }]
                        });
                    }

                    series.push(pair);
                    cont++;
                });

                swdSetProcess.series = series;
                swdSetProcess.yAxis = yAxis;
            },

            _createParametersChartOptions: function (swdSet, isAnEmptyData) {
                swdSetProcess = {series: [], title: [], tooltip: [], yAxis: []};
                parametersSettings = {datasets: []};

                if (isAnEmptyData) {
                    service._parametersSettings(parametersSettings);
                    service._constructTitles(parametersSettings, swdSetProcess);
                    service._constructTooltips(parametersSettings, swdSetProcess);
                } else {
                    service._parametersSettings(parametersSettings);
                    service._constructSeries(swdSet, parametersSettings, swdSetProcess);
                    service._constructTitles(parametersSettings, swdSetProcess);
                    service._constructTooltips(parametersSettings, swdSetProcess);
                }

                var optionsParametersConfig = {
                    creditsText: i18NService.get('APP.DIREITOS'),
                    noData: i18NService.get('APP.NO_DATA'),
                    settings: parametersSettings.datasets,
                    title: swdSetProcess.title,
                    tooltip: swdSetProcess.tooltip,
                    series: swdSetProcess.series,
                    yAxis: swdSetProcess.yAxis,
                    xAxis: {
                        crosshair: {color: 'darkblue', dashStyle: 'line'},
                        events: {
                            setExtremes: syncExtremes
                        },
                        type: 'datetime',
                        lineColor: 'gray',
                        tickColor: 'gray',
                        minorTickWidth: 1,
                        minorTickLength: 5,
                        minorTickInterval: 'auto',
                        minorTickColor: 'black',
                        gridLineColor: 'gray',
                        gridLineWidth: 1,
                        gridLineDashStyle: 'dot',
                        gridZIndex: 1,
                        minorGridLineWidth: 0,
                        showEmpty: true,
                        labels: {
                            style:
                                    {
                                        fontSize: '12px'
                                    }
                        }
                    }
                };

                var options = highChartsOptionsService.getParametersOptions(optionsParametersConfig);
                return options;
            },

            getParametersChartOptions: function (minDate, maxDate) {
                var deferred = $q.defer();

                if (minDate === null || maxDate === null) {
                    var data = {};
                    deferred.resolve(service._createParametersChartOptions(data, true));
                } else {
                    swdService.getParametersData(minDate, maxDate)
                            .then(function (data) {
                                var isEmpty = isArrayEmpty(data);
                                if (isEmpty) {
                                    data = {};
                                }
                                deferred.resolve(service._createParametersChartOptions(data, isEmpty));
                            })
                            .catch(function (message) {
                                deferred.reject(message);
                            });
                }

                return deferred.promise;
            },

            getParametersRangeDate: function () {
                var deferred = $q.defer();
                swdService.getParametersRangeDate()
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
