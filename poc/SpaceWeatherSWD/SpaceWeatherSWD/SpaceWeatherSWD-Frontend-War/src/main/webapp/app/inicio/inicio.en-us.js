app.config(['I18NServiceProvider',
    function (i18NServiceProvider) {
        i18NServiceProvider.add({
            EN_US: {
                INICIO: {
                    INDICES: 'Indices',
                    VALORES: 'Indices',
                    TITULO_RESUMO: 'Summary of the Interplanetary Medium Indices',
                    SUBTITULO_RESUMO: 'Maximum values',
                    SUBTITULO_RESUMO_HOUR: 'Hourly indices',
                    SUBTITULO_RESUMO_DAY: 'Daily maximum',
                    SUBTITULO_RESUMO_DAY_COMPLEMENT: 'most recent between '
                }
            }
        });
    }
]);