
<%@ page import="server.Publicacion" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'publicacion.label', default: 'Publicacion')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-publicacion" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-publicacion" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list publicacion">
			
				<g:if test="${publicacionInstance?.publicador}">
				<li class="fieldcontain">
					<span id="publicador-label" class="property-label"><g:message code="publicacion.publicador.label" default="Publicador" /></span>
					
						<span class="property-value" aria-labelledby="publicador-label"><g:link controller="usuario" action="show" id="${publicacionInstance?.publicador?.id}">${publicacionInstance?.publicador?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${publicacionInstance?.condiciones}">
				<li class="fieldcontain">
					<span id="condiciones-label" class="property-label"><g:message code="publicacion.condiciones.label" default="Condiciones" /></span>
					
						<span class="property-value" aria-labelledby="condiciones-label"><g:fieldValue bean="${publicacionInstance}" field="condiciones"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${publicacionInstance?.nombreMascota}">
				<li class="fieldcontain">
					<span id="nombreMascota-label" class="property-label"><g:message code="publicacion.nombreMascota.label" default="Nombre Mascota" /></span>
					
						<span class="property-value" aria-labelledby="nombreMascota-label"><g:fieldValue bean="${publicacionInstance}" field="nombreMascota"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${publicacionInstance?.videoLink}">
				<li class="fieldcontain">
					<span id="videoLink-label" class="property-label"><g:message code="publicacion.videoLink.label" default="Video Link" /></span>
					
						<span class="property-value" aria-labelledby="videoLink-label"><g:fieldValue bean="${publicacionInstance}" field="videoLink"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${publicacionInstance?.fotos}">
				<li class="fieldcontain">
					<span id="fotos-label" class="property-label"><g:message code="publicacion.fotos.label" default="Fotos" /></span>
					
						<g:each in="${publicacionInstance.fotos}" var="f">
						<span class="property-value" aria-labelledby="fotos-label"><g:link controller="foto" action="show" id="${f.id}">${f?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${publicacionInstance?.concretado}">
				<li class="fieldcontain">
					<span id="concretado-label" class="property-label"><g:message code="publicacion.concretado.label" default="Concretado" /></span>
					
						<span class="property-value" aria-labelledby="concretado-label"><g:link controller="usuario" action="show" id="${publicacionInstance?.concretado?.id}">${publicacionInstance?.concretado?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${publicacionInstance?.transito}">
				<li class="fieldcontain">
					<span id="transito-label" class="property-label"><g:message code="publicacion.transito.label" default="Transito" /></span>
					
						<span class="property-value" aria-labelledby="transito-label"><g:link controller="usuario" action="show" id="${publicacionInstance?.transito?.id}">${publicacionInstance?.transito?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${publicacionInstance?.direccion}">
				<li class="fieldcontain">
					<span id="direccion-label" class="property-label"><g:message code="publicacion.direccion.label" default="Direccion" /></span>
					
						<span class="property-value" aria-labelledby="direccion-label"><g:fieldValue bean="${publicacionInstance}" field="direccion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${publicacionInstance?.fechaConcretado}">
				<li class="fieldcontain">
					<span id="fechaConcretado-label" class="property-label"><g:message code="publicacion.fechaConcretado.label" default="Fecha Concretado" /></span>
					
						<span class="property-value" aria-labelledby="fechaConcretado-label"><g:formatDate date="${publicacionInstance?.fechaConcretado}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${publicacionInstance?.activa}">
				<li class="fieldcontain">
					<span id="activa-label" class="property-label"><g:message code="publicacion.activa.label" default="Activa" /></span>
					
						<span class="property-value" aria-labelledby="activa-label"><g:formatBoolean boolean="${publicacionInstance?.activa}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${publicacionInstance?.castrado}">
				<li class="fieldcontain">
					<span id="castrado-label" class="property-label"><g:message code="publicacion.castrado.label" default="Castrado" /></span>
					
						<span class="property-value" aria-labelledby="castrado-label"><g:link controller="castrado" action="show" id="${publicacionInstance?.castrado?.id}">${publicacionInstance?.castrado?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${publicacionInstance?.color}">
				<li class="fieldcontain">
					<span id="color-label" class="property-label"><g:message code="publicacion.color.label" default="Color" /></span>
					
						<span class="property-value" aria-labelledby="color-label"><g:link controller="color" action="show" id="${publicacionInstance?.color?.id}">${publicacionInstance?.color?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${publicacionInstance?.compatibleCon}">
				<li class="fieldcontain">
					<span id="compatibleCon-label" class="property-label"><g:message code="publicacion.compatibleCon.label" default="Compatible Con" /></span>
					
						<span class="property-value" aria-labelledby="compatibleCon-label"><g:link controller="compatibleCon" action="show" id="${publicacionInstance?.compatibleCon?.id}">${publicacionInstance?.compatibleCon?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${publicacionInstance?.denuncias}">
				<li class="fieldcontain">
					<span id="denuncias-label" class="property-label"><g:message code="publicacion.denuncias.label" default="Denuncias" /></span>
					
						<g:each in="${publicacionInstance.denuncias}" var="d">
						<span class="property-value" aria-labelledby="denuncias-label"><g:link controller="denuncia" action="show" id="${d.id}">${d?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${publicacionInstance?.edad}">
				<li class="fieldcontain">
					<span id="edad-label" class="property-label"><g:message code="publicacion.edad.label" default="Edad" /></span>
					
						<span class="property-value" aria-labelledby="edad-label"><g:link controller="edad" action="show" id="${publicacionInstance?.edad?.id}">${publicacionInstance?.edad?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${publicacionInstance?.energia}">
				<li class="fieldcontain">
					<span id="energia-label" class="property-label"><g:message code="publicacion.energia.label" default="Energia" /></span>
					
						<span class="property-value" aria-labelledby="energia-label"><g:link controller="energia" action="show" id="${publicacionInstance?.energia?.id}">${publicacionInstance?.energia?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${publicacionInstance?.especie}">
				<li class="fieldcontain">
					<span id="especie-label" class="property-label"><g:message code="publicacion.especie.label" default="Especie" /></span>
					
						<span class="property-value" aria-labelledby="especie-label"><g:link controller="especie" action="show" id="${publicacionInstance?.especie?.id}">${publicacionInstance?.especie?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${publicacionInstance?.fechaPublicacion}">
				<li class="fieldcontain">
					<span id="fechaPublicacion-label" class="property-label"><g:message code="publicacion.fechaPublicacion.label" default="Fecha Publicacion" /></span>
					
						<span class="property-value" aria-labelledby="fechaPublicacion-label"><g:formatDate date="${publicacionInstance?.fechaPublicacion}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${publicacionInstance?.latitud}">
				<li class="fieldcontain">
					<span id="latitud-label" class="property-label"><g:message code="publicacion.latitud.label" default="Latitud" /></span>
					
						<span class="property-value" aria-labelledby="latitud-label"><g:fieldValue bean="${publicacionInstance}" field="latitud"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${publicacionInstance?.longitud}">
				<li class="fieldcontain">
					<span id="longitud-label" class="property-label"><g:message code="publicacion.longitud.label" default="Longitud" /></span>
					
						<span class="property-value" aria-labelledby="longitud-label"><g:fieldValue bean="${publicacionInstance}" field="longitud"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${publicacionInstance?.necesitaTransito}">
				<li class="fieldcontain">
					<span id="necesitaTransito-label" class="property-label"><g:message code="publicacion.necesitaTransito.label" default="Necesita Transito" /></span>
					
						<span class="property-value" aria-labelledby="necesitaTransito-label"><g:formatBoolean boolean="${publicacionInstance?.necesitaTransito}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${publicacionInstance?.ofrecenTransito}">
				<li class="fieldcontain">
					<span id="ofrecenTransito-label" class="property-label"><g:message code="publicacion.ofrecenTransito.label" default="Ofrecen Transito" /></span>
					
						<g:each in="${publicacionInstance.ofrecenTransito}" var="o">
						<span class="property-value" aria-labelledby="ofrecenTransito-label"><g:link controller="usuario" action="show" id="${o.id}">${o?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${publicacionInstance?.papelesAlDia}">
				<li class="fieldcontain">
					<span id="papelesAlDia-label" class="property-label"><g:message code="publicacion.papelesAlDia.label" default="Papeles Al Dia" /></span>
					
						<span class="property-value" aria-labelledby="papelesAlDia-label"><g:link controller="papelesAlDia" action="show" id="${publicacionInstance?.papelesAlDia?.id}">${publicacionInstance?.papelesAlDia?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${publicacionInstance?.preguntas}">
				<li class="fieldcontain">
					<span id="preguntas-label" class="property-label"><g:message code="publicacion.preguntas.label" default="Preguntas" /></span>
					
						<g:each in="${publicacionInstance.preguntas}" var="p">
						<span class="property-value" aria-labelledby="preguntas-label"><g:link controller="mensaje" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${publicacionInstance?.proteccion}">
				<li class="fieldcontain">
					<span id="proteccion-label" class="property-label"><g:message code="publicacion.proteccion.label" default="Proteccion" /></span>
					
						<span class="property-value" aria-labelledby="proteccion-label"><g:link controller="proteccion" action="show" id="${publicacionInstance?.proteccion?.id}">${publicacionInstance?.proteccion?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${publicacionInstance?.quierenAdoptar}">
				<li class="fieldcontain">
					<span id="quierenAdoptar-label" class="property-label"><g:message code="publicacion.quierenAdoptar.label" default="Quieren Adoptar" /></span>
					
						<g:each in="${publicacionInstance.quierenAdoptar}" var="q">
						<span class="property-value" aria-labelledby="quierenAdoptar-label"><g:link controller="usuario" action="show" id="${q.id}">${q?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${publicacionInstance?.raza}">
				<li class="fieldcontain">
					<span id="raza-label" class="property-label"><g:message code="publicacion.raza.label" default="Raza" /></span>
					
						<span class="property-value" aria-labelledby="raza-label"><g:link controller="raza" action="show" id="${publicacionInstance?.raza?.id}">${publicacionInstance?.raza?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${publicacionInstance?.requiereCuidadosEspeciales}">
				<li class="fieldcontain">
					<span id="requiereCuidadosEspeciales-label" class="property-label"><g:message code="publicacion.requiereCuidadosEspeciales.label" default="Requiere Cuidados Especiales" /></span>
					
						<span class="property-value" aria-labelledby="requiereCuidadosEspeciales-label"><g:formatBoolean boolean="${publicacionInstance?.requiereCuidadosEspeciales}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${publicacionInstance?.sexo}">
				<li class="fieldcontain">
					<span id="sexo-label" class="property-label"><g:message code="publicacion.sexo.label" default="Sexo" /></span>
					
						<span class="property-value" aria-labelledby="sexo-label"><g:link controller="sexo" action="show" id="${publicacionInstance?.sexo?.id}">${publicacionInstance?.sexo?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${publicacionInstance?.tamanio}">
				<li class="fieldcontain">
					<span id="tamanio-label" class="property-label"><g:message code="publicacion.tamanio.label" default="Tamanio" /></span>
					
						<span class="property-value" aria-labelledby="tamanio-label"><g:link controller="tamanio" action="show" id="${publicacionInstance?.tamanio?.id}">${publicacionInstance?.tamanio?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${publicacionInstance?.tipoPublicacion}">
				<li class="fieldcontain">
					<span id="tipoPublicacion-label" class="property-label"><g:message code="publicacion.tipoPublicacion.label" default="Tipo Publicacion" /></span>
					
						<span class="property-value" aria-labelledby="tipoPublicacion-label"><g:fieldValue bean="${publicacionInstance}" field="tipoPublicacion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${publicacionInstance?.vacunasAlDia}">
				<li class="fieldcontain">
					<span id="vacunasAlDia-label" class="property-label"><g:message code="publicacion.vacunasAlDia.label" default="Vacunas Al Dia" /></span>
					
						<span class="property-value" aria-labelledby="vacunasAlDia-label"><g:link controller="vacunasAlDia" action="show" id="${publicacionInstance?.vacunasAlDia?.id}">${publicacionInstance?.vacunasAlDia?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:publicacionInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${publicacionInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
