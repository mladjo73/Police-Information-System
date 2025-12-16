package model;

import javax.swing.JOptionPane;

import view.AppView;
import view.CustomRowHeader;

/**
 * Klasa koja opisuje stanje aplikacije i ažurira korisnički interfejs
 * pri dodavanju novog reda u tabelu.
 * @author grupa4
 * 
 *
 */

public class CreationState extends AbstractAppState
{
	
	public CreationState(AppView window)
	{
		super(window);
		// TODO Auto-generated constructor stub
		
		
		form.disablePrimaryInputs();
		
		statusbar.setStatusMessage("Creating"); 
		toolbar.setVisible(true);
		toolbar.setButtonsInCreationState();
		this.menubar.acceptItem.setEnabled(true);
		this.menubar.cancelItem.setEnabled(true);
		this.menubar.deleteItem.setEnabled(false);
		this.menubar.firstItem.setEnabled(false);
		this.menubar.lastItem.setEnabled(false);
		this.menubar.previousItem.setEnabled(false);
		this.menubar.nextItem.setEnabled(false);
		this.menubar.reportItem.setEnabled(false);
		this.menubar.editItem.setEnabled(false);
		this.menubar.newItem.setEnabled(false);
		
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
	}

	
	@Override
	public void handleCancel()
	{
		// TODO Auto-generated method stub
		form.setVisible(false);
		form.clearAll();
		window.setAppState(new ActiveState(window, ""));
	}

	
	@Override
	protected void handleSpecificSelection()
	{
		// TODO Auto-generated method stub
		form.disablePrimaryInputs();
		window.setAppState(new SelectionState(window));
	}

	@Override
	protected void handleSpecificSubmit()
	{
		// TODO Auto-generated method stub
		boolean success = false;
		CustomTableModel tblModel = (CustomTableModel)table.getModel();
		success = tblModel.addRow(form.getValues());
		
		if(success) {
			JOptionPane.showMessageDialog(null, "Uspjesan unos.");
			form.clearAll();
			tblModel.getAllData();
			table.revalidate();
			
			CustomRowHeader rowHeader = window.getRowHeader();
	          
			 
            rowHeader.updateRowHeader(table); // Osviježi RowHeader
            rowHeader.revalidate();
            rowHeader.repaint();
		}
		else {
			JOptionPane.showMessageDialog(null, "Greska prilikom unosa.");

		}
	}

	@Override
	public void handleNext() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleFirst() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handlePrev() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleLast() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void handleChange() {
		
	}

}