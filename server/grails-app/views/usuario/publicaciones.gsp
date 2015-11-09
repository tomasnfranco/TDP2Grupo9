
<%@ page import="server.Publicacion" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="mainSinBarra">
		<g:set var="entityName" value="${message(code: 'publicacion.label', default: 'Publicacion')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-publicacion" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div id="list-publicacion" class="content scaffold-list" role="main">
			<h1>Publicaciones de ${user.username}</h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>

						<g:sortableColumn property="nombreMascota" title="${message(code: 'publicacion.nombreMascota.label', default: 'Nombre Mascota')}" />

						<th>Especie</th>
					
						<th>Cantidad de Denuncias</th>
					
						<th></th>
					
						<th></th>
					
						<th></th>
					</tr>
				</thead>
				<tbody>
				<g:each in="${publicacionInstanceList}" status="i" var="publicacionInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

						<td><g:link controller="publicacion" action="ver" id="${publicacionInstance.id}">${fieldValue(bean: publicacionInstance, field: "nombreMascota")}</g:link></td>
					
						<td>${fieldValue(bean: publicacionInstance, field: "especie")}</td>
					
						<td>${denuncias[publicacionInstance.id]}</td>
					
						<td></td>
					
						<td></td>
					
						<td>
						</td>
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${publicacionInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>