<%-- /WEB-INF/modulos/seguridad/perfiles.ficha.jsp --%>
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

	
	function devolverDatosMunicipio(municipio, provincia)
	{
	 var campoProvincia = document.getElementById(provincia);
	 var idProvincia = campoProvincia[campoProvincia.selectedIndex].value;

	 var url = 'doCargaProvincias.do?provincia='+idProvincia;
	 
	 // Ponemos en blanco
	 document.getElementById(municipio).length = 0;
	 
	 setTimeout("loadXMLDoc('"+url+"','"+municipio+"')", 1000); 
	}
	
</script>

<table style="background-color: white; border: 1px solid #E5E5E5; width: 100%;">

	    <tr>          
		  <td colspan="4" align="right">
		  	<span style='color: #FA7D3D; font-size: 12px; font-weight: bold;'>
				<c:if test="${!empty Cobradores.cobId}">
					Cobrador: ${Cobrador.cobNombre} ${Cobrador.cobApellidos}
				</c:if>	
			</span>
		  </td>
	    </tr>
	<tr>
		<td colspan="7" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Datos Cobradores<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	    <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 15%' align="left" class="texto_negrita">Nombre</td>
                <td style='width: 40%' align="left"> 
				<html:text size='28' styleClass="js_requerido"  maxlength="75" property="cobNombre" name="cobNombre" value="${Cobrador.cobNombre}"/>&nbsp;
				</td>
				<td style='width: 4%'>&nbsp;</td>
                <td style='width: 11%' align="left" class="texto_negrita">Apellidos</td>
                <td style='width: 30%' align="left"> 
				<html:text   size='28' maxlength="75" property="cobApellidos" name="cobApellidos" value="${Cobrador.cobApellidos}"/>&nbsp;
                </td>
              </tr>
            </table>
		  </td>
        </tr>
		<!-- Fin Linea 21 -->
		<!-- Linea 22 -->
        <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 15%' align="left" class="texto_negrita">Dirección</td>
                <td style='width: 40%' align="left">
					<html:text  size='28' maxlength="75" property="cobDireccion" name="cobDireccion" value="${Cobrador.cobDireccion}"/>&nbsp;
				</td>
				<td style='width: 4%'>&nbsp;</td>
                <td style='width: 11%' align="left" class="texto_negrita">NIF</td>
                <td style='width: 30%' align="left"> 
				<html:text styleClass="js_requerido" size='28' maxlength="10" property="cobNif" name="cobNif" value="${Cobrador.cobNif}"/>&nbsp;
                </td>
              </tr>
            </table>
		  </td>
        </tr>
        
        <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 15%' align="left" class="texto_negrita">Provincia</td>
                <td style='width: 40%' align="left">
					<html:select  styleClass="js_requerido" style="width: 160px" styleId="cobProvId" onchange="javascript: devolverDatosMunicipio('cobLocId','cobProvId');" property="cobProvId" value="${Cobrador.cobProvId}">
						  <html:option value="">[Seleccionar]</html:option>
						  <html:options property="provId" labelProperty="provNombre" collection="LISTAPROVINCIAS"/>
					</html:select>
				</td>
				<td style='width: 4%'>&nbsp;</td>
                <td style='width: 11%' align="left" class="texto_negrita">Localidad</td>
                <td style='width: 30%' align="left"> 
					<html:select  styleClass="js_requerido" style="width: 160px" styleId="cobLocId" property="cobLocId" value="${Cobrador.cobLocId}">
						  <html:option value="">[Seleccionar]</html:option>
						  <html:options property="locId" labelProperty="locNombre" collection="LISTALOCALIDADES"/>
					</html:select>

                </td>
              </tr>
            </table>
		  </td>
        </tr>
        
        <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
               <td style='width: 15%' align="left" class="texto_negrita">Telefono</td>
                <td style='width: 40%' align="left">
					<html:text styleClass="js_telefono" size='28' maxlength="10" property="cobTelefono" name="cobTelefono" value="${Cobrador.cobTelefono}"/>&nbsp;
				</td>
				<td style='width: 4%'>&nbsp;</td>
                <td style='width: 11%' align="left" class="texto_negrita">C.P.</td>
                <td style='width: 30%' align="left"> 
				<html:text size='28' styleClass="js_codigopostal" maxlength="10" property="cobCodigoPostal" name="cobCodigoPostal" value="${Cobrador.cobCodigoPostal}"/>&nbsp;
                </td>
              </tr>
            </table>
		  </td>
        </tr>       
        <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 15%' align="left" class="texto_negrita">Movil</td>
                <td style='width: 40%' align="left"> 
				<html:text styleClass="js_telefono" size='28' maxlength="10" property="cobMovil" name="cobMovil" value="${Cobrador.cobMovil}"/>&nbsp;
                </td>
                <td style='width: 45%' align="left"> 
              </tr>
            </table>
		  </td>
        </tr>       
</table>

<script>
		setTimeout("devolverDatosMunicipio('cobLocId','cobProvId')",0);
		setTimeout("aplicarValor('${Cobrador.cobLocId}','cobLocId')",300);
</script>
<html:hidden property="cobId" value="${Cobrador.cobId}"/>
<!-- Fin Ficha Perfiles -->
