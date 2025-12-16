package model;

import view.AppView;

/**
 * Stanje mirovanja aplikacije.
 * Predstavlja stanje neposredno nakon pokretanja aplikacije i povezivanja na bazu, kada nije izabrana tabela za prikaz.
 * 
 * @author grupa4
 */

public class IdleState extends AbstractAppState {
	
	public IdleState(AppView appView) 
	{
		super(appView);
		// TODO Auto-generated constructor stub
		
		 statusbar.setRowMessage("Row");
		appView.getStatusBar().setStatusMessage("Idle");
		this.toolbar.setVisible(false);
		//this.toolbar.setButtonsEnabled(false);
		this.menubar.acceptItem.setEnabled(false);
		this.menubar.cancelItem.setEnabled(false);
		this.menubar.deleteItem.setEnabled(false);
		this.menubar.firstItem.setEnabled(false);
		this.menubar.lastItem.setEnabled(false);
		this.menubar.previousItem.setEnabled(false);
		this.menubar.nextItem.setEnabled(false);
		this.menubar.reportItem.setEnabled(true);
		this.menubar.editItem.setEnabled(false);
		this.menubar.newItem.setEnabled(false);
	
	}

	@Override
	public void loadData() {
		// TODO Auto-generated method stub
		
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
	protected void handleSpecificSelection() {
		this.form.disablePrimaryInputs();
		this.window.setAppState(new SelectionState(window));
		
	}

	@Override
	protected void handleSpecificSubmit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleCreate() {
		form.enableInputs();
		form.setVisible(true);
		
		window.setAppState(new CreationState(window));
	}
	

}
