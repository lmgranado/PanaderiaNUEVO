package utils;

import java.util.ArrayList;

import org.displaytag.pagination.PaginatedListTest;
import org.displaytag.properties.SortOrderEnum;

import panaderia.struts.forms.EntidadBean;
import panaderia.utilidades.ClassUtil;


public class UtilesDAO {

	public static Object[] generaSqlListadoPaginado(String sql, PaginatedListTest listadoPaginado, EntidadBean objeto){
        
        String campo = listadoPaginado.getSortCriterion();
        SortOrderEnum direccion = listadoPaginado.getSortDirection();
        String strDireccion = " ASC";
        Object[] consulta = new Object[2];
        try{
	        
	        if(listadoPaginado.isEmpty()) {
	        	listadoPaginado.setEmpty(false);
	        	listadoPaginado.setList(new ArrayList());                
	        }
	        else {
	            
	            if (campo!=null && !campo.equals("")) {
	            	
	            	strDireccion = ClassUtil.buscaConstanteNueva( objeto.getClass(), campo + ",PRIMARY_KEY" , direccion.getName() );
	            }                
	            
	            sql = sql + strDireccion;
	            
	            
	            int primerElto = listadoPaginado.getPageSize() * (listadoPaginado.getIndex());
	            consulta = objeto.consultaAVectorReflexivaEspPaginacion(sql,listadoPaginado.getPageSize(),primerElto);
	
	        } 
        }catch(Exception e){
        	System.out.println(e);
        }

		return consulta;
	}	
}
