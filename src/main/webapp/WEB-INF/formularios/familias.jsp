<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/c-1_0-rt.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>


<script type="text/javascript">
	vFormName = "frmDetalle";
	function doSubmit(){
		vForm = document.getElementById(vFormName);		
        if( validateFields(vForm.name) ){	
        	_enviarDetalle('frmDetalle');
        }
	}
	
	function copiaFichaAProductos()
	{
		if('${Familia.famId}'!=''){
		 var campoFicha = document.getElementById('famFprodId');
		 var idFicha = campoFicha[campoFicha.selectedIndex].value;
		 if( confirm( 'Va a cambiar la ficha asociada a la familia y por tanto se van a sobreescribir\n todos los datos de las fichas de los productos, �desea continuar?' ) ){
		 	var url = 'doCopiaFichaAProductos.do?fprodId='+idFicha+'&famId=${Familia.famId}';
		 	vForm = document.getElementById(vFormName);
		 	vForm.action = url;
		 	_enviarDetalle('frmDetalle');
			ventana('doVentanaBarraProgreso.do', 500, 330, 'toolbar=0,location=0,directories=0,status=0,menubar =0,scrollbars=0,resizable=0')	 	
		 }
		 else{
		 	//Si no quiere cambiar la opcion, colocamos el select en su valor original
		 	campoFicha.selectedIndex = document.getElementById('fprodIdAnt').value;
		 }
		}
	}
	
</script>


<table style="background-color: white; border: 1px solid #E5E5E5; width: 100%">
	    <tr>          
		  <td colspan="4" align="right">
		  	<span style='color: #FA7D3D; font-size: 12px; font-weight: bold;'>
				<c:if test="${!empty Familia.famId}">
					Familia: ${Familia.famNombre}
				</c:if>	
			</span>
		  </td>
	    </tr>
	<tr>
		<td colspan="7" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Datos Familia<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	    <tr> 
          <td align="center" colspan='2' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 5%' align="left" class="texto_negrita">Nombre</td>
                <td style='width: 20%' align="left"> 
				<html:text styleClass="js_requerido" size='28' maxlength="75" property="famNombre" name="famNombre" value="${Familia.famNombre}"/>
				</td>
				<td style='width: 4%'>&nbsp;</td>
                <td style='width: 5%' align="left" class="texto_negrita">Ficha t�cnica</td>
                <td style='width: 30%' align="left"> 
					<html:select styleClass="js_requerido" style="width: 200px" styleId="famFprodId" property="famFprodId" onchange="javascript: copiaFichaAProductos();" value="${Familia.famFprodId}">
			  			<html:option value="">[Seleccionar]</html:option>
			  			<html:options property="fprodId" labelProperty="fprodNombreFicha" collection="LISTAFICHAS"/>
		    		</html:select>
                </td>
              </tr>
            </table>
		  </td>
        </tr>
      <tr>
		<td colspan="7" style="height: 100">
			&nbsp;
		</td>
	  </tr>
</table>
	
<html:hidden property="famId" value="${Familia.famId}"/>
<input type="hidden" id="fprodIdAnt" value="${Familia.famFprodId}"/>
