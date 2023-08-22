<%@page language="java" pageEncoding="UTF-8"%>	
<!DOCTYPE HTML PUBLIC "-//W3C//Ddiv HTML 4.01 Transitional//EN">
<html>
<head>
	<title>Realizando operación...</title>
	<link rel="stylesheet" href="css/barraprogreso.css" type="text/css" media="screen"/>
	<script src="js/loading.js" type="text/javascript"></script>
</head>
<body onload="start()">
	<div id="restart">
		<div id="finalizadoSms">
			<font style="text-align: center;">La operación ha finalizado con exito.</font>
		</div>
		<div id="button" onclick="window.close()">
			<img alt="cerrar" title="cerrar" src="./img/cerrar.png">
		</div>
	</div>
	<div id="loadingZone">
		<div id="loadingSms">Realizando operación...</div>
		<div id="infoProgress">0%</div>
		<br class="clear" />
		<div id="loadingBar">
			<div id="progressBar">&nbsp;</div>
		</div>
		<div id="infoLoading"></div>
	</div>
</body>
</html>