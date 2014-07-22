package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DbAbstract {
    private String url = "hartong.net", user = "hartong1_is", pass = "1Nc3ntr0", db = "hartong1_is";
    private int port = 3306, rstU;
    private ResultSet rst;
    protected Connection con;
    private Statement stmt;

    /*
     * definieren database door gebruik te maken van bovenstaande vars
     * 
     * @author rwijhe
     */

    public DbAbstract() {
	super();
	try {
	    Class.forName("com.mysql.jdbc.Driver");
	    this.url = "jdbc:mysql://" + url + ":" + port + "/" + db + "?user=" + user + "&password=" + pass + "&jdbcCompliantTruncation=false";

	} catch (ClassNotFoundException e) {
	    System.out.println("Driver kan niet geladen worden");
	    e.printStackTrace();

	}

    }

    /*
     * toevoegen van een record aan de database
     * 
     * @author rwijhe
     */

    public void addUpdateRecord(String query) {
    	System.out.println("addUpdateRecord:"+query);
	try {
	    makeConnection();
	    makeResultSetUpdate(query);

	} catch (Exception e) {
			System.out.println("fout: " + e);
	}
    }
    /*
     * connectie opzetten naar de database
     * 
     * @author rwijhe
     */

    public void makeConnection() {
	try {
	    con = DriverManager.getConnection(url);
	    stmt = con.createStatement();
	} catch (SQLException e) {
	    System.out.println("Verbinding kan niet gemaakt worden.");
	    e.printStackTrace();
	}
    }

    /*
     * resultSet uitvoeren voor een select query en returnen
     * 
     * @auhtor rwijhe
     */

    public ResultSet makeResultSet(String query) {
System.out.println(query);
	try {
	    rst = stmt.executeQuery(query);
	} catch (SQLException e) {

	    e.printStackTrace();
	    System.out.println("Error in makeResultSet= "+ query+"\n" + e);
	}
	return rst;

    }

    /*
     * resultSet voor een update query uitvoeren
     * 
     * @author rwijhe
     */

    public void makeResultSetUpdate(String query) {
    	System.out.println("resultset query :"+query);
	try {
	    rstU = stmt.executeUpdate(query);
	} catch (SQLException e) {
	    e.printStackTrace();
	}

    }

    /*
     * verbreken van de connectie en resultset sluiten
     * 
     * @author
     */
    public void closeConnectRst() {
	try {
	    rst.close();
	    stmt.close();
	    con.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	    System.out.println("sluiten" + e);
	}
    }
    
    public void closeConnection() {
    	try {
    	    stmt.close();
    	    con.close();
    	} catch (SQLException e) {
    	    e.printStackTrace();
    	    System.out.println("sluiten" + e);
    	}
        }

    /*
     * getters & setters vars
     * 
     * @author rwijhe
     */

    protected String getUrl() {
	return url;
    }

    protected void setUrl(String url) {
	this.url = url;
    }

    protected String getUser() {
	return user;
    }

    protected String getDb() {
	return db;
    }

    protected void setDb(String db) {
	this.db = db;
    }

    protected int getRstU() {
	return rstU;
    }

    protected void setRstU(int rstU) {
	this.rstU = rstU;
    }

    protected ResultSet getRst() {
	return rst;
    }

    protected void setRst(ResultSet rst) {
	this.rst = rst;
    }

    protected Connection getCon() {
	return con;
    }

    protected void setCon(Connection con) {
	this.con = con;
    }

    protected Statement getStmt() {
	return stmt;
    }

    protected void setStmt(Statement stmt) {
	this.stmt = stmt;
    }

    protected void setUser(String user) {
	this.user = user;
    }

    protected String getPass() {
	return pass;
    }

    protected void setPass(String pass) {
	this.pass = pass;
    }

    protected int getPort() {
	return port;
    }

    protected void setPort(int port) {
	this.port = port;
    }

}
