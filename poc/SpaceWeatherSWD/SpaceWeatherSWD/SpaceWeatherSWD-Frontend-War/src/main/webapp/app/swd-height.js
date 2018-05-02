app.directive('swdHeight', function ($window) {
    return{
        link: function (scope, element, attrs) {
            element.css('min-height', ($window.innerHeight - 200) + 'px');
        }
    };
});