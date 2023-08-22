package utils;



import java.io.IOException;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;

import javax.naming.NamingException;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDriver;
import org.apache.commons.pool.impl.GenericObjectPool;

import panaderia.utilidades.RecuperaProperties;



class MonitorConexiones extends Thread {
	
	  private long milisegundos;
	  private GenericObjectPool pool;
  
	  public MonitorConexiones( long segundos, GenericObjectPool pool ) {
	    this.milisegundos   = segundos*1000;
	    this.pool           = pool;
	  }
  
	  public void run(){
	    for(;;) 
	    {
	      try{ sleep(milisegundos); }
	      catch (InterruptedException e){ 
	    	  System.out.println("Error cerrar pool run"); 
	    	  }
	      
	      pool.clear(); 
	    }
	  }
  
	  public int conexionesActivas(){
		  return pool.getNumActive();
		  
	  }
	  
	  public int conexionesLibres(){
		  return pool.getNumIdle();
		  
	  }
	  
	  public int conexionesMax(){
		  return pool.getMaxActive();
	  }
}

public class SQLManager {

	  static private MonitorConexiones monitor = null;
	  private  static String USER = "";
	  private static String PASSWORD = "";
	  private static String DRIVER = "";
	  private static String URL = ""; 
	  private static long tiempoEspera = 0;
	  
	  private Connection con;
	  private Statement stmt;
	  private PreparedStatement pstmt;
	  private ResultSet rs;
	  private CallableStatement cs; 
	  private static boolean esInterna = false;

	  
	  private static final int tipo_String = 1;
	  private static final int tipo_Int = 2;
	  private static final int tipo_Double = 3;
	  private static final int tipo_Float = 4;
	  private static final int tipo_Date = 5;
	  
	private static String contexto = "PanaderiaNUEVO"; 
	  
	    
	 static {  	
		 int maxConections=20;
		  try{
			  RecuperaProperties rp = new RecuperaProperties("conexion");
		      URL = rp.getValue("URL");
		      PASSWORD = rp.getValue("PASSWORD");
		      USER = rp.getValue("ADM");
		      tiempoEspera =Long.parseLong(rp.getValue("TIEMPOESPERA"));
		      maxConections=Integer.parseInt(rp.getValue("MAXCONECTIONS"));
		      
		      
		      DriverManager.registerDriver(new org.gjt.mm.mysql.Driver());
	 	} catch( SQLException e ) {
	 		System.err.println("No establecido el driver de oracle."); 		
	 	}
	 	catch (Exception e) {
		   	System.out.println(e);
	 	} 
	 	catch (Throwable ex){
	 		System.out.println(ex);
	 	}
	 	 
	     GenericObjectPool connectionPool = new GenericObjectPool( null );
	     connectionPool.setMaxActive( maxConections );
	     ConnectionFactory connectionFactory = new DriverManagerConnectionFactory( URL, USER, PASSWORD );
	     new PoolableConnectionFactory( connectionFactory, connectionPool, null, null, false, true );
	     PoolingDriver driver = new PoolingDriver();
	     driver.registerPool( contexto, connectionPool );
	     
	     
	     monitor = new MonitorConexiones( tiempoEspera, connectionPool );
	     monitor.start();
	 }


	private static Connection ObtenerConexionBD() throws NamingException, SQLException, IOException {
		
		Connection con = DriverManager.getConnection( "jdbc:apache:commons:dbcp:"+contexto );
		
		return con;
	}  

	
	public void cerrarBD(){
		try {            	
	    	stmt.close();                       
	        con.close();
	    }catch (Exception e) {
	    	System.out.println("[SQLManager] cerrado: "+e);
	    }
	    
	    try {          	
	    		 con.close();
	    }catch (Exception e) {
	    	
	    } 
	}


	
	
	
	public SQLManager(){
		try	{
			
			
				
			
				con = ObtenerConexionBD();
				stmt = con.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );
				esInterna = false;
		}
		catch (Exception e)	{
			System.out.println("[SQLManager] Error al obtener la conexion.");
			e.printStackTrace();
		}	
	}
	
	public SQLManager( Connection cone ){
		try	{
			
				con = cone;
				stmt = con.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );
				esInterna = true;
		}
		catch (Exception e)	{
			System.out.println("[SQLManager] Error al obtener la conexion.");
			e.printStackTrace();
		}	
	}

	public Connection getConnection(){
		return con;
	}
	
	
	
	public void prepareStatement( String query )	{
		try	{
			pstmt = con.prepareStatement( query );
		}
		catch (Exception e)	{
			System.out.println("[SQLManager] Error consulta: "+query);
			e.printStackTrace();
		}
	}
	
	
	
	
	public void makeQueryPstmt( )	{
		try	{
			
			rs = pstmt.executeQuery( );
		}
		catch (Exception e)	{
			System.out.println("[SQLManager] Error makeQueryPstmt");
			e.printStackTrace();
		}
	}

	
	
	
	public boolean makeUpdatePstmt( ) {
		boolean ok = true;
		try	{
			
			pstmt.executeUpdate( );
		}
		catch (Exception e)	{
			ok = false;
			System.out.println("[SQLManager] Error makeUpdatePstmt");
			e.printStackTrace();
		}
		return (ok);
	}
	
	
	
	
	public void makeQuery( String query )	{
		try	{
			
			rs = stmt.executeQuery( query );
		}
		catch (Exception e)	{
			System.out.println("[SQLManager] Error consulta: "+query);
			e.printStackTrace();
		}
	}

	
	
	
	public boolean makeUpdate( String update ) {
		boolean ok = true;
		try	{
			
			stmt.executeUpdate( update );
		}
		catch (Exception e)	{
			ok = false;
			System.out.println("[SQLManager] Error update: "+update);
			e.printStackTrace();
		}
		return (ok);
	}
	
	
	
	
	public boolean prepareCall( String query ) {
		boolean ok = true;
		try	{
			
			cs = con.prepareCall( query );
		}
		catch (Exception e)	{
			ok = false;
			System.out.println("[SQLManager] Error makeCall: "+query);
			e.printStackTrace();
		}
		return (ok);
	}
	
	
	
	
	public boolean makeCall( ) {
		boolean ok = true;
		try	{
			
			cs.executeQuery( );
		}
		catch (Exception e)	{
			ok = false;
			System.out.println("[SQLManager] Error makeCall");
			e.printStackTrace();
		}
		return (ok);
	}
	
	
	
	
	public void makeBinaryUpdate( String update, byte[] fichero )	{
		try	{
			pstmt = con.prepareStatement(update);
			pstmt.setBytes(1, fichero);
			pstmt.executeUpdate();			
		}
		catch (Exception e)	{
			e.printStackTrace();
		}
	}	

	
	
	
	public void beforeFirst() {
		try	{
			rs.beforeFirst();
		}
		catch (Exception e)	{
			e.printStackTrace();
		}
	}

	
	
	
	public boolean nextResult() {
		boolean flag = false;
		try	{
			flag = rs.next();
		}
		catch (Exception e)	{
			e.printStackTrace();
		}
		return (flag);
	}
	
	
	
	
	public boolean desplazaResult( int dire ) {
		boolean flag = true;
		try	{
			if( dire > 0 ){
				rs.setFetchDirection(ResultSet.FETCH_FORWARD);			
				rs.absolute(dire);
			}			
		}
		catch (Exception e)	{
			System.out.println("[SQLMAnager] Dire: "+dire );
			e.printStackTrace();
			flag=false;
		}
		return (flag);
	}

	
	
	
	public String readResult( String columna ) {
		String result = null;
		try	{
			result = rs.getString( columna );
			
			
			if(result==null || result.equals("null"))
				result="";
		}
		catch (Exception e)	{
			System.out.println("[SQLManager] columna: "+columna);
			e.printStackTrace();
		}
		return (result);
	}
	
	public String readResult( int columna ) {
		String result = null;
		try	{
			result = rs.getString( columna );
			
			
			if(result==null || result.equals("null"))
				result="";
		}
		catch (Exception e)	{
			System.out.println("[SQLManager] columna: "+columna);
			e.printStackTrace();
		}
		return (result);
	}

	
	
	
	public int readInt( String columna ) {
		int result = 0;
		try	{
			result = rs.getInt( columna );
		}
		catch (Exception e)	{
			System.out.println("[SQLManager] columna: "+columna);
			e.printStackTrace();
		}
		return (result);
	}
	

	
	
	public double readDouble( String columna ) {
		double result = 0;
		try	{
			result = rs.getDouble( columna );
		}
		catch (Exception e)	{
			System.out.println("[SQLManager] columna double: "+columna);
			e.printStackTrace();
		}
		return (result);
	}
	
	
	
	
	public Date readDateOnly( String columna ) {
		Date result = null;
		try	{
			result = rs.getDate( columna );
		}
		catch (Exception e)	{
			System.out.println("[SQLManager] columna readDateOnly: "+columna);
			e.printStackTrace();
		}
		return ( result );
	}
	
	public Date readDateOnly( int columna ) {
		Date result = null;
		try	{
			result = rs.getDate( columna );
		}
		catch (Exception e)	{
			System.out.println("[SQLManager] columna readDateOnly: "+columna);
			e.printStackTrace();
		}
		return ( result );
	}
	
	public String readDate( String columna ) {
		String temp = null;
		String new_date = null;
		try {
			
			temp = rs.getDate( columna )+"";
			if (temp != null && !temp.equals("null")) {				
				if(temp.length()>10)
					temp=temp.substring(0,10);
				
				
				StringTokenizer date_st = new StringTokenizer(temp, "-");
				
				String year = date_st.nextToken();
	    	
				if(year.length()>4)
					year=year.substring(0,3);
				else if(year.length()==2)
					year="20"+year;
	    	
				
				String month = date_st.nextToken();
				if(month.length()==1)
					month="0"+month;
				
				String day = date_st.nextToken();
				if(day.length()==1)
					day="0"+day;
   			
				
				new_date = day + "-" + month + "-" + year;
			}
			else{
				new_date ="";
			}
		}
		catch (Exception e)	{
			new_date = "";				
			System.out.println("[SQLManager] Exc columna: "+columna+" temp "+temp);
			e.printStackTrace();
		}
		return (new_date);
	}
	
	
	public String readDate( int columna ){
		String temp = null;
		String new_date = null;
		try {
			
			temp = rs.getDate( columna )+"";
			if (temp != null && !temp.equals("null")) {
				if(temp.length()>10)
					temp=temp.substring(0,10);
				
				
				StringTokenizer date_st = new StringTokenizer(temp, "-");
				
				String year = date_st.nextToken();
				
				String month = date_st.nextToken();
				
				String day = date_st.nextToken();
				
				new_date = day + "-" + month + "-" + year;
			}
			else{
				new_date ="";
			}
		}
		catch (Exception e)	{
			new_date = "";
			System.out.println("[SQLManager] columna: "+columna);
			e.printStackTrace();
		}
		return (new_date);
	}
	
	
	
	
	public String readDateTime( String columna ) {
		String temp = null;
		String new_date = null;
		try	{
			
			temp = rs.getString( columna );
			if (temp != null)	{
				
				new_date = null;
				StringTokenizer date_st1 = new StringTokenizer(temp, " ");
				
				temp = date_st1.nextToken();
				StringTokenizer date_st2 = new StringTokenizer(temp, "-");
	    	
	    	String year = date_st2.nextToken();
	    	
	    	String month = date_st2.nextToken();
   			
   			String day = date_st2.nextToken();
   			
   			new_date = day + "-" + month + "-" + year;
   			
   			temp = date_st1.nextToken();
   			new_date = new_date + " (" + temp + ")";
   		}
		}
		catch (Exception e)	{
			new_date = null;
			e.printStackTrace();
		}
		return (new_date);
	}

	
	
	
	public Blob readBlob( String columna ) {
		Blob result = null;
		try	{
			result = rs.getBlob( columna );
		}
		catch (Exception e)	{
			e.printStackTrace();
		}
		return (result);
	}
	
	
	
	
	public void setParametro( int indice, int tipo, Object dato ){
		try{
			switch( tipo ){		
				case tipo_String:
					pstmt.setString( indice, (String) dato );
					break;
					
				case tipo_Int:
					pstmt.setInt( indice, Integer.parseInt( dato.toString() ) );
					break;
					
				case tipo_Float:
					pstmt.setFloat( indice, Float.parseFloat( dato.toString() ) );
					break;
					
				case tipo_Double:
					pstmt.setDouble( indice, Double.parseDouble( dato.toString() ) );
					break;
					
				case tipo_Date:
					pstmt.setDate( indice, Date.valueOf( dato.toString() ) );
					break;
			}
		}catch (Exception e) {
			System.out.println("[SQLManager] setParametro: Error parseo del tipo "+tipo);
			e.printStackTrace();
		}
	}
	
	
	
	
	
	public void setInt( int indice, int dato ){
		try	{
			pstmt.setInt( indice, dato );
		}
		catch (Exception e)	{
			e.printStackTrace();
		}
	}
	
	
	
	
	public void setString( int indice, String dato ){
		try	{
			pstmt.setString( indice, dato );
		}
		catch (Exception e)	{
			e.printStackTrace();
		}
	}
	
	
	
	
	public void setDouble( int indice, double dato ){
		try	{
			pstmt.setDouble( indice, dato );
		}
		catch (Exception e)	{
			e.printStackTrace();
		}
	}
	
	
	
	
	public void setFloat( int indice, float dato ){
		try	{
			pstmt.setFloat( indice, dato );
		}
		catch (Exception e)	{
			e.printStackTrace();
		}
	}
	
	
	
	
	public void setDate( int indice, Date dato ){
		try	{
			pstmt.setDate( indice, dato );
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	public void setCallString( int indice, String dato ){
		try	{
			cs.setString( indice, dato );
		}
		catch (Exception e)	{
			e.printStackTrace();
		}
	}
	
	public void setCallInt( int indice, int dato ){
		try	{
			cs.setInt( indice, dato );
		}
		catch (Exception e)	{
			e.printStackTrace();
		}
	}
	
	public void registerOutParameter( int indice, int tipo ){
		try	{
			cs.registerOutParameter( indice, tipo );
		}
		catch (Exception e)	{
			e.printStackTrace();
		}
	}
	
	public int readCallInt( int indice ){
		int result = 0;
		try	{
			result = cs.getInt( indice );
		}
		catch (Exception e)	{
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean wasNull( ){
		boolean es = false;
		try	{
			es = cs.wasNull();
		}
		catch (Exception e)	{
			e.printStackTrace();
		}
		return es;
	}
	
	
	
	
	public void closeConnection() {
		try	{
			rs = null;
			if ( stmt != null )
				stmt.close();
			stmt = null;
			if ( pstmt != null )
				pstmt.close();
			pstmt = null;
			if ( con != null && !esInterna )
				con.close();
			con = null;
			if( cs != null)
				cs.close();
			cs = null;
		}
		catch (Exception e)	{
			e.printStackTrace();
		}
	}
	

	 
	  
	  private static Connection cone = null;
	  private static boolean conectadoInterna = false; 
	  
	  public void abrirConexionInterna(){
		  try{
			  System.out.println("[AccesoBD] *****Especial conexion: llamo internaaa");
			  cone = ObtenerConexionBD(); 
			  conectadoInterna = true;
		  } catch (Exception e) {
	      	System.out.println("[AccesoBD] Especial conexion: "+e);
	      }  
	  }
	  
	  public void cerrarConexionInterna(){
		  try {        	
	          cone.close();
	          conectadoInterna = false;
	      } catch (Exception e) {
	    	  System.out.println("[AccesoBD] Especial cerrar conexion: "+e);
	      }  
	  }
	  
	  public void commitConexionInterna(){
		  try {        	
	          cone.commit();
	         
	      } catch (Exception e) {
	    	  System.out.println("[AccesoBD] Especial commit conexion: "+e);
	      }  
	  }
	  
	
	
	
	  public int conexionesActivas(){
		  return monitor.conexionesLibres();
	  }
	  
	  public int conexionesMax(){
		  return monitor.conexionesMax();
	  }
	  
	  public int conexionesLibres(){
		  return monitor.conexionesLibres();
		  
	  }

	  public boolean isConectadoInterna() {
		  return conectadoInterna;
	  }

}
