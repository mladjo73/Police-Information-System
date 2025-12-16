package model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.TableModel;

import view.AppView;
import view.FormPanel;
import view.Menubar;
import view.Statusbar;
import view.Table;
import view.Toolbar;

/**
 *  Apstraktna klasa koju nasljedjuju konkretna stanja aplikacije i koja definise zajednicko ponasanje za sva stanja aplikacije.
 * @author grupa4
 * 
 *
 */

public abstract class AbstractAppState implements ApplicationState
{
	protected AppView window;
	protected Table table;
	protected FormPanel form;
	protected Toolbar toolbar;
	protected Statusbar statusbar;
	protected Menubar menubar;
	
	public AbstractAppState(AppView window)
	{
		this.window = window;
		table = window.getMainTable();
		form = window.getFormPanel();
		menubar = window.getMenuBarComponent();
		toolbar = window.getToolbar();
		statusbar = window.getStatusBar();
	}
	
	/**
	 * Metoda za obradu dogadjaja selekcije reda u tabeli od strane korisnika u zavisnosti od stanja aplikacije
	 */
	protected abstract void handleSpecificSelection();
	/**
	 * Metoda za definisanje ponasanja Submit dugmeta u razlicitim stanjima aplikacije
	 */
	protected abstract void handleSpecificSubmit();
	

	@Override
	public void handleSelectionChanged(ListSelectionEvent e) {
		
		boolean selectionHappened = handleSelectionCommon(e);
		if(selectionHappened) {
			handleSpecificSelection();
		}
	}

	@Override
	public void handleCreate() {
		
	}

	@Override
	public void handleChange() {
			
	}

	@Override
	public void handleDelete() {
			
	}

	@Override
	public void handleCancel() {
		
	}

	@Override
	public void handleSubmit() {
		
		if(!form.validateInputs()) {
			JOptionPane.showMessageDialog(null, "Polja oznacena sa * su obavezna!");
		}
		else {
			handleSpecificSubmit();
		}
	}
	
	private boolean handleSelectionCommon(ListSelectionEvent e) {
		boolean selectionHappened = false;
		
		if(e.getValueIsAdjusting()) {
			return selectionHappened;
		}
		
		TableModel tblModel = window.getMainTable().getModel();
		
		ListSelectionModel lsm = (ListSelectionModel) e.getSource();
		
		if(!lsm.isSelectionEmpty()) {
			int selectedRow = lsm.getMinSelectionIndex();
			List<Object> row = new ArrayList<>();
			
			for(int i=0; i<tblModel.getColumnCount(); i++) {
				row.add(tblModel.getValueAt(selectedRow, i));
			}
			
			form.fillInputs(row);
			form.disableInputs();
			
			selectionHappened = true;
		}
		return selectionHappened;
	}
}