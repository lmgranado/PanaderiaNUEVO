<%-- /WEB-INF/modulos/seguridad/perfiles.ficha.jsp --%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/c-1_0-rt.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<script type="text/javascript">
	vFormName = "frmDetalle";
	function doSubmit(){
		vForm = document.getElementById(vFormName);	
		if(validateFields(vForm.name)){
			if(vForm.clDescuento.value > 100){
				alert('El porcentaje de descuento no puede ser superior a 100%');
				return false;
			}
			if(vForm.clProporcionIva.value.replace(",",".") > 100){
				alert('La proporci�n de IVA no puede ser superior a 100%');
				return false;
			}
			if( vForm.clProporcionIva.value.replace(",",".") > 0  && document.getElementById('clIdEmpresa').value == ""){
				alert('Debe elegir un tipo de empresa para la facturaci�n.');
				return false;
			}
			else{
				if( document.getElementById('existecif').value > 0 ){
					var x = confirm( 'Atenci�n, existe un cliente con el mismo CIF.\n �Desea continuar?' );
					if( x ){
						_enviarDetalle('frmDetalle');
					}else{
						return false;
					}	
				}
				else{
					_enviarDetalle('frmDetalle');
				}
			}
        }
	}


	function compruebaCIFCliente(){
		var clNif = document.getElementById('clNif').value;
		var clId = document.getElementById('clId').value;
		var url = 'doCompruebaCifCliente.do?clNif='+clNif+'&clId='+clId;
		setTimeout("loadTextDoc('"+url+"','existecif')", 0); 
		
	}
	
	function devolverDatosMunicipio(municipio, provincia)
	{
		 var campoProvincia = document.getElementById(provincia);
		 var idProvincia = campoProvincia[campoProvincia.selectedIndex].value;
	
		 var url = 'doCargaProvincias.do?provincia='+idProvincia;
		 
		 // Ponemos en blanco
		 document.getElementById(municipio).length = 0;
		 
		 
		 
		 setTimeout("loadXMLDoc('"+url+"','"+municipio+"')", 0); 
	}
	
	function muestraEmpresa( valor ){
		var elem = document.getElementById('clIdEmpresa');
		if( valor > formato_numero(0) ){
			elem.disabled='';
		}else{
			elem.selectedIndex = '';
			elem.disabled='true';
		}	
	}
	
</script>


<table style="background-color: white; border: 1px solid #E5E5E5; width: 100%">
	    <tr>          
		  <td colspan="7" align="right">
		  	<span style='color: #FA7D3D; font-size: 12px; font-weight: bold;'>
				<c:if test="${!empty Cliente.clId}">
					 ${Cliente.clNombre} ${Cliente.clApellidos}
				</c:if>	
			</span>
		  </td>
	    </tr>
	<tr>
		<td colspan="7" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Datos clientes<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	    <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 15%' align="left" class="texto_negrita">Nombre</td>
                <td style='width: 40%' align="left"> 
				<html:text size='28' styleClass="js_requerido" maxlength="75" property="clNombre" name="clNombre" value="${Cliente.clNombre}"/>
				</td>
				<td style='width: 4%'>&nbsp;</td>
                <td style='width: 11%' align="left" class="texto_negrita">Apellidos</td>
                <td style='width: 30%' align="left"> 
				<html:text size='28' maxlength="75" property="clApellidos" name="clApellidos" value="${Cliente.clApellidos}"/>
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
                <td style='width: 15%' align="left" class="texto_negrita">Nombre Co.</td>
                <td style='width: 40%' align="left">
					<html:text size='28' styleClass="js_requerido" maxlength="75" property="clNombreComercial" name="clNombreComercial" value="${Cliente.clNombreComercial}"/>
				</td>
				<td style='width: 4%'>&nbsp;</td>
                <td style='width: 11%' align="left" class="texto_negrita">NIF</td>
                <td style='width: 30%' align="left"> 
                <c:if test="${empty Cliente.clNif}">
                	<c:set value="0000000T" var="clNif"/>
                </c:if>
                <c:if test="${!empty Cliente.clNif}">
                	<c:set value="${Cliente.clNif}" var="clNif"/>
                </c:if>
				<html:text size='28' styleClass="js_requerido" maxlength="10" property="clNif" styleId="clNif" name="clNif" onblur="javascript:compruebaCIFCliente();" value="${clNif}"/>

                </td>
              </tr>
            </table>
		  </td>
        </tr>
        <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 15%' align="left" class="texto_negrita">Direcci�n</td>
                <td style='width: 40%' align="left">
					<html:text size='28' styleClass="js_requerido" maxlength="75" property="clDireccion" name="clDireccion" value="${Cliente.clDireccion}"/>
				</td>
				<td style='width: 4%'>&nbsp;</td>
                <td style='width: 11%' align="left" class="texto_negrita">C.P.</td>
                <td style='width: 30%' align="left"> 
				<html:text size='28' styleClass="js_requerido js_codigopostal" maxlength="10" property="clCodigoPostal" name="clCodigoPostal" value="${Cliente.clCodigoPostal}"/>

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
					<html:select  styleClass="js_requerido" style="width: 160px" styleId="clProvId" onchange="javascript: devolverDatosMunicipio('clLocId','clProvId');" property="clProvId" value="${Cliente.clProvId}">
						  <html:option value="">[Seleccionar]</html:option>
						  <html:options property="provId" labelProperty="provNombre" collection="LISTAPROVINCIAS"/>
					</html:select>
				</td>
				<td style='width: 4%'>&nbsp;</td>
                <td style='width: 11%' align="left" class="texto_negrita">Localidad</td>
                <td style='width: 30%' align="left"> 
					<html:select  styleClass="js_requerido" style="width: 160px" styleId="clLocId" property="clLocId" value="${Cliente.clLocId}">
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
					<html:text size='28' styleClass="js_requerido js_telefono" maxlength="10" property="clTelefono" name="clTelefono" value="${Cliente.clTelefono}"/>
				</td>
				<td style='width: 4%'>&nbsp;</td>
                <td style='width: 11%' align="left" class="texto_negrita">Movil</td>
                <td style='width: 30%' align="left"> 
				<html:text size='28' styleClass="js_telefono" maxlength="10" property="clMovil" name="clMovil" value="${Cliente.clMovil}"/>

                </td>
              </tr>
            </table>
		  </td>
        </tr>
        <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 15%' align="left" class="texto_negrita">Persona Contacto</td>
                <td style='width: 40%' align="left">
					<html:text size='28' maxlength="75" property="clPersonaContacto" name="clPersonaContacto" value="${Cliente.clPersonaContacto}"/>
				</td>
				<td style='width: 4%'>&nbsp;</td>
                <td style='width: 11%' align="left" class="texto_negrita">Fax</td>
                <td style='width: 30%' align="left"> 
				<html:text size='28' styleClass="js_telefono"  maxlength="10" property="clFax" name="clFax" value="${Cliente.clFax}"/>
                </td>
              </tr>
            </table>
		  </td>
        </tr>
        <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 15%' align="left" class="texto_negrita">Email</td>
                <td style='width: 40%' align="left">
					<html:text size='28' styleClass="jsvalidate_email"  maxlength="75" property="clEmail" name="clEmail" value="${Cliente.clEmail}"/>
				</td>
				<td style='width: 4%'>&nbsp;</td>
                <td style='width: 11%' align="left" class="texto_negrita">Web</td>
                <td style='width: 30%' align="left"> 
				<html:text size='28' maxlength="75" property="clWeb" name="clWeb" value="${Cliente.clWeb}"/>
                </td>
              </tr>
            </table>
		  </td>
        </tr>
        <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 15%' align="left" class="texto_negrita">Cobrador</td>
                <td style='width: 40%' align="left">
					<html:select  styleClass="js_requerido" style="width: 160px" styleId="clCobId" property="clCobId" value="${Cliente.clCobId}">
						  <html:option value="">[Seleccionar]</html:option>
						  <c:forEach items="${LISTACOBRADORES}" var="cobrador">
						  	<html:option value="${cobrador.cobId}">${cobrador.cobNombre} ${cobrador.cobApellidos}</html:option>
						  </c:forEach>
					</html:select>
				</td>
				<td style='width: 4%'>&nbsp;</td>
                <td style='width: 11%' align="left" class="texto_negrita">Pertenece a</td>
                <td style='width: 30%' align="left"> 
					<html:select  disabled="${emp}" style="width: 160px" styleId="clPertenencia" property="clPertenencia" value="${Cliente.clPertenencia}">
						  <html:option value="-1">[Seleccionar]</html:option>
						  <html:option value="1">Pinepan S.L.</html:option>
						  <html:option value="2">Obrador</html:option>
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
                <td style='width: 15%' align="left" class="texto_negrita">Activo</td>
                <td style='width: 40%' align="left">
					<html:select  styleClass="js_requerido"  style="width: 160px" styleId="clActivo" property="clActivo" value="${Cliente.clActivo}">
						  <html:option value="">[Seleccionar]</html:option>
						  <html:option value="1">Activo</html:option>
						  <html:option value="0">Inactivo</html:option>
					</html:select>
				</td>
				<td style='width: 4%'>&nbsp;</td>
                <td style='width: 11%' align="left" class="texto_negrita"></td>
                <td style='width: 30%' align="left"> 
                </td>
              </tr>
            </table>
		  </td>
        </tr>
	<tr>
		<td colspan="7" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Condiciones de facturaci�n<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
	
	
        <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 15%' align="left" class="texto_negrita">Cuenta Bancaria</td>
                <td style='width: 40%' align="left">
					<html:text size='28' maxlength="75" property="clCcc" name="clCcc" value="${Cliente.clCcc}"/>
				</td>
				<td style='width: 4%'>&nbsp;</td>
                <td style='width: 11%' align="left" class="texto_negrita">Banco</td>
                <td style='width: 30%' align="left"> 
					<html:text size='28' maxlength="75" property="clNombreBanco" name="clNombreBanco" value="${Cliente.clNombreBanco}"/>
                </td>
              </tr>
            </table>
		  </td>
        </tr>
        <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 15%' align="left" class="texto_negrita">Forma Pago</td>
                <td style='width: 40%' align="left">
					<html:select  styleClass="js_requerido" style="width: 160px" styleId="clFpId" property="clFpId" value="${Cliente.clFpId}">
						  <html:option value="">[Seleccionar]</html:option>
						  <html:options property="fpId" labelProperty="fpDescripcion" collection="LISTAFORMASPAGO"/>
					</html:select>
				</td>
				<td style='width: 4%'>&nbsp;</td>
                <td style='width: 11%' align="left" class="texto_negrita">Periodo Facturaci�n</td>
                <td style='width: 30%' align="left"> 
					<html:select  styleClass="js_requerido" style="width: 160px" styleId="clPfId" property="clPfId" value="${Cliente.clPfId}">
						  <html:option value="">[Seleccionar]</html:option>
						  <html:options property="pfId" labelProperty="pfDescripcion" collection="LISTAPERIODOS"/>
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
                <td style='width: 15%;' align="left" class="texto_negrita">Descuento</td>
                <td style='width: 40%;' align="left" class="texto_negrita">
                	<fmt:formatNumber pattern="###,###.###" var="clDescuento">${Cliente.clDescuento*100}</fmt:formatNumber>
					<html:text size='28' onblur="javascript:this.value=formato_numero(this.value,1)" maxlength="5" property="clDescuento" name="clDescuento" value="${clDescuento}"/>%
				</td>
				<td style='width: 4%'>&nbsp;</td>
                <td style='width: 11%' align="left" class="texto_negrita">Proporci�n IVA</td>
                <td style='width: 30%' align="left" class="texto_negrita"> 
				<fmt:formatNumber pattern="###,###.###" var="clProporcionIva">${Cliente.clProporcionIva*100}</fmt:formatNumber>
					<html:text size='28' onblur="javascript:this.value=formato_numero(this.value,1);muestraEmpresa(this.value);" maxlength="5" property="clProporcionIva" name="clProporcionIva" value="${clProporcionIva}"/>%
                </td>
              </tr>
            </table>
		  </td>
        </tr>
        
        <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
	          <c:set var="emp" value="true"></c:set>
	          <c:if test="${Cliente.clProporcionIva*100>0}">
	          	<c:set var="emp" value="false"></c:set>
	          </c:if>
			<table id="empresa" width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 15%' align="left" class="texto_negrita">Empresa</td>
                <td style='width: 40%' align="left">
					<html:select  disabled="${emp}" style="width: 160px" styleId="clIdEmpresa" property="clIdEmpresa" value="${Cliente.clIdEmpresa}">
						  <html:option value="">[Seleccionar]</html:option>
						  <html:option value="1">Pinepan S.L.</html:option>
						  <html:option value="2">Obrador</html:option>
					</html:select>
				</td>
				<td colspan="3" style='width: 45%'>&nbsp;</td>
              </tr>
            </table>
		  </td>
        </tr>
</table>

<script>
		setTimeout("devolverDatosMunicipio('clLocId','clProvId')",400);
		setTimeout("aplicarValor('${Cliente.clLocId}','clLocId')",800);
</script>
<html:hidden property="clId" styleId="clId" value="${Cliente.clId}"/>
<input type="hidden" id="existecif" name="existecif" value="0"/>
<!-- Fin Ficha Perfiles -->
