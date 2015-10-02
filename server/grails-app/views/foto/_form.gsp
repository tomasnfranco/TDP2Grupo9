<%@ page import="server.Foto" %>



<div class="fieldcontain ${hasErrors(bean: fotoInstance, field: 'base64', 'error')} required">
	<label for="base64">
		<g:message code="foto.base64.label" default="Base64" />
		<span class="required-indicator">*</span>
	</label>
	<g:textArea name="base64" cols="40" rows="5" maxlength="100000" required="" value="${fotoInstance?.base64}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: fotoInstance, field: 'publicacion', 'error')} required">
	<label for="publicacion">
		<g:message code="foto.publicacion.label" default="Publicacion" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="publicacion" name="publicacion.id" from="${server.Publicacion.list()}" optionKey="id" required="" value="${fotoInstance?.publicacion?.id}" class="many-to-one"/>

</div>

