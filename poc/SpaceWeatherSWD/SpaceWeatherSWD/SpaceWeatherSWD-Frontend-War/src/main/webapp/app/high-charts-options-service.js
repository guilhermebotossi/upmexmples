app.service('HighChartsOptionsService', [
    function () {
        var service = {
            getKpOptions: function (optionsKpConfig) {
                return {
                    exporting: false,
                    chart: {
                        zoomType: 'xy',
                        marginLeft: 50,
                        marginTop: 55,
                        spacingTop: 10,
                        spacingBottom: 20,
                        plotBorderColor: 'black',
                        plotBorderWidth: 1,
                        height: 300
                    },
                    title: {
                        text: optionsKpConfig.title
                    },
                    subtitle: {
                        text: optionsKpConfig.subtitle
                    },
                    xAxis: optionsKpConfig.xAxis,
                    yAxis: optionsKpConfig.yAxis,
                    rangeSelector: {
                        enabled: false
                    },
                    legend: {
                        enabled: false
                    },
                    lang: {
                        noData: optionsKpConfig.noData
                    },
                    tooltip: {
                        useHTML: true,
                        borderWidth: 2,
                        followPointer: true,
                        style: {
                            fontSize: '16px'
                        }
                    },
                    credits: {
                        enabled: true,
                        href: 'http://www2.inpe.br/climaespacial/',
                        text: optionsKpConfig.creditsText,
                        position: {
                            align: 'right',
                            x: -15
                        }
                    },
                    series: optionsKpConfig.series,
                    plotOptions: {
                        column: {
                            pointPadding: 0.04,
                            groupPadding: 0.04
                        },
                        columnrange: {
                            pointPadding: 0.04,
                            groupPadding: 0.04,
                            grouping: false,
                            dataLabels: {
                                inside: true,
                                enabled: true,
                                align: 'center',
                                format: '{point.name}%',
                                color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'black',
                                style: {
                                    fontSize: '10px',
                                    fontWeight: 'bold'
                                }
                            },
                            colorByPoint: true,
                            colors: ['rgba(0, 245, 0, 0.6)', 'rgba(245, 245, 0, 0.6)', 'rgba(245, 0, 0, 0.6)']
                        }
                    }
                };
            },

            getSummaryIndexOptions: function (optionsIndexesChartConfig) {
                return {
                    exporting: {
                        chartOptions: {
                            plotOptions: {
                                series: {
                                    dataLabels: {
                                        enabled: false
                                    }
                                }
                            }
                        },
                        scale: 2,
                        fallbackToExportServer: false
                    },
                    chart: {
                        zoomType: 'xy',
                        marginTop: 75,
                        plotBorderColor: 'black',
                        plotBorderWidth: 1
                    },
                    title: {
                        text: optionsIndexesChartConfig.title
                    },
                    subtitle: {
                        text: optionsIndexesChartConfig.subtitle
                    },
                    xAxis: optionsIndexesChartConfig.xAxis,
                    yAxis: optionsIndexesChartConfig.yAxis,
                    rangeSelector: {
                        enabled: false
                    },
                    lang: {
                        noData: optionsIndexesChartConfig.noData
                    },
                    credits: {
                        enabled: true,
                        href: 'http://www2.inpe.br/climaespacial/',
                        text: optionsIndexesChartConfig.creditsText,
                        position: {
                            align: 'right',
                            x: -15,
                            y: -45
                        }
                    },
                    legend: {
                        itemStyle: {fontSize: "14px"},
                        symbolHeight: 20,
                        symbolWidth: 25,
                        symbolRadius: 0
                    },
                    plotOptions: {
                        series: {
                            lineWidth: 3,
                            stickyTracking: false,
                            marker: {
                                enabled: true
                            }
                        }
                    },
                    tooltip: {
                        useHTML: true,
                        borderWidth: 0,
                        backgroundColor: 'none',
                        headerFormat: '',
                        shadow: false,
                        style: {
                            fontSize: '18px'
                        },
                        positioner: function () {
                            return {
                                x: this.chart.chartWidth - this.label.width,
                                y: 43
                            };
                        }
                    },
                    series: optionsIndexesChartConfig.series
                };
            },

            getParametersOptions: function (optionsParametersConfig) {

                var options = [];

                for (var index = 0; index < optionsParametersConfig.title.length; index++) {
                    var option = {
                        exporting: {
                            chartOptions: {
                                plotOptions: {
                                    series: {
                                        dataLabels: {
                                            enabled: false
                                        }
                                    }
                                }
                            },
                            buttons: {
                                contextButton: {
                                    align: 'right',
                                    x: -5,
                                    y: 41
                                }
                            },
                            scale: 2,
                            fallbackToExportServer: false
                        },
                        chart: {
                            marginLeft: 40,
                            spacingTop: 5,
                            spacingBottom: 2,
                            plotBorderColor: 'black',
                            plotBorderWidth: 1,
                            zoomType: 'xy',
                            resetZoomButton: {
                                position: {
                                    x: -15,
                                    y: 75
                                },
                                relativeTo: 'chart'
                            }
                        },
                        title: {
                            text: optionsParametersConfig.title[index],
                            align: 'left',
                            margin: 5,
                            x: 20,
                            useHTML: true
                        },
                        xAxis: optionsParametersConfig.xAxis,
                        yAxis: optionsParametersConfig.yAxis[index],
                        lang: {
                            noData: optionsParametersConfig.noData
                        },
                        credits: {
                            enabled: true,
                            href: 'http://www2.inpe.br/climaespacial/',
                            text: optionsParametersConfig.creditsText,
                            position: {
                                align: 'right',
                                x: -15,
                                y: -55
                            }
                        },
                        legend: {
                            useHTML: true,
                            itemStyle: {
                                fontSize: '12px'
                            },
                            y: 0,
                            padding: 0,
                            itemMarginTop: 0,
                            itemMarginBottom: 0
                        },
                        plotOptions: {
                            series: {
                                lineWidth: 2,
                                marker: {
                                    enabled: false
                                },
                                stickyTracking: false,
                                events: {
                                    legendItemClick: function () {
                                        return false;
                                    }
                                },
                                states: {
                                    hover: {
                                        halo: {
                                            size: 0
                                        }
                                    }
                                }
                            }
                        },
                        tooltip: optionsParametersConfig.tooltip[index],
                        series: optionsParametersConfig.series[index]
                    };

                    options.push(option);
                }

                return options;
            }
        };
        return service;
    }
]);