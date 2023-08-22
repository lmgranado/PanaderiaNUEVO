// --------------- Comienzo Variables a modificar --------------------------------

// Url for the next page when all the necessary components are detected
var nextUrl =  "../doAutenticacion.do";

// Url base of digital sign se
var url_base = "..";


// --------------- Fin variables a modificar --------------------------------------


// Url para ir a buscar la pagina muestra los links para ver que se instala
var dnUrl = url_base + "/servicio/paginas/Descargas.jsp";
var dnUrlLinux = url_base + "/servicio/paginas/DescargasLinux.jsp";

// Url del applet de deteccion de componentes instalados
var strcodebase_detect_jar = "TelventDetect.jar";
var strcodebase_detect_cab = "TelventDetect.cab";

function InstallProduct(strPrevURL)
{
  alert ("No se encuentran los componentes necesarios para la firma digital. Redireccionando a la página de instalación.");
  window.location.href=strPrevURL;
}


function Proceed()
{
  //alert("Los componentes necesarios han sido verficados. Accediendo..");
  if ( nextUrl != "")
    window.location.href =nextUrl;
}

var strOS = navigator.platform;


if (strOS.toLowerCase().indexOf("win") == -1)
  strOS = "Unix";
else
  strOS = "win";



if (strOS.toLowerCase().indexOf("win") != -1)
{
  if(navigator.appName.toLowerCase() == "netscape" )
  {
    var appletMimeType = navigator.mimeTypes['application/x-java-applet'];
    if(!appletMimeType)
    {  
      alert ("Se necesita el Plug-in JRE 1.4. Redireccionando a la página de instalación.");
      window.location.href=dnUrl +'?download=jvmneeded,clientnotchecked';
    }
    else
    {

      var description = appletMimeType.enabledPlugin.description;
      if( description.indexOf("Java")==-1 && description.indexOf("Plug-in")==-1 && description.indexOf("1.4")==-1 && description.indexOf("1.5")==-1 && description.indexOf("1.6")==-1 )
      {
        alert("Detectado "+description);
        alert ("Se necesita el Plug-in JRE 1.4 o superior. Redireccionando a la página de instalación.");
        window.location.href=dnUrl +'?download=jvmneeded,clientnotchecked';
      }
      else
      {
        //alert("Detectado Plug-in JRE 1.4 o 1.5 neceario. Asegurese de tener HABILITADO el Plug-in en el Panel de Control.");
  
        if(parseInt(navigator.appVersion, 10) < 5)
        {
          var str;
  
          str += '<EMBED type="application/x-java-applet;version=1.4" width="1" ';
          str += 'height="1" align="baseline" code="DetectVM.class" ';
          str += 'codebase="." ARCHIVE="TelventDetect.jar" ';
          str += 'FtpURL = "' + dnUrl + '" Browser ="Netscape" OS="' + strOS + '" > ';
          str += '</embed>';  
          document.write(str);
        }
        else
        {
          document.write ('<APPLET CODE="DetectVM.class" width="0" height="0"  ARCHIVE="' +strcodebase_detect_jar + '" MAYSCRIPT><PARAM NAME=FtpURL VALUE="' + dnUrl + '"> <PARAM NAME="Browser" VALUE="netscape"> <PARAM NAME=OS VALUE="' + strOS + '"></APPLET>');
        }
      }
    }
  }
  else // IE
  {
    if((navigator.appVersion.indexOf("Windows NT 5.1") != -1) || (navigator.appVersion.indexOf("Windows NT 5.2") != -1) || (navigator.appVersion.indexOf("Windows NT 6.0") != -1)) // Estamos en XP
    {
        window.location.href='XPMensaje.htm';
    }
    else
    {    
      document.write ('<APPLET CODE="DetectVM.class" width="0" height="0"  ARCHIVE="' +strcodebase_detect_jar + '" MAYSCRIPT><PARAM NAME=cabbase VALUE="'+ strcodebase_detect_cab +'"> <PARAM NAME=FtpURL VALUE="' + dnUrl + '"> <PARAM NAME="Browser" VALUE="IE"> <PARAM NAME=OS VALUE="' + strOS + '">  </APPLET>');
      document.close();
    }
  }
}
else
{
  // Detectamos navegador
  var temp = navigator.userAgent;
  if(temp.indexOf("rv:") != -1)
  {
    var version = temp.substring(temp.indexOf("rv:")+3,temp.indexOf(")"))
    var primer_digito = version.substring(0,1);
    var segundo_digito = version.substring(2,3);
    var primer_digito_entero = parseInt(primer_digito);
    var segundo_digito_entero = parseInt(segundo_digito);
  
    if (primer_digito_entero >=1 && segundo_digito_entero >=3)
    {
      var appletMimeType = navigator.mimeTypes['application/x-java-applet'];
      if(!appletMimeType)
      {  
        alert ("Se necesita Plug-in Java 1.4 o superior. Redireccionando a página instalación.");
        window.location.href=dnUrlLinux +'?download=jvmneeded,clientnotchecked';
      }
      else
      {
        var description = appletMimeType.enabledPlugin.description;
        if( description.indexOf("Java")==-1 && description.indexOf("Plug-in")==-1 && description.indexOf("1.4")==-1 && description.indexOf("1.5")==-1 && description.indexOf("1.6")==-1 )
        {
          alert("Detectado "+description);
          alert("Se necesita el Plug-in JRE 1.4 o superior. Redireccionando a la página de instalación.");
          window.location.href=dnUrlLinux +'?download=jvmneeded,clientnotchecked';
        }
        else
        {
          document.write ('<APPLET CODE="DetectVM.class" width="1" height="1"  ARCHIVE="TelventDetectLinux.jar"><PARAM NAME=FtpURL VALUE="' + dnUrlLinux + '"></APPLET>');
        }
      }
    }
    else
    {
      alert("Se necesita un navegador Mozilla 1.3 o superior. Redireccionando a Pagina instalación ....");
      window.location.href=dnUrlLinux +'?download=mozillaneeded,clientnotchecked';
    }
  }
  else
  {
    alert("Se necesita un navegador Mozilla 1.3 o superior. Redireccionando a Pagina instalación ....");
    window.location.href=dnUrlLinux +'?download=mozillaneeded,clientnotchecked';
  }
}
