package model;

import java.util.HashMap;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.TreeElement.Column;

public abstract class GeneralTableModel extends AbstractTableModel {
	

	private static final long serialVersionUID = 1L;
	
	protected List<Column> columns = null;  
	protected List<List<Object>> data = null; //lista redova tabele

	@Override
	public int getRowCount() {
		 return data.size();
	}

	@Override
	public int getColumnCount() {
		return columns.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		//return null;
		return data.get(rowIndex).get(columnIndex);
	}
	
	
	@Override
	public String getColumnName(int column) {
		return columns.get(column).getName();
	}
		
	public List<Column> getColumns() {
		return this.columns;
	}
	
	/**
	 *  Metoda koja vraca indeks kolone na osnovu njenog imena
	 * @param name
	 * @return
	 */
	
	public int getIndexOfColumn(String name) {
		for(int i = 0; i < columns.size(); i++) {
			if(columns.get(i).getName().equals(name)) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Vraća objekat kolone na osnovu indeksa.
	 * @param i Indeks kolone.
	 * @return Objekat kolone.
	 */
	public Column getColumn(int i) {
		return columns.get(i);
	}
	
	/**
	 * Metoda za pribavljanje podataka za tabelu
	 */
	public abstract void getAllData();
	public abstract boolean deleteRowById(Object id);
	public abstract boolean getRowById(Object id);
	public abstract boolean addRow(HashMap<String, Object> hashMap);
	public abstract boolean editRow(HashMap<String, Object> params);


}
