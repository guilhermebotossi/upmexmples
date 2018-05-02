app.service('SWDService', ['$q', '$http',
    function ($q, $http) {
        var service = {
            getKpForecastData: function () {
                var deferred = $q.defer();
                $http.get('resource/kp/forecast')
                        .success(function (data) {
                            deferred.resolve(data);
                        })
                        .error(function (errorData) {
                            deferred.reject(errorData.ExceptionMessage);
                        });
                return deferred.promise;
            },

            getMaxSummaryData: function (minDate, maxDate) {
                var deferred = $q.defer();

                var resultMinDate = minDate.toISOString().substring(0, 10);
                var resultMaxDate = maxDate.toISOString().substring(0, 10);

                $http.get('resource/idx/max', {params: {minData: resultMinDate, maxData: resultMaxDate}})
                        .success(function (data) {
                            deferred.resolve(data);
                        })
                        .error(function (errorData) {
                            deferred.reject(errorData.ExceptionMessage);
                        });
                return deferred.promise;
            },

            getIndexesRangeDate: function () {
                var deferred = $q.defer();
                $http.get('resource/idx/rangeDate', {})
                        .success(function (data) {
                            deferred.resolve(data);
                        })
                        .error(function (errorData) {
                            deferred.reject(errorData.ExceptionMessage);
                        });
                return deferred.promise;
            },

            getParametersData: function (minDate, maxDate) {
                var deferred = $q.defer();

                var resultMinDate = minDate.toISOString().substring(0, 10);
                var resultMaxDate = maxDate.toISOString().substring(0, 10);

                $http.get('resource/param/parameters', {params: {minData: resultMinDate, maxData: resultMaxDate}})
                        .success(function (data) {
                            deferred.resolve(data);
                        })
                        .error(function (errorData) {
                            deferred.reject(errorData.ExceptionMessage);
                        });
                return deferred.promise;
            },
            
            getParametersRangeDate: function () {
                var deferred = $q.defer();
                $http.get('resource/param/rangeDate', {})
                        .success(function (data) {
                            deferred.resolve(data);
                        })
                        .error(function (errorData) {
                            deferred.reject(errorData.ExceptionMessage);
                        });
                return deferred.promise;
            }

        };
        return service;
    }
]);