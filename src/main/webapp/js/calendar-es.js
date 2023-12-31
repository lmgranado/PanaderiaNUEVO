// ** I18N

Calendar._DN = new Array
("Domingo",
 "Lunes",
 "Martes",
 "Mi"+String.fromCharCode(233)+"rcoles",
 "Jueves",
 "Viernes",
 "S"+String.fromCharCode(225)+"bado",
 "Domingo");

// Please note that the following array of short day names (and the same goes
// for short month names, _SMN) isn't absolutely necessary.  We give it here
// for exemplification on how one can customize the short day names, but if
// they are simply the first N letters of the full name you can simply say:
//
//   Calendar._SDN_len = N; // short day name length
//   Calendar._SMN_len = N; // short month name length
//
// If N = 3 then this is not needed either since we assume a value of 3 if not
// present, to be compatible with translation files that were written before
// this feature.

// short day names
Calendar._SDN = new Array
("Dom",
 "Lun",
 "Mar",
 "Mi"+String.fromCharCode(233)+"",
 "Jue",
 "Vie",
 "S"+String.fromCharCode(225)+"b",
 "Dom");

// First day of the week. "0" means display Sunday first, "1" means display
// Monday first, etc.
Calendar._FD = 1;

// full month names
Calendar._MN = new Array
("Enero",
 "Febrero",
 "Marzo",
 "Abril",
 "Mayo",
 "Junio",
 "Julio",
 "Agosto",
 "Septiembre",
 "Octubre",
 "Noviembre",
 "Diciembre");

// short month names
Calendar._SMN = new Array
("Ene",
 "Feb",
 "Mar",
 "Abr",
 "May",
 "Jun",
 "Jul",
 "Ago",
 "Sep",
 "Oct",
 "Nov",
 "Dic");

// tooltips
Calendar._TT = {};
Calendar._TT["INFO"] = "Acerca del calendario";

Calendar._TT["ABOUT"] =
"Selecci"+String.fromCharCode(243)+"n de fecha:\n" +
"- Use los botones \xab, \xbb para seleccionar el a"+String.fromCharCode(241)+"o\n" +
"- Use los botones " + String.fromCharCode(0x2039) + ", " + String.fromCharCode(0x203a) + " para seleccionar el mes\n" +
"- Mantenga pulsado el rat"+String.fromCharCode(243)+"n en cualquiera de estos botones para una selecci"+String.fromCharCode(243)+"n r"+String.fromCharCode(225)+"pida.";
Calendar._TT["ABOUT_TIME"] = "\n\n" +
"Selecci"+String.fromCharCode(243)+"n de hora:\n" +
"- Pulse en cualquiera de las partes de la hora para incrementarla\n" +
"- o pulse las may"+String.fromCharCode(250)+"sculas mientras hace clic para decrementarla\n" +
"- o haga clic y arrastre el rat"+String.fromCharCode(243)+"n para una selecci"+String.fromCharCode(243)+"n m"+String.fromCharCode(225)+"s r"+String.fromCharCode(225)+"pida.";

Calendar._TT["PREV_YEAR"] = "A"+String.fromCharCode(241)+"o anterior (mantener para men"+String.fromCharCode(250)+")";
Calendar._TT["PREV_MONTH"] = "Mes anterior (mantener para men"+String.fromCharCode(250)+")";
Calendar._TT["GO_TODAY"] = "Ir a hoy";
Calendar._TT["NEXT_MONTH"] = "Mes siguiente (mantener para men"+String.fromCharCode(250)+")";
Calendar._TT["NEXT_YEAR"] = "A"+String.fromCharCode(241)+"o siguiente (mantener para men"+String.fromCharCode(250)+")";
Calendar._TT["SEL_DATE"] = "Seleccionar fecha";
Calendar._TT["DRAG_TO_MOVE"] = "Arrastrar para mover";
Calendar._TT["PART_TODAY"] = " (hoy)";

// the following is to inform that "%s" is to be the first day of week
// %s will be replaced with the day name.
Calendar._TT["DAY_FIRST"] = "%s primer d"+String.fromCharCode(237)+"a de la semana";

// This may be locale-dependent.  It specifies the week-end days, as an array
// of comma-separated numbers.  The numbers are from 0 to 6: 0 means Sunday, 1
// means Monday, etc.
Calendar._TT["WEEKEND"] = "0,6";

Calendar._TT["CLOSE"] = "Cerrar";
Calendar._TT["TODAY"] = "Hoy";
Calendar._TT["TIME_PART"] = "(May"+String.fromCharCode(250)+"scula-)Clic o arrastre para cambiar valor";

// date formats
Calendar._TT["DEF_DATE_FORMAT"] = "%d/%m/%Y";
Calendar._TT["TT_DATE_FORMAT"] = "%A, %e de %B de %Y";

Calendar._TT["WK"] = "sem";
Calendar._TT["TIME"] = "Hora:";
