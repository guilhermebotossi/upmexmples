app.config(['I18NServiceProvider',
    function (i18NServiceProvider) {
        i18NServiceProvider.add({
            EN_US: {
                KP: {
                    TITULO: 'Kp index',
                    SUBTITULO: 'Forecasting and observation',
                    PREVISAO: 'predicted',
                    TOOLTIP_HEADER: 'Estimative in',
                    TOOLTIP_PREVISAO_1: 'The 95% confidence interval for Kp in this selected future period presents values in the colored range is between',
                    TOOLTIP_PREVISAO_2: 'and',
                    TOOLTIP_PREVISAO_3: '%',
                    LEGENDA_TIT: 'Forecast',
                    LEGENDA_1: 'Prob. KP [0,5-]', 
                    LEGENDA_2: 'Prob. KP [5o, 7-]',
                    LEGENDA_3: 'Prob. KP [7o, 9o]',
                    Y_AXIS_LABEl : 'Values'
                }
            }
        });
    }
]);