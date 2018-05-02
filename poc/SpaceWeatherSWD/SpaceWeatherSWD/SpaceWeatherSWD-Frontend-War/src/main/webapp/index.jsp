<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html data-ng-app="app">
    <head>
        <title>Solar Winds</title>
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
        <base href="<%=request.getContextPath()%>/" />
        <link href="resources/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="resources/css/app.css" rel="stylesheet" type="text/css"/>
        <link href="resources/css/angular-material.min.css" rel="stylesheet" type="text/css">
    </head>
    <body data-ng-controller="AppController as appCtrl" class="app">

        <div id="wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-8 col-lg-offset-2 col-sm-12 col-sm-offset-0" style="padding: 0">
                        <div class="panel panel-default" style="margin-top: 10px;border: none;">
                            <div class="panel-body" style="padding: 0">
                                <div data-ng-view data-swd-height></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="external/jquery-2.2.4.min.js" type="text/javascript"></script>
        <script src="external/moment.min.js" type="text/javascript"></script>
        <script src="external/bootstrap.min.js" type="text/javascript"></script>
        <script src="external/highcharts.js"></script>
        <script src="external/highcharts-more.js"></script>
        <script src="external/exporting.js"></script>
        <script src="external/offline-exporting.js"></script>
        <script src="external/no-data-to-display.js"></script>

        <script src="external/angular.min.js"></script>
        <script src="external/angular-route.min.js"></script>
        <script src="external/angular-resource.min.js"></script>
        
        <!-- Angular Material requires Angular.js Libraries -->
        <script src="external/angular-animate.min.js"></script>
        <script src="external/angular-aria.min.js"></script>
        <script src="external/angular-messages.min.js"></script>

        <!-- Angular Material Library -->
        <script src="external/angular-material.min.js"></script>

        <script src="app/app.js" type="text/javascript"></script>
        <script src="app/high-charts-directive.js" type="text/javascript"></script>
        <script src="app/high-charts-options-service.js" type="text/javascript"></script>
        <script src="app/app-controller.js" type="text/javascript"></script>
        <script src="app/routes.js" type="text/javascript"></script>
        <script src="app/language-service.js" type="text/javascript"></script>
        <script src="app/swd-service.js" type="text/javascript"></script>
        <script src="app/swd-height.js" type="text/javascript"></script>
        <script src="app/i18n-provider.js" type="text/javascript"></script>
        <script src="app/i18n-filter.js" type="text/javascript"></script>

        <script src="app/app.pt-br.js" type="text/javascript"></script>
        <script src="app/app.en-us.js" type="text/javascript"></script>
        
        <script src="app/hello/hello.pt-br.js" type="text/javascript"></script>
        <script src="app/hello/hello.en-us.js" type="text/javascript"></script>
        <script src="app/hello/hello-service.js" type="text/javascript"></script>
        <script src="app/hello/hello-controller.js" type="text/javascript"></script>

        <script src="app/inicio/inicio.pt-br.js" type="text/javascript"></script>
        <script src="app/inicio/inicio.en-us.js" type="text/javascript"></script>
        <script src="app/inicio/inicio-service.js" type="text/javascript"></script>
        <script src="app/inicio/inicio-controller.js" type="text/javascript"></script>

        <script src="app/sobre/sobre.pt-br.js" type="text/javascript"></script>
        <script src="app/sobre/sobre.en-us.js" type="text/javascript"></script>
        <script src="app/sobre/sobre-controller.js" type="text/javascript"></script>

        <script src="app/parameter/param.pt-br.js" type="text/javascript"></script>
        <script src="app/parameter/param.en-us.js" type="text/javascript"></script>
        <script src="app/parameter/param-service.js" type="text/javascript"></script>
        <script src="app/parameter/param-controller.js" type="text/javascript"></script>

        <script src="app/kp/kp.pt-br.js" type="text/javascript"></script>
        <script src="app/kp/kp.en-us.js" type="text/javascript"></script>
        <script src="app/kp/kp-service.js" type="text/javascript"></script>
        <script src="app/kp/kp-controller.js" type="text/javascript"></script>
    </body>
</html>
