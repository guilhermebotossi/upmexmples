app.config(['I18NServiceProvider',
    function (i18NServiceProvider) {
        i18NServiceProvider.add({
            PT_BR: {
                KP: {
                    TITULO: 'Índice Kp',
                    SUBTITULO: 'Observação e Previsão',
                    PREVISAO: 'previsto',
                    TOOLTIP_HEADER: 'Estimativa em',
                    TOOLTIP_PREVISAO_1: 'Há entre',
                    TOOLTIP_PREVISAO_2: 'e',
                    TOOLTIP_PREVISAO_3: '% de chance, com 95% de confiabilidade, do índice Kp no período futuro selecionado apresentar valores no intervalo destacado',
                    LEGENDA_TIT: 'Previsão',
                    LEGENDA_1: 'Prob. KP [0,5-]', 
                    LEGENDA_2: 'Prob. KP [5o, 7-]',
                    LEGENDA_3: 'Prob. KP [7o, 9o]',
                    Y_AXIS_LABEL : 'Valores'
                }
            }
        });
    }
]);