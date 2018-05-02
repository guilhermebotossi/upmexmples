app.config(['I18NServiceProvider',
    function (i18NServiceProvider) {
        i18NServiceProvider.add({
            EN_US: {
                APP: {
                    INPE: 'National Institute for Space Research (INPE)',
                    EMBRACE: 'EMBRACE/INPE',
                    DIREITOS: 'Figure copyright 2017 © INPE - All rights reserved',  
                    TITULO: 'Brazilian Space Weather Monitoring Program (EMBRACE/INPE)',
                    NO_DATA: 'No data available for this range',
                    DATA_INICIAL: 'Initial date',
                    DATA_FINAL: 'Final date',
                    RESUMO_MENSAGEM: 'The selected range may correspond to any data range available in the EMBRACE/INPE databases, however, it must obey the limit of 31 days',
                    BD_VAZIO_MENSAGEM: 'No data available in our databases. Please, check again later',
                    E: ' and ', 
                    ATE: ' to ', 
                    ANGULAR_FORMATDATE: 'MM/DD/YYYY',
                    TOOLTIP_FORMATDATE: '%b %d, %Y %H:%M',
                    ALL: 'All',
                    REALTIME: 'Real time',
                    UTC: 'Time [UTC]',
                    DADOS_EMBRACE: 'Please, acknowledge EMBRACE/INPE for the data in your publication',
                    CARREGANDO: 'Loading...'
                }
            }
        });
    }
]);