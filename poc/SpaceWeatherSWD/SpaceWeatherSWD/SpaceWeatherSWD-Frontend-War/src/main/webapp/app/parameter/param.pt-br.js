app.config(['I18NServiceProvider',
    function (i18NServiceProvider) {
        i18NServiceProvider.add({
            PT_BR: {
                PARAM: {
                    VALORES: 'Valores',
                    TITULO: 'Perfis Temporais',
                    SUBTITULO: 'Valores ',
                    SUBTITULO_COMPLEMENT: 'dados entre ',
                    MEDIA: 'Média horária',
                    DENSIDADE: 'Densidade',
                    VELOCIDADE: 'Velocidade',
                    TEMPERATURA: 'Temperatura',
                    DAYSIDE: 'PLD da Magnetopausa',
                    DAYSIDE_SUB: 'Posição do Lado Diurno da Magnetopausa',
                    BOTTOM_MSG: 'Os dados de Bt, Bx, By, Bz, Densidade, Velocidade e Temperatura do Vento Solar são fornecidos pelo satélite DSCOVR (SWPC/NOAA)'
                    
                }
            }
        });
    }
]);