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
		<div id="grailsLogo" role="banner"><h1><a href="#" style="font-weight: bold;text-decoration:none;cursor:default;">BUSCA SUS HUELLAS</a></h1></div>
		<g:if test="${session.administrador}">
			<div class="nav" role="navigation">
				<ul>
					<li><g:link class="list" action="reporte" controller="publicacion" style="background-image: url(../assets/skin/chart_bar.png);">Estadísticas</g:link></li>
					<li><g:link class="list" action="administrar" controller="usuario" style="background-image: url(../assets/skin/group.png);">Administrar Usuarios</g:link></li>
					<li><g:link class="list" action="reporte" controller="publicacion" style="background-image: url(../assets/skin/table_edit.png);">Administrar Publicaciones</g:link></li>
				</ul>
			</div>
		</g:if>
		<g:layoutBody/>

		<div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>
	</body>
</html>
