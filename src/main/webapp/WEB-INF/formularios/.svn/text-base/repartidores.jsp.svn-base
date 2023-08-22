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
				<c:if test="${!empty Repartidores.repId}">
					Repartidor: ${Repartidor.repNombre} ${Repartidor.repApellidos}
				</c:if>	
			</span>
		  </td>
	    </tr>
	<tr>
		<td colspan="7" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Datos Repartidores<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	    <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 15%' align="left" class="texto_negrita">Nombre</td>
                <td style='width: 40%' align="left"> 
				<html:text styleClass="js_requerido" size='28' maxlength="75" property="repNombre" name="repNombre" value="${Repartidor.repNombre}"/>&nbsp;
				</td>
				<td style='width: 4%'>&nbsp;</td>
                <td style='width: 11%' align="left" class="texto_negrita">Apellidos</td>
                <td style='width: 30%' align="left"> 
				<html:text size='28' maxlength="75" property="repApellidos" name="repApellidos" value="${Repartidor.repApellidos}"/>&nbsp;
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
					<html:text size='28' maxlength="75" property="repDireccion" name="repDireccion" value="${Repartidor.repDireccion}"/>&nbsp;
				</td>
				<td style='width: 4%'>&nbsp;</td>
                <td style='width: 11%' align="left" class="texto_negrita">NIF</td>
                <td style='width: 30%' align="left"> 
				<html:text styleClass="js_requerido" size='28' maxlength="10" property="repNif" name="repNif" value="${Repartidor.repNif}"/>&nbsp;
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
					<html:select styleClass="js_requerido" style="width: 160px" styleId="repProvId" onchange="javascript: devolverDatosMunicipio('repLocId','repProvId');" property="repProvId" value="${Repartidor.repProvId}">
						  <html:option value="">[Seleccionar]</html:option>
						  <html:options property="provId" labelProperty="provNombre" collection="LISTAPROVINCIAS"/>
					</html:select>
				</td>
				<td style='width: 4%'>&nbsp;</td>
                <td style='width: 11%' align="left" class="texto_negrita">Localidad</td>
                <td style='width: 30%' align="left"> 
					<html:select styleClass="js_requerido" style="width: 160px" styleId="repLocId" property="repLocId" value="${Repartidor.repLocId}">
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
					<html:text size='28' maxlength="10" property="repTelefono" name="repTelefono" value="${Repartidor.repTelefono}"/>&nbsp;
				</td>
				<td style='width: 4%'>&nbsp;</td>
                <td style='width: 11%' align="left" class="texto_negrita">C.P.</td>
                <td style='width: 30%' align="left"> 
				<html:text styleClass="js_codigopostal" size='28' maxlength="10" property="repCodigoPostal" name="repCodigoPostal" value="${Repartidor.repCodigoPostal}"/>&nbsp;
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
				<html:text styleClass="js_telefono" size='28' maxlength="10" property="repMovil" name="repMovil" value="${Repartidor.repMovil}"/>&nbsp;
				<td style='width: 45%'>&nbsp;</td>
              </tr>
            </table>
            <br/><br/><br/><br/>
		  </td>
        </tr>       
</table>

<script>
		setTimeout("devolverDatosMunicipio('repLocId','repProvId')",0);
		setTimeout("aplicarValor('${Repartidor.repLocId}','repLocId')",300);
</script>
<html:hidden property="repId" value="${Repartidor.repId}"/>
<!-- Fin Ficha Perfiles -->
