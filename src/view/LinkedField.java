package view;

import java.awt.Component;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import databaseAccess.DbConnection;
import model.CustomTableModel;
import model.GeneralTableModel;
/**
 *Ova klasa predstavlja polje unosa koje je povezano sa drugom tabelom u bazi podataka, 
 *omogućavajući odabir vrijednosti iz te tabele putem dijaloga za selekciju, pružajući funkcionalnosti za dobijanje vrijednosti, 
 *postavljanje vrijednosti, kao i definisanje referentnog dugmeta i omogućavanje/onemogućavanje polja.
 *@author grupa4
 * 
 */
public class LinkedField extends JPanel implements InputField
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final InputField field;
	private final String refColumn;
	private final String refTable;
	private Table tableView;
	private JButton button;
	
	public LinkedField(InputField field, String refTable, String refColumn) {
		// TODO Auto-generated constructor stub
		this.field = field;
		this.refColumn = refColumn;
		this.refTable = refTable;
		
		DbConnection instance = DbConnection.GetInstance();
        Connection conn = instance.getConn();
	
		try {
			String queryString = "SELECT * FROM [" + refTable + "]";
			PreparedStatement statement = conn.prepareStatement(queryString);
			ResultSet resultSet = statement.executeQuery();
			//System.out.println("dobila sam " + resultSet + "rezultat" );
			CustomTableModel tblModel = new CustomTableModel(resultSet);
			//System.out.println("BROJ KOLONA U SELEKTORSKOM: " + tblModel.getColumnCount());
			//System.out.println("BROJ REDOVA U SELEKTORSKOM: " + tblModel.getRowCount());

			
			this.tableView = new Table(tblModel);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		button = new JButton("...");
		button.setPreferredSize(new Dimension(20, 20));
		
		setName(field.getName());
		//setValue(field.getValue());
		
		//button.addActionListener(e->{new SelectionDialog(this, tableView, refTable, refColumn);});
		button.addActionListener(e->{selectMatchingRow();}); 
		
		setReferenceBtn(button);
		add((Component) field);
	}
	
	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		if(field.getValue() == null) {
			return null;
		}
		
		try {
			
	        DbConnection instance = DbConnection.GetInstance();
	        Connection conn = instance.getConn();
			ResultSet pks;
			pks = conn.getMetaData().getPrimaryKeys(instance.getDatabase(), "pisg4", refTable);
			
			
			ResultSet fks = conn.getMetaData().getImportedKeys(instance.getDatabase(), "pisg4", refTable);
			boolean isFk = true;
			
			List<String> fkeys = new ArrayList<>();
			
			while(fks.next()) {
				fkeys.add(fks.getString("FKCOLUMN_NAME"));
			}
			
			String colName = "";
			while(pks.next() && isFk) {
				colName = pks.getString("COLUMN_NAME");
				isFk = false;
				for(int i=0; i<fkeys.size(); i++) {
					if(colName.equals(fkeys.get(i))); isFk = true;
				}
			}
			
			if(colName.equals("Drzava")) {
				colName="Oznaka";
			}
			 
			String query = "SELECT [" + colName + "] FROM [" + refTable + "] WHERE [" + refColumn + "]='" + field.getValue() + "'";
			System.out.println(query);
			PreparedStatement statement = conn.prepareStatement(query);
			ResultSet primaryKeyRs = statement.executeQuery();
			primaryKeyRs.next();
			return primaryKeyRs.getObject(colName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void setValue(Object object) {
		// TODO Auto-generated method stub
		field.setValue(object);
		//selectMatchingRow();
		
	}
	
	private void selectMatchingRow() {
		GeneralTableModel tableModel = tableView.getTableModel();
		int matchingColumn = tableModel.getIndexOfColumn(refColumn);

		for (int i = 0; i < tableModel.getRowCount(); i++) {
			Object fieldValue = tableModel.getValueAt(i, matchingColumn);
			
			if (field.getValue() == null) {
				new SelectionDialog(this,  tableView, refTable, refColumn, 0);
				tableView.setRowSelectionInterval(1, 1);
				return;
			} else if (field.getValue().equals(fieldValue)){
				new SelectionDialog(this, tableView, refTable, refColumn,i);
				tableView.setRowSelectionInterval(i, i);
				return;
			}
		}
	}

	@Override
	public void setReferenceBtn(Component btn) {
		// TODO Auto-generated method stun
		field.setReferenceBtn(btn);
		
	}

	@Override
	public boolean isPrimary() {
		// TODO Auto-generated method stub
		return field.isPrimary();
	}

	@Override
	public boolean isNullable() {
		// TODO Auto-generated method stub
		return field.isNullable();
	}
	@Override
	public String getName() {
		return field.getName();
	}

	@Override
	public void setEnabled(boolean enabled) {
		//this.field.setEnabled(enabled);
		this.button.setEnabled(enabled);
	}
}

