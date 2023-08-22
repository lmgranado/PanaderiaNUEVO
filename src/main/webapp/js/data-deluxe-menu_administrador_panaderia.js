/*
   Deluxe Menu Data File
   Created by Deluxe Tuner v2.4
   http://deluxe-menu.com
*/

// -- Deluxe Tuner Style Names
var itemStylesNames=[];
var menuStylesNames=[];
// -- End of Deluxe Tuner Style Names

//--- Common
var isHorizontal=1;
var smColumns=1;
var smOrientation=0;
var smViewType=0;
var dmRTL=0;
var pressedItem=-2;
var itemCursor="pointer";
var itemTarget="_self";
var statusString="link";
var blankImage="deluxe-menu.files/blank.gif";
var pathPrefix_img="";
var pathPrefix_link="";

//--- Dimensions
var menuWidth="";
var menuHeight="";
var smWidth="";
var smHeight="";

//--- Positioning
var absolutePos=0;
var posX="10px";
var posY="10px";
var topDX=0;
var topDY=1;
var DX=-5;
var DY=0;

//--- Font
var fontStyle="600 11px Verdana, Verdana";
var fontColor=["#02356F","#02356F"];
var fontDecoration=["none","none"];
var fontColorDisabled="#022952";

//--- Appearance
var menuBackColor="#FFFFFF";
var menuBackImage="";
var menuBackRepeat="repeat";
var menuBorderColor="#D9D9D9";
var menuBorderWidth=2;
var menuBorderStyle="solid";

//--- Item Appearance
var itemBackColor=["#D9D9D9","#D9D9D9"];
var itemBackImage=["",""];
var itemBorderWidth=2;
var itemBorderColor=["#D9D9D9","#D9D9D9"];
var itemBorderStyle=["solid","solid"];
var itemSpacing=3;
var itemPadding="4px";
var itemAlignTop="left";
var itemAlign="left";
var subMenuAlign="left";

//--- Icons
var iconTopWidth=4;
var iconTopHeight=7;
var iconWidth=4;
var iconHeight=7;
var arrowWidth=4;
var arrowHeight=7;
var arrowImageMain=["",""];
var arrowImageSub=["",""];

//--- Separators
var separatorImage="";
var separatorWidth="100%";
var separatorHeight="3px";
var separatorAlignment="left";
var separatorVImage="";
var separatorVWidth="3px";
var separatorVHeight="100%";
var separatorPadding="0px";

//--- Floatable Menu
var floatable=0;
var floatIterations=6;
var floatableX=1;
var floatableY=1;

//--- Movable Menu
var movable=0;
var moveWidth=12;
var moveHeight=20;
var moveColor="#DECA9A";
var moveImage="";
var moveCursor="move";
var smMovable=0;
var closeBtnW=15;
var closeBtnH=15;
var closeBtn="";

//--- Transitional Effects & Filters
var transparency="100";
var transition=24;
var transOptions="";
var transDuration=20;
var transDuration2=20;
var shadowLen=3;
var shadowColor="#D9D9D9";
var shadowTop=0;

//--- CSS Support (CSS-based Menu)
var cssStyle=0;
var cssSubmenu="";
var cssItem=["",""];
var cssItemText=["",""];

//--- Advanced
var dmObjectsCheck=0;
var saveNavigationPath=1;
var showByClick=0;
var noWrap=1;
var smShowPause=200;
var smHidePause=1000;
var smSmartScroll=1;
var topSmartScroll=0;
var smHideOnClick=1;
var dm_writeAll=1;

//--- AJAX-like Technology
var dmAJAX=0;
var dmAJAXCount=0;

//--- Dynamic Menu
var dynamic=0;

//--- Keystrokes Support
var keystrokes=0;
var dm_focus=1;
var dm_actKey=113;


var menuItems = [

    ["Empresa","", "img/play_azul.gif", "", "", "", "", "", "", ],
    	["|Cobradores","doListadoCobradores.do", "img/play_naranja.gif", "", "", "", "", "", "", ],
        ["|Repartidores","doListadoRepartidores.do", "img/play_naranja.gif", "", "", "", "", "", "", ],
        ["|Usuarios","doListadoUsuarios.do", "img/play_naranja.gif", "", "", "", "", "", "", ],
        ["|Familias","doListadoFamilias.do", "img/play_naranja.gif", "", "", "", "", "", "", ],
        ["|Formas Pago","doListadoFormasPago.do", "img/play_naranja.gif", "", "", "", "", "", "", ],
        ["|Periodos Facturación","doListadoPeriodosFacturacion.do", "img/play_naranja.gif", "", "", "", "", "", "", ],
        ["|Fichas Productos","doListadoFichasProductos.do", "img/play_naranja.gif", "", "", "", "", "", "", ],
        ["|Entradas Inventario","doListadoHistoricoInventarios.do", "img/play_naranja.gif", "", "", "", "", "", "", ],
    ["Ventas","", "img/play_azul.gif", "", "", "", "", "", "", ],
        ["|Productos","doListadoProductos.do", "img/play_naranja.gif", "", "", "", "", "", "", ],
        ["|Clientes","doListadoClientes.do?session=1", "img/play_naranja.gif", "", "", "", "", "", "", ],
        //Cambiamos nombre de rutas por entregas, cuidado a la hora de tocar estos módulos
        ["|Entregas","doListadoRutas.do", "img/play_naranja.gif", "", "", "", "", "", "", ],
        ["|Rutas","doListadoEntregas.do", "img/play_naranja.gif", "", "", "", "", "", "", ],
    ["Documentos Venta","", "img/play_azul.gif", "", "", "", "", "", "", ],
    	["|Notas","doListadoNotasEntrega.do?session=1", "img/play_naranja.gif", "", "", "", "", "", "", ],
        ["|Albaranes","doListadoAlbaranesVenta.do", "img/play_naranja.gif", "", "", "", "", "", "", ],
        ["|Facturas","doListadoFacturasVenta.do", "img/play_naranja.gif", "", "", "", "", "", "", ],
        ["|Factura Directa","doFacturasVentaFormulario.do?fvTipo=1", "img/play_naranja.gif", "", "", "", "", "", "", ],
        ["|Factura Abono","doFacturasVentaFormulario.do?fvTipo=2", "img/play_naranja.gif", "", "", "", "", "", "", ],
    ["Compras","", "img/play_azul.gif", "", "", "", "", "", "", ],
        ["|Proveedores","doListadoProveedores.do", "img/play_naranja.gif", "", "", "", "", "", "", ],
        ["|Facturas","doListadoFacturasCompra.do", "img/play_naranja.gif", "", "", "", "", "", "", ],
        ["|Fabricación","doListadoFacturasFabricacion.do", "img/play_naranja.gif", "", "", "", "", "", "", ],
  	["Trazabilidad","", "img/play_azul.gif", "", "", "", "", "", "", ],
        ["|Lotes","doListadoStock.do", "img/play_naranja.gif", "", "", "", "", "", "", ],
        ["|Informes","", "img/play_naranja.gif", "", "", "", "", "", "", ],
        ["||Plan de Trazabilidad","doInformeTrazabilidad.do", "img/play_naranja.gif", "", "", "", "", "", "", ],
    ["Informes","", "img/play_azul.gif", "", "", "", "", "", "", ],
        ["|Ventas","doListadoInformesVenta.do", "img/play_naranja.gif", "", "", "", "", "", "", ],
        ["|Productos","doInformeListadoProductosF.do", "img/play_naranja.gif", "", "", "", "", "", "", ],
        ["|Precios","doInformeListadoPreciosF.do", "img/play_naranja.gif", "", "", "", "", "", "", ],
        ["|Clientes","doInformeListadoClientes.do", "img/play_naranja.gif", "", "", "_new", "", "", "", ],
        ["|Proveedores","doInformeListadoProveedores.do", "img/play_naranja.gif", "", "", "_new", "", "", "", ],
    ["Utilidades","", "img/play_azul.gif", "", "", "", "", "", "", ],
    	["|Módulo de Cobros","doListadoCobros.do", "img/play_naranja.gif", "", "", "", "", "", "", ],
        ["|Previsión de fabricación","doListadoPrevision.do", "img/play_naranja.gif", "", "", "", "", "", "", ],
        ["|Actualizador de precios","doActualizadorPrecios.do", "img/play_naranja.gif", "", "", "", "", "", "", ],        
    ["Salir","doLogin.do", "img/play_azul.gif", "", "", "", "", "", "", ]
];

dm_init();