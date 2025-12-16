package model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.JOptionPane;

import com.microsoft.sqlserver.jdbc.SQLServerResultSet;

import databaseAccess.DbConnection;
import model.TreeElement.Column;
import model.TreeElement.TableHelper;

/**
 * Klasa za pozivanje CRUD stored procedura iz baze podataka.
 * 
 * @author grupa4
 */
public class StoredProceduresUtil {
	
	
	private TableHelper table;
    private DbConnection instance;
    private Connection conn;
	
	public StoredProceduresUtil(TableHelper table)
	{
		this.table = table;
		this.instance = DbConnection.GetInstance();
		this.conn = instance.getConn();
	}
	
	public ResultSet read()
	{
		return read(table.getRetrieveSProc());
	}
	
	public ResultSet read(String procedure) {
		try {
			return getCallableStatement(procedure).executeQuery();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	
	private CallableStatement getCallableStatement(String procedure) {
		// TODO Auto-generated method stub
		if(procedure == null || procedure.length() == 0) {
			JOptionPane.showMessageDialog(null, "Procedura nije pronadjena");
			return null;
		}
		try {
			return conn.prepareCall(procedure, SQLServerResultSet.TYPE_SCROLL_INSENSITIVE, SQLServerResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "Greska");
			return null;
		}
	}
	
public boolean delete(Object id) {
		
		CallableStatement callableStatement = getCallableStatement(table.getDeleteSProc());
		try {
			callableStatement.setObject(1, id);
			
			callableStatement.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

public boolean update(HashMap<String, Object> params) {
	CallableStatement callableStatement = getCallableStatement(table.getUpdateSProc());
	for(int i=0; i<table.getAllElements().size(); i++) {
		Column col = (Column) table.getElementAt(i);
		try {
			callableStatement.setObject(i+1, params.get(col.getName()));
			System.out.println("Parametar->"+params.get(col.getName()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	try {
		callableStatement.execute();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	}
	return true;
}

/**
 * Metoda za poziv Insert stored procedure.
 * 
 * @param params parametri za insert u formi HashMape gdje kljuc predstavlja ime kolone
 * @return ResultSet koji predstavlja novi red u tabeli
 */

public ResultSet insert(HashMap<String, Object> params) {
	
	CallableStatement callableStatement = getCallableStatement(table.getCreateSProc());
	int inc = 1;
	for(int i=0; i<table.getAllElements().size(); i++) {
		Column col = (Column) table.getElementAt(i);
		if(col.isPrimary() && i==0)
		{	
		inc=0; 
		}
		else {
	
			try {
				callableStatement.setObject(i + inc, params.get(col.getName()));
				System.out.println(params.get(col.getName()));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	try {
		return callableStatement.executeQuery();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	}
	
}
/**
 * Method to retrieve ResultSetMetaData from a stored procedure.
 * 
 * @param procedure The name of the stored procedure to call.
 * @return ResultSetMetaData of the result set returned by the stored procedure.
 */
public ResultSetMetaData getResultSetMetaData(String procedure) {
    try {
        CallableStatement callableStatement = getCallableStatement(procedure);
        ResultSet resultSet = callableStatement.executeQuery();
        return resultSet.getMetaData();
    } catch (SQLException e) {
        e.printStackTrace();
        return null;
    }
}
	

}
