app.config(['I18NServiceProvider',
    function (i18NServiceProvider) {
        i18NServiceProvider.add({
            PT_BR: {
                APP: {
                    INPE: 'Instituto Nacional de Pesquisas Espaciais (INPE)',
                    EMBRACE: 'EMBRACE/INPE',
                    DIREITOS: 'Copyright da figura 2017 © INPE - Todos direitos reservados',                
                    TITULO: 'Estudo e Monitoramento Brasileiro do Clima Espacial (EMBRACE/INPE)',
                    NO_DATA: 'Sem dados disponíveis para o período',
                    DATA_INICIAL: 'Data inicial',
                    DATA_FINAL: 'Data final',
                    RESUMO_MENSAGEM: 'O intervalo selecionado para exibição poderá corresponder a qualquer período de dados disponíveis nas bases EMBRACE/INPE, porém, deverá obeder o limite de 31 dias',
                    BD_VAZIO_MENSAGEM: 'Não há dados disponíveis em nossas bases de dados. Por favor, tente mais tarde',
                    E: ' e ', 
                    ATE: ' a ', 
                    ANGULAR_FORMATDATE: 'DD/MM/YYYY',
                    TOOLTIP_FORMATDATE: '%d %b, %Y %H:%M',
                    ALL: 'Tudo',
                    REALTIME: 'Tempo real',
                    UTC: 'Tempo [UTC]',
                    DADOS_EMBRACE: 'Por favor, não esqueça de agradecer em sua publicação ao EMBRACE/INPE pelos dados aqui obtidos',
                    CARREGANDO: 'Carregando...'
                }
            }
        });
    }
]);