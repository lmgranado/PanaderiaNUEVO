
var xmlhttp;

function doCerrar(){
	window.close();
}

function loadXMLSubsanar(url)
{
 xmlhttp=null;
  // code for Mozilla, etc.
  if (window.XMLHttpRequest)
  {
   xmlhttp=new XMLHttpRequest()
  }
  // code for IE
  else if (window.ActiveXObject)
  {
   xmlhttp=new ActiveXObject("Microsoft.XMLHTTP")
  }
  if(xmlhttp!=null)
  {
   //xmlhttp.onreadystatechange=state_Change
   xmlhttp.onreadystatechange=function() {state_Subsana() };
   xmlhttp.open("GET",url,true)
   xmlhttp.send(null)
  }
 else
 {
   alert("Your browser does not support XMLHTTP.")
 }
}

function state_Subsana()
{
// if xmlhttp shows "loaded"
  if (xmlhttp.readyState==4)
  {
   // if "OK"
    if (xmlhttp.status==200)
    {
	    // ...some code here...
	    return xmlhttp.responseText;
	}
    else
    {
    alert("Problem retrieving XML data");
    }
  }
}

function doSubsanar(tifo, actionEnvio){
	vForm = document.getElementById(vFormName);	
	var nProy = document.getElementById('nProy').value;
	var url = 'doSubsanar.do?xTifo=' + tifo + '&nProy=' + nProy;
	setTimeout("loadXMLSubsanar('" + url + "')", 0); 
	vForm.action.value = actionEnvio + '?nProy=' + nProy;	
	_enviarDetalle('frmDetalle');
}

function validarCP(cp,provincia)
{
  var strPrefijo=cp.substring(0,2);
  if(provincia.value!=strPrefijo)
  {
    alert("El codigo postal no se corresponde con la provincia");
    return false;
  }
  return true;
}

function quitar_comas(numero){
	numero=numero.toString().replace(/\ |\./g,'');
	numero=numero.toString().replace(/\ |\,/g,'.');
	return numero;
}

function isCuentaBancariaMult(banco,sucursal,digitos,cuenta){
		/*
		 * Comprobar que los datos son correcto
		*/
       var cc1=banco.value.toString()+sucursal.value.toString();
       var cc2=cuenta.value.toString();
       var dc=digitos.value.toString();
       
    		if (!(cc1.match(/^\d{8}$/) && cc2.match(/^\d{10}$/) && dc.match(/^\d{2}$/) ))
       {
         alert("Número de cuenta bancaria no válido. Compruebe que haya escrito correctamente TODOS los dígitos");
         return false;
       }
      	    
   	  var arrWeights = new Array(1,2,4,8,5,10,9,7,3,6);	// vector de pesos
   		var dc1=0, dc2=0;
      	    
   		/*
   		 * Cálculo del primer dígito de cintrol
   		*/
  		    for (i=7;i>=0;i--) dc1 += arrWeights[i+2] * cc1.charAt(i);
   		dc1 = 11 - (dc1 % 11);
 		    if (11 == dc1) dc1 = 0;
  		    if (10 == dc1) dc1 = 1;
      	    
  		    /*
  		     * Cálculo del segundo dígito de control
  		    */
  		    for (i=9;i>=0;i--) dc2 += arrWeights[i] * cc2.charAt(i);
   		dc2 = 11 - (dc2 % 11);
  		    if (11 == dc2) dc2 = 0;
   		if (10 == dc2) dc2 = 1;
      	    
   		/*
   		 * Comprobar la coincidencia y delvolver el resultado
   		*/
    		if(!(10*dc1+dc2 == dc))
       {
         alert("Número de cuenta bancaria no válido. Compruebe que haya escrito correctamente TODOS los dígitos");// Javascript infiere tipo entero para dc1 y dc2
         return false
       }
       return true;
}

function hayChecked(form){
	hayCheck = false;
	vForm = document.getElementById("frmListado");
	if(vForm.checkList!= undefined){
		size = vForm.checkList.length;
		value = vForm.checkList.value;
		if (value != undefined)
			hayCheck = vForm.checkList.checked;
		if (size != undefined){
			i=0; 		
			while (i<size && !hayCheck){
				hayCheck = vForm.checkList[i].checked;
				i++;
			}		
		}
	}
	return hayCheck;
}

function hayCheckedImpresion(form){
	hayCheck = false;
	vForm = document.getElementById("frmListado");
	if(vForm.checkListImpresion!= undefined){
		size = vForm.checkListImpresion.length;
		value = vForm.checkListImpresion.value;
		if (value != undefined)
			hayCheck = vForm.checkListImpresion.checked;
		if (size != undefined){
			i=0; 		
			while (i<size && !hayCheck){
				hayCheck = vForm.checkListImpresion[i].checked;
				i++;
			}		
		}
	}
	return hayCheck;
}

function existeCheckbox(form){
	hayCheck = false;
	vForm = document.getElementById("frmListado");
	if(vForm.checkList!= undefined){
		hayCheck = true;
	}
	return hayCheck;
}


function hayMasDeUnChecked(form){
	hayCheck = false;
	contador=0;
	vForm = document.getElementById(form);
	if(vForm.checkList!= undefined){
		size = vForm.checkList.length;
		value = vForm.checkList.value;
		if (value != undefined)
			hayCheck = vForm.checkList.checked;
		if (size != undefined){
			i=0; 		
			while (i<size){
				if(vForm.checkList[i].checked){
					contador++;
					hayCheck = true;
				}
				i++;
			}
			if(contador>1){
				alert('No puede haber más de un elemento seleccionado');
				return false;
			}		
		}
	}
	if(!hayCheck){
		alert('Debe marcar algún elemento.');
		return false;
	}
	return hayCheck;
}

function doVolver(ruta){
	document.getElementById('frmDetalle').action=ruta;
	_enviarDetalle('frmDetalle');
}	

function doVolverListado( ruta ){
	document.getElementById('frmListado').action=ruta;
	_enviarDetalle('frmListado');
}


function _validaTeclaNumero(e)
{
		var key;
		var keychar;
	
		if (window.event)
			key = window.event.keyCode;
		else if (e)
			key = e.which;
		else
			return true;
		keychar = String.fromCharCode(key);
	
		// control keys
		if ((key==null) || (key==0) || (key==8) ||
			(key==9) || (key==13) || (key==27))
		   return true;
	
		// numbers
		else if ((("0123456789,").indexOf(keychar) > -1))
		   return true;
		   
		else
		   return false;
}

function _validaTeclaNumeroSinComa(e)
{
		var key;
		var keychar;
	
		if (window.event)
			key = window.event.keyCode;
		else if (e)
			key = e.which;
		else
			return true;
		keychar = String.fromCharCode(key);
	
		// control keys
		if ((key==null) || (key==0) || (key==8) ||
			(key==9) || (key==13) || (key==27))
		   return true;
	
		// numbers
		else if ((("0123456789").indexOf(keychar) > -1))
		   return true;
		   
		else
		   return false;
}

	function ventana(StrUrl, IntAncho, IntAlto, StrOtros)
	{
		if( StrOtros=="")
			StrOtros='scrollbars=yes';
			
		if (IntAncho=="")
			IntAncho=500;
	
		if (IntAlto=="")
			IntAlto=500;
	
			var PosX = (screen.availWidth - IntAncho)/2;
			var PosY = (screen.availHeight - IntAlto)/2;
	
		return(window.open(StrUrl, "","height=" + IntAlto + ",width=" + IntAncho + ",left=" + PosX + ",top=" +PosY + ",resizable=1," + StrOtros));
	}
	
	function ventanaNombre(StrUrl, IntAncho, IntAlto, StrOtros, StrNombre)
	{
		if( StrOtros=="")
			StrOtros='scrollbars=yes';
			
		if (IntAncho=="")
			IntAncho=500;
	
		if (IntAlto=="")
			IntAlto=500;
	
			var PosX = (screen.availWidth - IntAncho)/2;
			var PosY = (screen.availHeight - IntAlto)/2;
	
		return(window.open(StrUrl, StrNombre,"height=" + IntAlto + ",width=" + IntAncho + ",left=" + PosX + ",top=" +PosY + ",resizable=1," + StrOtros));
	}

  	function buscaFormularioAlQuePertenece(elemento) {
	  var elto = elemento;
	  while(elto != null && !elto.action) {
	  	elto = elto.parentNode;
	  }
	  return elto;
	}
	
	function doBuscar(elemento) {
		var form = buscaFormularioAlQuePertenece(elemento);
		document.getElementById('filtro').value = '1';
		form.target = "";
		form.submit();
	}
	
	function doLimpiar(elemento) {
		var vForm = buscaFormularioAlQuePertenece(elemento);

		for (i=0;i<vForm.elements.length;i++) {
			if (vForm.elements[i].type=="text"){
				vForm.elements[i].value = "";
			}
			else if (vForm.elements[i].type=="select-one"){
				vForm.elements[i].value = "";
			}
		}
		document.getElementById('filtro').value = '1';
		vForm.target = "";
		//vForm.submit();
	}
	
	function change_pagesize(elemento) {
		var pagesize = elemento[elemento.selectedIndex].value;
		var form = buscaFormularioAlQuePertenece(elemento);
		form.action = form.action + '?filtro=1&pagesize=' + pagesize;
		form.submit();
	}	
	
	function checkUncheck() {
		var inputs;
		inputs = document.getElementsByTagName('input');
		var encontrado = false;
		var checked = 'checked';
				 
		for(var i=0; i < inputs.length && !encontrado; i++) {
	
			if(inputs[i].getAttribute('type')=='checkbox' && inputs[i].id=='checkList') {	
				if (!inputs[i].checked && inputs[i].disabled==false) {
					encontrado = true;
				}
			}
		}
		if (!encontrado) {
			checked = '';
		}
		for(var i=0; i < inputs.length; i++) {
			if(inputs[i].disabled==false && inputs[i].id=='checkList'){
				inputs[i].checked = checked;
			}
		}
	}	
		
	function checkUncheckImpresion() {
		var inputs;
		inputs = document.getElementsByTagName('input');
		var encontrado = false;
		var checked = 'checked';
				 
		for(var i=0; i < inputs.length && !encontrado; i++) {
	
			if(inputs[i].getAttribute('type')=='checkbox' && inputs[i].id=='checkListImpresion') {	
				if (!inputs[i].checked && inputs[i].disabled==false) {
					encontrado = true;
				}
			}
		}
		if (!encontrado) {
			checked = '';
		}
		for(var i=0; i < inputs.length; i++) {
			if(inputs[i].disabled==false && inputs[i].id=='checkListImpresion'){
				inputs[i].checked = checked;
			}
		}
	}
	
	function limpiarBusqueda(nombre) {
	    var form = eval('document.'+nombre);
	    form.reset();
	    var eltos = form.elements;
	    for(var i=0; i<eltos.length;i++) {
	    	if(eltos[i].type!='hidden') {
	    		eltos[i].value = '';
	    	}
	    	if(eltos[i].type=='select-one') {
	    		eltos[i].selectedIndex=0;
	    	}
	    }
	    
	    form.submit();
	    
	}
	

// -------------- Código para AJAX	 --------------


	function loadTextDoc( url, campo )
	{
		  xmlhttp=null;
		  // code for Mozilla, etc.
		  if (window.XMLHttpRequest)
		  {
		   	xmlhttp=new XMLHttpRequest();
		  }
		  // code for IE
		  else if (window.ActiveXObject)
		  {
		   	xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		  }
		  if(xmlhttp!=null)
		  {
		   		xmlhttp.onreadystatechange=function(){ getTexto(campo) };	
		   		xmlhttp.open("GET",url,true);			
	   			xmlhttp.send(null);
		  }
		 else
		 {
		   alert("Your browser does not support XMLHTTP.")
		 }
	}
	
	function getTexto(campo)
	{
	// if xmlhttp shows "loaded"
	  if (xmlhttp.readyState==4)
	  {
	   // if "OK"
	    if (xmlhttp.status==200)
	    {
		    // ...some code here...
		     var valores = xmlhttp.responseText;
		     document.getElementById(campo).value = valores;
		}
	    else
	    {
	    alert("Problem retrieving XML data")
	    }
	  }
	}
	
	 function sleep(milliSeconds){ 
		var startTime = new Date().getTime(); // get the current time 
		while (new Date().getTime() < startTime + milliSeconds); 
	} 
	
	
	function loadXMLDoc(url,tipo)
	{
		
	  xmlhttp=null;
	  // code for Mozilla, etc.
	  if (window.XMLHttpRequest)
	  {
	   xmlhttp=new XMLHttpRequest()
	  }
	  // code for IE
	  else if (window.ActiveXObject)
	  {
	   xmlhttp=new ActiveXObject("Microsoft.XMLHTTP")
	  }
	  if(xmlhttp!=null)
	  {
	   //xmlhttp.onreadystatechange=state_Change
	   xmlhttp.onreadystatechange=function() {state_Change(tipo) };
	   xmlhttp.open("GET",url,true)
	   xmlhttp.send(null)
	  }
	 else
	 {
	   alert("Your browser does not support XMLHTTP.")
	 }
	}
	
	function state_Change(tipo)
	{
	// if xmlhttp shows "loaded"
	  if (xmlhttp.readyState==4)
	  {
	   // if "OK"
	    if (xmlhttp.status==200)
	    {
		    // ...some code here...
		     var valores = xmlhttp.responseText.split("~");
		     
			 // Ponemos los valores
			 for(i=0; i<valores.length-1; i++){
			 	var opciones = valores[i].split("__");
			 	document.getElementById(tipo).options[i] = new Option(opciones[1], opciones[0]);
			 }
		}
	    else
	    {
	    alert("Problem retrieving XML data")
	    }
	  }
	}
	
	function loadXMLDocInput(url,tipo)
	{
		alert( "enta en load");
	  xmlhttp=null;
	  // code for Mozilla, etc.
	  if (window.XMLHttpRequest)
	  {
	   xmlhttp=new XMLHttpRequest()
	  }
	  // code for IE
	  else if (window.ActiveXObject)
	  {
	   xmlhttp=new ActiveXObject("Microsoft.XMLHTTP")
	  }
	  if(xmlhttp!=null)
	  {
	   //xmlhttp.onreadystatechange=state_Change
	   xmlhttp.onreadystatechange=function() {state_ChangeInput(tipo) };
	   
	   xmlhttp.open("GET",url,true)
	   xmlhttp.send(null)
	  }
	 else
	 {
	   alert("Your browser does not support XMLHTTP.")
	 }
	}
	
	function state_ChangeInput(tipo)
	{
	// if xmlhttp shows "loaded"
	  if (xmlhttp.readyState==4)
	  {
	   // if "OK"
	    if (xmlhttp.status==200)
	    {
		    // ...some code here...
		     var valor = xmlhttp.responseText;
		     
			 // Ponemos el
			 document.getElementById(tipo).value = valor;
		}
	    else 
	    {
	    alert("Problem retrieving XML data")
	    }
	  }
	}
	
	
	//Luis Miguel
	function loadXMLDocInput(url,tipo1, tipo2)
	{
		
	  xmlhttp=null;
	  // code for Mozilla, etc.
	  if (window.XMLHttpRequest)
	  {
	   xmlhttp=new XMLHttpRequest()
	  }
	  // code for IE
	  else if (window.ActiveXObject)
	  {
	   xmlhttp=new ActiveXObject("Microsoft.XMLHTTP")
	  }
	  if(xmlhttp!=null)
	  {
		  //xmlhttp.onreadystatechange=state_Change
		  xmlhttp.onreadystatechange=function() {state_ChangeInputs(tipo1, tipo2) };
		  xmlhttp.open("GET",url,true)
		  xmlhttp.send(null)
	  }
	 else
	 {
	   alert("Your browser does not support XMLHTTP.")
	 }
	}
	
	
	function state_ChangeInputs(tipo1, tipo2)
	{
	// if xmlhttp shows "loaded"
	  if (xmlhttp.readyState==4)
	  {
	   // if "OK"
	    if (xmlhttp.status==200)
	    {
	    	 // ...some code here...
		     //var valor = xmlhttp.responseText;
		     var valores = xmlhttp.responseText.split("__");
			 // Ponemos el
			 document.getElementById(tipo1).value = valores[0];
			 document.getElementById(tipo2).value = valores[1];
			 //document.getElementById(tipo2).value = valores[1];
			 
		}
	    else 
	    {
	    alert("Problem retrieving XML data")
	    }
	  }
	}
// ------------------------------	

	function aplicarValor(valor, campo){
		 if(valor!=null && valor!=''){
		 	document.getElementById(campo).value = valor;
		 }
	}

function formato_numero(num,opcion) 
{
  return formato_numero2(num, opcion, 2);
}

function formato_numero2(num,opcion, nDecimales) 
{
  if( num == "" ) num = "0";
    
  negativo = false;
  if (num < 0){
    negativo = true;          
    num = Math.abs(num);
  }
  
  /*if(num.indexOf('.')!=-1){
  		alert("Sustituya los '.' (puntos) por ',' (comas)");
  		num = "";
  		return "";
  }*/
  num = num.toString().replace(/\ |\,/g,'.');
  //num = num.toString().replace(/\ |\./g,',');
  
  //num = num.toString().replace(/\ |\,/g,'');
    
  if(isNaN(num)) num = "0";

  if( nDecimales > 0 )
  {
    indicePuntoDecimal = num.lastIndexOf('.');
    if( indicePuntoDecimal != -1 )
    {
      cents = num.substring( indicePuntoDecimal+1, num.length );
      if( cents.length > nDecimales )
      {
        cents = Math.round((cents/Math.pow(10,cents.length-nDecimales))).toString();
        if( cents.length > nDecimales )
        {
          num = parseFloat(num)+1;
          num = num.toString();
          cents = '0';
        }
        while( cents.length < nDecimales )
        {
          cents = '0' + cents;
        }
      }
    }
    else
    {
      cents = "";
    }

    indicePuntoDecimal = num.lastIndexOf('.');
    if( indicePuntoDecimal != -1 )
    {
      num = num.substring( 0, indicePuntoDecimal );
    }
    num = Math.round(num*Math.pow(10,nDecimales)/Math.pow(10,nDecimales)).toString();
  }
  else
  {
    cents = "";
    num = Math.round(num).toString();
  }

  while( cents.length < nDecimales )
  {
    cents = cents + '0';
  }
      
  for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++){
    //num = num.substring(0,num.length-(4*i+3))+'.'+num.substring(num.length-(4*i+3));
    num = num.substring(0,num.length-(4*i+3))+''+num.substring(num.length-(4*i+3));
  }
      
  if (negativo)
    num = '-' + num;
  
  if( nDecimales > 0 )
    num = num + ',' + cents;
  
  return num;
}

function _enviarDetalle(nameFormDetalle){
	vForm = document.getElementById(nameFormDetalle);
	vForm.submit();	
}

function _enviarDetalleAccion(nameFormDetalle){
	vForm = document.getElementById(nameFormDetalle);
	vForm.action.value = vForm.action.value + '?accion=true';
	vForm.submit();	
}

function MostrarFechaActual() 
{ 
	var nombre_dia = new Array("domingo", "lunes", "martes", "miércoles", "jueves", "viernes", "sábado");
	var nombre_mes = new Array("enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre");
	
	var hoy_es = new Date(); 
	dia_mes = hoy_es.getDate(); 
	dia_semana = hoy_es.getDay(); 
	mes = hoy_es.getMonth() + 1; 
	anyo = hoy_es.getYear(); 
	
	if (anyo < 100)
		anyo = '19' + anyo;
	else 
		if ( ( anyo > 100 ) && ( anyo < 999 ) )
		{
			var cadena_anyo = new String(anyo) ; 
			anyo = '20' + cadena_anyo.substring(1,3);
		} 
	document.write(nombre_dia[dia_semana] + ", " + dia_mes + " de " + nombre_mes[mes - 1] + " de " + anyo);
} 

function fechaActualFormato() 
{ 
	var hoy_es = new Date(); 
	dia_mes = hoy_es.getDate(); 
	mes = hoy_es.getMonth() + 1; 
	anyo = hoy_es.getYear(); 
	
	if (anyo < 100)
		anyo = '19' + anyo;
	else if ( ( anyo > 100 ) && ( anyo < 999 ) )
		{
			var cadena_anyo = new String(anyo) ; 
			anyo = '20' + cadena_anyo.substring(1,3);
		} 
	return (dia_mes + '/' + mes + '/' + anyo);
} 
	
// RELOJ 24 HORAS 
var Reloj24H = null;
var RelojEnMarcha = false;

function DetenerReloj24 ()
{
	if(RelojEnMarcha) 
		clearTimeout(Reloj24H); 
	RelojEnMarcha = false;
} 

function MostrarHoraActual() 
{ 
	var ahora = new Date();
	var hora = ahora.getHours();
	var minuto = ahora.getMinutes();
	var segundo = ahora.getSeconds(); 
	var HHMMSS;
	
	if (hora < 10) 
	{
		HHMMSS = "0" + hora;
	}
	else 
	{
		HHMMSS = " " + hora;
	}
	
	if (minuto < 10) 
	{
		HHMMSS += ":0" + minuto;
	}
	else 
	{
		HHMMSS += ":" + minuto;
	} 
	
	if (segundo < 10) 
	{
		HHMMSS += ":0" + segundo;
	}
	else 
	{
		HHMMSS += ":" + segundo;
	} 
	
	document.getElementById('digitos').value = HHMMSS;
	Reloj24H = setTimeout("MostrarHoraActual()",1000);
	RelojEnMarcha = true;
} 

function IniciarReloj24()
{ 
	DetenerReloj24(); 
	MostrarHoraActual();
} 

function changeCase(nombre)
{
	var index;
	var tmpStr;
	var tmpChar;
	var preString;
	var postString;
	var strlen;
	tmpStr = nombre.toLowerCase();
	strLen = tmpStr.length;
	if (strLen > 0) 
	{
		for (index = 0; index < strLen; index++) 
		{
			if (index == 0) 
			{
				tmpChar = tmpStr.substring(0,1).toUpperCase();
				postString = tmpStr.substring(1,strLen);
				tmpStr = tmpChar + postString;
			}
			else 
			{
				tmpChar = tmpStr.substring(index, index+1);
				if (tmpChar == " " && index < (strLen-1)) 
				{
					tmpChar = tmpStr.substring(index+1, index+2).toUpperCase();
					preString = tmpStr.substring(0, index+1);
					postString = tmpStr.substring(index+2,strLen);
					tmpStr = preString + tmpChar + postString;
				}
			}
		}
	}
	document.reloj.nombre.value = tmpStr;
}

function formato_entero(numero){
  if(numero.indexOf('.')!=-1 || numero.indexOf(',')!=-1){
  	alert("Este numero debe ser entero (sin decimales, ni puntos ni comas)");
  	return "0";
  }
   numero = parseInt(numero,10);
   if (isNaN(numero))
      return "0";
   else if (numero=='')
   	  return "0";
   else
      return numero;
}


function validarNifCifNie (texto){
  var letra = texto.substr(0,1);
  
  if ( isNaN(letra) ){  
    if(letra == "X" || letra =="x"){
      return validar_nie(texto);
    }
  }

  return validarNifCif(texto);
}

function validarNifCif(texto)
{
  var letra = texto.substr(0,1);
  
  if ( isNaN(letra) )
    return validar_cif(texto);
  else
    return validar_nif(texto);
}

function validar_nie(valor)
{
  value=valor;
  letra= new Array();
  letra[0] = "T";
  letra[1] = "R";
  letra[2] = "W";
  letra[3] = "A";
  letra[4] = "G";
  letra[5] = "M";
  letra[6] = "Y";
  letra[7] = "F";
  letra[8] = "P";
  letra[9] = "D";
  letra[10] = "X";
  letra[11] = "B";
  letra[12] = "N";
  letra[13] = "J";
  letra[14] = "Z";
  letra[15] = "S";
  letra[16] = "Q";
  letra[17] = "V";
  letra[18] = "H";
  letra[19] = "L";
  letra[20] = "C";
  letra[21] = "K";
  letra[22] = "E";
  
  if(value.length<9)
  {
    alert("El NIE no es correcto. Debe tener 9 dígitos");
    //valor.focus();
    return false;
  }
  else
  {
    var letra1=value.substring(0,1);
    var numeros="0" + value.substring(1,8);
    var letra2=value.substring(8,9).toUpperCase();
    if(isNaN(numeros) || !isNaN(letra1) || !isNaN(letra2))
    {
      alert("El NIE no es correcto.Debe tener una letra al principio, siete números y una letra al final");
      //valor.focus();
      return false;
    }
    else
    {
      if(letra1!='X'){
        alert("el valor introducido no se corresponde con un NIE");
        return false;
      }
      var letra_2=(numeros%23);
      if(letra[letra_2]!=letra2)
      {
        alert("La letra final del NIE no es correcta");
        //valor.focus();
        return false;
      }
    }
  }
  return true;
}

function validar_cif(valor){
		var cif=valor;
		if(!/^[A-Za-z0-9]{9}$/.test(cif)){
			alert("El CIF no es correcto. Debe tener 9 dígitos");
			return false;
		}
		else if(!/^[ABCDEFGHKLMNPQS]/.test(cif)){
			alert("El CIF no es correcto. El primer dígito debe ser una letra de las siguientes: A,B,C,D,E,F,G,H,K,L,M,N,P,Q,S");
			return false;
		}
		
		var letra=cif.substr(0,1);
		var numeros=cif.substr(1,7);
		var control=cif.substr(8,9);
		if(isNaN(numeros)){
			alert("El CIF no e correcto. Los 7 dígitos del medio del CIF deben ser números");
			return false;
		}
		var v1 = new Array(0,2,4,6,8,1,3,5,7,9);
		var temp=0;
		var temp1;
		
		for(i=2;i<=6;i+=2){
			temp=temp + v1[parseInt(cif.substr(i-1,1))];
			temp=temp + parseInt(cif.substr(i,1));
		}
		temp=temp + v1[parseInt(cif.substr(7,1))];
		temp=(10-(temp%10));
		if(temp == 10){
			if(letra=='P' || letra=='Q' || letra=='S'){
				temp="J";
			}
			else{
				temp=0;
			}
		}
		else if(temp==9){
			if(letra=='P' || letra=='Q' || letra=='S'){
				temp="I";
			}
		}
		else if(temp==8){
			if(letra=='P' || letra=='Q' || letra=='S'){
				temp="H";
			}
		}
		else if(temp==7){
			if(letra=='P' || letra=='Q' || letra=='S'){
				temp="G";
			}
		}
		else if(temp==6){
			if(letra=='P' || letra=='Q' || letra=='S'){
				temp="F";
			}
		}
		else if(temp==5){
			if(letra=='P' || letra=='Q' || letra=='S'){
				temp="E";
			}
		}
		else if(temp==4){
			if(letra=='P' || letra=='Q' || letra=='S'){
				temp="D";
			}
		}
		else if(temp==3){
			if(letra=='P' || letra=='Q' || letra=='S'){
				temp="C";
			}
		}
		else if(temp==2){
			if(letra=='P' || letra=='Q' || letra=='S'){
				temp="B";
			}
		}
		else if(temp==1){
			if(letra=='P' || letra=='Q' || letra=='S'){
				temp="A";
			}
		}
		if(temp!=control){
			alert("el valor del último dígito del CIF no es correcto");
			return false;
		}
		return true;
}

function validar_nif(value)
	{
		var letra= new Array("T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E");
	
		if(value.length<9)
		{
			alert("El NIF de la persona que cumplimenta el cuestionario no es correcto. Debe tener 8 dígitos y una letra al final");
			return false;
		}
		else
		{
			var numeros=value.substring(0,8);
			var letranif=value.substring(8,9).toUpperCase();
			if(isNaN(numeros) || !isNaN(letranif))
			{
				alert("El NIF de la persona que cumplimenta el cuestionario no es correcto.Debe tener 8 dígitos y una letra al final");
				return false;
			}
			else{
				var letra_2=(numeros%23);
				if(letra[letra_2]!=letranif)
				{
					alert("La letra del NIF de la persona que cumplimenta el cuestionario no es correcta");
        	return false;
				}
			}
		}
		return true;
	}

function validarCIF(texto){
	    var pares = 0;
	    var impares = 0;
	    var suma;
	    var ultima;
	    var unumero;
	    var uletra = new Array("J", "A", "B", "C", "D", "E", "F", "G", "H", "I");
	    var xxx;
	  
	    texto = texto.toUpperCase();
	    
	    var regular =/^[ABCDEFGHKLMNPQS]\d\d\d\d\d\d\d[0-9,A-J]$/g;
	     if (!regular.exec(texto)) return false;
	         
	     ultima = texto.substr(8,1);
	
	     for (var cont = 1 ; cont < 7 ; cont ++){
	         xxx = (2 * parseInt(texto.substr(cont++,1))).toString() + 0;
	         impares += parseInt(xxx.substr(0,1)) + parseInt(xxx.substr(1,1));
	         pares += parseInt(texto.substr(cont,1));
	     }
	     xxx = (2 * parseInt(texto.substr(cont,1))).toString();
	     impares += parseInt(xxx.substr(0,1)) + parseInt(0 + xxx.substr(1,1));
	     
	     suma = (pares + impares).toString();
	     unumero = parseInt(suma.substr(suma.length - 1, 1));
	     unumero = (10 - unumero).toString();
	     if(unumero == 10) unumero = 0;
	     
	     if ((ultima == unumero) || (ultima == uletra[unumero]))
	         return true;
	     else
	         return false;
	
	} 
function compararFecha(Obj1,Obj2) 
	{
		String1 = Obj1;
		String2 = Obj2;
		// Si los dias y los meses llegan con un valor menor que 10 
		// Se concatena un 0 a cada valor dentro del string 
		if (String1.substring(1,2)=="/") {
			String1="0"+String1
		}
		if (String1.substring(4,5)=="/"){
			String1=String1.substring(0,3)+"0"+String1.substring(3,9)
		}
		
		if (String2.substring(1,2)=="/") {
			String2="0"+String2
		}
		if (String2.substring(4,5)=="/"){
			String2=String2.substring(0,3)+"0"+String2.substring(3,9)
		}
		
		dia1=String1.substring(0,2);
		mes1=String1.substring(3,5);
		anyo1=String1.substring(6,10);
		dia2=String2.substring(0,2);
		mes2=String2.substring(3,5);
		anyo2=String2.substring(6,10);
		
		
		if (dia1 == "08") // parseInt("08") == 10 base octogonal
			dia1 = "8";
		if (dia1 == '09') // parseInt("09") == 11 base octogonal
			dia1 = "9";
		if (mes1 == "08") // parseInt("08") == 10 base octogonal
			mes1 = "8";
		if (mes1 == "09") // parseInt("09") == 11 base octogonal
			mes1 = "9";
		if (dia2 == "08") // parseInt("08") == 10 base octogonal
			dia2 = "8";
		if (dia2 == '09') // parseInt("09") == 11 base octogonal
			dia2 = "9";
		if (mes2 == "08") // parseInt("08") == 10 base octogonal
			mes2 = "8";
		if (mes2 == "09") // parseInt("09") == 11 base octogonal
			mes2 = "9";
		
		dia1=parseInt(dia1);
		dia2=parseInt(dia2);
		mes1=parseInt(mes1);
		mes2=parseInt(mes2);
		anyo1=parseInt(anyo1);
		anyo2=parseInt(anyo2);
		
		if (anyo1>anyo2)
		{
			return false;
		}
		
		if ((anyo1==anyo2) && (mes1>mes2))
		{
			return false;
		}
		if ((anyo1==anyo2) && (mes1==mes2) && (dia1>dia2))
		{
			return false;
		} 
		
		return true;
	}

	
	
function aceptaNumerosEnteros(evt){
	var nav4 = window.Event ? true : false;
	// NOTE: Backspace = 8, Enter = 13, '0' = 48, '9' = 57, '-' = 45
	var key = nav4 ? evt.which : evt.keyCode;
	return (key <= 13 || (key >= 48 && key <= 57) || key == 45);
}

/*	Funcion ejecutada en el keypress de una celda,para que solo acepte numeros reales en dicha celda.	*/
function aceptaNumerosReales(evt)
{
	var nav4 = window.Event ? true : false;
	// NOTE: Backspace = 8, Enter = 13, '0' = 48, '9' = 57, '-' = 45, '.' = 46
	var key = nav4 ? evt.which : evt.keyCode;
	return (key <= 13 || (key >= 48 && key <= 57) || key == 45 || key == 46);
}

function diferencia(fecha, fecha2){  
    var xMonth=fecha.substring(3, 5);  
    var xDay=fecha.substring(0, 2);  
    var xYear=fecha.substring(6,10);  
    var yMonth=fecha2.substring(3, 5);  
    var yDay=fecha2.substring(0, 2);  
    var yYear=fecha2.substring(6,10); 
    var resultado = 0;
    resultado = (yYear - xYear)*365;
    resultado = resultado + (yMonth - xMonth)*30;
    return resultado;
}

function compare_dates(fecha, fecha2)  
{  
    var xMonth=fecha.substring(3, 5);  
    var xDay=fecha.substring(0, 2);  
    var xYear=fecha.substring(6,10);  
    var yMonth=fecha2.substring(3, 5);  
    var yDay=fecha2.substring(0, 2);  
    var yYear=fecha2.substring(6,10);  
    if (xYear> yYear)  
    {  
        return(true)  
    }  
    else  
    {  
      if (xYear == yYear)  
      {   
        if (xMonth> yMonth)  
        {  
            return(true)  
        }  
        else  
        {   
          if (xMonth == yMonth)  
          {  
            if (xDay>= yDay)  
              return(true);  
            else  
              return(false);  
          }  
          else  
            return(false);  
        }  
      }  
      else  
        return(false);  
    }  
}  