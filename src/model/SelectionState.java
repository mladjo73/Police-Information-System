package model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.TreeElement.Column;
import view.AppView;
import view.CustomRowHeader;

/**
 * Klasa koja opisuje stanje u aplikaciji kada je korisnik izabrao neki red iz tabele.
 * @author grupa4
 * 
 *
 */

public class SelectionState extends AbstractAppState
{

	public SelectionState(AppView window)
	{
		super(window);
		// TODO Auto-generated constructor stub
		statusbar.setStatusMessage("Selection");
		//statusbar.setRowMessage((table.getSelectedRow()+1)+"/"+table.getRowCount()); */
		
		form.setVisible(true);
		this.handleSpecificSelection();
		
		this.toolbar.setButtonsEnabled(true);
		this.menubar.acceptItem.setEnabled(true);
		this.menubar.cancelItem.setEnabled(true);
		this.menubar.deleteItem.setEnabled(true);
		this.menubar.firstItem.setEnabled(true);
		this.menubar.lastItem.setEnabled(true);
		this.menubar.previousItem.setEnabled(true);
		this.menubar.nextItem.setEnabled(true);
		this.menubar.reportItem.setEnabled(true);
		this.menubar.editItem.setEnabled(true);
		this.menubar.newItem.setEnabled(true); 
	}

	@Override
	public void loadData()
	{
		// TODO Auto-generated method stub

	}

	

	@Override
	public void handleCreate()
	{
		// TODO Auto-generated method stub
		form.clearAll();
		form.enableInputs();
		table.getSelectionModel().clearSelection();
		form.setVisible(true);
		
		window.setAppState(new CreationState(window));
	}

	@Override
	public void handleChange()
	{
		// TODO Auto-generated method stub
		int row = table.getSelectedRow();
		int cols = table.getColumnCount();
		if(row >= 0) {
			List<Object> rowData = new ArrayList<>();
			for(int i=0; i<cols; i++) {
				rowData.add(table.getValueAt(row, i));
			}
			form.clearAll();
			form.fillInputs(rowData);
			form.enableInputs();
			form.disablePrimaryInputs();
			form.setVisible(true);	
			
			//window.setAppState(new ModifyState(window));
			
		}
	}

	@Override
	public void handleDelete()
	{
		// TODO Auto-generated method stub
		GeneralTableModel tblModel = (GeneralTableModel) table.getModel();
		
		for(int i=0; i<tblModel.getColumnCount(); i++) {
			Column col = tblModel.getColumn(i);
			
			if(col.isPrimary()) {
				Object key = tblModel.getValueAt(table.getSelectedRow(), i);
				
				int result = JOptionPane.showConfirmDialog(window, "Da li zelite da obrisete selektovani red?", "Uklanjanje reda", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				
				if(result == 0) {
					boolean success = tblModel.deleteRowById(key);
					if(success) {
						form.clearAll();
						tblModel.getAllData();
						table.revalidate();
						
						CustomRowHeader rowHeader = window.getRowHeader();
			            rowHeader.updateRowHeader(table); // Osviježi RowHeader
			            rowHeader.revalidate();
			            rowHeader.repaint();
			            
						JOptionPane.showMessageDialog(window, "Red uspjesno obrisan");				

					
					}
					else {
						JOptionPane.showMessageDialog(window, "Brisanje neuspjesno.");

					}
				}
				break;
			}
		}
	}

	@Override
	public void handleCancel()
	{
		form.clearAll();
		window.getMainTable().getSelectionModel().clearSelection();
		form.setVisible(false);
		window.setAppState(new ActiveState(window, ""));
	}

	@Override
	public void handleSubmit()
	{
		// TODO Auto-generated method stub
		if(!form.validateInputs()) {
			JOptionPane.showMessageDialog(null, "Polja oznacena sa * su obavezna!");
		}
		else {
			handleSpecificSubmit();
		}
	}

	@Override
	protected void handleSpecificSelection()
	{ 
	
	//ovo mi je pravilo gresku kod prelaska stanja,pozove mi handleChange i predje u ModifyState
		 handleChange();
		form.disableInputs();
		form.revalidate(); 
		
		
		//statusbar.setRowMessage((table.getSelectedRow()+1)+"/"+table.getRowCount());
	}

	@Override
	protected void handleSpecificSubmit()
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void handleNext() {
		int red = table.getSelectedRow();
		red++;
		if(red>=table.getRowCount())
			red=0;
		table.setRowSelectionInterval(red, red);
		
	}

	@Override
	public void handleFirst() {
		table.setRowSelectionInterval(0, 0);
		
	}

	@Override
	public void handlePrev() {
		int red = table.getSelectedRow();
		red--;
		if(red<0)
			red=table.getRowCount()-1;
		table.setRowSelectionInterval(red, red);
		
		
	}

	@Override
	public void handleLast() {
		table.setRowSelectionInterval(table.getRowCount()-1, table.getRowCount()-1);
		
	}

}

