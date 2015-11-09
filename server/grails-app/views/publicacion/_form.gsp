<%@ page import="server.Publicacion" %>



<div class="fieldcontain ${hasErrors(bean: publicacionInstance, field: 'publicador', 'error')} required">
	<label for="publicador">
		<g:message code="publicacion.publicador.label" default="Publicador" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="publicador" name="publicador.id" from="${server.Usuario.list()}" optionKey="id" required="" value="${publicacionInstance?.publicador?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: publicacionInstance, field: 'condiciones', 'error')} ">
	<label for="condiciones">
		<g:message code="publicacion.condiciones.label" default="Condiciones" />
		
	</label>
	<g:textField name="condiciones" value="${publicacionInstance?.condiciones}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: publicacionInstance, field: 'nombreMascota', 'error')} ">
	<label for="nombreMascota">
		<g:message code="publicacion.nombreMascota.label" default="Nombre Mascota" />
		
	</label>
	<g:textField name="nombreMascota" value="${publicacionInstance?.nombreMascota}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: publicacionInstance, field: 'videoLink', 'error')} ">
	<label for="videoLink">
		<g:message code="publicacion.videoLink.label" default="Video Link" />
		
	</label>
	<g:textField name="videoLink" value="${publicacionInstance?.videoLink}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: publicacionInstance, field: 'fotos', 'error')} ">
	<label for="fotos">
		<g:message code="publicacion.fotos.label" default="Fotos" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${publicacionInstance?.fotos?}" var="f">
    <li><g:link controller="foto" action="show" id="${f.id}">${f?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="foto" action="create" params="['publicacion.id': publicacionInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'foto.label', default: 'Foto')])}</g:link>
</li>
</ul>


</div>

<div class="fieldcontain ${hasErrors(bean: publicacionInstance, field: 'concretado', 'error')} ">
	<label for="concretado">
		<g:message code="publicacion.concretado.label" default="Concretado" />
		
	</label>
	<g:select id="concretado" name="concretado.id" from="${server.Usuario.list()}" optionKey="id" value="${publicacionInstance?.concretado?.id}" class="many-to-one" noSelection="['null': '']"/>

</div>

<div class="fieldcontain ${hasErrors(bean: publicacionInstance, field: 'transito', 'error')} ">
	<label for="transito">
		<g:message code="publicacion.transito.label" default="Transito" />
		
	</label>
	<g:select id="transito" name="transito.id" from="${server.Usuario.list()}" optionKey="id" value="${publicacionInstance?.transito?.id}" class="many-to-one" noSelection="['null': '']"/>

</div>

<div class="fieldcontain ${hasErrors(bean: publicacionInstance, field: 'direccion', 'error')} ">
	<label for="direccion">
		<g:message code="publicacion.direccion.label" default="Direccion" />
		
	</label>
	<g:textField name="direccion" value="${publicacionInstance?.direccion}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: publicacionInstance, field: 'fechaConcretado', 'error')} ">
	<label for="fechaConcretado">
		<g:message code="publicacion.fechaConcretado.label" default="Fecha Concretado" />
		
	</label>
	<g:datePicker name="fechaConcretado" precision="day"  value="${publicacionInstance?.fechaConcretado}" default="none" noSelection="['': '']" />

</div>

<div class="fieldcontain ${hasErrors(bean: publicacionInstance, field: 'activa', 'error')} ">
	<label for="activa">
		<g:message code="publicacion.activa.label" default="Activa" />
		
	</label>
	<g:checkBox name="activa" value="${publicacionInstance?.activa}" />

</div>

<div class="fieldcontain ${hasErrors(bean: publicacionInstance, field: 'castrado', 'error')} required">
	<label for="castrado">
		<g:message code="publicacion.castrado.label" default="Castrado" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="castrado" name="castrado.id" from="${server.Castrado.list()}" optionKey="id" required="" value="${publicacionInstance?.castrado?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: publicacionInstance, field: 'color', 'error')} required">
	<label for="color">
		<g:message code="publicacion.color.label" default="Color" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="color" name="color.id" from="${server.Color.list()}" optionKey="id" required="" value="${publicacionInstance?.color?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: publicacionInstance, field: 'compatibleCon', 'error')} required">
	<label for="compatibleCon">
		<g:message code="publicacion.compatibleCon.label" default="Compatible Con" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="compatibleCon" name="compatibleCon.id" from="${server.CompatibleCon.list()}" optionKey="id" required="" value="${publicacionInstance?.compatibleCon?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: publicacionInstance, field: 'denuncias', 'error')} ">
	<label for="denuncias">
		<g:message code="publicacion.denuncias.label" default="Denuncias" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${publicacionInstance?.denuncias?}" var="d">
    <li><g:link controller="denuncia" action="show" id="${d.id}">${d?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="denuncia" action="create" params="['publicacion.id': publicacionInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'denuncia.label', default: 'Denuncia')])}</g:link>
</li>
</ul>


</div>

<div class="fieldcontain ${hasErrors(bean: publicacionInstance, field: 'edad', 'error')} required">
	<label for="edad">
		<g:message code="publicacion.edad.label" default="Edad" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="edad" name="edad.id" from="${server.Edad.list()}" optionKey="id" required="" value="${publicacionInstance?.edad?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: publicacionInstance, field: 'energia', 'error')} required">
	<label for="energia">
		<g:message code="publicacion.energia.label" default="Energia" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="energia" name="energia.id" from="${server.Energia.list()}" optionKey="id" required="" value="${publicacionInstance?.energia?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: publicacionInstance, field: 'especie', 'error')} required">
	<label for="especie">
		<g:message code="publicacion.especie.label" default="Especie" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="especie" name="especie.id" from="${server.Especie.list()}" optionKey="id" required="" value="${publicacionInstance?.especie?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: publicacionInstance, field: 'fechaPublicacion', 'error')} required">
	<label for="fechaPublicacion">
		<g:message code="publicacion.fechaPublicacion.label" default="Fecha Publicacion" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaPublicacion" precision="day"  value="${publicacionInstance?.fechaPublicacion}"  />

</div>

<div class="fieldcontain ${hasErrors(bean: publicacionInstance, field: 'latitud', 'error')} required">
	<label for="latitud">
		<g:message code="publicacion.latitud.label" default="Latitud" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="latitud" value="${fieldValue(bean: publicacionInstance, field: 'latitud')}" required=""/>

</div>

<div class="fieldcontain ${hasErrors(bean: publicacionInstance, field: 'longitud', 'error')} required">
	<label for="longitud">
		<g:message code="publicacion.longitud.label" default="Longitud" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="longitud" value="${fieldValue(bean: publicacionInstance, field: 'longitud')}" required=""/>

</div>

<div class="fieldcontain ${hasErrors(bean: publicacionInstance, field: 'necesitaTransito', 'error')} ">
	<label for="necesitaTransito">
		<g:message code="publicacion.necesitaTransito.label" default="Necesita Transito" />
		
	</label>
	<g:checkBox name="necesitaTransito" value="${publicacionInstance?.necesitaTransito}" />

</div>

<div class="fieldcontain ${hasErrors(bean: publicacionInstance, field: 'ofrecenTransito', 'error')} ">
	<label for="ofrecenTransito">
		<g:message code="publicacion.ofrecenTransito.label" default="Ofrecen Transito" />
		
	</label>
	<g:select name="ofrecenTransito" from="${server.Usuario.list()}" multiple="multiple" optionKey="id" size="5" value="${publicacionInstance?.ofrecenTransito*.id}" class="many-to-many"/>

</div>

<div class="fieldcontain ${hasErrors(bean: publicacionInstance, field: 'papelesAlDia', 'error')} required">
	<label for="papelesAlDia">
		<g:message code="publicacion.papelesAlDia.label" default="Papeles Al Dia" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="papelesAlDia" name="papelesAlDia.id" from="${server.PapelesAlDia.list()}" optionKey="id" required="" value="${publicacionInstance?.papelesAlDia?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: publicacionInstance, field: 'preguntas', 'error')} ">
	<label for="preguntas">
		<g:message code="publicacion.preguntas.label" default="Preguntas" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${publicacionInstance?.preguntas?}" var="p">
    <li><g:link controller="mensaje" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="mensaje" action="create" params="['publicacion.id': publicacionInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'mensaje.label', default: 'Mensaje')])}</g:link>
</li>
</ul>


</div>

<div class="fieldcontain ${hasErrors(bean: publicacionInstance, field: 'proteccion', 'error')} required">
	<label for="proteccion">
		<g:message code="publicacion.proteccion.label" default="Proteccion" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="proteccion" name="proteccion.id" from="${server.Proteccion.list()}" optionKey="id" required="" value="${publicacionInstance?.proteccion?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: publicacionInstance, field: 'quierenAdoptar', 'error')} ">
	<label for="quierenAdoptar">
		<g:message code="publicacion.quierenAdoptar.label" default="Quieren Adoptar" />
		
	</label>
	<g:select name="quierenAdoptar" from="${server.Usuario.list()}" multiple="multiple" optionKey="id" size="5" value="${publicacionInstance?.quierenAdoptar*.id}" class="many-to-many"/>

</div>

<div class="fieldcontain ${hasErrors(bean: publicacionInstance, field: 'raza', 'error')} required">
	<label for="raza">
		<g:message code="publicacion.raza.label" default="Raza" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="raza" name="raza.id" from="${server.Raza.list()}" optionKey="id" required="" value="${publicacionInstance?.raza?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: publicacionInstance, field: 'requiereCuidadosEspeciales', 'error')} ">
	<label for="requiereCuidadosEspeciales">
		<g:message code="publicacion.requiereCuidadosEspeciales.label" default="Requiere Cuidados Especiales" />
		
	</label>
	<g:checkBox name="requiereCuidadosEspeciales" value="${publicacionInstance?.requiereCuidadosEspeciales}" />

</div>

<div class="fieldcontain ${hasErrors(bean: publicacionInstance, field: 'sexo', 'error')} required">
	<label for="sexo">
		<g:message code="publicacion.sexo.label" default="Sexo" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="sexo" name="sexo.id" from="${server.Sexo.list()}" optionKey="id" required="" value="${publicacionInstance?.sexo?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: publicacionInstance, field: 'tamanio', 'error')} required">
	<label for="tamanio">
		<g:message code="publicacion.tamanio.label" default="Tamanio" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="tamanio" name="tamanio.id" from="${server.Tamanio.list()}" optionKey="id" required="" value="${publicacionInstance?.tamanio?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: publicacionInstance, field: 'tipoPublicacion', 'error')} required">
	<label for="tipoPublicacion">
		<g:message code="publicacion.tipoPublicacion.label" default="Tipo Publicacion" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="tipoPublicacion" type="number" value="${publicacionInstance.tipoPublicacion}" required=""/>

</div>

<div class="fieldcontain ${hasErrors(bean: publicacionInstance, field: 'vacunasAlDia', 'error')} required">
	<label for="vacunasAlDia">
		<g:message code="publicacion.vacunasAlDia.label" default="Vacunas Al Dia" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="vacunasAlDia" name="vacunasAlDia.id" from="${server.VacunasAlDia.list()}" optionKey="id" required="" value="${publicacionInstance?.vacunasAlDia?.id}" class="many-to-one"/>

</div>

