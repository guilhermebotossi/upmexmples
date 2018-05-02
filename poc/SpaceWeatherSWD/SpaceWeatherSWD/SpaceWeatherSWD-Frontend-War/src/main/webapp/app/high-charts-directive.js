app.directive('highChart', ['LanguageService', function (languageService) {
        return {
            restrict: 'E',
            replace: true,
            template: '<div id="container"/>',
            scope: {
                options: '='
            },
            link: function (scope, element) {
                scope.$watch("options", function () {
                    if (scope.options) {
                        if (scope.options instanceof Array) {
                            $(element[0]).html("");
                            $.each(scope.options, function (i, option) {
                                $('<div>').appendTo(element[0]).highcharts(option);
                            });

                            $('#container').bind('mousemove touchmove touchstart', function (e) {
                            	
                            	var chart, points, i, numberSeries;

                                for (i = 0; i < Highcharts.charts.length; i = i + 1) {
                                    chart = Highcharts.charts[i];
                                    event = chart.pointer.normalize(e.originalEvent);
                                    numberSeries = chart.series.length;

                                    if (numberSeries > 0) {
                                    	if (numberSeries === 2) {
                                            points = [
                                            	chart.series[0].searchPoint(event, true),
                                            	chart.series[1].searchPoint(event, true)
                                            ];
                                            
                                            if (points[0] && points[1]) {
                                            	points[0].onMouseOver();
                                            	points[1].onMouseOver();
                                            	chart.tooltip.refresh([points[0], points[1]]);
                                                chart.xAxis[0].drawCrosshair(e, points[0]);
                                            } else if (points[1]) {
                                            	points[1].onMouseOver();
                                                chart.tooltip.refresh([points[1]]);
                                                chart.xAxis[0].drawCrosshair(e, points[1]);
                                            }
                                        } else if (numberSeries === 4) {
                                            points = [
                                            	chart.series[0].searchPoint(event, true),
                                            	chart.series[1].searchPoint(event, true),
                                            	chart.series[2].searchPoint(event, true),
                                            	chart.series[3].searchPoint(event, true)
                                            ];

                                            if (points[0] && points[1] && points[2] && points[3]) {
                                            	points[0].onMouseOver();
                                            	points[1].onMouseOver();
                                            	points[2].onMouseOver();
                                                points[3].onMouseOver();
                                                chart.tooltip.refresh([points[0], points[1], points[2], points[3]]);
                                                chart.xAxis[0].drawCrosshair(e, points[0]);
                                            } else if (points[2] && points[3]) {
                                            	points[2].onMouseOver();
                                                points[3].onMouseOver();
                                                chart.tooltip.refresh([points[2], points[3]]);
                                                chart.xAxis[0].drawCrosshair(e, points[2]);
                                            }
                                        }
                                    }
                                }
                                
                            });
                            Highcharts.Pointer.prototype.reset = function () {
                                return undefined;
                            };

                            Highcharts.Point.prototype.highlight = function (event) {
                            	this.onMouseOver();
                                this.series.chart.tooltip.refresh([this]);
                                this.series.chart.xAxis[0].drawCrosshair(event, this);
                            };

                        } else {
                            Highcharts.chart(element[0], scope.options, function (chart) {
                                if (scope.options.graphSubtitle) {
                                    chart.renderer.text(scope.options.graphSubtitle.graphSubtitleText, scope.options.graphSubtitle.x, scope.options.graphSubtitle.y)
                                            .css({
                                                color: '#b3b3b3',
                                                fontSize: '16px'
                                            })
                                            .add();
                                }
                            });
                        }
                    }

                });
            }
        };
    }]);
