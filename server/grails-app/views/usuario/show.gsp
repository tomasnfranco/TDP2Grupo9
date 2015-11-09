
<%@ page import="server.Usuario" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'usuario.label', default: 'Usuario')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-usuario" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-usuario" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list usuario">
			
				<g:if test="${usuarioInstance?.username}">
				<li class="fieldcontain">
					<span id="username-label" class="property-label"><g:message code="usuario.username.label" default="Username" /></span>
					
						<span class="property-value" aria-labelledby="username-label"><g:fieldValue bean="${usuarioInstance}" field="username"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${usuarioInstance?.email}">
				<li class="fieldcontain">
					<span id="email-label" class="property-label"><g:message code="usuario.email.label" default="Email" /></span>
					
						<span class="property-value" aria-labelledby="email-label"><g:fieldValue bean="${usuarioInstance}" field="email"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${usuarioInstance?.password}">
				<li class="fieldcontain">
					<span id="password-label" class="property-label"><g:message code="usuario.password.label" default="Password" /></span>
					
						<span class="property-value" aria-labelledby="password-label"><g:fieldValue bean="${usuarioInstance}" field="password"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${usuarioInstance?.facebookId}">
				<li class="fieldcontain">
					<span id="facebookId-label" class="property-label"><g:message code="usuario.facebookId.label" default="Facebook Id" /></span>
					
						<span class="property-value" aria-labelledby="facebookId-label"><g:fieldValue bean="${usuarioInstance}" field="facebookId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${usuarioInstance?.token}">
				<li class="fieldcontain">
					<span id="token-label" class="property-label"><g:message code="usuario.token.label" default="Token" /></span>
					
						<span class="property-value" aria-labelledby="token-label"><g:fieldValue bean="${usuarioInstance}" field="token"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${usuarioInstance?.telefono}">
				<li class="fieldcontain">
					<span id="telefono-label" class="property-label"><g:message code="usuario.telefono.label" default="Telefono" /></span>
					
						<span class="property-value" aria-labelledby="telefono-label"><g:fieldValue bean="${usuarioInstance}" field="telefono"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${usuarioInstance?.direccion}">
				<li class="fieldcontain">
					<span id="direccion-label" class="property-label"><g:message code="usuario.direccion.label" default="Direccion" /></span>
					
						<span class="property-value" aria-labelledby="direccion-label"><g:fieldValue bean="${usuarioInstance}" field="direccion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${usuarioInstance?.foto}">
				<li class="fieldcontain">
					<span id="foto-label" class="property-label"><g:message code="usuario.foto.label" default="Foto" /></span>
					
						<span class="property-value" aria-labelledby="foto-label"><g:fieldValue bean="${usuarioInstance}" field="foto"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${usuarioInstance?.gcmId}">
				<li class="fieldcontain">
					<span id="gcmId-label" class="property-label"><g:message code="usuario.gcmId.label" default="Gcm Id" /></span>
					
						<span class="property-value" aria-labelledby="gcmId-label"><g:fieldValue bean="${usuarioInstance}" field="gcmId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${usuarioInstance?.activo}">
				<li class="fieldcontain">
					<span id="activo-label" class="property-label"><g:message code="usuario.activo.label" default="Activo" /></span>
					
						<span class="property-value" aria-labelledby="activo-label"><g:formatBoolean boolean="${usuarioInstance?.activo}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${usuarioInstance?.autoPublicar}">
				<li class="fieldcontain">
					<span id="autoPublicar-label" class="property-label"><g:message code="usuario.autoPublicar.label" default="Auto Publicar" /></span>
					
						<span class="property-value" aria-labelledby="autoPublicar-label"><g:formatBoolean boolean="${usuarioInstance?.autoPublicar}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${usuarioInstance?.latitud}">
				<li class="fieldcontain">
					<span id="latitud-label" class="property-label"><g:message code="usuario.latitud.label" default="Latitud" /></span>
					
						<span class="property-value" aria-labelledby="latitud-label"><g:fieldValue bean="${usuarioInstance}" field="latitud"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${usuarioInstance?.longitud}">
				<li class="fieldcontain">
					<span id="longitud-label" class="property-label"><g:message code="usuario.longitud.label" default="Longitud" /></span>
					
						<span class="property-value" aria-labelledby="longitud-label"><g:fieldValue bean="${usuarioInstance}" field="longitud"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${usuarioInstance?.ofreceTransito}">
				<li class="fieldcontain">
					<span id="ofreceTransito-label" class="property-label"><g:message code="usuario.ofreceTransito.label" default="Ofrece Transito" /></span>
					
						<span class="property-value" aria-labelledby="ofreceTransito-label"><g:formatBoolean boolean="${usuarioInstance?.ofreceTransito}" /></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:usuarioInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${usuarioInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
