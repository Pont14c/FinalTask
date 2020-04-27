package ua.nure.ivanovv.SummaryTask4.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

/**
 * DB manager. 
 * 
 * @author V.Ivanov
 * 
 */
public final class DBManager {

	private static final Logger LOG = Logger.getLogger(DBManager.class);

	private static DBManager instance;

	public static synchronized DBManager getInstance() {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}


	private DBManager(){
	}

	/**
	 * Returns a DB connection from the Pool Connections. 
	 * 
	 */
	public Connection getConnection()  {
		Connection con = null;
		try {
			Context initContext = new InitialContext();
			DataSource ds = (DataSource)initContext.lookup("java:comp/env/jdbc/st4db");
			con = ds.getConnection();
		} catch (NamingException | SQLException ex) {
			LOG.error("Cannot obtain a connection from the pool", ex);			
		}
		return con;

	}
	
}
