
<%@ page import="server.Foto" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'foto.label', default: 'Foto')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-foto" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-foto" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list foto">
			
				<g:if test="${fotoInstance?.base64}">
				<li class="fieldcontain">
					<span id="base64-label" class="property-label"><g:message code="foto.base64.label" default="Base64" /></span>
					
						<span class="property-value" aria-labelledby="base64-label"><g:fieldValue bean="${fotoInstance}" field="base64"/></span>
					
				</li>
					<li class="fieldcontain">
						<span id="base64-label" class="property-label">Foto</span>

						<span class="property-value" aria-labelledby="base64-label">
							<img alt="Embedded Image" src="data:image/png;base64,${fieldValue(bean: fotoInstance, field: "base64")}" />
						</span>
					</li>
				</g:if>
			
				<g:if test="${fotoInstance?.publicacion}">
				<li class="fieldcontain">
					<span id="publicacion-label" class="property-label"><g:message code="foto.publicacion.label" default="Publicacion" /></span>
					
						<span class="property-value" aria-labelledby="publicacion-label"><g:link controller="publicacion" action="show" id="${fotoInstance?.publicacion?.id}">${fotoInstance?.publicacion?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:fotoInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${fotoInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
