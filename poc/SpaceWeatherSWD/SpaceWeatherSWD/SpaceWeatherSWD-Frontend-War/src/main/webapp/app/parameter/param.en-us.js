app.config(['I18NServiceProvider',
    function (i18NServiceProvider) {
        i18NServiceProvider.add({
            EN_US: {
                PARAM: {
                    VALORES: 'Values',
                    TITULO: 'Time Profiles',
                    SUBTITULO: 'Values ',
                    SUBTITULO_COMPLEMENT: 'data between ',
                    MEDIA: 'Hourly average',
                    DENSIDADE: 'Density',
                    VELOCIDADE: 'Radial Speed',
                    TEMPERATURA: 'Temperature',
                    DAYSIDE: 'Day-side Magnetopause Location',
                    BOTTOM_MSG: 'The Bt, Bx, By, Bz, Density, Radial Speed and Temperature Solar Wind data are provided by the DSCOVR (SWPC/NOAA) spacecraft'
                }
            }
        });
    }
]);