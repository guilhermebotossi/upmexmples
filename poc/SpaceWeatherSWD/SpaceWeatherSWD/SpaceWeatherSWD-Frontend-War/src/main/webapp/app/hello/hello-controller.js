app.controller('HelloController', ['HelloService',
    function (helloService) {
        var self = this;
        
        self.hello = '';

        helloService.hello()
                .then(function (data) {
                    self.hello = data;
                })
                .catch(function (message) {
                    console.log(message);
                });

    }
]);