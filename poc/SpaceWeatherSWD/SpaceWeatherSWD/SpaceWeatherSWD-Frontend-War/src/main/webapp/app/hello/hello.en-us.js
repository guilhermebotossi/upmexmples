app.config(['I18NServiceProvider',
    function (i18NServiceProvider) {
        i18NServiceProvider.add({
            EN_US: {
                HELLO: {
                    TITULO: 'This page is just an experiment of something... So, hello!'
                }
            }
        });
    }
]);