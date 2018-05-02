app.config(['I18NServiceProvider',
    function (i18NServiceProvider) {
        i18NServiceProvider.add({
            PT_BR: {
                INICIO: {
                    INDICES: 'Índices',
                    VALORES: 'Índices',
                    TITULO_RESUMO: 'Resumo dos índices do meio interplanetário',
                    SUBTITULO_RESUMO: 'Valores máximos',
                    SUBTITULO_RESUMO_HOUR: 'Índices horários',
                    SUBTITULO_RESUMO_DAY: 'Máximos diários',
                    SUBTITULO_RESUMO_DAY_COMPLEMENT: 'mais recentes entre '
                }
            }
        });
    }
]);