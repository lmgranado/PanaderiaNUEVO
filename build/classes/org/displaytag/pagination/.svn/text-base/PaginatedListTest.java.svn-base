package org.displaytag.pagination;

import org.apache.commons.beanutils.BeanUtils;

import org.displaytag.actionform.ActionFormTest;
import org.displaytag.properties.SortOrderEnum;

import org.test.utiles.Escaper;
import org.test.utiles.Utilidades;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class PaginatedListTest implements IPaginatedListTest {
    
    /**
     * Determina si se desea que al acceder a la tabla por primera vez,
     * aparezca vacía, si ningún registro listado, de  manera que el usuario
     * deba rellenar los campos para realizar búsquedas.
     */
    private boolean empty;

    /** Nombre de la tabla que se establecerá en el DisplayTag. */
    private String tableName;

    /** Página actual. Comienza en 0. */
    private int index;

    /**
     * Número de elementos por página. (Número de filas que se muestran
     * en cada página).
     */
    private int pageSize;

    /**
     * Número total de elementos. (Número total de filas, las que se
     * muestran y las que no).
     */
    private int fullListSize;

    /**
     * Lista de elementos (filas) que se encuentran en la página actual
     * (index).
     */
    private List list;

    /**
     * Dirección de la ordenación, ascendente o descendente. Por
     * defecto, ascendente.
     */
    private SortOrderEnum sortDirection = SortOrderEnum.ASCENDING;

    /** Criterio para la ordenación. (Nombre del campo por el que se ordena). */
    private String sortCriterion;

    /** Http servlet request. */
    private HttpServletRequest request;

    /** Exportación. */
    private int export;

    /** Ordenación por criterios. */
    private String orderBy;

/**
     * Constructor por defecto.
     */
    protected PaginatedListTest() {
        // Vacío.
    }

/**
     * Constructor con algunos parámetros.
     *
     * @param empty Parámetro para mostrar vacía la tabla la primera vez.
     * @param tableName Nombre de la tabla.
     */
    protected PaginatedListTest(boolean empty, String tableName) {
        // Parámetros que no vienen del request.
        this.empty = empty;
        this.tableName = tableName;
    }

/**
     * Constructor utilizado cuando se accede a la tabla por primera vez,
     * que sirve para establecer los parámetros de la tabla por defecto.
     *
     * @param request HttpServletRequest.
     * @param empty Parámetro para mostrar vacía la tabla la primera vez.
     * @param tableName Nombre de la tabla.
     * @param sortDirection Dirección de ordenación.
     * @param sortCriterion Campo por el que se ordena.
     * @param pageSize Tamaño de página.
     */
    protected PaginatedListTest(HttpServletRequest request, boolean empty,
        String tableName, String sortDirection, String sortCriterion,
        int pageSize) {
        // Se establecen los parámetros por defecto.
        this.request = request;

        this.empty = empty;
        this.tableName = tableName;

        if (sortDirection.equals(IPaginatedListTest.ASC)) {
            this.sortDirection = SortOrderEnum.ASCENDING;
        }
        else {
            this.sortDirection = SortOrderEnum.DESCENDING;
        }

        this.sortCriterion = sortCriterion;
        this.pageSize = pageSize;

        // En el constructor, antes de llamar al método que devuelve la lista según el filtro,
        // el tamaño total de la lista y la lista de elementos que se muestran
        // son parámetros desconocidos, por lo que se inicializan a nulos.
        this.fullListSize = 0;
        this.list = null;
    }

    /**
     * <p>Devuelve el PaginatedListTest. Pueden darse tres casos:</p>
     *  <p>- Devuelve un PaginatedListTest nuevo.</p>
     *  <p>- Devuelve el PaginatedListTest que está en el request.</p>
     *  <p>- Devuelve el PaginatedListTest que está en sesión.</p>
     *
     * @param request HttpServletRequest.
     * @param empty Parámetro para mostrar vacía la tabla la primera vez.
     * @param tableName Nombre de la tabla.
     * @param sortDirection Dirección de ordenación.
     * @param sortCriterion Campo por el que se ordena.
     * @param pageSize Tamaño de página.
     *
     * @return paginatedList PaginatedListTest.
     */
    public static PaginatedListTest getPaginatedList(
        HttpServletRequest request, boolean empty, String tableName,
        String sortDirection, String sortCriterion, int pageSize) {
        PaginatedListTest paginatedList = new PaginatedListTest(empty,
            tableName);
        paginatedList.request = request;

        String page = null;
        String direction = null;
        String export = null;
        String sort = null;
        String pagesize = null;
        
        
        // Si está en el request.
        if(request!=null) {
            page = request.getParameter(IPaginatedListTest.PAGE);
            direction = request.getParameter(IPaginatedListTest.DIRECTION);
            export = request.getParameter(IPaginatedListTest.EXPORT);
            sort = request.getParameter(IPaginatedListTest.SORT);
            pagesize = request.getParameter(IPaginatedListTest.PAGESIZE);
            
        }

        if ((page != null) || (direction != null) || (export != null) 
            || (sort != null) || (pagesize != null)) {
            paginatedList.paginatedListRequest(request, sortDirection,
                sortCriterion, pageSize);
        }
        else {

            // Si hay que cogerlo de la sesión.
            if (request!=null
                    && (request.getAttribute(IPaginatedListTest.SESSION) != null)
                    && request.getAttribute(IPaginatedListTest.SESSION).equals("1")
                    && request.getSession().getAttribute(tableName) != null) {
                paginatedList = (PaginatedListTest) request.getSession()
                    .getAttribute(tableName);
                paginatedList.setRequest(request);
            }
            else {
                // Como no está en el request ni en la sesión, se crea un nuevo PaginatedListTest,
                // con los parámetros por defecto.
                paginatedList = new PaginatedListTest(request, empty,
                    tableName, sortDirection, sortCriterion, pageSize);
            }
        }

        return paginatedList;
    }

    /**
     * Método que devuelve el PaginatedList que se encuentra en el request.
     * Si algunos parámetros no están en el request, coge los valores por defecto.
     *
     * @param request HttpServletRequest.
     * @param sortDirectionReq Dirección de ordenado por defecto. 
     * @param sortCriterionReq Campo de ordenación por defecto.
     * @param pageSizeReq Tamaño de la página por defecto.
     */
    protected void paginatedListRequest(HttpServletRequest request,
        String sortDirectionReq, String sortCriterionReq, int pageSizeReq) {
        // Parámetros que pueden venir del request.
        String index = request.getParameter(IPaginatedListTest.PAGE);
        String orden = request.getParameter(IPaginatedListTest.DIRECTION);
        String export = request.getParameter(IPaginatedListTest.EXPORT);
        String sort = request.getParameter(IPaginatedListTest.SORT);
        String pageSize = request.getParameter(IPaginatedListTest.PAGESIZE);
       
        // Parámetros que puede que se deseen de sesión.
        if ((index != null) || (orden != null) || (export != null) 
                || (sort != null) || (pageSize != null)) {
 
        }else if ((request.getAttribute(IPaginatedListTest.SESSION) != null) 
                && request.getAttribute(IPaginatedListTest.SESSION).equals("1")) {
            
            PaginatedListTest paginatedListSes = (PaginatedListTest) request.getSession()
                .getAttribute(tableName);
            
            if (index == null) {
                int indexIncremented = paginatedListSes.getIndex() + 1;
                index = indexIncremented + "";
            }
            if (orden == null) {
                orden = paginatedListSes.getSortDir();
                
            }
            if (export == null) {
                export = paginatedListSes.getExport() + "";
            }
            if (sort == null) {
                sort = paginatedListSes.getSortCriterion();
            }
            if (pageSize == null) {
                pageSize = paginatedListSes.getPageSize() + "";
            }
        }

        this.request = request;

        if (index != null) {
            this.index = Integer.parseInt(index) - 1;
        }

        if (orden != null) {
            if (orden.equals(IPaginatedListTest.ASC)) {
                this.sortDirection = SortOrderEnum.ASCENDING;
            }
            else {
                this.sortDirection = SortOrderEnum.DESCENDING;
            }
        }
        else {
            if (sortDirectionReq.equals(IPaginatedListTest.ASC)) {
                this.sortDirection = SortOrderEnum.ASCENDING;
            }
            else {
                this.sortDirection = SortOrderEnum.DESCENDING;
            }
        }

        if (export != null) {
            this.export = Integer.parseInt(export);
        }

        if (sort != null) {
            this.sortCriterion = sort;
        }
        else {
            this.sortCriterion = sortCriterionReq;
        }

        if ((pageSize != null) && (pageSize != "")) {
            this.pageSize = Integer.parseInt(pageSize);
        }
        else {
            this.pageSize = pageSizeReq;
        }
    }

    /**
     * Método que establece dos valores importantes para la paginación.
     * [1] --> Lista de elementos que se van a mostrar en la página. 
     * [2] --> Tamaño total de la lista (Todos los elementos)
     *
     * @param request HttpServletRequest.
     * @param bean Bean.
     * @param business Bussines.
     * @param form Form.
     * @param method Method.
     */
    public void getResultList(HttpServletRequest request, Object bean,
        Object business, ActionFormTest form, String method) {
        try {
            this.request = request;

            // CASOS POSIBLES:
            //1.- Viene el filtro.
            String filtro = Escaper.skipNull((form.getFilters()) [0]);
            String buscar = Escaper.skipNull((form.getFilters()) [1]);

            if (filtro.equals("1") && request.getMethod().equals("POST")) {
                this.index = 0;
            }

            if (buscar.equals("1")) {
                // Se copian las propiedades al bean para que filtre según los campos dados.
                BeanUtils.copyProperties(bean, form);
            }


            if (((request.getParameter("session") != null) 
                && (request.getParameter("session").equals("1"))) 
                && ((request.getSession() != null) 
                && (request.getSession().getAttribute(form.getSessionFilter()) != null))) {
                
                form = (ActionFormTest) request.getSession()
                    .getAttribute(form.getSessionFilter());
                // Se copian las propiedades al bean para que filtre según los campos dados.
                BeanUtils.copyProperties(bean, form);
            }

            //}
            // 3.- Tanto si viene el filtro, como si no viene porque está en sesión,
            // como si no viene porque no hay, se invoca al método correpondiente de la 
            // capa de negocio.
            Method methodFound = Utilidades.encuentraMetodo(business, method);

            Object [] args = new Object[2];

            args [0] = bean;
            args [1] = this;

            // Llamada al método de la capa de negocio para que
            // establezca las variables que faltan del paginatedListTest:
            // fullListSize / list
            PaginatedListTest pagList = (PaginatedListTest) methodFound.invoke(business,
                args);
            this.fullListSize = pagList.fullListSize;
            this.list = pagList.list;
        }
        catch (Exception e) {
            throw new RuntimeException("Error en PaginatedListTest en el método getPaginatedList. " 
                    + e, e);
        }
    }

    /**
     * Getter.
     * @return fullListSize Número total de elementos.
     */
    public int getFullListSize() {
        return fullListSize;
    }

    /**
     * Setter.
     * @param fullListSize Número total de elementos.
     */
    public void setFullListSize(int fullListSize) {
        this.fullListSize = fullListSize;
    }

    /**
     * Getter.
     * @return index Página actual (empieza en 0).
     */
    public int getIndex() {
        return index;
    }

    /**
     * Setter.
     * @param index Página actual (empieza en 0).
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Getter.
     * @return list Lista de elementos que se muestran.
     */
    public List getList() {
        return list;
    }

    /**
     * Setter.
     * @param list Lista de elementos que se muestran.
     */
    public void setList(List list) {
        this.list = list;
    }

    /**
     * Getter.
     * @return pageSize Número de elementos por página.
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Setter.
     * @param pageSize Número de elementos por página.
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Getter.
     * @return request HttpServletRequest.
     */
    public HttpServletRequest getRequest() {
        return request;
    }

    /**
     * Setter.
     * @param request HttpServletRequest.
     */
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * Getter.
     * @return empty boolean que indica si se muestra vacía
     * la lista la primera vez.
     */
    public boolean isEmpty() {
        return empty;
    }

    /**
     * Setter.
     * @param empty boolean que indica si se muestra vacía
     * la lista la primera vez.
     */
    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    /**
     * Getter.
     * @return sortCriterion Campo por el que se ordena.
     */
    public String getSortCriterion() {
        return sortCriterion;
    }

    /**
     * Setter.
     * @param sortCriterion Campo por el que se ordena.
     */
    public void setSortCriterion(String sortCriterion) {
        this.sortCriterion = sortCriterion;
    }

    /**
     * Getter.
     * @return sortDirection La dirección de ordenado.
     */
    public SortOrderEnum getSortDirection() {
        return sortDirection;
    }

    /**
     * Setter.
     * @param sortDirection La dirección de ordenado.
     */
    public void setSortDirection(SortOrderEnum sortDirection) {
        this.sortDirection = sortDirection;
    }

    /**
     * Getter.
     * @return export Parámetro de exportación.
     */
    public int getExport() {
        return export;
    }

    /**
     * Setter.
     * @param export Parámetro de exportación.
     */
    public void setExport(int export) {
        this.export = export;
    }
    
    /**
     * Getter.
     * @return orderBy String.
     */
    public String getOrderBy() {
        return orderBy;
    }

    /**
     * Setter.
     * @param orderBy String.
     */
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    /**
     * Getter.
     * @return tableName Nombre de la tabla.
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * Setter.
     * @param tableName Nombre de la tabla.
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    
    /**
     * Obtiene el número de elementos por página. A excepción de ser la
     * última página, debe ser igual que pageSize.
     *
     * @return Número de filas.
     */
    public int getObjectsPerPage() {
        return this.pageSize;
    }

    /**
     * Obtiene el número de página actual empezando por 1.
     *
     * @return index + 1
     */
    public int getPageNumber() {
        return this.index + 1;
    }

    /**
     * Returns an ID for the search used to get the list. It may be
     * null. Such an ID can be necessary if the full list is cached, in a way
     * or another (in the session, in the business tier, or anywhere else), to
     * be able to retrieve the full list from the cache.
     *
     * @return the search ID
     */
    public String getSearchId() {
        // Not implemented for now.
        //This is required, if we want the ID to be included in the paginated purpose.
        return null;
    }

    /**
     * Adds the order by parameter to a query. If there is already an
     * order by, <b>prepends</b> the current order to the one included in the
     * query
     *
     * @param query DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String addOrderBy(String query) {
        // Not implemented for now.
        return "";
    }

    /**
     * Locates the "order by" clause, if there is one.
     *
     * @return The position of the first character after the "order by" clause
     *         if there is one, -1 if not.
     */
    public String getSqlSortDirection() {
        return SortOrderEnum.DESCENDING.equals(this.sortDirection) ? "desc"
                                                                   : "asc";
    }
    
    /**
     * @return sort La dirección de ordenado.
     */
    public String getSortDir() {
        
        String sort = "";
        if (sortDirection.getName().equals("ascending")) {
            sort = "asc";
        }
        else {
            sort = "desc";
        }
        return sort;
    }
}
