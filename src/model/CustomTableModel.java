package model;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.TreeElement.Column;
import model.TreeElement.TableHelper;

public class CustomTableModel extends GeneralTableModel {

	private static final long serialVersionUID = 1L;
	private StoredProceduresUtil stProcUtil;
	private TableHelper table;
	private ResultSet resultSet = null;
	
	/**
	 * Konstruktor koji kreira model tabele na osnovu zadate tabele ucitane iz XML seme.
	 * @param table Tabela iz modela stabla koja se koristi za kreiranje modela tabele.
	 */
	public CustomTableModel(TableHelper table) {
		// TODO Auto-generated constructor stub
		this.table = table;
		this.stProcUtil = new StoredProceduresUtil(table);
		resultSet = stProcUtil.read();
		
		setColumns();
		getAllData();
		
	}
	/**
	 * Konstruktor koji kreira model tabele na osnovu ResultSet-a. Koristi se kod ucitavanja tabela u selektorskom dijalogu.
	 * @param resultSet ResultSet koji se koristi za popunjavanje modela tabele.
	 */
	
	public CustomTableModel(ResultSet resultSet) {
		try {
			ResultSetMetaData rsmd = resultSet.getMetaData();
			
			this.columns = new ArrayList<>();
			
			for(int i=0; i<rsmd.getColumnCount(); i++) {
				Column column = new Column(rsmd.getColumnName(i+1));
				column.setSize(rsmd.getPrecision(i+1));
				this.columns.add(column);
				System.out.println(column.getName());
			}
			
			this.data = new ArrayList<>();
			while(resultSet.next()) {
				List<Object> row = new ArrayList<>();
				for(int i=1; i<=columns.size(); i++) {
					row.add(resultSet.getObject(i));
				}
				this.data.add(row);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public CustomTableModel() {
		this.table = null;
	}
	
	@Override
	public void getAllData() {
		
		resultSet = stProcUtil.read();

		try {			
			this.data = new ArrayList<>();
			while (resultSet.next()) {
				List<Object> row = new ArrayList<>();
				for(int i=1; i<=columns.size(); i++) {
					row.add(resultSet.getObject(i));
					System.out.println(resultSet.getObject(i));
				}
				this.data.add(row);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	private void setColumns() {
		this.columns = new ArrayList<>();
		for(int i=0; i<table.getAllElements().size(); i++) {
			this.columns.add(((Column) table.getElementAt(i)));
			
		}
		
		try {
			ResultSetMetaData rsmd = resultSet.getMetaData();
			

			for(int i=0; i<table.getAllElements().size(); i++) {
				this.columns.get(i).setType(rsmd.getColumnClassName(i+1));
				this.columns.get(i).setSize(rsmd.getPrecision(i+1));
				this.columns.get(i).setScale(rsmd.getScale(i+1));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean deleteRowById(Object id) {
		// TODO Auto-generated method stub	
		return stProcUtil.delete(id);
	}

	@Override
	public boolean getRowById(Object id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addRow(HashMap<String, Object> params) {
		StoredProceduresUtil proc = new StoredProceduresUtil(this.table);
		ResultSet rs = proc.insert(params);
		if(rs == null)
			return false;
		
		this.resultSet = proc.read();
		getAllData();
		return true;
	}

	@Override
	public boolean editRow(HashMap<String, Object> params) {
		// TODO Auto-generated method stub
		return stProcUtil.update(params);
	}
	public TableHelper getTable() {
		return table;
	}
	
	

}
