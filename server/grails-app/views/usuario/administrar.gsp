
<%@ page import="server.Usuario" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'usuario.label', default: 'Usuario')}" />
		<title>Administrar Usuarios</title>
	</head>
	<body>
		<a href="#list-usuario" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div id="list-usuario" class="content scaffold-list" role="main">
			<h1>Administrar Usuarios</h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="username" title="${message(code: 'usuario.username.label', default: 'Username')}" />
					
						<g:sortableColumn property="email" title="${message(code: 'usuario.email.label', default: 'Email')}" />

						<th>Cantidad Publicaciones</th>

						<th>Cantidad Denuncias</th>
					</tr>
				</thead>
				<tbody>
				<g:each in="${lista}" status="i" var="usuarioInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td>${fieldValue(bean: usuarioInstance, field: "username")}</td>
					
						<td>${fieldValue(bean: usuarioInstance, field: "email")}</td>
					
						<td>${publicacionesSize[usuarioInstance.id] ?: 0}</td>
					
						<td>${denuncias[usuarioInstance.id] ?: 0}</td>
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${usuarioInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>