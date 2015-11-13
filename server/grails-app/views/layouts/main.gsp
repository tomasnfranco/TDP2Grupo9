<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title><g:layoutTitle default="Grails"/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="apple-touch-icon" href="${assetPath(src: 'apple-touch-icon.png')}">
		<link rel="apple-touch-icon" sizes="114x114" href="${assetPath(src: 'apple-touch-icon-retina.png')}">
  		<asset:stylesheet src="application.css"/>
		<asset:javascript src="application.js"/>
		<g:layoutHead/>
	</head>
	<body>
		<div id="grailsLogo" role="banner">
			<table width="100%">
				<tr>
					<td width="100px" height="100px" style="padding:0px;">
						<img src="${assetPath(src:'icono.png')}" alt="Busca Sus Huellas" style="width: 100px; float: left;height: 100px;">
					</td>
					<td style="vertical-align: middle;">
						<a href="#">BUSCA SUS HUELLAS</a>
					</td>
				</tr>
			</table>
		</div>
		<g:if test="${session.administrador}">
			<div class="nav" role="navigation">
				<ul>
					<li><g:link class="reportes" action="reporte" controller="publicacion">Estadísticas</g:link></li>
					<li><g:link class="usuarios" action="administrar" controller="usuario">Administrar Usuarios</g:link></li>
					<li><g:link class="publicaciones" action="administrar" controller="publicacion">Administrar Publicaciones</g:link></li>
					<li><g:link class="logout" action="logout" controller="hello">Cerrar Sesión</g:link> </li>
				</ul>
			</div>
		</g:if>
		<g:layoutBody/>

		<div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>
	</body>
</html>
