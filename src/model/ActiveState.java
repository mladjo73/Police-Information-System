package model;

import java.util.ArrayList;
import java.util.List;

import view.AppView;

/**
 * Klasa koja opisuje stanje u aplikaciji kada je prikazana odabrana tabela,
 * a nije selektovan nijedan red tabele.
 * @author grupa4
 * 
 *
 */

public class ActiveState extends AbstractAppState
{
	public static String tableName = "";
	
	public ActiveState(AppView window, String string)
	{
		super(window);
		// TODO Auto-generated constructor stub
		if(string != "")
		{
			ActiveState.tableName = string;
		}
		
		 window.getStatusBar().setStatusMessage("Active");
		
		
		
		this.toolbar.setVisible(true);
		form.setVisible(false);
		
		this.toolbar.setButtonsInActiveState();
		this.menubar.acceptItem.setEnabled(false);
		this.menubar.cancelItem.setEnabled(false);
		this.menubar.deleteItem.setEnabled(false);
		this.menubar.firstItem.setEnabled(false);
		this.menubar.lastItem.setEnabled(false);
		this.menubar.previousItem.setEnabled(false);
		this.menubar.nextItem.setEnabled(false);
		this.menubar.reportItem.setEnabled(true);
		this.menubar.editItem.setEnabled(false);
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
		form.enableInputs();
		form.setVisible(true);
		//form.disableInputs();
		window.setAppState(new CreationState(window));
	}

	

	@Override
	protected void handleSpecificSelection()
	{
		int row = table.getSelectedRow();
		int cols = table.getColumnCount();
		if(row >= 0) {
			List<Object> rowData = new ArrayList<>();
			for(int i=0; i<cols; i++) {
				rowData.add(table.getValueAt(row, i));
			}
			form.clearAll();
			form.fillInputs(rowData);
			form.disableInputs();
			form.setVisible(true);
			
		}
		window.setAppState(new SelectionState(window));
	}

	@Override
	protected void handleSpecificSubmit()
	{
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

}
