<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
    <meta name="layout" content="main"/>
    <title>Ingreso de Administrador</title>
</head>

<body>
<div class="body">
    <div style="margin: 0 30%; text-align:center;">

        <g:if test="${flash.message}">
            <div class="message" role="status" style="width: 400px;">${flash.message}</div>
        </g:if>
        <g:form action="login" controller="hello">
            <table>
                <tr>
                    <td>Usuario:</td><td><g:textField name="usuario" value="${params.usuario}" required=""/></td>
                </tr>
                <tr>
                    <td>Contraseña:</td><td><g:passwordField name="password" required=""/></td>
                </tr>
                <tr>
                    <td colspan="2" style="text-align: center">
                        <g:submitButton name="ingresar" class="ingresar" value="Ingresar"
                                        controller="hello" action="login"/>
                </td>
                </tr>
            </table>
        </g:form>
    </div>
</div>
</body>
</html>