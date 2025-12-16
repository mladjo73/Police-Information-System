package model;

import javax.swing.event.ListSelectionEvent;

/**
 * Interfejs sa potpisima metoda koje će po potrebi implementirati konkretna stanja aplikacije.
 * @author grupa4
 * 
 *
 */

public interface ApplicationState
{
	public void loadData();
	void handleSelectionChanged(ListSelectionEvent e);
	void handleCreate();
	void handleChange();
	void handleDelete();
	void handleCancel();
	void handleSubmit();
	void handleNext();
	void handleFirst();
	void handlePrev();
	void handleLast();
}
