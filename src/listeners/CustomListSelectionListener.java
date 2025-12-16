package listeners;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.SelectionState;
import view.AppView;

/**
 * Klasa implementira ListSelectionListener interface za praćenje promjena izbora reda u tabeli.
 * 
 * @author grupa4
 */
public class CustomListSelectionListener implements ListSelectionListener {
	private AppView appView;
	public CustomListSelectionListener(AppView appView) {
		
		this.appView = appView;
	}
	@Override
	public void valueChanged(ListSelectionEvent e) {
		
		if(!e.getValueIsAdjusting()) {
			appView.getAppState().handleSelectionChanged(e);
			if(!(appView.getAppState() instanceof SelectionState)) {
				appView.setAppState(new SelectionState(appView));
			}
			
		}
	}
	
	

}
