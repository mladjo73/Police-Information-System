package model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.TreeElement.Column;
import view.AppView;
import view.CustomRowHeader;

/**
 * Klasa koja implementira stanje aplikacije pri izmjeni selektovanog reda tabele
 * @author grupa4
 * 
 *
 */
public class ModifyState extends AbstractAppState {

	public ModifyState(AppView window) {
		super(window);
		// TODO Auto-generated constructor stub
		
		statusbar.setStatusMessage("Modifying");
		/*statusbar.setRowMessage((table.getSelectedRow()+1)+"/"+table.getRowCount()); */
		
		this.menubar.acceptItem.setEnabled(true);
		this.menubar.cancelItem.setEnabled(true);
		this.menubar.deleteItem.setEnabled(true);
		this.menubar.firstItem.setEnabled(true);
		this.menubar.lastItem.setEnabled(true);
		this.menubar.previousItem.setEnabled(true);
		this.menubar.nextItem.setEnabled(true);
		this.menubar.reportItem.setEnabled(true);
		this.menubar.editItem.setEnabled(false);
		this.menubar.newItem.setEnabled(true);
		
		toolbar.setButtonsInModifyState();
	}

	@Override
	public void loadData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleNext() {
		// TODO Auto-generated method stub
		int row = table.getSelectedRow();
		row++;
		if(row >= table.getRowCount()) {
			row = 0;
		}
		table.setRowSelectionInterval(row, row);
	}

	@Override
	public void handleFirst() {
		// TODO Auto-generated method stub
		table.setRowSelectionInterval(0, 0);
		
	}

	@Override
	public void handlePrev() {
		// TODO Auto-generated method stub
		int row = table.getSelectedRow();
		row--;
		if(row<0)
			row=table.getRowCount()-1;
		table.setRowSelectionInterval(row, row);
	}

	@Override
	public void handleLast() {
		// TODO Auto-generated method stub
		table.setRowSelectionInterval(table.getRowCount()-1, table.getRowCount()-1);
	}

	@Override
	protected void handleSpecificSelection() {
		// TODO Auto-generated method stub
		//greska selection modify
		handleChange();
		form.disableInputs();
		form.revalidate();
		//statusbar.setRowMessage((table.getSelectedRow()+1)+"/"+table.getRowCount());
		
	}
	
	@Override
	public void handleCreate() {
		form.clearAll();
		form.enableInputs();
		table.getSelectionModel().clearSelection();
		form.setVisible(true);
		
		window.setAppState(new CreationState(window));
	}

	@Override
	protected void handleSpecificSubmit() {
		// TODO Auto-generated method stub
		
		boolean success = false;
		CustomTableModel tblModel = (CustomTableModel)table.getModel();
		success = tblModel.editRow(form.getValues());
		int row = table.getSelectedRow();
		if(success) {
			JOptionPane.showMessageDialog(null, "Uspjesna izmjena reda.");

			tblModel.getAllData();
			tblModel.fireTableDataChanged();
			table.setRowSelectionInterval(row, row);
			table.revalidate();
		}
		else {
			JOptionPane.showMessageDialog(null, "Greska prilikom izmjene reda.");

		}
		
	}
	
	@Override
	public void handleChange() {
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
			
		}
	}
	
	@Override
	public void handleDelete() {
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
			}
		}
	}
	
	@Override
	public void handleCancel() {
		form.clearAll();
		window.getMainTable().getSelectionModel().clearSelection();
		form.setVisible(false);
		
		window.setAppState(new ActiveState(window, ""));
	}

}

