<%@ page import="server.Publicacion" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'publicacion.label', default: 'Publicacion')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<a href="#show-publicacion" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                  default="Skip to content&hellip;"/></a>

<div id="show-publicacion" class="content scaffold-show" role="main">
    <h1>Mostrar Publicación</h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list publicacion">

        <g:if test="${publicacionInstance?.publicador}">
            <li class="fieldcontain">
                <span id="publicador-label" class="property-label"><g:message code="publicacion.publicador.label"
                                                                              default="Publicador"/></span>

                <span class="property-value" aria-labelledby="publicador-label">
                    <g:link action="publicaciones" controller="usuario" id="${publicacionInstance.publicador?.id}"> ${publicacionInstance?.publicador?.encodeAsHTML()}</g:link></span
            </li>
        </g:if>

        <g:if test="${publicacionInstance?.condiciones && publicacionInstance?.condiciones.trim()}">
            <li class="fieldcontain">
                <span id="condiciones-label" class="property-label"><g:message code="publicacion.condiciones.label"
                                                                               default="Condiciones"/></span>

                <span class="property-value" aria-labelledby="condiciones-label"><g:fieldValue
                        bean="${publicacionInstance}" field="condiciones"/></span>

            </li>
        </g:if>

        <g:if test="${publicacionInstance?.nombreMascota}">
            <li class="fieldcontain">
                <span id="nombreMascota-label" class="property-label"><g:message code="publicacion.nombreMascota.label"
                                                                                 default="Nombre Mascota"/></span>

                <span class="property-value" aria-labelledby="nombreMascota-label"><g:fieldValue
                        bean="${publicacionInstance}" field="nombreMascota"/></span>

            </li>
        </g:if>

        <g:if test="${publicacionInstance?.especie}">
            <li class="fieldcontain">
                <span id="especie-label" class="property-label"><g:message code="publicacion.especie.label"
                                                                           default="Especie"/></span>

                <span class="property-value"
                      aria-labelledby="especie-label">${publicacionInstance?.especie?.tipo.encodeAsHTML()}</span>

            </li>
        </g:if>

        <g:if test="${publicacionInstance?.videoLink}">
            <li class="fieldcontain">
                <span id="videoLink-label" class="property-label"><g:message code="publicacion.videoLink.label"
                                                                             default="Video"/></span>

                <span class="property-value" aria-labelledby="videoLink-label"><a
                        href="${publicacionInstance.videoLink}" target="_blank">Abrir video</a></span>

            </li>
        </g:if>

        <g:if test="${publicacionInstance?.fotos}">
            <li class="fieldcontain">
                <span id="fotos-label" class="property-label"><g:message code="publicacion.fotos.label"
                                                                         default="Fotos"/></span>

                <g:each in="${publicacionInstance.fotos}" var="f">
                    <span class="property-value" aria-labelledby="fotos-label">
                        <img alt="Embedded Image" src="data:image/png;base64,${f?.encodeAsHTML()}"/>
                    </span>
                </g:each>

            </li>
        </g:if>

        <g:if test="${publicacionInstance?.direccion}">
            <li class="fieldcontain">
                <span id="direccion-label" class="property-label">Dirección</span>

                <span class="property-value" aria-labelledby="direccion-label"><g:fieldValue
                        bean="${publicacionInstance}" field="direccion"/></span>

            </li>
        </g:if>

        <g:if test="${publicacionInstance?.activa}">
            <li class="fieldcontain">
                <span id="activa-label" class="property-label"><g:message code="publicacion.activa.label"
                                                                          default="Activa"/></span>

                <span class="property-value" aria-labelledby="activa-label"><g:formatBoolean
                        boolean="${publicacionInstance?.activa}" true="Si" false="No"/></span>

            </li>
        </g:if>

        <g:if test="${publicacionInstance?.castrado}">
            <li class="fieldcontain">
                <span id="castrado-label" class="property-label"><g:message code="publicacion.castrado.label"
                                                                            default="Castrado"/></span>

                <span class="property-value"
                      aria-labelledby="castrado-label">${publicacionInstance?.castrado?.tipo.encodeAsHTML()}</span>

            </li>
        </g:if>

        <g:if test="${publicacionInstance?.color}">
            <li class="fieldcontain">
                <span id="color-label" class="property-label"><g:message code="publicacion.color.label"
                                                                         default="Color"/></span>

                <span class="property-value"
                      aria-labelledby="color-label">${publicacionInstance?.color?.nombre.encodeAsHTML()}</span>

            </li>
        </g:if>

        <g:if test="${publicacionInstance?.compatibleCon}">
            <li class="fieldcontain">
                <span id="compatibleCon-label" class="property-label"><g:message code="publicacion.compatibleCon.label"
                                                                                 default="Compatible Con"/></span>

                <span class="property-value"
                      aria-labelledby="compatibleCon-label">${publicacionInstance?.compatibleCon?.compatibleCon.encodeAsHTML()}</span>

            </li>
        </g:if>

        <g:if test="${publicacionInstance?.edad}">
            <li class="fieldcontain">
                <span id="edad-label" class="property-label"><g:message code="publicacion.edad.label"
                                                                        default="Edad"/></span>

                <span class="property-value"
                      aria-labelledby="edad-label">${publicacionInstance?.edad?.nombre.encodeAsHTML()}</span>

            </li>
        </g:if>

        <g:if test="${publicacionInstance?.energia}">
            <li class="fieldcontain">
                <span id="energia-label" class="property-label"><g:message code="publicacion.energia.label"
                                                                           default="Energía"/></span>

                <span class="property-value"
                      aria-labelledby="energia-label">${publicacionInstance?.energia?.tipo.encodeAsHTML()}</span>

            </li>
        </g:if>



        <g:if test="${publicacionInstance?.fechaPublicacion}">
            <li class="fieldcontain">
                <span id="fechaPublicacion-label" class="property-label"><g:message
                        code="publicacion.fechaPublicacion.label" default="Fecha Publicación"/></span>

                <span class="property-value" aria-labelledby="fechaPublicacion-label"><g:formatDate
                        date="${publicacionInstance?.fechaPublicacion}" format="dd/MM/yyyy HH:mm 'Hs.'"/></span>

            </li>
        </g:if>


        <g:if test="${publicacionInstance?.necesitaTransito}">
            <li class="fieldcontain">
                <span id="necesitaTransito-label" class="property-label"><g:message
                        code="publicacion.necesitaTransito.label" default="Necesita Transito"/></span>

                <span class="property-value" aria-labelledby="necesitaTransito-label"><g:formatBoolean
                        boolean="${publicacionInstance?.necesitaTransito}"/></span>

            </li>
        </g:if>

        <g:if test="${publicacionInstance?.papelesAlDia}">
            <li class="fieldcontain">
                <span id="papelesAlDia-label" class="property-label"><g:message code="publicacion.papelesAlDia.label"
                                                                                default="Papeles Al Día"/></span>

                <span class="property-value"
                      aria-labelledby="papelesAlDia-label">${publicacionInstance?.papelesAlDia?.tipo.encodeAsHTML()}</span>

            </li>
        </g:if>

        <g:if test="${publicacionInstance?.proteccion}">
            <li class="fieldcontain">
                <span id="proteccion-label" class="property-label"><g:message code="publicacion.proteccion.label"
                                                                              default="Protección"/></span>

                <span class="property-value" aria-labelledby="proteccion-label">
                    ${publicacionInstance?.proteccion?.tipo.encodeAsHTML()}</span>

            </li>
        </g:if>

        <g:if test="${publicacionInstance?.raza}">
            <li class="fieldcontain">
                <span id="raza-label" class="property-label"><g:message code="publicacion.raza.label"
                                                                        default="Raza"/></span>

                <span class="property-value" aria-labelledby="raza-label">${publicacionInstance?.raza?.nombre.encodeAsHTML()}</span>

            </li>
        </g:if>

        <g:if test="${publicacionInstance?.requiereCuidadosEspeciales}">
            <li class="fieldcontain">
                <span id="requiereCuidadosEspeciales-label" class="property-label"><g:message
                        code="publicacion.requiereCuidadosEspeciales.label"
                        default="Requiere Cuidados Especiales"/></span>

                <span class="property-value" aria-labelledby="requiereCuidadosEspeciales-label"><g:formatBoolean
                        boolean="${publicacionInstance?.requiereCuidadosEspeciales}"/></span>

            </li>
        </g:if>

        <g:if test="${publicacionInstance?.sexo}">
            <li class="fieldcontain">
                <span id="sexo-label" class="property-label"><g:message code="publicacion.sexo.label"
                                                                        default="Sexo"/></span>

                <span class="property-value" aria-labelledby="sexo-label">${publicacionInstance?.sexo?.tipo.encodeAsHTML()}</span>

            </li>
        </g:if>

        <g:if test="${publicacionInstance?.tamanio}">
            <li class="fieldcontain">
                <span id="tamanio-label" class="property-label"><g:message code="publicacion.tamanio.label"
                                                                           default="Tamaño"/></span>

                <span class="property-value" aria-labelledby="tamanio-label">
                    ${publicacionInstance?.tamanio?.tipo.encodeAsHTML()}</span>

            </li>
        </g:if>

        <g:if test="${publicacionInstance?.tipoPublicacion}">
            <li class="fieldcontain">
                <span id="tipoPublicacion-label" class="property-label"><g:message
                        code="publicacion.tipoPublicacion.label" default="Tipo Publicación"/></span>

                <span class="property-value" aria-labelledby="tipoPublicacion-label">${publicacionInstance.tipoPublicacion == 1 ? 'Adopcion':
                 (publicacionInstance.tipoPublicacion == 2 ? 'Perdida' : 'Encontrada' )}</span>

            </li>
        </g:if>

        <g:if test="${publicacionInstance?.vacunasAlDia}">
            <li class="fieldcontain">
                <span id="vacunasAlDia-label" class="property-label"><g:message code="publicacion.vacunasAlDia.label"
                                                                                default="Vacunas Al Día"/></span>

                <span class="property-value" aria-labelledby="vacunasAlDia-label">
                    ${publicacionInstance?.vacunasAlDia?.tipo.encodeAsHTML()}</span>

            </li>
        </g:if>
        <g:if test="${publicacionInstance?.denuncias}">
            <li class="fieldcontain">
                <span id="denuncias-label" class="property-label"><strong><g:message code="publicacion.denuncias.label"
                                                                                     default="Denuncias"/></strong></span>

                <g:each in="${publicacionInstance.denuncias}" var="d">
                    <br/>
                    <span class="property-value"
                          aria-labelledby="denuncias-label">${d.denunciante.username} : ${d?.motivo}<br/>
							Descripción: ${d?.descripcion != '.' ? d?.descripcion : ''}
				  </span>
                </g:each>

            </li>
        </g:if>
    </ol>
</div>
</body>
</html>
