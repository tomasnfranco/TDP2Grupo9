
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
		<div id="list-publicacion" class="content scaffold-list" role="main">
			<h1>Publicaciones de ${user.username}</h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<div style="text-align: right;padding-right: 10px;margin-bottom: 5px;font-weight: bold;">
				<span style="border: solid 1px #FF2405;-webkit-border-radius: 10px;
				-moz-border-radius: 10px;
				border-radius: 10px;padding:3px;">
				<g:link action="bloquearPublicaciones" id="${user.id}" onclick="return confirm('¿Está seguro de bloquear todas las publicaciones de ${user.username}?');"><img src="${assetPath(src:'skin/table_delete.png')}" style="padding-right:2px;"/>Bloquear Publicaciones</g:link>
				</span>
			</div>
			<g:form action="publicaciones" id="${params.id}">
				<table>
					<tr>
						<td>Nombre Mascota: <g:textField name="mascota" value="${params.mascota}" /></td>
						<td>Especie: <g:select name="especie" value="${params.especie}" from="[0:'Todas',1:'Perro',2:'Gato']" optionKey="key" optionValue="value"/></td>
						<td>Tipo Publicacion: <g:select name="tipoPublicacion" from="[0:'Todos',1:'Adopcion',2:'Perdida',3:'Encontrada']" value="${params.tipoPublicacion}"  optionKey="key" optionValue="value"/></td>
						<td>Activo: <g:select name="activo" from="['Todos','Si','No']"  value="${params.activo}"></g:select></td>
					</tr>
					<tr>
						<td colspan="4" style="text-align: center" width="100%">
							<g:submitButton name="Filtrar" action="administrar"></g:submitButton>&nbsp;&nbsp;
							<g:link action="publicaciones" id="${params.id}">Limpiar Filtros</g:link>
						</td>
					</tr>
				</table>
			</g:form>
			<table>
			<thead>
					<tr>

						<g:sortableColumn property="nombreMascota" title="Nombre Mascota" />

						<g:sortableColumn property="especie" title="Especie" />

						<g:sortableColumn property="tipoPublicacion" title="Tipo" />

						<g:sortableColumn property="denuncias" title="Cantidad de Denuncias" />

						<g:sortableColumn property="activa" title="Activa" />
					
						<th>Bloquear</th>
					
						<th></th>
					</tr>
				</thead>
				<tbody>
				<g:each in="${publicacionInstanceList}" status="i" var="publicacionInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

						<td><g:link controller="publicacion" action="ver" id="${publicacionInstance.id}">${fieldValue(bean: publicacionInstance, field: "nombreMascota")}</g:link></td>
					
						<td>${fieldValue(bean: publicacionInstance, field: "especie")}</td>

						<td>${publicacionInstance.tipoPublicacion == 1 ? 'Adopcion' : (publicacionInstance.tipoPublicacion == 2 ? 'Perdida' : 'Encontrada')}</td>
					
						<td>${publicacionInstance.denuncias.size()}</td>
					
						<td><g:formatBoolean boolean="${publicacionInstance.activa}" true="Si" false="No"/></td>
					
						<td>
							<g:if test="${publicacionInstance.activa}">
							<g:link action="bloquearPublicacion" id="${publicacionInstance.id}" onclick="return confirm('¿Está seguro de bloquear la publicación de ${publicacionInstance.nombreMascota}?');">
								<img src="${assetPath(src:'skin/table_delete.png')}" style="padding-right:2px;" title="Bloquear"/>
							</g:link>
							</g:if>
						</td>
					
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