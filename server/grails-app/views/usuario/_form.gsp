<%@ page import="server.Usuario" %>



<div class="fieldcontain ${hasErrors(bean: usuarioInstance, field: 'username', 'error')} ">
	<label for="username">
		<g:message code="usuario.username.label" default="Username" />
		
	</label>
	<g:textField name="username" value="${usuarioInstance?.username}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: usuarioInstance, field: 'email', 'error')} ">
	<label for="email">
		<g:message code="usuario.email.label" default="Email" />
		
	</label>
	<g:field type="email" name="email" value="${usuarioInstance?.email}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: usuarioInstance, field: 'password', 'error')} ">
	<label for="password">
		<g:message code="usuario.password.label" default="Password" />
		
	</label>
	<g:field type="password" name="password" value="${usuarioInstance?.password}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: usuarioInstance, field: 'facebookId', 'error')} required">
	<label for="facebookId">
		<g:message code="usuario.facebookId.label" default="Facebook Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="facebookId" type="number" value="${usuarioInstance.facebookId}" required=""/>

</div>

<div class="fieldcontain ${hasErrors(bean: usuarioInstance, field: 'token', 'error')} ">
	<label for="token">
		<g:message code="usuario.token.label" default="Token" />
		
	</label>
	<g:textField name="token" value="${usuarioInstance?.token}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: usuarioInstance, field: 'telefono', 'error')} required">
	<label for="telefono">
		<g:message code="usuario.telefono.label" default="Telefono" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="telefono" required="" value="${usuarioInstance?.telefono}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: usuarioInstance, field: 'direccion', 'error')} required">
	<label for="direccion">
		<g:message code="usuario.direccion.label" default="Direccion" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="direccion" required="" value="${usuarioInstance?.direccion}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: usuarioInstance, field: 'foto', 'error')} ">
	<label for="foto">
		<g:message code="usuario.foto.label" default="Foto" />
		
	</label>
	<g:textField name="foto" value="${usuarioInstance?.foto}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: usuarioInstance, field: 'gcmId', 'error')} ">
	<label for="gcmId">
		<g:message code="usuario.gcmId.label" default="Gcm Id" />
		
	</label>
	<g:textField name="gcmId" value="${usuarioInstance?.gcmId}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: usuarioInstance, field: 'activo', 'error')} ">
	<label for="activo">
		<g:message code="usuario.activo.label" default="Activo" />
		
	</label>
	<g:checkBox name="activo" value="${usuarioInstance?.activo}" />

</div>

<div class="fieldcontain ${hasErrors(bean: usuarioInstance, field: 'autoPublicar', 'error')} ">
	<label for="autoPublicar">
		<g:message code="usuario.autoPublicar.label" default="Auto Publicar" />
		
	</label>
	<g:checkBox name="autoPublicar" value="${usuarioInstance?.autoPublicar}" />

</div>

<div class="fieldcontain ${hasErrors(bean: usuarioInstance, field: 'latitud', 'error')} required">
	<label for="latitud">
		<g:message code="usuario.latitud.label" default="Latitud" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="latitud" value="${fieldValue(bean: usuarioInstance, field: 'latitud')}" required=""/>

</div>

<div class="fieldcontain ${hasErrors(bean: usuarioInstance, field: 'longitud', 'error')} required">
	<label for="longitud">
		<g:message code="usuario.longitud.label" default="Longitud" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="longitud" value="${fieldValue(bean: usuarioInstance, field: 'longitud')}" required=""/>

</div>

<div class="fieldcontain ${hasErrors(bean: usuarioInstance, field: 'ofreceTransito', 'error')} ">
	<label for="ofreceTransito">
		<g:message code="usuario.ofreceTransito.label" default="Ofrece Transito" />
		
	</label>
	<g:checkBox name="ofreceTransito" value="${usuarioInstance?.ofreceTransito}" />

</div>

