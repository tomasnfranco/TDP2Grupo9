
<%@ page import="server.Publicacion" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'publicacion.label', default: 'Publicacion')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-publicacion" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-publicacion" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<th><g:message code="publicacion.publicador.label" default="Publicador" /></th>
					
						<g:sortableColumn property="condiciones" title="${message(code: 'publicacion.condiciones.label', default: 'Condiciones')}" />
					
						<g:sortableColumn property="nombreMascota" title="${message(code: 'publicacion.nombreMascota.label', default: 'Nombre Mascota')}" />
					
						<g:sortableColumn property="videoLink" title="${message(code: 'publicacion.videoLink.label', default: 'Video Link')}" />
					
						<th><g:message code="publicacion.concretado.label" default="Concretado" /></th>
					
						<th><g:message code="publicacion.transito.label" default="Transito" /></th>
					
						<th><g:img dir="images" file="skin/database_edit.png" tag="Editar" alt="Editar"></g:img></th>
					</tr>
				</thead>
				<tbody>
				<g:each in="${publicacionInstanceList}" status="i" var="publicacionInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${publicacionInstance.id}">${fieldValue(bean: publicacionInstance, field: "publicador")}</g:link></td>
					
						<td>${fieldValue(bean: publicacionInstance, field: "condiciones")}</td>
					
						<td>${fieldValue(bean: publicacionInstance, field: "nombreMascota")}</td>
					
						<td>${fieldValue(bean: publicacionInstance, field: "videoLink")}</td>
					
						<td>${fieldValue(bean: publicacionInstance, field: "concretado")}</td>
					
						<td>${fieldValue(bean: publicacionInstance, field: "transito")}</td>
					
						<td>
							<g:link action="edit" resource="${publicacionInstance}"><g:img dir="images" file="skin/database_edit.png" tag="Editar" alt="Editar"></g:img></g:link>
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