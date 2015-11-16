<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main">

    <title>Reporte</title>
    <style>
        td{
            text-align: center;
        }
        th{
            text-align: center;
        }
    </style>
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript">
        google.load("visualization", "1", {packages:["corechart"]});
        google.setOnLoadCallback(drawChart);
        function drawChart() {
            var data = google.visualization.arrayToDataTable([
                ['Tipo', 'Cantidad', { role: 'style' }],
                ['Perdidas', ${perdidas}, '#00bc66' ],
                ['Encontradas', ${encontradas}, 'silver'],
                ['En Adopción', ${enAdopcion}, 'gold' ],
                ['Adoptadas', ${adoptadas}, 'default' ]
            ]);


            var view = new google.visualization.DataView(data);
            view.setColumns([0, 1,
                { calc: "stringify",
                    sourceColumn: 1,
                    type: "string",
                    role: "annotation" },
                2]);

            var options = {
                animation: {startup : true},
                width: 600,
                height: 400,
                bar: {groupWidth: "95%"},
                legend: { position: "none" }
            };
            var chart = new google.visualization.ColumnChart(document.getElementById("columnchart_values"));
            chart.draw(view, options);
        }
    </script>
</head>

<body>
<div id="list-foto" class="content scaffold-list" role="main">
    <h1>Métricas</h1>
    <g:form action="reporte" method="GET">
        <table>
            <tr>
                <td>
                    <div>
                        <label>
                            Promedio de En Adopción-Adoptadas: ${tiempoPromAdop} días. <br/>
                            Adoptadas / En Adopción: <g:formatNumber number="${totalAdoptadas/totalEnAdopcion}" type="number" maxFractionDigits="2" />
                        </label>
                    </div>
                </td>
                <td>
                    <div>
                        <label>
                            Promedio de Perdidas-Encontradas: ${tiempoPromEncontrar} días.<br/>
                            Encontradas / Perdidas :  <g:formatNumber number="${totalEncontradas/totalPerdidas}" type="number" maxFractionDigits="2" />
                        </label>
                    </div>
                </td>
            </tr>
        </table>
    </g:form>
    <h1>Estadísticas</h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:form action="reporte" method="GET">
    <table>
        <tr>
            <td>
                <div>
                    <label for="fechaDesde">
                        Desde:
                    </label>
                    <g:datePicker name="desde" precision="day" value="${desde}" relativeYears="[-1..0]" />
                </div>
            </td>
            <td>
                <div>
                    <label for="fechaHasta">
                        Hasta:
                    </label>
                    <g:datePicker name="hasta" precision="day" value="${hasta}" relativeYears="[-1..0]"  />
                </div>
            </td>
            <td>
                <div>
                    <label for="especie">
                        Especie:
                    </label>
                    <g:select name="especie" from="${especies}" noSelection="['-1':'Ambos']" optionKey="id" value="${params.especie}"></g:select>
                </div>
            </td></tr>
        <tr>
            <td colspan="3">
                <div>
                <g:submitButton name="procesar" value="Procesar Busqueda"></g:submitButton>
                </div>
            </td>
        </tr>
    </table>
    </g:form>
    <div id="columnchart_values" style="width: 600px; height: 400px; margin-left: auto;margin-right: auto;"></div>
    <div id="info1"></div>
    <table >
        <thead>
        <tr>
            <th>Perdidas</th>
            <th>Encontradas</th>
            <th>En Adopción</th>
            <th>Adoptadas</th>
        </tr>
        </thead>
        <tbody>
            <tr class="even">
                <td>${perdidas}</td>
                <td>${encontradas}</td>
                <td>${enAdopcion}</td>
                <td>${adoptadas}</td>
            </tr>
        </tbody>
    </table>
</div>
</body>
</html>