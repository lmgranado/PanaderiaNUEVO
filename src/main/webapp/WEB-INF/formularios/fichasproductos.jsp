<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/c-1_0-rt.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<script type="text/javascript">
	vFormName = "frmDetalle";
	
	function doSubmit(){
		vForm = document.getElementById(vFormName);		
		document.getElementById('frmDetalle').action = 'doFichasProductosFormularioGrabar.do';
        _enviarDetalle('frmDetalle');		
	}
	
	function doAnadirDetalle(){
		vForm = document.getElementById(vFormName); 

		if(document.getElementById('comprodNombre').value==''){
			alert('Debe indicar un nombre');
			return;
		}else if(document.getElementById('comprodDescripcion').value==''){
			alert('Debe indicar una descripcion');
			return;
		}else if( formato_numero(document.getElementById('comprodPeso').value) == '' ){
			return;
		}else if( formato_numero(document.getElementById('comprodPeso').value) <= formato_numero(0)){
			alert('El peso debe ser mayor que 0');
			return;
		}else if(document.getElementById('comprodMedida').value==''){
			alert('Debe indicar una medida');
			return;
		}else{ 
			document.getElementById('frmDetalle').action='doComposicionProductosFormularioGrabar.do';
			document.getElementById('frmDetalle').submit();
		}
	}
	
	function doEliminarDetalle(){
		vForm = document.getElementById(vFormName);				
		var idDetalles = hayChecked(vForm);
		if( idDetalles == '-1' ){
			alert('Debe elegir al menos un compuesto para eliminar');
		}
		else{
			document.getElementById('frmDetalle').action = 'doComposicionProductosBorrar.do';
			_enviarDetalle('frmDetalle');
		}
	}
	
	/* Comprueba se hay algún paroducto seleccionado, devuelve -1 si no hay ninguno */
	function hayChecked(form){
		hayCheck = false;
		vForm = document.getElementById("frmDetalle");
		value = -1;
		if(vForm.checkList!= undefined){
			size = vForm.checkList.length;
			value = vForm.checkList.value;
			if (value != undefined)
				hayCheck = vForm.checkList.checked;
			if (size != undefined){
				i=0; 		
				while (i<size && !hayCheck){
					hayCheck = vForm.checkList[i].checked;
					value = vForm.checkList[i].value;
					i++;
				}		
			}
		}
		if( !hayCheck ){
			value=-1;
		}
		return value;
	}
</script>

<html:form styleId="frmDetalle" action="/doFichasProductosFormularioGrabar" method="post">
<table cellpadding="5px" style="background-color: white; border: 1px solid #E5E5E5; width: 100%">
	<tr>          
		  <td colspan="4" align="right">
		  	<span style='color: #FA7D3D; font-size: 12px; font-weight: bold;'>
				<c:if test="${!empty Ficha.fprodId}">
					Nombre de Ficha: ${Ficha.fprodNombreFicha}
				</c:if>	
			</span>
		  </td>
	</tr>
	<tr>
		<td colspan="7" style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
			<img src="img/play.gif"/>&nbsp;Datos Básicos de la Ficha<br>
			<hr style="color: #DFDEDE">
		</td>
	</tr>
		<tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 2%' align="left"></td>
                <td style='width: 40%' align="left"  class="texto_negrita">
                	Nombre de la Ficha<br> 
					<html:text size='91' maxlength="75"  property="fprodNombreFicha" name="fprodNombreFicha" value="${Ficha.fprodNombreFicha}"/>
				</td>
				<td style='width: 5%' align="left" ></td>
                <td style='width: 40%' align="left" class="texto_negrita">
	            </td>
              </tr>
            </table>
		  </td>
        </tr>	
		<tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 2%' align="left"></td>
                <td style='width: 40%' align="left"  class="texto_negrita">
                	Denominación Comercial<br> 
					<html:text size='91' maxlength="75"  property="fprodDenominacionComercial" name="fprodDenominacionComercial" value="${Ficha.fprodDenominacionComercial}"/>
				</td>
				<td style='width: 5%' align="left" ></td>
                <td style='width: 40%' align="left" class="texto_negrita">
                	Clasificación del producto según legislación vigente<br> 
					<html:text size='91' maxlength="75"  property="fprodClasificacionLegislacion" name="fprodClasificacionLegislacion" value="${Ficha.fprodClasificacionLegislacion}"/>
	            </td>
              </tr>
            </table>
		  </td>
        </tr>	
        
        <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 2%' align="left"></td>
                <td style='width: 40%' align="left"  class="texto_negrita">
                	Clasificación del producto según industria<br> 
					<html:text size='91' maxlength="75"  property="fprodClasificacionIndustria" name="fprodClasificacionIndustria" value="${Ficha.fprodClasificacionIndustria}"/>
				</td>
				<td style='width: 5%' align="left" ></td>
                <td style='width: 40%' align="left" class="texto_negrita">
                	Marca comercial sobre la que se elabora y envasa<br> 
					<html:text size='91' maxlength="75"  property="fprodMarcaComercial" name="fprodMarcaComercial" value="${Ficha.fprodMarcaComercial}"/>
	            </td>
              </tr>
            </table>
		  </td>
        </tr>	
        
        <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 2%' align="left"></td>
                <td style='width: 40%' align="left"  class="texto_negrita">
                	Procesado<br> 
					<html:textarea styleClass="textarea" rows="3"  cols="65" property="fprodProcesado" name="fprodProcesado" value="${Ficha.fprodProcesado}"/>
				</td>
				<td style='width: 5%' align="left" ></td>
                <td style='width: 40%' align="left" class="texto_negrita">
                	Formato de Presentación<br>
					<html:textarea styleClass="textarea" rows="3"  cols="65" property="fprodFormato" name="fprodFormato" value="${Ficha.fprodFormato}"/>
	            </td>
              </tr>
            </table>
		  </td>
        </tr>	
        
        <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 2%' align="left"></td>
                <td style='width: 40%' align="left"  class="texto_negrita">
                	Envasado (cuando el cliente es una colectividad)<br>
					<html:textarea styleClass="textarea" rows="3"  cols="65" property="fprodEnvasado" name="fprodEnvasado" value="${Ficha.fprodEnvasado}"/>
				</td>
				<td style='width: 5%' align="left" ></td>
                <td style='width: 40%' align="left" class="texto_negrita">
                	Etiquetado<br> 
					<html:textarea styleClass="textarea" rows="3"  cols="65" property="fprodEtiquetado" name="fprodEtiquetado" value="${Ficha.fprodEtiquetado}"/>
	            </td>
              </tr>
            </table>
		  </td>
        </tr>	
        
        <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 2%' align="left"></td>
                <td style='width: 40%' align="left"  class="texto_negrita">
                	Condiciones de almacenamiento<br>
					<html:textarea styleClass="textarea" rows="3"  cols="65" property="fprodCondicionAlmacenamiento" name="fprodCondicionAlmacenamiento" value="${Ficha.fprodCondicionAlmacenamiento}"/>
				</td>
				<td style='width: 5%' align="left" ></td>
                <td style='width: 40%' align="left" class="texto_negrita">
                	Condiciones de transporte<br>
					<html:textarea styleClass="textarea" rows="3"  cols="65" property="fprodCondicionTransporte" name="fprodCondicionTransporte" value="${Ficha.fprodCondicionTransporte}"/>
	            </td>
              </tr>
            </table>
		  </td>
        </tr>	
        
        <tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 2%' align="left"></td>
                <td style='width: 40%' align="left"  class="texto_negrita">
                	Destino final previsto para el producto<br>
					<html:textarea styleClass="textarea" rows="3"  cols="65" property="fprodDestinoProducto" name="fprodDestinoProducto" value="${Ficha.fprodDestinoProducto}"/>
				</td>
				<td style='width: 5%' align="left" ></td>
                <td style='width: 40%' align="left" class="texto_negrita">
                	Caracteristicas del número de lote (coincide con la fecha de fabricación F/F)<br>
					<html:textarea styleClass="textarea" rows="3"  cols="65" property="fprodCaracteristicaLote" name="fprodCaracteristicaLote" value="${Ficha.fprodCaracteristicaLote}"/>
	            </td>
              </tr>
            </table>
		  </td>
        </tr>	
    	
    	<tr> 
          <td align="center" colspan='4' style="padding-left: 20px;"> 
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="texto">
              <tr> 
                <td style='width: 2%' align="left"></td>
                <td style='width: 40%' align="left"  class="texto_negrita">
                	Consumo preferente (C/P)<br>
					<html:textarea styleClass="textarea" rows="3"  cols="65" property="fprodConsumoPreferente" name="fprodConsumoPreferente" value="${Ficha.fprodConsumoPreferente}"/>
				</td>
				<td style='width: 5%' align="left" ></td>
                <td style='width: 40%' align="left" class="texto_negrita">
                	Características del producto terminado (R.D. 1137/1984, y posteriores modificaciones)<br>
					<html:textarea styleClass="textarea" rows="3"  cols="65" property="fprodCaracteristicaProducto" name="fprodCaracteristicaProducto" value="${Ficha.fprodCaracteristicaProducto}"/>
	            </td>
              </tr>
            </table>
		  </td>
        </tr>		
        
        <tr ><td height="20px"></td></tr>  
        <c:if test="${!empty Ficha.fprodId}">
	        <tr>
			<td colspan="1" align=left style="font-family: Verdana; font-size: 11px; color: #2DACE4;padding: 5px 0px 0px 5px">
				<img src="img/play.gif"/>&nbsp;Composición cualitativa y cuantitativa<br>
				<hr style="color: #DFDEDE">
				<table width="40%">
					<tr>
						<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 10%;padding-left: 20px;">
								Nombre<br>
								<input size="30" id="comprodNombre" style="text-align: left;" name="comprodNombre" maxlength="64"  type="text" value="">
							</td>
							<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 10%">
								Descripción<br>
								<input size="40" id="comprodDescripcion" style="text-align: left;" name="comprodDescripcion" type="text" value="">
							</td>
							<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 10%">
								Peso<br>
								<input onkeypress='return aceptaNumerosReales(event);'  size="16" id="comprodPeso" style="text-align: right;" name="comprodPeso" maxlength="11"  type="text" value="">
							</td>
							<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 10%">
								Medida<br>
								<input size="16" id="comprodMedida" style="text-align: right;" name="comprodMedida" maxlength="32"  type="text" value="">
							</td>
								<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 5%">
									<a onclick="javascript: doAnadirDetalle();">
										<img src="img/add.png" title="anadir composicion"/>
									</a>
								</td>
								<td style="font-family: Verdana; font-size: 11px; color: #666666; width: 5%">
									<a onclick="javascript: doEliminarDetalle();"> 
										<img  src="img/remove.png" title="eliminar composicion"/>
									</a>
								</td>
						</tr>
					</table>	
			</td>
			</tr>
	        <tr>
				<td style="width: 60%;padding-left: 20px;" colspan="1">
					<table cellpadding="1" class="displaytag_tabla" cellspacing="1" style="margin-bottom: 20px">
					<thead>
							<tr class="${estilo}">		
								<th class="columna sortable">
									&nbsp;
								</th>					
								<th class="columna sortable">
									Nombre
								</th>							
								<th class="columna sortable">
									Descripción
								</th>
								<th class="columna sortable">
									Peso
								</th>
							</tr>
						</thead>
						<c:forEach items="${LISTACOMPOSICIONFICHA}" var="composicion">
							<tr class="${estilo}">
								<td class="columna" style="text-align: center;">
									<input type="checkbox" name="checkList" id="checkList" class="check" value="${composicion.comprodId}"/>
								</td>
								<td class="columna" style="text-align: left;">
									<c:if test="${empty composicion.comprodNombre}">&nbsp;</c:if>
									<c:if test="${!empty composicion.comprodNombre}">
										<c:out value="${composicion.comprodNombre}"/>
									</c:if>
								</td>
								<td class="columna" style="text-align: left;">
									<c:if test="${empty composicion.comprodDescripcion}">&nbsp;</c:if>
									<c:if test="${!empty composicion.comprodDescripcion}">
										<c:out value="${composicion.comprodDescripcion}"/>
									</c:if>
								</td>
								<td class="columna" style="text-align: right;">
									<c:if test="${empty composicion.comprodPeso}">&nbsp;</c:if>
									<c:if test="${!empty composicion.comprodPeso}">
										<fmt:formatNumber pattern="###,###.###" var="peso">${composicion.comprodPeso}</fmt:formatNumber>
										<c:out value="${peso}  ${composicion.comprodMedida}"/>
									</c:if>
								</td>
							</tr>						
						</c:forEach>
					</table>
				</td>
				<td width="40%" style="vertical-align: top;"></td>
			</tr>
		</c:if>
</table>

<html:hidden property="fprodId" styleId="fprodId" value="${Ficha.fprodId}"/>
</html:form> 
