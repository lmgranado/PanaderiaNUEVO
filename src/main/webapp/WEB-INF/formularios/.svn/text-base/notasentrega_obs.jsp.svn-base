<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">
	vFormName = "frmDetalle";
	function doSubmit(){
		vForm = document.getElementById(vFormName);		
		_enviarDetalle('frmDetalle');
	}
	

</script>
<html:form action="/doNotasEntregaCrear" styleId="frmDetalle" method="post">	
    <table class="displaytag_tabla" style="margin: 0px 20px; width: 95%;" cellpadding="2" cellspacing="2">
    	<tr>
			<td align="left" class="texto_negrita" style="font-weight: bold; text-decoration: underline;">
				Puede introducir una observación al bloque de notas de entrega que va a generar.<br>
				Posteriormente puede modificar las observaciones de manera individual en el listado de notas de entrega.
				
			</td>
		</tr>
		<tr>
		<td style="font-family: Verdana; font-size: 11px; color: #666666">
			Fecha Notas Entrega<br>			 	 	
           	<input type="text" style="background-color: #CCCCCC;" readonly="readonly" id="fechaNotas" name="fechaNotas" value=""/>
           	<img style="cursor: pointer;" class="vtop" id="calendario1" title="Seleccione fecha"  alt="Seleccione fecha" src="img/calendario.gif"/>
           	<script type="text/javascript">
                     Calendar.setup({
                         inputField     :    "fechaNotas"   // id of the input field
                     ,
                         button         :    "calendario1"  // trigger for the calendar (button ID)
                     });
           	</script>
		</td>
		</tr>
    	<tr>
			<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 12%">
				Observaciones<br>
				<textarea rows="5" cols="30" name="notObservaciones"></textarea>
			</td>
        </tr>
		<tr>
			<td align="center" colspan="2">
				<a onclick="javascript: doSubmit();"><img src="img/nota.png"></a>&nbsp;
				<a onclick="javascript: doCerrar();"><img src="img/ko.png"></a>
			</td>
		</tr>
	</table>
	<html:hidden property="entId" value="${entId}"/>
	<input type='hidden' value="true" name="editar" />
	
</html:form>