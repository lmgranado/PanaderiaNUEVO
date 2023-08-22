<%@ page isErrorPage = "true"%>
				
<% if (exception != null) { %>
	La excepción causante del error ha sido: 
        <% exception.printStackTrace(new java.io.PrintWriter(out)); 
        	System.out.println(exception);%>
<% }%> 