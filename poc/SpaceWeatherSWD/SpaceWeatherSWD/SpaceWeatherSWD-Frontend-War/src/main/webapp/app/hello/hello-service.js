app.service('HelloService', ['$q', '$http',
    function ($q, $http) {
        var service = {
            hello: function () {
                var deferred = $q.defer();
                $http.get('resource/hello')
                        .success(function (data) {
                            var md = data.modificationDate;
                            var fmd = md.dayOfMonth + '/' + md.monthValue + '/' + md.year + ' ' + md.hour + ':' + md.minute + ':' + md.second;
                            deferred.resolve(fmd);
                        })
                        .error(function (errorData) {
                            deferred.reject(errorData.ExceptionMessage);
                        });
                return deferred.promise;
            },
        };
        return service;
    }
]);