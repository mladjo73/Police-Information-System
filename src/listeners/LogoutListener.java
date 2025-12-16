package listeners;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import controller.LoginController;
import view.*;

/**
 * Klasa koja implementira odjavu korisnika sa sistema.
 * @author grupa4
 * 
 *
 */

public class LogoutListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		 JFrame currentWindow = (JFrame) SwingUtilities.getWindowAncestor((JMenuItem) e.getSource());

	        // Zatvori trenutni prozor aplikacije
	        
	        
	        JPopupMenu menuItem = (JPopupMenu)((JMenuItem)e.getSource()).getParent();
	        AppView window = (AppView)SwingUtilities.getWindowAncestor((JMenuBar)((JMenuItem)menuItem.getInvoker()).getParent());
			window.dispose();
	        
	        // Ponovo pokreni proces prijave
	        LoginController.startLoginProcess("xml/sema.xml");
	
		
	}

}
